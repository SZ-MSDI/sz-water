package com.sz.water.entity;

import java.util.Date;

public class Basin {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basin.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basin.range
     *
     * @mbg.generated
     */
    private Object range;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basin.basinName
     *
     * @mbg.generated
     */
    private String basinname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basin.creatTime
     *
     * @mbg.generated
     */
    private Date creattime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basin.id
     *
     * @return the value of basin.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basin.id
     *
     * @param id the value for basin.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basin.range
     *
     * @return the value of basin.range
     *
     * @mbg.generated
     */
    public Object getRange() {
        return range;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basin.range
     *
     * @param range the value for basin.range
     *
     * @mbg.generated
     */
    public void setRange(Object range) {
        this.range = range;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basin.basinName
     *
     * @return the value of basin.basinName
     *
     * @mbg.generated
     */
    public String getBasinname() {
        return basinname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basin.basinName
     *
     * @param basinname the value for basin.basinName
     *
     * @mbg.generated
     */
    public void setBasinname(String basinname) {
        this.basinname = basinname == null ? null : basinname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basin.creatTime
     *
     * @return the value of basin.creatTime
     *
     * @mbg.generated
     */
    public Date getCreattime() {
        return creattime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basin.creatTime
     *
     * @param creattime the value for basin.creatTime
     *
     * @mbg.generated
     */
    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
}