package com.sz.water.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sz.water.entity.Syslog;

@Mapper
public interface SyslogMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	int insert(Syslog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	int insertSelective(Syslog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	Syslog selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(Syslog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table syslog
	 *
	 * @mbg.generated
	 */
	int updateByPrimaryKey(Syslog record);
}