package syntacticTree;

import parser.*;

public class CaseStatNode extends StatementNode {
    
    public StatementNode stat;
    public ExpreNode expr;

    public CaseStatNode(Token t1, ExpreNode e, StatementNode s1) {
        super(t1);
        expr = e;
        stat = s1;
    }
}