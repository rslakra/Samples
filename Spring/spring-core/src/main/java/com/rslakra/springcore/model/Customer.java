/**
 *
 */
package com.rslakra.springcore.model;

import java.util.StringJoiner;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 */
public class Customer {

    private String firstName;
    private String middleName;
    private String lastName;

    public Customer() {
    }

    /**
     * Returns the value of the firstName.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The firstName to be set.
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the value of the middleName.
     *
     * @return middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * The middleName to be set.
     *
     * @param middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns the value of the lastName.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The lastName to be set.
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Builds the fields key/value.
     *
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private static String buildFieldJoiner(String fieldName, String fieldValue) {
        StringJoiner fieldJoiner = new StringJoiner("=");
        fieldJoiner.add(fieldName).add(fieldValue);
        return fieldJoiner.toString();
    }

    /**
     * Returns the string representation of this object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" , ", "<", ">");
        joiner.add(buildFieldJoiner("firstName", getFirstName()));
        joiner.add(buildFieldJoiner("middleName", getMiddleName()));
        joiner.add(buildFieldJoiner("lastName", getLastName()));
        return joiner.toString();
    }

}
