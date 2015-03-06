package symtable;


// corresponde a uma declaraco de mtodo na tabela de smbolos
public class EntryMethod extends EntryTable {
    public EntryTable type; // tipo de retorno do mtodo
    public int dim; // nmero de dimenses do retorno
    public EntryRec param; // tipo dos parmetros
    public int totallocals; // nmero de variveis locais
    public int totalstack; // tamanho da pilha necessria
    public boolean fake; // true se  um falso construtor
    public boolean hassuper; // true se mtodo possui chamada super

    // cria elemento para inserir na tabela 
    public EntryMethod(String n, EntryTable p, int d, EntryRec r) {
        name = n;
        type = p;
        dim = d;
        param = r;
        totallocals = 0;
        totalstack = 0;
        fake = false;
        hassuper = false;
    }

    public EntryMethod(String n, EntryTable p, boolean b) {
        name = n;
        type = p;
        dim = 0;
        param = null;
        totallocals = 0;
        totalstack = 0;
        fake = b;
        hassuper = false;
    }

    public String dscJava() {
        String s = strDim(dim);
        s += type.dscJava();

        return s;
    }
}
