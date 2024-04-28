/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2024 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */

package net.sf.jsqlparser;


import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.relational_algebra.uga.cs4370.mydbimpl.RelAlgebra;

public class ParserDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RelAlgebra relAlg = new RelAlgebra();
        while (true) {
            System.out.println("Enter SQL Query");
            String sqlStr = scanner.nextLine();
    
            try {
                PlainSelect select = (PlainSelect) CCJSqlParserUtil.parse(sqlStr);


                VisitorManager visitor = new VisitorManager(relAlg);

                FromItem fromItem = select.getFromItem();
                fromItem.accept(visitor);
        
                for (SelectItem selectItem : select.getSelectItems()) {
                    selectItem.accept(visitor);
                }


                if (select.getJoins() != null) {
                    for (Join join : select.getJoins()) {
                        visitor.addJoinItem(join);
                    }
                }

                Expression where = select.getWhere();
                if (where != null) {
                    where.accept(visitor);
                }

                visitor.processQuery();
                visitor.getRel().print();
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
