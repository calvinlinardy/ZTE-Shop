package com.zte.zshop.service.impl;

import com.zte.zshop.dao.OrderDao;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.entity.Order;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.vo.OrderVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * Author:helloboy
 * Date:2020-11-20 14:00
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService
{

    @Autowired
    private OrderDao orderDao;
    @Override
    public void addOrder(OrderVo orderVo)
    {
        try
        {
            Order order = new Order();
            PropertyUtils.copyProperties(order,orderVo);
            Customer customer=new Customer();
            customer.setId(orderVo.getCustomerId());
            order.setCustomer(customer);
            orderDao.insertOrder(order);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

    }
}
