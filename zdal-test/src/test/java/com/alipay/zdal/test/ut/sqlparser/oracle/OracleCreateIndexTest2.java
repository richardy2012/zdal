package com.alipay.zdal.test.ut.sqlparser.oracle;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import com.alipay.zdal.parser.sql.ast.SQLStatement;
import com.alipay.zdal.parser.sql.dialect.oracle.parser.OracleStatementParser;
import com.alipay.zdal.parser.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alipay.zdal.parser.sql.stat.TableStat;


public class OracleCreateIndexTest2   {
@Test
    public void test_0() throws Exception {
        String sql = //
        "CREATE UNIQUE INDEX \"ESCROW\".\"SYS_IOT_TOP_196679\" " + //
                "on \"ESCROW\".\"SYS_JOURNAL_196678\"(\"C0\",\"C1\",\"C2\",\"C3\",\"RID\") " + //
                "INDEX ONLY TOPLEVEL TABLESPACE \"ESCROW_INDX\" NOPARALLEL";

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);
       

        Assert.assertEquals(1, statementList.size());

        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
        statemen.accept(visitor);

        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
        System.out.println("coditions : " + visitor.getConditions());
        System.out.println("relationships : " + visitor.getRelationships());
        System.out.println("orderBy : " + visitor.getOrderByColumns());

        Assert.assertEquals(1, visitor.getTables().size());

        Assert.assertTrue(visitor.getTables().containsKey(
            new TableStat.Name("ESCROW.SYS_JOURNAL_196678")));

        Assert.assertEquals(5, visitor.getColumns().size());

        // Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "*")));
        // Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "YEAR")));
        // Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("pivot_table", "order_mode")));
    }
}
