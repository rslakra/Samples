/**
 *
 */
package com.rslakra.springannotation;

import com.rslakra.springannotation.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 */
public class CustomerApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);
        System.out.println(customerService.findCustomers());
    }

}
