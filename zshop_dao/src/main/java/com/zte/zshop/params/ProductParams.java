package com.zte.zshop.params;

/**
 * Author:helloboy
 * Date:2020-11-11 8:54
 * Description:<描述>
 * 该类用于封装查询产品条件
 */
public class ProductParams
{
    private String name;

    private Double minPrice;

    private Double maxPrice;

    private Integer productTypeId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(Double minPrice)
    {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice()
    {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public Integer getProductTypeId()
    {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId)
    {
        this.productTypeId = productTypeId;
    }
}
