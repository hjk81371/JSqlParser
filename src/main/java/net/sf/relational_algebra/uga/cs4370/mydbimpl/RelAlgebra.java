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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.relational_algebra.uga.cs4370.mydb.*;
import net.sf.relational_algebra.uga.cs4370.mydbimpl.*;

public class RelAlgebra {

    RA ra;

    public RelAlgebra() {
        ra = new Implementation();
    }

 

    public Relation select(Relation rel, List<String> cols) {
        Relation newRel = ra.project(rel, cols);
        return newRel;
    } // select

    public Relation where(Relation rel, Predicate p) {
        return ra.select(rel, p);
    }

    public Relation join(Relation rel, String table) {
        Relation newRel = getNewRelation(table);
        return ra.join(rel, newRel);
    }







    public Relation getNewRelation(String tableName) {

        Relation newRel;

        if (tableName.equals("advisor")) {

            newRel = new RelationBuilder()
            .attributeNames(List.of("student_id", "instructor_id"))
            .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
            .build();
            
            newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/advisor_export.csv");
            return newRel;

        } else if (tableName.equals("course")) {

        newRel = new RelationBuilder()
        .attributeNames(List.of("course_id", "title", "dept_name", "credits"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/course_export.csv");
        return newRel;

        } else if (tableName.equals("department")) {
            
        newRel = new RelationBuilder()
        .attributeNames(List.of("dept_name", "building", "budget"))
        .attributeTypes(List.of(Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        
        newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/department_export.csv");
        return newRel;

        } else if (tableName.equals("instructor")) {
            newRel = new RelationBuilder()
            .attributeNames(List.of("instructor_id", "name", "dept_name", "salary"))
            .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
            .build();
            
            newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/instructor_export.csv");
            return newRel;
        } else if (tableName.equals("prereqs")) {

        newRel = new RelationBuilder()
        .attributeNames(List.of("course_id", "prereq_id"))
        .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
        .build();
        
        newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/prereqs_export.csv");
        return newRel;

        } else if (tableName.equals("student")) {

        newRel = new RelationBuilder()
        .attributeNames(List.of("student_id", "name", "dept_name", "tot_cred"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        
        newRel.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/student_export.csv");
        return newRel;

        } else {
            System.out.println("Cannot find table: " + tableName);
            return null;
        }
    }
    
}
