package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.Sewagetreatment;

@Mapper
public interface SewagetreatmentMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	int insert(Sewagetreatment record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	int insertSelective(Sewagetreatment record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	Sewagetreatment selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(Sewagetreatment record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sewagetreatment
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(Sewagetreatment record);
}