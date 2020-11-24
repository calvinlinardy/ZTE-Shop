package com.zte.zshop.dao;

import com.zte.zshop.entity.ProductType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-23 8:48
 * Description:<描述>
 */
@Repository
public interface ProductTypeDao
{
    //查询所有产品类型
    public List<ProductType> selectAll();

    public ProductType selectByName(String productTypeName);

    public ProductType selectById(Integer id);

    public void insert(@Param("name") String name,@Param("status")Integer status);

    public void updateName(@Param("id") Integer id,@Param("name") String name);

    public void updateStatus(@Param("id") Integer id,@Param("status") Integer status);

    public void deleteById(Integer id);

    public List<ProductType> selectByStatus(Integer status);


}
