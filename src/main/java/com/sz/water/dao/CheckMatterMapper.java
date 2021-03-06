package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.CheckMatter;

@Mapper
public interface CheckMatterMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	int insert(CheckMatter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	int insertSelective(CheckMatter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	CheckMatter selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(CheckMatter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table check_matter
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(CheckMatter record);
}