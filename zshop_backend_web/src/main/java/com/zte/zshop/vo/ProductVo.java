package com.zte.zshop.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Author:helloboy
 * Date:2020-10-29 14:08
 * Description:<描述>
 */
//该对象主要用于封装在表单传递的信息
public class ProductVo
{

    private Integer id;


    private String name;

    private Double price;

    private CommonsMultipartFile file;

    private Integer productTypeId;

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

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public CommonsMultipartFile getFile()
    {
        return file;
    }

    public void setFile(CommonsMultipartFile file)
    {
        this.file = file;
    }

    public Integer getProductTypeId()
    {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId)
    {
        this.productTypeId = productTypeId;
    }

    @Override
    public String toString()
    {
        return "ProductVo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", file=" + file +
                ", productTypeId=" + productTypeId +
                '}';
    }
}
