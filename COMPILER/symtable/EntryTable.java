package symtable;


// classe geral para as possveis entradas na tabela de smbolos
abstract public class EntryTable {
    public String name; // nome do smbolo (var., mtodo ou classe)
    public EntryTable next; // apontador para prximo dentro da tabela 
    public int scope; // nmero do aninhamento corrente
    public Symtable mytable; // entrada aponta para a tabela da qual ela  parte

    abstract public String dscJava();

    static public String strDim(int n) {
        String p = "";

        for (int i = 0; i < n; i++)
            p += "[";

        return p;
    }
}
