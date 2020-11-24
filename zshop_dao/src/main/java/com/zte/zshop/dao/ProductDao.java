package com.zte.zshop.dao;

import com.zte.zshop.entity.Product;
import com.zte.zshop.params.ProductParams;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-29 10:14
 * Description:<描述>
 */
public interface ProductDao
{

    public void insertProduct(Product product);

    public Product selectByName(String name);

    //public List<Product> selectAll();

    public Product selectById(Integer id);

    public void updateProduct(Product product);

    public void deleteById(Integer id);

    public List<Product> selectByEnable();

    public List<Product> selectByParams(ProductParams productParams);
}
