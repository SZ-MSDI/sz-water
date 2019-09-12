package com.sz.water.entity;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.loginName
     *
     * @mbg.generated
     */
    private String loginname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userName
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.sex
     *
     * @mbg.generated
     */
    private Integer sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.position
     *
     * @mbg.generated
     */
    private String position;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phoneNumber
     *
     * @mbg.generated
     */
    private String phonenumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.headPortrait
     *
     * @mbg.generated
     */
    private String headportrait;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.organization_id
     *
     * @mbg.generated
     */
    private Long organizationId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.loginName
     *
     * @return the value of user.loginName
     *
     * @mbg.generated
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.loginName
     *
     * @param loginname the value for user.loginName
     *
     * @mbg.generated
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userName
     *
     * @return the value of user.userName
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userName
     *
     * @param username the value for user.userName
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.sex
     *
     * @return the value of user.sex
     *
     * @mbg.generated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.sex
     *
     * @param sex the value for user.sex
     *
     * @mbg.generated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.position
     *
     * @return the value of user.position
     *
     * @mbg.generated
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.position
     *
     * @param position the value for user.position
     *
     * @mbg.generated
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phoneNumber
     *
     * @return the value of user.phoneNumber
     *
     * @mbg.generated
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phoneNumber
     *
     * @param phonenumber the value for user.phoneNumber
     *
     * @mbg.generated
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.headPortrait
     *
     * @return the value of user.headPortrait
     *
     * @mbg.generated
     */
    public String getHeadportrait() {
        return headportrait;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.headPortrait
     *
     * @param headportrait the value for user.headPortrait
     *
     * @mbg.generated
     */
    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait == null ? null : headportrait.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.status
     *
     * @return the value of user.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.status
     *
     * @param status the value for user.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.organization_id
     *
     * @return the value of user.organization_id
     *
     * @mbg.generated
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.organization_id
     *
     * @param organizationId the value for user.organization_id
     *
     * @mbg.generated
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}