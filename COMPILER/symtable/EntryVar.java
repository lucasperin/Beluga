package symtable;


// classe que abriga uma declaraco de varivel na tabela de smbolos
public class EntryVar extends EntryTable {
    public EntryTable type; // apontador para o tipo da varivel
    public int dim; // nmero de dimenses da varivel
    public int localcount; // numerao seqencial para as vars. locais

    // cria uma entrada para var. de classe
    public EntryVar(String n, EntryTable p, int d) {
        name = n; // nome da varivel
        type = p; // apontador para a classe
        dim = d; // nmero de dimenses
        localcount = -1; // nmero seqencial  sempre -1 (var. no local)
    }

    // cria uma entrada para var.local
    public EntryVar(String n, EntryTable p, int d, int k) {
        name = n; // nome da varivel
        type = p; // apontador para a classe
        dim = d; // nmero de dimenses
        localcount = k; // inclui tbem o nmero seqencial 
    }

    public String dscJava() {
        String s = strDim(dim);
        s += type.dscJava();

        return s;
    }
}
