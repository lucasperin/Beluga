package semanalysis;

import symtable.*;

import syntacticTree.*;


public class VarCheck extends ClassCheck {
    public VarCheck() {
        super();
    }

    public void VarCheckRoot(ListNode x) throws SemanticException {
        ClassCheckRoot(x); // faz anlise das classes
        VarCheckClassDeclListNode(x);

        if (foundSemanticError != 0) { // se houve erro, lana exceo
            throw new SemanticException(foundSemanticError +
                " Semantic Errors found (phase 2)");
        }
    }

    public void VarCheckClassDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            VarCheckClassDeclNode((ClassDeclNode) x.node);
        } catch (SemanticException e) { // se um erro ocorreu na classe, da a msg mas faz a anlise p/ prxima
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        VarCheckClassDeclListNode(x.next);
    }

    public void VarCheckClassDeclNode(ClassDeclNode x)
        throws SemanticException {
        Symtable temphold = Curtable; // salva tabela corrente
        EntryClass c = null;
        EntryClass nc;

        if (x == null) {
            return;
        }

        if (x.supername != null) { // verifica se superclasse foi definida
            c = (EntryClass) Curtable.classFindUp(x.supername.image);

            if (c == null) // Se no achou superclasse, ERRO
             {
                throw new SemanticException(x.position,
                    "Superclass " + x.supername.image + " not found");
            }
        }

        nc = (EntryClass) Curtable.classFindUp(x.name.image);
        nc.parent = c; // coloca na tabela o apontador p/ superclasse
        Curtable = nc.nested; // tabela corrente = tabela da classe
        VarCheckClassBodyNode(x.body);
        Curtable = temphold; // recupera tabela corrente
    }

    public void VarCheckClassBodyNode(ClassBodyNode x) {
        if (x == null) {
            return;
        }

        VarCheckClassDeclListNode(x.clist);
        VarCheckVarDeclListNode(x.vlist);
        VarCheckConstructDeclListNode(x.ctlist);

        // se no existe constructor(), insere um falso
        if (Curtable.methodFindInclass("construtor", null) == null) {
            Curtable.add(new EntryMethod("construtor", Curtable.levelup, true));
        }

        VarCheckMethodDeclListNode(x.mlist);
    }

    public void VarCheckVarDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            VarCheckVarDeclNode((VarDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        VarCheckVarDeclListNode(x.next);
    }

    public void VarCheckVarDeclNode(VarDeclNode x) throws SemanticException {
        EntryTable c;
        ListNode p;

        if (x == null) {
            return;
        }

        // acha entrada do tipo da varivel
        c = Curtable.classFindUp(x.position.image);

        // se no achou, ERRO
        if (c == null) {
            throw new SemanticException(x.position,
                "Class " + x.position.image + " not found");
        }

        // para cada varivel da declaraco, cria uma entrada na tabela
        for (p = x.vars; p != null; p = p.next) {
            VarNode q = (VarNode) p.node;
            Curtable.add(new EntryVar(q.position.image, c, q.dim));
        }
    }

    public void VarCheckConstructDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            VarCheckConstructDeclNode((ConstructDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        VarCheckConstructDeclListNode(x.next);
    }

    public void VarCheckConstructDeclNode(ConstructDeclNode x)
        throws SemanticException {
        EntryMethod c;
        EntryRec r = null;
        EntryTable e;
        ListNode p;
        VarDeclNode q;
        VarNode u;
        int n;

        if (x == null) {
            return;
        }

        p = x.body.param;
        n = 0;

        while (p != null) // para cada parmetro do construtor
         {
            q = (VarDeclNode) p.node; // q = n com a declaraco do parmetro
            u = (VarNode) q.vars.node; // u = n com o nome e dimenso
            n++;

            // acha a entrada do tipo na tabela
            e = Curtable.classFindUp(q.position.image);

            // se no achou: ERRO
            if (e == null) {
                throw new SemanticException(q.position,
                    "Class " + q.position.image + " not found");
            }

            // constri a lista com os parmetros
            r = new EntryRec(e, u.dim, n, r);
            p = p.next;
        }

        if (r != null) {
            r = r.inverte(); // inverte a lista
        }

        // procura construtor com essa assinatura dentro da mesma classe
        c = Curtable.methodFindInclass("construtor", r);

        if (c == null) { // se no achou, insere
            c = new EntryMethod("construtor", Curtable.levelup, 0, r);
            Curtable.add(c);
        } else { // construtor j definido na mesma classe: ERRO
            throw new SemanticException(x.position,
                "Construtor " + Curtable.levelup.name + "(" +
                ((r == null) ? "" : r.toStr()) + ")" + " already declared");
        }
    }

    public void VarCheckMethodDeclListNode(ListNode x) {
        if (x == null) {
            return;
        }

        try {
            VarCheckMethodDeclNode((MethodDeclNode) x.node);
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            foundSemanticError++;
        }

        VarCheckMethodDeclListNode(x.next);
    }

    public void VarCheckMethodDeclNode(MethodDeclNode x)
        throws SemanticException {
        EntryMethod c;
        EntryRec r = null;
        EntryTable e;
        ListNode p;
        VarDeclNode q;
        VarNode u;
        int n;

        if (x == null) {
            return;
        }

        p = x.body.param;
        n = 0;

        while (p != null) // para cada parmetro do mtodo
         {
            n++;
            q = (VarDeclNode) p.node; // q = n da declaraco do parmetro
            u = (VarNode) q.vars.node; // u = n com o nome e dimenso

            // acha a entrada na tabela do tipo
            e = Curtable.classFindUp(q.position.image);

            // se no achou, ERRO
            if (e == null) {
                throw new SemanticException(q.position,
                    "Class " + q.position.image + " not found");
            }

            // constri lista de tipos dos parmetros
            r = new EntryRec(e, u.dim, n, r);
            p = p.next;
        }

        if (r != null) {
            r = r.inverte(); // inverte a lista
        }

        // procura na tabela o tipo de retorno do mtodo
        e = Curtable.classFindUp(x.position.image);

        if (e == null) {
            throw new SemanticException(x.position,
                "Class " + x.position.image + " not found");
        }

        // procura mtodo na tabela, dentro da mesma classe
        c = Curtable.methodFindInclass(x.name.image, r);

        if (c == null) { // se no achou, insere
            c = new EntryMethod(x.name.image, e, x.dim, r);
            Curtable.add(c);
        } else { // metodo j definido na mesma classe, ERRO
            throw new SemanticException(x.position,
                "Method " + x.name.image + "(" + ((r == null) ? "" : r.toStr()) +
                ")" + " already declared");
        }
    }
}
