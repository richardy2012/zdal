package com.alipay.zdal.test.ut.sqlparser.oracle;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import com.alipay.zdal.parser.sql.ast.SQLStatement;
import com.alipay.zdal.parser.sql.dialect.oracle.parser.OracleStatementParser;
import com.alipay.zdal.parser.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alipay.zdal.parser.sql.stat.TableStat;


public class OracleSelectTest5   {
@Test
    public void test_0() throws Exception {
        String sql = "SELECT t1.department_id, t2.* FROM hr_info t1, TABLE(t1.people) t2" + //
                     "   WHERE t2.department_id = t1.department_id;";

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);
        

        Assert.assertEquals(1, statementList.size());

        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
        statemen.accept(visitor);

        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
        System.out.println("alias : " + visitor.getAliasMap());
        System.out.println("conditions : " + visitor.getConditions());
        System.out.println("orderBy : " + visitor.getOrderByColumns());
        System.out.println("groupBy : " + visitor.getGroupByColumns());
        System.out.println("variant : " + visitor.getVariants());
        System.out.println("relationShip : " + visitor.getRelationships());
        System.out.println("--------------------------------");

        Assert.assertEquals(2, visitor.getTables().size());

        Assert.assertTrue(visitor.getTables().containsKey(new TableStat.Name("hr_info")));
        Assert.assertTrue(visitor.getTables().containsKey(new TableStat.Name("hr_info.people")));

        Assert.assertEquals(3, visitor.getColumns().size());

        Assert.assertTrue(visitor.getColumns().contains(
            new TableStat.Column("hr_info", "department_id")));
        Assert.assertTrue(visitor.getColumns().contains(
            new TableStat.Column("hr_info.people", "*")));
        Assert.assertTrue(visitor.getColumns().contains(
            new TableStat.Column("hr_info.people", "department_id")));
    }
}
