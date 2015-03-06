package symtable;

public class Symtable {
    public EntryTable top; // apontador para o topo da tabela (mais recente)
    public int scptr; // nmero que controla o escopo (aninhamento) corrente
    public EntryClass levelup; // apontador para a entrada EntryClass de nvel sup.

    public Symtable() // cria uma tabela vazia
     {
        top = null;
        scptr = 0;
        levelup = null;
    }

    public Symtable(EntryClass up) // cria tabela vazia apontando para nvel sup.
     {
        top = null;
        scptr = 0;
        levelup = up;
    }

    public void add(EntryTable x) // adiciona uma entrada  tabela
     {
        x.next = top; // inclui nova entrada no topo
        top = x;
        x.scope = scptr; // atribui para a entrada o nmero do escopo
        x.mytable = this; // faz a entrada apontar para a tabela  qual pertence
    }

    public void beginScope() {
        scptr++; // inicia novo aninhamento de variveis
    }

    public void endScope() {
        while ((top != null) && (top.scope == scptr))
            top = top.next; // retira todas as variveis do aninhamento corrente

        scptr--; // finaliza aninhamento corrente
    }

    /* Esse metodo procura o simbolo x na tabela e tambem na(s) tabela(s) de
    nivel superior, apontada por levelup. Procura por uma entrada do
    tipo EntryClass ou EntrySimple */
    public EntryTable classFindUp(String x) {
        EntryTable p = top;

        // para cada elemento da tabela corrente
        while (p != null) {
            // verifica se  uma entrada de classe ou tipo simples e compara o nome
            if (((p instanceof EntryClass) || (p instanceof EntrySimple)) &&
                    p.name.equals(x)) {
                return p;
            }

            p = p.next; // prxima entrada
        }

        if (levelup == null) { // se no achou e  o nvel mais externo 

            return null; // retorna null
        }

        // procura no nvel mais externo 
        return levelup.mytable.classFindUp(x);
    }

    /* Esse metodo procura o simbolo x na tabela e tambem na(s) tabela(s) da(s)
    superclasse(s), apontada por levelup.parent. Procura por uma entrada do
    tipo EntryVar */
    public EntryVar varFind(String x) {
        return varFind(x, 1);
    }

    /* Esse metodo procura a n-esima ocorrencia do simbolo x na tabela e tambem
    na(s) tabela(s) da(s)  superclasse(s), apontada por levelup.parent. Procura
    por uma entrada do  tipo EntryVar */
    public EntryVar varFind(String x, int n) {
        EntryTable p = top;
        EntryClass q;

        while (p != null) {
            if (p instanceof EntryVar && p.name.equals(x)) {
                if (--n == 0) {
                    return (EntryVar) p;
                }
            }

            p = p.next;
        }

        q = levelup;

        if (q.parent == null) {
            return null;
        }

        return q.parent.nested.varFind(x, n);
    }

    /* Esse metodo procura o simbolo x com uma lista de parametros igual a r na
    tabela e tambem na(s) tabela(s) da(s)  superclasse(s), apontada por
    levelup.parent. Procura por uma entrada do  tipo EntryMethod */
    public EntryMethod methodFind(String x, EntryRec r) {
        EntryTable p = top;
        EntryClass q;

        while (p != null) {
            if (p instanceof EntryMethod && p.name.equals(x)) {
                EntryMethod t = (EntryMethod) p;

                if (t.param == null) {
                    if (r == null) {
                        return t;
                    }
                } else {
                    if (t.param.equals(r)) {
                        return t;
                    }
                }
            }

            p = p.next;
        }

        q = levelup;

        if (q.parent == null) {
            return null;
        }

        return q.parent.nested.methodFind(x, r);
    }

    /* Esse metodo procura o simbolo x com uma lista de parametros igual a r
    apenas na tabela, nao na(s) tabela(s) da(s)  superclasse(s),
    apontada por levelup.parent. Procura por uma entrada do  tipo EntryMethod */
    public EntryMethod methodFindInclass(String x, EntryRec r) {
        EntryTable p = top;
        EntryClass q;

        // para cada entrada da tabela
        while (p != null) {
            // verifica se tipo  EntryMethod e compara o nome
            if (p instanceof EntryMethod && p.name.equals(x)) {
                EntryMethod t = (EntryMethod) p;

                // compara os parmetros
                if (t.param == null) {
                    if (r == null) {
                        return t;
                    }
                } else {
                    if (t.param.equals(r)) {
                        return t;
                    }
                }
            }

            p = p.next; // prxima entrada
        }

        return null; // no achou
    }
}
