/**
 *
 */
package com.rslakra.springcore.repository;

import com.rslakra.springcore.model.Customer;

import java.util.List;

/**
 * @author Rohtash Lakra
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
