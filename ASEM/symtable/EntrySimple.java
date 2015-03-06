package symtable;


// entrada utilizada para declarar os tipos bsicos da linguagem
public class EntrySimple extends EntryTable {
    public EntrySimple(String n) {
        name = n;
    }

    public String dscJava() // devolve descritor de tipo
     {
        if (name.equals("inteiro")) {
            return "I";
        }else if(name.equals("cara_coroa")){
        	return "Ljava/lang/boolean;";
        }else if(name.equals("vazio")){
        	return "Ljava/lang/Void;";
        } else {
            return "Ljava/lang/String;"; // classe String JAVA
        }
    }
}
