/**
 *
 */
package com.rslakra.springcore.repository;

import com.rslakra.springcore.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 *
 */
public class CustomerRepositoryImpl implements CustomerRepository {

    /**
     * (non-Javadoc)
     *
     * @see CustomerRepository#findCustomers()
     */
    @Override
    public List<Customer> findCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(newCustomer("Roh", "Lak"));
        customers.add(newCustomer("Roh", "Singh"));
        customers.add(newCustomer("Roh", "Singh", "Lak"));
        customers.add(newCustomer("San", "Lak"));

        return customers;
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @return
     */
    private Customer newCustomer(String firstName, String lastName) {
        return newCustomer(firstName, null, lastName);
    }

    private Customer newCustomer(String firstName, String middleName, String lastName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setMiddleName(middleName);
        customer.setLastName(lastName);

        return customer;
    }

}
