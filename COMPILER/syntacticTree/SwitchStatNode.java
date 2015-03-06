package syntacticTree;

import parser.*;

public class SwitchStatNode extends StatementNode {
        
    public ExpreNode expr;
    public CaseListNode caselist;

    public SwitchStatNode(Token t, ExpreNode e, CaseListNode c1) {
        super(t);
        expr = e;
        caselist = c1;
    }
}