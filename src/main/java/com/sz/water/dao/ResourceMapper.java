package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.Resource;

@Mapper
public interface ResourceMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	int insert(Resource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	int insertSelective(Resource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	Resource selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(Resource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table resource
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(Resource record);
}