package com.alipay.zdal.parser.druid.bvt.sql.oracle;

import java.util.List;

import junit.framework.Assert;

import com.alipay.zdal.parser.druid.sql.OracleTest;
import com.alipay.zdal.parser.sql.ast.SQLStatement;
import com.alipay.zdal.parser.sql.dialect.oracle.parser.OracleStatementParser;
import com.alipay.zdal.parser.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alipay.zdal.parser.sql.stat.TableStat;

/**
 * 
 * @author xiaoqing.zhouxq
 * @version $Id: OracleSelectTest16.java, v 0.1 2012-5-17 ����10:20:13 xiaoqing.zhouxq Exp $
 */
public class OracleSelectTest16 extends OracleTest {

    public void test_0() throws Exception {
        String sql = "select privilege#,level from sysauth$ connect by grantee#=prior privilege# and privilege#>0 start with grantee#=:1 and privilege#>0"; //

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);
        print(statementList);

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

        Assert.assertEquals(1, visitor.getTables().size());

        Assert.assertTrue(visitor.getTables().containsKey(new TableStat.Name("sysauth$")));

        Assert.assertEquals(3, visitor.getColumns().size());

        //        Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "*")));
        //        Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "YEAR")));
        //        Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "order_mode")));
    }
}
