package com.sz.water.dao;

import com.sz.water.entity.CheckTask;

public interface CheckTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    int insert(CheckTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    int insertSelective(CheckTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    CheckTask selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CheckTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_task
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CheckTask record);
}