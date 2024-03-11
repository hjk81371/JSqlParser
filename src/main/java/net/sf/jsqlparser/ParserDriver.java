
package net.sf.jsqlparser;


import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;

public class ParserDriver {
    public static void main(String[] args) {
        String sqlStr = "select 1 from dual where a=b";

        try {
            Statement statement = CCJSqlParserUtil.parse(sqlStr);
            if (statement instanceof PlainSelect) {
                PlainSelect select = (PlainSelect) statement;

                // Access select items
                SelectItem selectItem = select.getSelectItems().get(0);
                if (selectItem.getExpression() instanceof LongValue) {
                    LongValue longValue = (LongValue) selectItem.getExpression();
                    if (longValue.getValue() == 1) {
                        System.out.println("Select item is a LongValue of 1");
                    } else {
                        System.out.println("Select item is not a LongValue of 1");
                    }
                } else {
                    System.out.println("Select item is not a LongValue");
                }

                // Access table
                Table table = (Table) select.getFromItem();
                if ("dual".equalsIgnoreCase(table.getName())) {
                    System.out.println("Table name is 'dual'");
                } else {
                    System.out.println("Table name is not 'dual'");
                }

                // Access WHERE clause
                EqualsTo equalsTo = (EqualsTo) select.getWhere();
                Column a = (Column) equalsTo.getLeftExpression();
                Column b = (Column) equalsTo.getRightExpression();
                if ("a".equalsIgnoreCase(a.getColumnName()) && "b".equalsIgnoreCase(b.getColumnName())) {
                    System.out.println("WHERE clause is 'a=b'");
                } else {
                    System.out.println("WHERE clause is not 'a=b'");
                }
            } else {
                System.out.println("This example handles only SELECT statements.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
