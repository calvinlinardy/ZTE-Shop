package com.zte.zshop.cart;

import com.zte.zshop.entity.Product;

/**
 * Author:helloboy
 * Date:2020-11-13 10:45
 * Description:<描述>
 * 购物车明细对象
 * 用于封装存放在购物车中的产品条目信息
 */
public class ShoppingCartItem
{
    //产品对象
    private Product product;

    //产品数量
    private int quantity;

    public ShoppingCartItem()
    {
    }

    public ShoppingCartItem(Product product)
    {
        this.product = product;
        this.quantity=1;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    //获取该对象在购物车中的总价
    public double getItemMoney(){
        return product.getPrice()*this.quantity;
    }

    //商品数量+1
    public void increment(){
        this.quantity++;
    }


}
