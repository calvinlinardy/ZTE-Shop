package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.exception.ProductTypeExistException;
import com.zte.zshop.entity.ProductType;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-23 10:17
 * Description:<描述>
 */
public interface ProductTypeService
{
    //查找所有商品信息
    public PageInfo<ProductType> findAll(Integer page, Integer rows);


    /*
        添加商品类型
        判断该商品类型是否已经存在
            如果存在，抛出异常
            如果不存在，添加
     */
    public void add(String productTypeName) throws ProductTypeExistException;

    public ProductType findById(Integer id);

    public void modifyName(Integer id, String name) throws ProductTypeExistException;

    public void removeById(Integer id);

    public void modifyStatus(Integer id);


    //查找所有启用的商品类型
    public List<ProductType> findEnable(Integer status);

}
