/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2024 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.relational_algebra.uga.cs4370.mydb;

import java.util.List;

/**
 * Functional interface to represent a predicate
 * in relational algebra operators.
 */
public interface Predicate {

    /**
     * Checks a row for a condition and returns true
     * if the row passes the predicate.
     */
    public boolean check(List<Cell> row);
    
}
