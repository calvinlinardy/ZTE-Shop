package com.zte.zshop.entity;

import java.io.Serializable;

/**
 * Author:helloboy
 * Date:2020-10-23 8:38
 * Description:<描述>
 * 实体bean的标准写法
 * 1：序列化
 * 2：私有属性(对应表中的字段)
 * 3：get/set方法
 * 4：有参无参构造器
 * 5：toString(可选)
 */
public class ProductType implements Serializable
{
    private Integer id;

    private String name;

    private Integer status;

    public ProductType()
    {
    }

    public ProductType(Integer id, String name, Integer status)
    {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
