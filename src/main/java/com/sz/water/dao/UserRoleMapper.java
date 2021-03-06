package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.UserRole;

@Mapper
public interface UserRoleMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	int insert(UserRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	int insertSelective(UserRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	UserRole selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(UserRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table user_role
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(UserRole record);
}