package recovery;

import parser.*;

import java.util.*;


public class First { //implementa os conjuntos first p/ alguns n.terminais

    static public final RecoverySet methoddecl = new RecoverySet();
    static public final RecoverySet vardecl = new RecoverySet();
    static public final RecoverySet classlist = new RecoverySet();
    static public final RecoverySet constructdecl = new RecoverySet();
    static public final RecoverySet statlist = new RecoverySet();
    static public final RecoverySet program = classlist;

    static {
        methoddecl.add(new Integer(langBConstants.INT));
        methoddecl.add(new Integer(langBConstants.STRING));
        methoddecl.add(new Integer(langBConstants.IDENT));

        vardecl.add(new Integer(langBConstants.INT));
        vardecl.add(new Integer(langBConstants.STRING));
        vardecl.add(new Integer(langBConstants.IDENT));

        classlist.add(new Integer(langBConstants.CLASS));

        constructdecl.add(new Integer(langBConstants.CONSTRUCTOR));

        statlist.addAll(vardecl);
        statlist.add(new Integer(langBConstants.IDENT)); // first do atribstat
        statlist.add(new Integer(langBConstants.PRINT));
        statlist.add(new Integer(langBConstants.READ));
        statlist.add(new Integer(langBConstants.RETURN));
        statlist.add(new Integer(langBConstants.SUPER));
        statlist.add(new Integer(langBConstants.IF));
        statlist.add(new Integer(langBConstants.FOR));
        statlist.add(new Integer(langBConstants.LBRACE));
        statlist.add(new Integer(langBConstants.BREAK));
        statlist.add(new Integer(langBConstants.SEMICOLON));
    }
}
