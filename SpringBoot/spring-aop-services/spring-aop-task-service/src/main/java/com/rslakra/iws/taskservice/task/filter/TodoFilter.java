package com.rslakra.iws.taskservice.task.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;
import com.rslakra.iws.taskservice.task.persistence.entity.Todo;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class TodoFilter extends DefaultFilter<Todo> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER_ID = "userId";

    /**
     * @param allParams
     */
    public TodoFilter(Map<String, Object> allParams) {
        super(allParams);
    }

}
