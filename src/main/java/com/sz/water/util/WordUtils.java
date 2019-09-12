/**
 * 
 */
package com.sz.water.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @Title: WordUtils
 * @Description: freemarker 生成 word模板工具类
 * @author 梁泽祥
 * @date 2019年5月17日
 */
public class WordUtils {

	private CustomXWPFDocument document;

	public CustomXWPFDocument getDocument() {
		return document;
	}

	public void setDocument(CustomXWPFDocument document) {
		this.document = document;
	}

	public WordUtils(InputStream inputStream) throws IOException {
		document = new CustomXWPFDocument(inputStream);
	}

	/**
	 * 将处理后的内容写入到输出流中
	 *
	 * @param outputStream
	 * @throws IOException
	 */
	public void write(OutputStream outputStream) throws IOException {
		document.write(outputStream);
	}

	/**
	 * 生成下载,仅支持docx文件格式模板
	 */
	public void replaceDocumentRestructure(Map<String, Object> dataMap, HttpServletResponse response, String title)
			throws IOException {
		// 替换数据
		replaceDocument(dataMap);
		ServletOutputStream out = null;

		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件名
			String fileName = title + ".doc";
			response.setHeader("Content-Disposition",
					"attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

			out = response.getOutputStream();
			getDocument().write(out);
		} finally {
			if (out != null)
				out.close();
		}

	}

	public void replaceDocument(Map<String, Object> dataMap) {

		if (!dataMap.containsKey("parametersMap")) {
			System.out.println("数据源错误--数据源(parametersMap)缺失");
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> parametersMap = (Map<String, Object>) dataMap.get("parametersMap");

		List<IBodyElement> bodyElements = document.getBodyElements();// 所有对象（段落+表格）
		int templateBodySize = bodyElements.size();// 标记模板文件（段落+表格）总个数

		int curT = 0;// 当前操作表格对象的索引
		int curP = 0;// 当前操作段落对象的索引
		for (int a = 0; a < templateBodySize; a++) {
			IBodyElement body = bodyElements.get(a);
			if (BodyElementType.TABLE.equals(body.getElementType())) {// 处理表格
				XWPFTable table = body.getBody().getTableArray(curT);

				List<XWPFTable> tables = body.getBody().getTables();
				table = tables.get(curT);
				if (table != null) {

					// 处理表格
					List<XWPFTableCell> tableCells = table.getRows().get(0).getTableCells();// 获取到模板表格第一行，用来判断表格类型
					String tableText = table.getText();// 表格中的所有文本

					if (tableText.indexOf("##{foreach") > -1) {
						// 查找到##{foreach标签，该表格需要处理循环
						if (tableCells.size() != 2 || tableCells.get(0).getText().indexOf("##{foreach") < 0
								|| tableCells.get(0).getText().trim().length() == 0) {
							System.out.println("文档中第" + (curT + 1) + "个表格模板错误,模板表格第一行需要设置2个单元格，"
									+ "第一个单元格存储表格类型(##{foreachTable}## 或者 ##{foreachTableRow}##)，第二个单元格定义数据源。");
							return;
						}

						String tableType = tableCells.get(0).getText();
						String dataSource = tableCells.get(1).getText();
						System.out.println("读取到数据源：" + dataSource);
						if (!dataMap.containsKey(dataSource)) {
							System.out.println("文档中第" + (curT + 1) + "个表格模板数据源缺失");
							return;
						}
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> tableDataList = (List<Map<String, Object>>) dataMap.get(dataSource);
						if ("##{foreachTable}##".equals(tableType)) {
							// System.out.println("循环生成表格");
							addTableInDocFooter(table, tableDataList, parametersMap, 1);

						} else if ("##{foreachTableRow}##".equals(tableType)) {
							// System.out.println("循环生成表格内部的行");
							addTableInDocFooter(table, tableDataList, parametersMap, 2);
						} else if ("##{foreachTableRowTable}##".equals(tableType)) {// 循环表格，并且表格内又循环生成行
							addTableInDocFooter(table, tableDataList, parametersMap, 4);
						} else if ("##{foreachTableTwoList}##".equals(tableType)) {
							addTableInDocFooter(table, tableDataList, parametersMap, 5);
						}

					} else if (tableText.indexOf("{") > -1) {
						// 没有查找到##{foreach标签，查找到了普通替换数据的{}标签，该表格只需要简单替换
						addTableInDocFooter(table, null, parametersMap, 3);
					} else {
						// 没有查找到任何标签，该表格是一个静态表格，仅需要复制一个即可。
						addTableInDocFooter(table, null, null, 0);
					}
					curT++;

				}
			} else if (BodyElementType.PARAGRAPH.equals(body.getElementType())) {// 处理段落
				// System.out.println("获取到段落");
				XWPFParagraph ph = body.getBody().getParagraphArray(curP);
				if (ph != null) {
					// htmlText = htmlText+readParagraphX(ph);
					addParagraphInDocFooter(ph, null, parametersMap, 0);

					curP++;
				}
			}

		}
		// 处理完毕模板，删除文本中的模板内容
		for (int a = 0; a < templateBodySize; a++) {
			document.removeBodyElement(0);
		}

	}

	/**
	 * 根据 模板表格 和 数据list 在word文档末尾生成表格
	 * 
	 * @param templateTable 模板表格
	 * @param list          循环数据集
	 * @param parametersMap 不循环数据集
	 * @param flag          (0为静态表格，1为表格整体循环，2为表格内部行循环，3为表格不循环仅简单替换标签即可，4)
	 *
	 */
	@SuppressWarnings("unchecked")
	public void addTableInDocFooter(XWPFTable templateTable, List<Map<String, Object>> list,
			Map<String, Object> parametersMap, int flag) {

		if (flag == 1) {// 表格整体循环
			for (Map<String, Object> map : list) {
				List<XWPFTableRow> templateTableRows = templateTable.getRows();// 获取模板表格所有行
				XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
				for (int i = 1; i < templateTableRows.size(); i++) {
					XWPFTableRow newCreateRow = newCreateTable.createRow();
					CopyTableRow(newCreateRow, templateTableRows.get(i));// 复制模板行文本和样式到新行
				}
				newCreateTable.removeRow(0);// 移除多出来的第一行
				document.createParagraph();// 添加回车换行
				replaceTable(newCreateTable, map);// 替换标签
			}

		} else if (flag == 2) {// 表格表格内部行循环
			XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
			List<XWPFTableRow> TempTableRows = templateTable.getRows();// 获取模板表格所有行
			int tagRowsIndex = 0;// 标签行indexs
			for (int i = 0, size = TempTableRows.size(); i < size; i++) {
				String rowText = TempTableRows.get(i).getCell(0).getText();// 获取到表格行的第一个单元格
				if (rowText.indexOf("##{foreachRows}##") > -1) {
					tagRowsIndex = i;
					break;
				}
			}

			if (tagRowsIndex == 0) {
				for (int i = 0, size = TempTableRows.size(); i < size; i++) {
					String rowText = TempTableRows.get(i).getCell(1).getText();// 获取到表格行的第一个单元格
					if (rowText.indexOf("##{foreachRows}##") > -1) {
						tagRowsIndex = i;
						break;
					}
				}
			}

			/* 复制模板行和标签行之前的行 */
			for (int i = 1; i < tagRowsIndex; i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, parametersMap);// 处理不循环标签的替换
			}

			/* 循环生成模板行 */
			XWPFTableRow tempRow = TempTableRows.get(tagRowsIndex + 1);// 获取到模板行
			for (int i = 0; i < list.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, tempRow);// 复制模板行
				replaceTableRow(newCreateRow, list.get(i));// 处理标签替换
			}

			/* 复制模板行和标签行之后的行 */
			for (int i = tagRowsIndex + 2; i < TempTableRows.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, parametersMap);// 处理不循环标签的替换
			}
			newCreateTable.removeRow(0);// 移除多出来的第一行
			document.createParagraph();// 添加回车换行

		} else if (flag == 5) {

			XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
			List<XWPFTableRow> TempTableRows = templateTable.getRows();// 获取模板表格所有行

			int fristTagRowsIndex = 0;// 标签行indexs
			int TwoTagRowIndex = 0;
			int TagRowsIndex1 = 0;
			int TagRowsIndex2 = 0;
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

			for (int i = 0, size = TempTableRows.size(); i < size; i++) {
				if (TempTableRows.get(i).getCell(1) == null) {
					continue;
				}
				String rowText = TempTableRows.get(i).getCell(1).getText();// 获取到表格行的第一个单元格
				if (rowText.indexOf("##{foreachRow1}##") > -1) {
					fristTagRowsIndex = i;
					String rowTextList = TempTableRows.get(i).getCell(2).getText();// 获取到表格行的第一个单元格
					list1 = (List<Map<String, Object>>) list.get(0).get(rowTextList);
				}
				if (rowText.indexOf("##{foreachRow2}##") > -1) {
					TwoTagRowIndex = i;
					String rowTextList = TempTableRows.get(i).getCell(2).getText();// 获取到表格行的第一个单元格
					list2 = (List<Map<String, Object>>) list.get(0).get(rowTextList);
				}
				if (rowText.indexOf("##{foreachRowf1}##") > -1) {
					TagRowsIndex1 = i;
				}
				if (rowText.indexOf("##{foreachRowf2}##") > -1) {
					TagRowsIndex2 = i;
				}
			}

			/* 复制模板行和标签行之前的行 */
			for (int i = 1; i < fristTagRowsIndex; i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, list.get(0));// 处理不循环标签的替换
			}

			/* 循环生成模板行 */
			XWPFTableRow tempRow = TempTableRows.get(TagRowsIndex1 + 1);// 获取到模板行
			for (int i = 0; i < list1.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, tempRow);// 复制模板行
				replaceTableRow(newCreateRow, list1.get(i));// 处理标签替换
			}

			/* 复制模板行和标签行之后的行 */
			for (int i = TagRowsIndex1 + 2; i < TwoTagRowIndex; i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, list.get(0));// 处理不循环标签的替换
			}

			/* 复制模板行和标签行之前的行 */
			for (int i = TwoTagRowIndex; i < TwoTagRowIndex; i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, list.get(0));// 处理不循环标签的替换
			}

			/* 循环生成模板行 */
			XWPFTableRow tempRow1 = TempTableRows.get(TagRowsIndex2 + 1);// 获取到模板行
			for (int i = 0; i < list2.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, tempRow1);// 复制模板行
				replaceTableRow(newCreateRow, list2.get(i));// 处理标签替换
			}

			/* 复制模板行和标签行之后的行 */
			for (int i = TagRowsIndex2 + 2; i < TempTableRows.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, TempTableRows.get(i));// 复制行
				replaceTableRow(newCreateRow, list.get(0));// 处理不循环标签的替换
			}

			newCreateTable.removeRow(0);// 移除多出来的第一行
			document.createParagraph();// 添加回车换行
		} else if (flag == 3) {
			// 表格不循环仅简单替换标签
			List<XWPFTableRow> templateTableRows = templateTable.getRows();// 获取模板表格所有行
			XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
			for (int i = 0; i < templateTableRows.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, templateTableRows.get(i));// 复制模板行文本和样式到新行
			}
			newCreateTable.removeRow(0);// 移除多出来的第一行
			document.createParagraph();// 添加回车换行

			replaceTable(newCreateTable, parametersMap);

		} else if (flag == 0) {
			List<XWPFTableRow> templateTableRows = templateTable.getRows();// 获取模板表格所有行
			XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
			for (int i = 0; i < templateTableRows.size(); i++) {
				XWPFTableRow newCreateRow = newCreateTable.createRow();
				CopyTableRow(newCreateRow, templateTableRows.get(i));// 复制模板行文本和样式到新行
			}
			newCreateTable.removeRow(0);// 移除多出来的第一行
			document.createParagraph();// 添加回车换行
		} else if (flag == 4) {

			for (Map<String, Object> map : list) {
				XWPFTable newCreateTable = document.createTable();// 创建新表格,默认一行一列
				List<XWPFTableRow> templateTableRows = templateTable.getRows();// 获取模板表格所有行
				int tagRowsIndex = 0;// 标签行indexs
				for (int i = 0, size = templateTableRows.size(); i < size; i++) {
					String rowText = templateTableRows.get(i).getCell(0).getText();// 获取到表格行的第一个单元格
					if (rowText.indexOf("##{foreachRows}##") > -1) {
						tagRowsIndex = i;
						break;
					}
				}

				/* 复制模板行和标签行之前的行 */
				for (int i = 1; i < tagRowsIndex; i++) {
					XWPFTableRow newCreateRow = newCreateTable.createRow();
					CopyTableRow(newCreateRow, templateTableRows.get(i));// 复制行
					replaceTableRow(newCreateRow, map);// 处理不循环标签的替换
				}

				// 第i个的第二个单元格循环此List List名
				String listText = templateTableRows.get(tagRowsIndex).getCell(1).getText();// 获取到表格行的第一个单元格
				List<Map<String, Object>> xunList = (List<Map<String, Object>>) map.get(listText);
				if (xunList != null) {
					/* 循环生成模板行 */
					XWPFTableRow tempRow = templateTableRows.get(tagRowsIndex + 1);// 获取到模板行
					for (int i = 0; i < xunList.size(); i++) {
						XWPFTableRow newCreateRow = newCreateTable.createRow();
						CopyTableRow(newCreateRow, tempRow);// 复制模板行
						replaceTableRow(newCreateRow, xunList.get(i));// 处理标签替换
					}
				}

				/* 复制模板行和标签行之后的行 */
				for (int i = tagRowsIndex + 2; i < templateTableRows.size(); i++) {
					XWPFTableRow newCreateRow = newCreateTable.createRow();
					CopyTableRow(newCreateRow, templateTableRows.get(i));// 复制行
					replaceTableRow(newCreateRow, map);// 处理不循环标签的替换
				}
				newCreateTable.removeRow(0);// 移除多出来的第一行
				XWPFParagraph page = document.createParagraph();// 添加回车换行
				int index = list.indexOf(map);
				if (index != list.size() - 1 && index != 0) {
					page.setPageBreak(true);
				}
			}
		}

	}

	/**
	 * 根据 模板段落 和 数据 在文档末尾生成段落
	 *
	 * @param templateParagraph 模板段落
	 * @param list              循环数据集
	 * @param parametersMap     不循环数据集
	 * @param flag              (0为不循环替换，1为循环替换)
	 *
	 */
	public void addParagraphInDocFooter(XWPFParagraph templateParagraph, List<Map<String, String>> list,
			Map<String, Object> parametersMap, int flag) {

		if (flag == 0) {
			XWPFParagraph createParagraph = document.createParagraph();
			// 设置段落样式
			createParagraph.getCTP().setPPr(templateParagraph.getCTP().getPPr());
			// 移除原始内容
			for (int pos = 0; pos < createParagraph.getRuns().size(); pos++) {
				createParagraph.removeRun(pos);
			}
			// 添加Run标签
			for (XWPFRun s : templateParagraph.getRuns()) {
				XWPFRun targetrun = createParagraph.createRun();
				CopyRun(targetrun, s);
			}

			replaceParagraph(createParagraph, parametersMap);

		} else if (flag == 1) {
			// 暂无实现
		}

	}

	/**
	 * 根据map替换段落元素内的{**}标签
	 * 
	 * @param xWPFParagraph
	 * @param parametersMap
	 *
	 */
	public void replaceParagraph(XWPFParagraph xWPFParagraph, Map<String, Object> parametersMap) {
		List<XWPFRun> runs = xWPFParagraph.getRuns();
		String xWPFParagraphText = xWPFParagraph.getText();
		String regEx = "\\{.+?\\}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(xWPFParagraphText);// 正则匹配字符串{****}

		if (matcher.find()) {
			// 查找到有标签才执行替换
			int beginRunIndex = xWPFParagraph.searchText("{", new PositionInParagraph()).getBeginRun();// 标签开始run位置
			int endRunIndex = xWPFParagraph.searchText("}", new PositionInParagraph()).getEndRun();// 结束标签
			StringBuffer key = new StringBuffer();

			if (beginRunIndex == endRunIndex) {
				// {**}在一个run标签内
				XWPFRun beginRun = runs.get(beginRunIndex);
				String beginRunText = beginRun.text();

				int beginIndex = beginRunText.indexOf("{");
				int endIndex = beginRunText.indexOf("}");
				int length = beginRunText.length();

				if (beginIndex == 0 && endIndex == length - 1) {
					// 该run标签只有{**}
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					key.append(beginRunText.substring(1, endIndex));

					Object ob = getValueBykeyObj(key.toString(), parametersMap);
					if (!(ob instanceof Map)) {
						insertNewRun.setText(getValueBykey(key.toString(), parametersMap));
					} else {
						Map<?, ?> pic = (Map<?, ?>) ob;
						System.out.println(pic);
						int width = Integer.parseInt(pic.get("width").toString());
						int height = Integer.parseInt(pic.get("height").toString());
						int picType = getPictureType(pic.get("type").toString());
						byte[] byteArray = (byte[]) pic.get("content");
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

						try {
							document.addPictureData(byteInputStream, picType);
							document.createPicture(document.getAllPictures().size() - 1, width, height, xWPFParagraph);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					xWPFParagraph.removeRun(beginRunIndex + 1);

				} else {
					// 该run标签为**{**}** 或者 **{**} 或者{**}**，替换key后，还需要加上原始key前后的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					key.append(beginRunText.substring(beginRunText.indexOf("{") + 1, beginRunText.indexOf("}")));
					String textString = beginRunText.substring(0, beginIndex)
							+ getValueBykey(key.toString(), parametersMap) + beginRunText.substring(endIndex + 1);

					Object ob = getValueBykeyObj(key.toString(), parametersMap);
					if (!(ob instanceof Map)) {
						insertNewRun.setText(textString);
					} else {
						Map<?, ?> pic = (Map<?, ?>) ob;

						int width = Integer.parseInt(pic.get("width").toString());
						int height = Integer.parseInt(pic.get("height").toString());
						int picType = getPictureType(pic.get("type").toString());
						byte[] byteArray = (byte[]) pic.get("content");
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

						try {
							document.addPictureData(byteInputStream, picType);
							document.createPicture(document.getAllPictures().size() - 1, width, height, xWPFParagraph);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					xWPFParagraph.removeRun(beginRunIndex + 1);
				}

			} else {
				// {**}被分成多个run

				// 先处理起始run标签,取得第一个{key}值
				XWPFRun beginRun = runs.get(beginRunIndex);
				String beginRunText = beginRun.text();
				int beginIndex = beginRunText.indexOf("{");
				if (beginRunText.length() > 1) {
					key.append(beginRunText.substring(beginIndex + 1));
				}
				ArrayList<Integer> removeRunList = new ArrayList<>();// 需要移除的run
				// 处理中间的run
				for (int i = beginRunIndex + 1; i < endRunIndex; i++) {
					XWPFRun run = runs.get(i);
					String runText = run.text();
					key.append(runText);
					removeRunList.add(i);
				}

				// 获取endRun中的key值
				XWPFRun endRun = runs.get(endRunIndex);
				String endRunText = endRun.text();
				int endIndex = endRunText.indexOf("}");
				// run中**}或者**}**
				if (endRunText.length() > 1 && endIndex != 0) {
					key.append(endRunText.substring(0, endIndex));
				}

				// *******************************************************************
				// 取得key值后替换标签

				// 先处理开始标签
				if (beginRunText.length() == 2) {
					// run标签内文本{
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本

					Object ob = getValueBykeyObj(key.toString(), parametersMap);
					if (!(ob instanceof Map)) {
						insertNewRun.setText(getValueBykey(key.toString(), parametersMap));
					} else {
						Map<?, ?> pic = (Map<?, ?>) ob;
						System.out.println(pic);
						int width = Integer.parseInt(pic.get("width").toString());
						int height = Integer.parseInt(pic.get("height").toString());
						int picType = getPictureType(pic.get("type").toString());
						byte[] byteArray = (byte[]) pic.get("content");
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

						try {
							document.addPictureData(byteInputStream, picType);
							document.createPicture(document.getAllPictures().size() - 1, width, height, xWPFParagraph);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					xWPFParagraph.removeRun(beginRunIndex + 1);// 移除原始的run
				} else {
					// 该run标签为**{**或者 {** ，替换key后，还需要加上原始key前的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());

					Object ob = getValueBykeyObj(key.toString(), parametersMap);
					if (!(ob instanceof Map)) {
						// 设置文本
						String textString = beginRunText.substring(0, beginRunText.indexOf("{"))
								+ getValueBykey(key.toString(), parametersMap);
						insertNewRun.setText(textString);
					} else if (ob instanceof Map) {

						Map<?, ?> pic = (Map<?, ?>) ob;
						int width = Integer.parseInt(pic.get("width").toString());
						int height = Integer.parseInt(pic.get("height").toString());
						int picType = getPictureType(pic.get("type").toString());
						byte[] byteArray = (byte[]) pic.get("content");
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

						try {

							document.addPictureData(byteInputStream, picType);
							document.createPicture(document.getAllPictures().size() - 1, width, height, xWPFParagraph);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					xWPFParagraph.removeRun(beginRunIndex + 1);// 移除原始的run
				}

				// 处理结束标签
				if (endRunText.length() == 1) {
					// run标签内文本只有}
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
					insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
					// 设置文本
					insertNewRun.setText("");
					xWPFParagraph.removeRun(endRunIndex + 1);// 移除原始的run

				} else {
					// 该run标签为**}**或者 }** 或者**}，替换key后，还需要加上原始key后的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
					try {
						insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
					} catch (Exception e) {
					}

					// 设置文本
					String textString = endRunText.substring(endRunText.indexOf("}") + 1);
					insertNewRun.setText(textString);
					xWPFParagraph.removeRun(endRunIndex + 1);// 移除原始的run
				}

				// 处理中间的run标签
				for (int i = 0; i < removeRunList.size(); i++) {
					XWPFRun xWPFRun = runs.get(removeRunList.get(i));// 原始run
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(removeRunList.get(i));
					insertNewRun.getCTR().setRPr(xWPFRun.getCTR().getRPr());
					insertNewRun.setText("");
					xWPFParagraph.removeRun(removeRunList.get(i) + 1);// 移除原始的run
				}

			} // 处理${**}被分成多个run

			replaceParagraph(xWPFParagraph, parametersMap);

		} // if 有标签

	}

	/**
	 * 复制表格行XWPFTableRow格式
	 *
	 * @param target 待修改格式的XWPFTableRow
	 * @param source 模板XWPFTableRow
	 */
	private void CopyTableRow(XWPFTableRow target, XWPFTableRow source) {

		int tempRowCellsize = source.getTableCells().size();// 模板行的列数
		for (int i = 0; i < tempRowCellsize - 1; i++) {
			target.addNewTableCell();// 为新添加的行添加与模板表格对应行行相同个数的单元格
		}
		// 复制样式
		target.getCtRow().setTrPr(source.getCtRow().getTrPr());
		// 复制单元格
		for (int i = 0; i < target.getTableCells().size(); i++) {
			copyTableCell(target.getCell(i), source.getCell(i));
		}
	}

	/**
	 * 复制单元格XWPFTableCell格式
	 *
	 * @param newTableCell      新创建的的单元格
	 * @param templateTableCell 模板单元格
	 *
	 */
	private void copyTableCell(XWPFTableCell newTableCell, XWPFTableCell templateTableCell) {
		// 列属性
		newTableCell.getCTTc().setTcPr(templateTableCell.getCTTc().getTcPr());
		// 删除目标 targetCell 所有文本段落
		for (int pos = 0; pos < newTableCell.getParagraphs().size(); pos++) {
			newTableCell.removeParagraph(pos);
		}
		// 添加新文本段落
		for (XWPFParagraph sp : templateTableCell.getParagraphs()) {
			XWPFParagraph targetP = newTableCell.addParagraph();
			copyParagraph(targetP, sp);
		}
	}

	/**
	 * 复制文本段落XWPFParagraph格式
	 *
	 * @param newParagraph      新创建的的段落
	 * @param templateParagraph 模板段落
	 *
	 */
	private void copyParagraph(XWPFParagraph newParagraph, XWPFParagraph templateParagraph) {
		// 设置段落样式
		newParagraph.getCTP().setPPr(templateParagraph.getCTP().getPPr());
		// 添加Run标签
		for (int pos = 0; pos < newParagraph.getRuns().size(); pos++) {
			newParagraph.removeRun(pos);

		}
		for (XWPFRun s : templateParagraph.getRuns()) {
			XWPFRun targetrun = newParagraph.createRun();
			CopyRun(targetrun, s);
		}

	}

	/**
	 * 复制文本节点run
	 * 
	 * @author Juveniless
	 * @date 2017年11月27日 下午3:47:17
	 * @param newRun      新创建的的文本节点
	 * @param templateRun 模板文本节点
	 *
	 */
	private void CopyRun(XWPFRun newRun, XWPFRun templateRun) {
		newRun.getCTR().setRPr(templateRun.getCTR().getRPr());
		// 设置文本
		newRun.setText(templateRun.text());

	}

	/**
	 * 根据参数parametersMap对表格的一行进行标签的替换
	 *
	 * @author Juveniless
	 * @date 2017年11月23日 下午2:09:24
	 * @param tableRow      表格行
	 * @param parametersMap 参数map
	 *
	 */
	public void replaceTableRow(XWPFTableRow tableRow, Map<String, Object> parametersMap) {

		List<XWPFTableCell> tableCells = tableRow.getTableCells();
		for (XWPFTableCell xWPFTableCell : tableCells) {
			List<XWPFParagraph> paragraphs = xWPFTableCell.getParagraphs();
			for (XWPFParagraph xwpfParagraph : paragraphs) {

				replaceParagraph(xwpfParagraph, parametersMap);
			}
		}

	}

	/**
	 * 根据map替换表格中的{key}标签
	 * 
	 * @author Juveniless
	 * @date 2017年12月4日 下午2:47:36
	 * @param xwpfTable
	 * @param parametersMap
	 *
	 */
	public void replaceTable(XWPFTable xwpfTable, Map<String, Object> parametersMap) {
		List<XWPFTableRow> rows = xwpfTable.getRows();
		for (XWPFTableRow xWPFTableRow : rows) {
			List<XWPFTableCell> tableCells = xWPFTableRow.getTableCells();
			for (XWPFTableCell xWPFTableCell : tableCells) {
				List<XWPFParagraph> paragraphs2 = xWPFTableCell.getParagraphs();
				for (XWPFParagraph xWPFParagraph : paragraphs2) {
					replaceParagraph(xWPFParagraph, parametersMap);
				}
			}
		}

	}

	private String getValueBykey(String key, Map<String, Object> map) {
		String returnValue = "";
		if (key != null) {
			try {
				returnValue = map.get(key) != null ? map.get(key).toString() : "";
			} catch (Exception e) {
				System.out.println("key:" + key + "***" + e);
				returnValue = "";
			}

		}
		return returnValue;
	}

	private Object getValueBykeyObj(String key, Map<String, Object> map) {
		Object returnValue = "";
		if (key != null) {
			try {
				returnValue = map.get(key);
			} catch (Exception e) {
				System.out.println("key:" + key + "***" + e);
				returnValue = "";
			}

		}
		return returnValue;
	}

	/**
	 * 将输入流中的数据写入字节数组
	 *
	 * @param in
	 * @return
	 */
	public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
		byte[] byteArray = null;
		try {
			int total = in.available();
			byteArray = new byte[total];
			in.read(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isClose) {
				try {
					in.close();
				} catch (Exception e2) {
					e2.getStackTrace();
				}
			}
		}
		return byteArray;
	}

	/**
	 * 根据图片类型，取得对应的图片类型代码
	 * 
	 * @param picType
	 * @return int
	 */
	private static int getPictureType(String picType) {
		int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
		if (picType != null) {
			if (picType.equalsIgnoreCase("png")) {
				res = CustomXWPFDocument.PICTURE_TYPE_PNG;
			} else if (picType.equalsIgnoreCase("dib")) {
				res = CustomXWPFDocument.PICTURE_TYPE_DIB;
			} else if (picType.equalsIgnoreCase("emf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_EMF;
			} else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
				res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
			} else if (picType.equalsIgnoreCase("wmf")) {
				res = CustomXWPFDocument.PICTURE_TYPE_WMF;
			}
		}
		return res;
	}

}
