package com.rslakra.iws.taskservice.account.filter;

import com.devamatre.appsuite.spring.filter.AbstractFilterImpl;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class UserFilter extends AbstractFilterImpl {

    /**
     * @param allParams
     */
    public UserFilter(final Map<String, Object> allParams) {
        super(allParams);
    }
}
