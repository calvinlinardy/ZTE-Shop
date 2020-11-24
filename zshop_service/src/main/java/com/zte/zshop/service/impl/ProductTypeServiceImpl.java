package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.Constant;
import com.zte.zshop.common.exception.ProductTypeExistException;
import com.zte.zshop.dao.ProductTypeDao;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-23 10:18
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ProductTypeServiceImpl implements ProductTypeService
{
    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<ProductType> findAll(Integer page, Integer rows)
    {
        //设置分页,注意：该语句必须下载dao操作之前
        PageHelper.startPage(page,rows);
        List<ProductType> productTypes = productTypeDao.selectAll();
        PageInfo<ProductType> pageInfo  =new PageInfo<>(productTypes);
        //返回的是pageInfo对象，而非list,不仅仅获取记录列表，还要得到更多信息，如上一页，下一页等等，用于实现分页条功能

        return pageInfo;
    }

    @Override
     /*
        添加商品类型
        判断该商品类型是否已经存在
            如果存在，抛出异常
            如果不存在，添加
     */
    public void add(String productTypeName) throws ProductTypeExistException
    {
       ProductType productType= productTypeDao.selectByName(productTypeName);
       if(productType!=null){
           throw new ProductTypeExistException("商品类型已经存在");

       }
       productTypeDao.insert(productTypeName, Constant.PRODUCT_TYPE_ENABLE);

    }

    @Override
    public ProductType findById(Integer id)
    {
        return productTypeDao.selectById(id);
    }

    @Override
    public void modifyName(Integer id, String name)throws ProductTypeExistException
    {
        //如果该名称在数据库中已经存在，报错
        ProductType productType = productTypeDao.selectByName(name);
        if(productType!=null){
            throw new ProductTypeExistException("商品类型已经存在");
        }
        //否则修改名称
        productTypeDao.updateName(id,name);

    }

    @Override
    public void removeById(Integer id)
    {
        productTypeDao.deleteById(id);
    }

    @Override
    public void modifyStatus(Integer id)
    {
        ProductType productType = productTypeDao.selectById(id);
        Integer status = productType.getStatus();
        if(status==Constant.PRODUCT_TYPE_ENABLE){
            status=Constant.PRODUCT_TYPE_DISABLE;
        }
        else if(status==Constant.PRODUCT_TYPE_DISABLE){
            status=Constant.PRODUCT_TYPE_ENABLE;
        }
        //更新状态
        productTypeDao.updateStatus(id,status);

    }

    @Override
    public List<ProductType> findEnable(Integer status)
    {
        return productTypeDao.selectByStatus(status);
    }
}
