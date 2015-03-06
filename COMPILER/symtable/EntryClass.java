package symtable;


// classe corresponde a uma declaraco de classe na tab. de smbolos
public class EntryClass extends EntryTable {
    public Symtable nested; // tabela para declaraco de elementos aninhados
    public EntryClass parent; // entrada correspondente  superclasse

    public EntryClass(String n, Symtable t) {
        name = n; // nome da classe declarada
        nested = new Symtable(this); // tabela onde inserir vars, mtodos ou classes
        parent = null; // sua superclasse
    }

    public String completeName() // devolve nome completo da classe
     {
        String p;
        Symtable t;
        EntryClass up;

        t = mytable;
        up = (EntryClass) t.levelup;

        if (up == null) {
            p = ""; // no  uma classe aninhada
        } else {
            p = up.completeName() + "$"; // classe aninhada
        }

        return p + name; // retorna nome nvel superior $ nome da classe
    }

    public String dscJava() // devolve descritor de tipo
     {
        return "L" + completeName() + ";";
    }
}
