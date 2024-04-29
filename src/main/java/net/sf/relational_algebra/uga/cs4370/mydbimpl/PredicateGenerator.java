/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2024 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.relational_algebra.uga.cs4370.mydbimpl;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.relational_algebra.uga.cs4370.mydb.Cell;
import net.sf.relational_algebra.uga.cs4370.mydb.Predicate;
import net.sf.relational_algebra.uga.cs4370.mydb.Relation;

public class PredicateGenerator {
    
    public static Predicate createPredicate(Relation rel, GreaterThan greaterThan) {
        String leftExpression = greaterThan.getLeftExpression().toString();
        String rightExpression = greaterThan.getRightExpression().toString();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                if (rel == null) {
                    System.out.println("RELATION IS NULL IN PREDICATE");
                } else {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    double value = (Double) cell.getAsDouble();
                    Boolean ans = value > Double.parseDouble(rightExpression);
                    return ans;
                }
                return false;
            }
        };
        
        return pred;
    } // GreaterThan

    public static Predicate createPredicate(Relation rel, MinorThan minorThan) {
        String leftExpression = minorThan.getLeftExpression().toString();
        String rightExpression = minorThan.getRightExpression().toString();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel.getAttrIndex(leftExpression);
                Cell cell = row.get(attrIndex);
                double value = (Double) cell.getAsDouble();
                Boolean ans = value < Double.parseDouble(rightExpression);
                return ans;
            }
        };
        
        return pred;
    } // MinorThan


    public static Predicate createPredicate(Relation rel, GreaterThanEquals geq) {
        String leftExpression = geq.getLeftExpression().toString();
        String rightExpression = geq.getRightExpression().toString();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel.getAttrIndex(leftExpression);
                Cell cell = row.get(attrIndex);
                double value = (Double) cell.getAsDouble();
                Boolean ans = value >= Double.parseDouble(rightExpression);
                return ans;
            }
        };
        
        return pred;
    } // Greater Than Equals


    public static Predicate createPredicate(Relation rel, MinorThanEquals leq) {
        String leftExpression = leq.getLeftExpression().toString();
        String rightExpression = leq.getRightExpression().toString();

        Predicate pred = new Predicate() {
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel.getAttrIndex(leftExpression);
                Cell cell = row.get(attrIndex);
                double value = (Double) cell.getAsDouble();
                Boolean ans = value <= Double.parseDouble(rightExpression);
                return ans;
            }
        };
        
        return pred;
    } // Minor Than Equals


    public static Predicate createPredicate(Relation rel, NotEqualsTo notEqualsTo) {
        String leftExpression = notEqualsTo.getLeftExpression().toString();
        String rightExpression = notEqualsTo.getRightExpression().toString();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel.getAttrIndex(leftExpression);
                Cell cell = row.get(attrIndex);
                String value = cell.getAsString();
                Boolean ans = value.equals(rightExpression);
                return !ans;
            }
        };
        
        return pred;
    } // Not Equals To

    public static Predicate createPredicate(Relation rel, EqualsTo equalsTo) {
        String leftExpression = equalsTo.getLeftExpression().toString();
        String rightExpression = equalsTo.getRightExpression().toString();

        Predicate pred = new Predicate(){
            @Override
            public boolean check(List<Cell> row) {
                int attrIndex = rel.getAttrIndex(leftExpression);
                Cell cell = row.get(attrIndex);
                String value = cell.getAsString();
                Boolean ans = value.equals(rightExpression);
                return ans;
            }
        };
        
        return pred;
    }

    public static Predicate createPredicate(Relation rel, ComparisonOperator operator) {
        if (operator instanceof EqualsTo) {

            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate(){
                @Override
                public boolean check(List<Cell> row) {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    String value = cell.getAsString();
                    Boolean ans = value.equals(rightExpression);
                    return ans;
                }
            };
            return pred;

        } else if (operator instanceof NotEqualsTo) {
            
            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate(){
                @Override
                public boolean check(List<Cell> row) {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    String value = cell.getAsString();
                    Boolean ans = value.equals(rightExpression);
                    return !ans;
                }
            };
            return pred;

        } else if (operator instanceof GreaterThan) {
            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate(){
                @Override
                public boolean check(List<Cell> row) {
                    if (rel == null) {
                        System.out.println("RELATION IS NULL IN PREDICATE");
                    } else {
                        int attrIndex = rel.getAttrIndex(leftExpression);
                        Cell cell = row.get(attrIndex);
                        double value = (Double) cell.getAsDouble();
                        Boolean ans = value > Double.parseDouble(rightExpression);
                        return ans;
                    }
                    return false;
                }
            };
            return pred;
        } else if (operator instanceof GreaterThanEquals) {
            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate(){
                @Override
                public boolean check(List<Cell> row) {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    double value = (Double) cell.getAsDouble();
                    Boolean ans = value >= Double.parseDouble(rightExpression);
                    return ans;
                }
            };
            
            return pred;
        } else if (operator instanceof MinorThan) {
            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate(){
                @Override
                public boolean check(List<Cell> row) {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    double value = (Double) cell.getAsDouble();
                    Boolean ans = value < Double.parseDouble(rightExpression);
                    return ans;
                }
            };
            
            return pred;
        } else if (operator instanceof MinorThanEquals) {
            String leftExpression = operator.getLeftExpression().toString();
            String rightExpression = operator.getRightExpression().toString();
    
            Predicate pred = new Predicate() {
                @Override
                public boolean check(List<Cell> row) {
                    int attrIndex = rel.getAttrIndex(leftExpression);
                    Cell cell = row.get(attrIndex);
                    double value = (Double) cell.getAsDouble();
                    Boolean ans = value <= Double.parseDouble(rightExpression);
                    return ans;
                }
            };
            
            return pred;
        } else {
            System.out.println("OPERATOR NOT INSTANCE OF ANYTHING.");
            return null;
        }
    }



}
