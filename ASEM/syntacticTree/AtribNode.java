package syntacticTree;

import parser.*;


public class AtribNode extends StatementNode {
    public ExpreNode expr1;
    public ExpreNode expr2;
    public ConditionalNode cond;

    public AtribNode(Token t, ExpreNode e1, ExpreNode e2, ConditionalNode a1) {
        super(t);
        expr1 = e1;
        expr2 = e2;
        cond = a1;
    }
}
