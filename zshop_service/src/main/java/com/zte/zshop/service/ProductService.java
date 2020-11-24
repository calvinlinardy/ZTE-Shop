package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.params.ProductParams;
import org.apache.commons.fileupload.FileUploadException;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-29 10:11
 * Description:<描述>
 */
public interface ProductService
{

    public void add(ProductDto productDto) throws FileNotFoundException, FileUploadException;

    public boolean checkName(String name);

    public PageInfo<Product> findAll(Integer pageNum, int pageSize);

    public Product findById(Integer id);

    public void modifyProduct(ProductDto productDto) throws FileUploadException;

    public void removeProduct(Integer id);

    public void getImage(String path, OutputStream out);

    public List<Product> findByParams(ProductParams productParams);

    public boolean addToCart(int id, ShoppingCart sc);

    //public void removeItemFromShoppingCart(ShoppingCart sc, Integer id);

    public void modifyItemQuantity(ShoppingCart sc, int id, int quantity);

    public void clearShoppingCart(ShoppingCart sc);

    public void removeItemsFromShoppingCart(ShoppingCart sc, int[] ids);
}
