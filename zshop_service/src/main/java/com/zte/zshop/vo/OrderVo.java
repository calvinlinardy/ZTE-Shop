package com.zte.zshop.vo;

import java.util.Date;

/**
 * Author:helloboy
 * Date:2020-11-20 10:11
 * Description:<描述>
 *
 */
public class OrderVo
{
    private Integer id;

    private String no;

    private Integer customerId;

    private Double price;

    private Date createDate;



    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
}
