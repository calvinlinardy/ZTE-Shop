package com.zte.zshop.service;

import com.zte.zshop.common.exception.LoginErrorException;
import com.zte.zshop.entity.Customer;

/**
 * Author:helloboy
 * Date:2020-11-12 14:52
 * Description:<描述>
 */
public interface CustomerService
{
    public Customer login(String loginName, String password)throws LoginErrorException;
}
