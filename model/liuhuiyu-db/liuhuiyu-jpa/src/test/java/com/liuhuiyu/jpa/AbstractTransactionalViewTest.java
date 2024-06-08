package com.liuhuiyu.jpa;

import com.google.gson.Gson;
import com.liuhuiyu.test.AbstractTest;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-31 13:25
 */
class AbstractTransactionalViewTest extends AbstractTest {
    public static final String SQL = "SELECT col1 AS a, col3 A S c, col2 AS b,col4 d,(select col5_1 f from table2) e FROM table T WHERE col1 = 10 AND col2 = 20 AND col3 = 30";

    @Test
    public void getColTest() throws JSQLParserException {
        final List<String> col = getCol(SQL);
        printObjectJson(col);
    }

    public List<String> getCol(String sql) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sql);
        List<String> nameList = new ArrayList<>();
        for (SelectItem selectItem : ((PlainSelect) stmt.getSelectBody()).getSelectItems()) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    nameList.add(item.getAlias().getName());
                }
            });
        }
        return nameList;
    }

    @Test
    public void s() throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse("SELECT col1 AS a, col3 AS c, col2 AS b,col4 d,(select col5_1 f from table2) e FROM table T WHERE col1 = 10 AND col2 = 20 AND col3 = 30");
        Map<String, Expression> map = new HashMap<>();
        Map<String, String> mapTable = new HashMap<>();

        ((PlainSelect) stmt.getSelectBody()).getFromItem().accept(new FromItemVisitorAdapter() {
            @Override
            public void visit(Table table) {
                // 获取别名 => 表名
                mapTable.put(table.getAlias().getName(), table.getName());
            }
        });

        ((PlainSelect) stmt.getSelectBody()).getWhere().accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(AndExpression expr) {
                // 获取where表达式
                System.out.println(expr);
            }
        });

        for (SelectItem selectItem : ((PlainSelect) stmt.getSelectBody()).getSelectItems()) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    // 获取字段别名 => 字段名
                    System.out.println(item.getAlias().getName());
                    map.put(item.getAlias().getName(), item.getExpression());
                }
            });
        }

        System.out.println("map " + map);
        System.out.println("mapTables" + mapTable);

//        Object obj = "";
//        LOG.info(new Gson().toJson(obj));
    }
}