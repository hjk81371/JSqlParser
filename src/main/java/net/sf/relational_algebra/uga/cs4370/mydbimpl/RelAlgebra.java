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

    private Relation relation;
    RA ra;

    public RelAlgebra() {
        ra = new Implementation();
    }

    public void setTable(String tableName) {

        if (tableName.equals("advisor")) {

            relation = new RelationBuilder()
            .attributeNames(List.of("s_ID", "i_ID"))
            .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
            .build();
            
            relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/advisor_export.csv");

        } else if (tableName.equals("course")) {

        relation = new RelationBuilder()
        .attributeNames(List.of("course_id", "title", "dept_name", "credits"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/course_export.csv");

        } else if (tableName.equals("department")) {
            
            relation = new RelationBuilder()
        .attributeNames(List.of("dept_name", "building", "budget"))
        .attributeTypes(List.of(Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        
        relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/department_export.csv");

        } else if (tableName.equals("instructor")) {
            relation = new RelationBuilder()
            .attributeNames(List.of("id", "name", "dept", "salary"))
            .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
            .build();
            
            relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/instructor_export.csv");
        } else if (tableName.equals("prereqs")) {

            relation = new RelationBuilder()
        .attributeNames(List.of("course_id", "prereq_id"))
        .attributeTypes(List.of(Type.INTEGER, Type.INTEGER))
        .build();
        
        relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/prereqs_export.csv");

        } else if (tableName.equals("student")) {

            relation = new RelationBuilder()
        .attributeNames(List.of("id", "name", "dept_name", "tot_cred"))
        .attributeTypes(List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.DOUBLE))
        .build();
        
        relation.loadData("/Users/harrisonkirstein/Desktop/JSqlParser/src/main/java/net/sf/relational_algebra/uga/cs4370/mydbimpl/data/student_export.csv");

        } else {
            System.out.println("Cannot find table: " + tableName);
        }

    } // setTable

    public Relation select(List<String> cols) {
        Relation newRel = ra.project(this.relation, cols);
        return newRel;
    } // select

    public Relation where(Relation rel, Predicate p) {
        return ra.select(rel, p);
    }
    
}
