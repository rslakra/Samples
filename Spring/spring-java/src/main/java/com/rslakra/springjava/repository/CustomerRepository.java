/**
 *
 */
package com.rslakra.springjava.repository;

import com.rslakra.springjava.model.Customer;

import java.util.List;

/**
 * @author Rohtash Singh Lakra
 * @version 1.0.0
 *
 */
public interface CustomerRepository {

    /**
     * Returns the list of customers.
     *
     * @return
     */
    List<Customer> findCustomers();

}
