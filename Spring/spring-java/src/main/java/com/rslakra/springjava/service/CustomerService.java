/**
 *
 */
package com.rslakra.springjava.service;

import com.rslakra.springjava.model.Customer;

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
