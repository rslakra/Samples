/**
 *
 */
package com.rslakra.springxml.service;

import com.rslakra.springxml.model.Customer;

import java.util.List;

/**
 * @author Rohtash Lakra
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
