package com.zte.zshop.params;

/**
 * Author:helloboy
 * Date:2020-11-06 8:26
 * Description:<描述>
 * 该对象用于封装需要查询的sysuser表中的值，表单中name提交过来的值需要注入到该对象中，属性名称需要和元素name的值相匹配
 */
public class SysuserParam
{

    private String name;

    private String loginName;

    private String phone;

    private Integer roleId;

    private Integer isValid;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Integer roleId)
    {
        this.roleId = roleId;
    }

    public Integer getIsValid()
    {
        return isValid;
    }

    public void setIsValid(Integer isValid)
    {
        this.isValid = isValid;
    }
}
