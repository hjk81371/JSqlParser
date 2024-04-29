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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.ArrayConstructor;
import net.sf.jsqlparser.expression.ArrayExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.ConnectByRootOperator;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.JsonFunction;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.OracleNamedFunctionParameter;
import net.sf.jsqlparser.expression.OverlapsCondition;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RangeExpression;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.RowGetExpression;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.TimezoneExpression;
import net.sf.jsqlparser.expression.TranscodingFunction;
import net.sf.jsqlparser.expression.TrimFunction;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.VariableAssignment;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.XMLSerializeExpr;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.ContainedBy;
import net.sf.jsqlparser.expression.operators.relational.Contains;
import net.sf.jsqlparser.expression.operators.relational.DoubleAnd;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.FullTextSearch;
import net.sf.jsqlparser.expression.operators.relational.GeometryDistance;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsDistinctExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MemberOfExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.expression.operators.relational.TSQLLeftJoin;
import net.sf.jsqlparser.expression.operators.relational.TSQLRightJoin;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesedFromItem;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.relational_algebra.uga.cs4370.mydb.*;
import net.sf.relational_algebra.uga.cs4370.mydbimpl.*;

public class VisitorManager implements SelectItemVisitor, FromItemVisitor, ExpressionVisitor {

    RelAlgebra relAlg;
    List<String> colList;
    List<String> tableList;
    Relation rel;
    List<Predicate> predicates;
    String selectTableName;
    List<ComparisonOperator> operators;

    public VisitorManager(RelAlgebra relAlg) {
        this.relAlg = relAlg;
        this.colList = new ArrayList<>();
        this.tableList = new ArrayList<>();
        predicates = new ArrayList<>();
        operators = new ArrayList<>();
        rel = null;
    }

    @Override
    public void visit(SelectItem item) {
        System.out.println("SELECT: " + item.toString());
        colList.add(item.toString());
    }

    @Override
    public void visit(Table tableName) {
        System.out.println("TABLE NAME: " + tableName);
        this.selectTableName = tableName.getName();
        this.rel = relAlg.getNewRelation(this.selectTableName);
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

    // EXPRESSION

    public void visit(BitwiseRightShift aThis) {

    }

    public void visit(BitwiseLeftShift aThis) {

    }

    public void visit(NullValue nullValue) {
        System.out.println("Select where value is null");
    }

    public void visit(Function function) {

    }

    public void visit(SignedExpression signedExpression) {

    }

    public void visit(JdbcParameter jdbcParameter) {

    }

    public void visit(JdbcNamedParameter jdbcNamedParameter) {

    }

    public void visit(DoubleValue doubleValue) {

    }

    public void visit(LongValue longValue){}

    public void visit(HexValue hexValue){}

    public void visit(DateValue dateValue){}

    public void visit(TimeValue timeValue){}

    public void visit(TimestampValue timestampValue){}

    public void visit(Parenthesis parenthesis){}

    public void visit(StringValue stringValue){}

    public void visit(Addition addition){}

    public void visit(Division division){}

    public void visit(IntegerDivision division){}

    public void visit(Multiplication multiplication){}

    public void visit(Subtraction subtraction){}

    public void visit(AndExpression andExpression){}

    public void visit(OrExpression orExpression){}

    public void visit(XorExpression orExpression){}

    public void visit(Between between){}

    public void visit(OverlapsCondition overlapsCondition){}

    public void visit(EqualsTo equalsTo){
        Predicate p = PredicateGenerator.createPredicate(this.rel, equalsTo);
        predicates.add(p);
        operators.add(equalsTo);
    }

    public void visit(GreaterThan greaterThan){
        Predicate p = PredicateGenerator.createPredicate(this.rel, greaterThan);
        predicates.add(p);
        operators.add(greaterThan);
    }

    public void visit(GreaterThanEquals greaterThanEquals){
        Predicate p = PredicateGenerator.createPredicate(this.rel, greaterThanEquals);
        predicates.add(p);
        operators.add(greaterThanEquals);
    }

    public void visit(InExpression inExpression){}

    public void visit(FullTextSearch fullTextSearch){}

    public void visit(IsNullExpression isNullExpression){
        System.out.println("Select where is null");
    }

    public void visit(IsBooleanExpression isBooleanExpression){
        System.out.println("Select where is boolean");
    }

    public void visit(LikeExpression likeExpression){
        System.out.println("Select where like");
    }

    public void visit(MinorThan minorThan){
        Predicate p = PredicateGenerator.createPredicate(this.rel, minorThan);
        predicates.add(p);
        operators.add(minorThan);
    }

    public void visit(MinorThanEquals minorThanEquals){
        Predicate p = PredicateGenerator.createPredicate(this.rel, minorThanEquals);
        predicates.add(p);
        operators.add(minorThanEquals);
    }

    public void visit(NotEqualsTo notEqualsTo){
        Predicate p = PredicateGenerator.createPredicate(this.rel, notEqualsTo);
        predicates.add(p);
        operators.add(notEqualsTo);
    }

    public void visit(DoubleAnd doubleAnd){}

    public void visit(Contains contains){
        System.out.println("select where contains");
    }

    public void visit(ContainedBy containedBy){}

    public void visit(Column tableColumn){
        System.out.println("Select where table column");
    }

    public void visit(CaseExpression caseExpression){}

    public void visit(WhenClause whenClause){
        System.out.println("When clause");
    }

    public void visit(ExistsExpression existsExpression){
        System.out.println("Select when exists");
    }

    public void visit(MemberOfExpression memberOfExpression){
        System.out.println("Select when member of");
    }

    public void visit(AnyComparisonExpression anyComparisonExpression){
        System.out.println("Select where any comparison expression");
    }

    public void visit(Concat concat){}

    public void visit(Matches matches){}

    public void visit(BitwiseAnd bitwiseAnd){
        System.out.println("where bitwise and");
    }

    public void visit(BitwiseOr bitwiseOr){
        System.out.println("when bitwise or");
    }

    public void visit(BitwiseXor bitwiseXor){}

    public void visit(CastExpression cast){}

    public void visit(Modulo modulo){}

    public void visit(AnalyticExpression aexpr){}

    public void visit(ExtractExpression eexpr){}

    public void visit(IntervalExpression iexpr){}

    public void visit(OracleHierarchicalExpression oexpr){}

    public void visit(RegExpMatchOperator rexpr){}

    public void visit(JsonExpression jsonExpr){}

    public void visit(JsonOperator jsonExpr){}

    public void visit(UserVariable var){
        System.out.println("Where using variable");
    }

    public void visit(NumericBind bind){}

    public void visit(KeepExpression aexpr){}

    public void visit(MySQLGroupConcat groupConcat){}

    public void visit(ExpressionList<?> expressionList){}

    public void visit(RowConstructor<?> rowConstructor){}

    public void visit(RowGetExpression rowGetExpression){}

    public void visit(OracleHint hint){}

    public void visit(TimeKeyExpression timeKeyExpression){}

    public void visit(DateTimeLiteralExpression literal){}

    public void visit(NotExpression aThis){}

    public void visit(NextValExpression aThis){}

    public void visit(CollateExpression aThis){}

    public void visit(SimilarToExpression aThis){}

    public void visit(ArrayExpression aThis){}

    public void visit(ArrayConstructor aThis){}

    public void visit(VariableAssignment aThis){}

    public void visit(XMLSerializeExpr aThis){}

    public void visit(TimezoneExpression aThis){}

    public void visit(JsonAggregateFunction aThis){}

    public void visit(JsonFunction aThis){}

    public void visit(ConnectByRootOperator aThis){}

    public void visit(OracleNamedFunctionParameter aThis){}

    public void visit(AllColumns allColumns){
        System.out.println("Where all columns");
    }

    public void visit(AllTableColumns allTableColumns){
        System.out.println("Where all table columns");
    }

    public void visit(AllValue allValue){
        System.out.println("Where all values");
    }

    public void visit(IsDistinctExpression isDistinctExpression){}

    public void visit(GeometryDistance geometryDistance){}

    public void visit(Select selectBody){
        System.out.println("Where select body");
    }

    public void visit(TranscodingFunction transcodingFunction){}

    public void visit(TrimFunction trimFunction){}

    public void visit(RangeExpression rangeExpression){}

    public void visit(TSQLLeftJoin tsqlLeftJoin){}

    public void visit(TSQLRightJoin tsqlRightJoin){}

    public void processQuery() {
        for (String table : tableList) {
            this.rel = relAlg.join(this.rel, table);
        }

        for (ComparisonOperator operator : operators) {
            Predicate p = PredicateGenerator.createPredicate(this.rel, operator);
            this.rel = relAlg.where(this.rel, p);
        }
        this.rel = relAlg.select(this.rel, this.colList);
    }

    public void addJoinItem(Join joinItem) {
        tableList.add(joinItem.getRightItem().toString());
    }

    public Relation getRel() {
        return this.rel;
    }

}
