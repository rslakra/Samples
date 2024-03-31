/**
 *
 */
package com.rslakra.springannotation.service;

import com.rslakra.springannotation.model.Customer;
import com.rslakra.springannotation.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
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
