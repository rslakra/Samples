/**
 *
 */
package com.rslakra.springcore.service;

import com.rslakra.springcore.model.Customer;
import com.rslakra.springcore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
