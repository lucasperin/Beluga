package syntacticTree;

import parser.*;


public class WhileStatNode extends StatementNode {
    public StatementNode stat;
    public ExpreNode expr;

    public WhileStatNode(Token t, ExpreNode e, StatementNode s1) {
        super(t);
        expr = e;
        stat = s1;
    }
}