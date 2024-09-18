package com.liuhuiyu.jpa.help;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * sql解析
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-31 13:58
 */
public class SqlResolution {
    private final Map<String, Integer> resolutionMap;

    public SqlResolution(String sql) {
        this.resolutionMap = getSelectField(sql);
    }

    public static final int NULL_INDEX = -1;

    public Integer getFieldIndex(String fieldName) {
        return this.resolutionMap.getOrDefault(fieldName.toLowerCase(), NULL_INDEX);
    }

    public static Map<String, Integer> getSelectField(String sql) {
        try {
            Select stmt = (Select) CCJSqlParserUtil.parse(sql);
            Map<String, Integer> map = new HashMap<>();
            final AtomicInteger index = new AtomicInteger(0);
            final AtomicInteger funcIndex = new AtomicInteger(0);
            for (SelectItem selectItem : ((PlainSelect) stmt.getSelectBody()).getSelectItems()) {
                selectItem.accept(new SelectItemVisitorAdapter() {
                    @Override
                    public void visit(SelectExpressionItem item) {
                        String name;
                        if (item.getAlias() != null) {
                            name = item.getAlias().getName();
                        }
                        else if (item.getExpression() instanceof Column) {
                            name = ((Column) item.getExpression()).getColumnName();
                        }
                        else if (item.getExpression() instanceof net.sf.jsqlparser.expression.Function) {
                            name = String.valueOf(funcIndex.getAndIncrement());
                        }
                        else {
                            return;
                        }
                        map.put(name.toLowerCase(), index.getAndIncrement());
                    }
                });
            }
            return map;
        }
        catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
}
