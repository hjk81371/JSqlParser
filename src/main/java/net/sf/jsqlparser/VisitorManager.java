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

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesedFromItem;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.TableFunction;

public class VisitorManager implements SelectItemVisitor, FromItemVisitor {

    @Override
    public void visit(SelectItem item) {
        System.out.println("SELECT: " + item);
    }

    @Override
    public void visit(Table tableName) {
        System.out.println("TABLE NAME: " + tableName);
    }

    @Override
    public void visit(ParenthesedSelect selectBody) {
        System.out.println("Parenthesed Select: " + selectBody);
    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        System.out.println("Lateral SubSelect: " + lateralSubSelect);
    }

    @Override
    public void visit(TableFunction tableFunction) {
        System.out.println("Table Function: " + tableFunction);
    }

    @Override
    public void visit(ParenthesedFromItem aThis) {
        System.out.println("A this:" + aThis);
    }


}
