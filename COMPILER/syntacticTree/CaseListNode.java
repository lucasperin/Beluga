package syntacticTree;

import parser.*;

public class CaseListNode extends StatementNode {
    
    public CaseStatNode casenode;
    public CaseListNode caselist;

    public CaseListNode(Token t, CaseStatNode c1, CaseListNode c2) {
	super(t);
        casenode = c1;
	caselist = c2;
    }
}