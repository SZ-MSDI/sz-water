package com.sz.water.entity;

import java.util.Date;

public class Syslog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.loginName
     *
     * @mbg.generated
     */
    private String loginname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.operationName
     *
     * @mbg.generated
     */
    private String operationname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.operationClass
     *
     * @mbg.generated
     */
    private String operationclass;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.operationAddress
     *
     * @mbg.generated
     */
    private String operationaddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.params
     *
     * @mbg.generated
     */
    private String params;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.ip
     *
     * @mbg.generated
     */
    private String ip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syslog.createTime
     *
     * @mbg.generated
     */
    private Date createtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.id
     *
     * @return the value of syslog.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.id
     *
     * @param id the value for syslog.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.loginName
     *
     * @return the value of syslog.loginName
     *
     * @mbg.generated
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.loginName
     *
     * @param loginname the value for syslog.loginName
     *
     * @mbg.generated
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.operationName
     *
     * @return the value of syslog.operationName
     *
     * @mbg.generated
     */
    public String getOperationname() {
        return operationname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.operationName
     *
     * @param operationname the value for syslog.operationName
     *
     * @mbg.generated
     */
    public void setOperationname(String operationname) {
        this.operationname = operationname == null ? null : operationname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.operationClass
     *
     * @return the value of syslog.operationClass
     *
     * @mbg.generated
     */
    public String getOperationclass() {
        return operationclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.operationClass
     *
     * @param operationclass the value for syslog.operationClass
     *
     * @mbg.generated
     */
    public void setOperationclass(String operationclass) {
        this.operationclass = operationclass == null ? null : operationclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.operationAddress
     *
     * @return the value of syslog.operationAddress
     *
     * @mbg.generated
     */
    public String getOperationaddress() {
        return operationaddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.operationAddress
     *
     * @param operationaddress the value for syslog.operationAddress
     *
     * @mbg.generated
     */
    public void setOperationaddress(String operationaddress) {
        this.operationaddress = operationaddress == null ? null : operationaddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.params
     *
     * @return the value of syslog.params
     *
     * @mbg.generated
     */
    public String getParams() {
        return params;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.params
     *
     * @param params the value for syslog.params
     *
     * @mbg.generated
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.ip
     *
     * @return the value of syslog.ip
     *
     * @mbg.generated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.ip
     *
     * @param ip the value for syslog.ip
     *
     * @mbg.generated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syslog.createTime
     *
     * @return the value of syslog.createTime
     *
     * @mbg.generated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syslog.createTime
     *
     * @param createtime the value for syslog.createTime
     *
     * @mbg.generated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}