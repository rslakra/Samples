/**
 *
 */
package com.rslakra.springcore.service;

import com.rslakra.springcore.model.Customer;

import java.util.List;

/**
 * @author Rohtash Singh Lakra
 * @version 1.0.0
 *
 */
public interface CustomerService {

    /**
     * Returns the list of <code>Customer</code>.
     *
     * @return
     */
    List<Customer> findCustomers();

}
