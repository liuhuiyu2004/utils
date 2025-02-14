package com.liuhuiyu.core.demo.design.patterns;

import org.junit.jupiter.api.Test;

/**
 * 解释器模式（Interpreter Pattern）提供了评估语言的语法或表达式的方式，它属于行为型模式。这种模式实现了一个表达式接口，该接口解释一个特定的上下文。这种模式被用在 SQL 解析、符号处理引擎等。
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 20:56
 */
public class InterpreterPattern {
    public interface Expression {
        public boolean interpret(String context);
    }

    public class TerminalExpression implements Expression {

        private String data;

        public TerminalExpression(String data) {
            this.data = data;
        }

        @Override
        public boolean interpret(String context) {
            return context.contains(data);
        }
    }

    public class OrExpression implements Expression {

        private Expression expr1 = null;
        private Expression expr2 = null;

        public OrExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean interpret(String context) {
            return expr1.interpret(context) || expr2.interpret(context);
        }
    }

    public class AndExpression implements Expression {

        private Expression expr1 = null;
        private Expression expr2 = null;

        public AndExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean interpret(String context) {
            return expr1.interpret(context) && expr2.interpret(context);
        }
    }


    //规则：Robert 和 John 是男性
    public Expression getMaleExpression() {
        Expression robert = new TerminalExpression("Robert");
        Expression john = new TerminalExpression("John");
        return new OrExpression(robert, john);
    }

    //规则：Julie 是一个已婚的女性
    public Expression getMarriedWomanExpression() {
        Expression julie = new TerminalExpression("Julie");
        Expression married = new TerminalExpression("Married");
        return new AndExpression(julie, married);
    }

    @Test
    public void test() {
        Expression isMale = getMaleExpression();
        Expression isMarriedWoman = getMarriedWomanExpression();

        System.out.println("John is male? " + isMale.interpret("John"));
        System.out.println("Julie is a married women? "
                + isMarriedWoman.interpret("Married Julie"));
    }
}
