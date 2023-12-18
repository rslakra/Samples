/**
 *
 */
package com.rslakra.springxml.service;

import com.rslakra.springxml.model.Customer;
import com.rslakra.springxml.repository.CustomerRepository;

import java.util.List;

/**
 * @author Rohtash Singh Lakra
 * @version 1.0.0
 *
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl() {

    }

    /**
     *
     * @param customerRepository
     */
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * The customerRepository to be set.
     *
     * @param customerRepository
     */
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * (non-Javadoc)
     *
     * @see CustomerService#findCustomers()
     */
    @Override
    public List<Customer> findCustomers() {
        return customerRepository.findCustomers();
    }

}
