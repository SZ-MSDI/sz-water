package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.User;

@Mapper
public interface UserMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	int insert(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	int insertSelective(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	User selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(User record);
}