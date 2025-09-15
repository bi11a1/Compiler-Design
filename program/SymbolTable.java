import java.util.ArrayList;

class SymbolTable
{
    // List of hash tables
    ArrayList< ArrayList<Pair<String, SymbolType>> > table;
    ArrayList< Pair<String, String> > scopeName;
    static String className;

    public void setClassName(String name) {
        SymbolTable.className = name;
    }

    public static String getClassName() {
        return SymbolTable.className;
    }

    public SymbolTable() {
        table = new ArrayList< ArrayList<Pair<String, SymbolType>> >();
        table.add(new ArrayList<Pair<String, SymbolType>>());
        scopeName = new ArrayList<Pair<String, String>>();
    }

    public void addScopeName(Pair<String, String> p) {
        scopeName.add(p);
    }
    
    public void removeScopeName() {
        scopeName.remove(scopeName.size()-1);
    }

    public ArrayList< Pair<String, String> > getScopeName() {
        return scopeName;
    }

    public void startScope() {
        table.add(new ArrayList<Pair<String, SymbolType>>());
    }

    public void endScope() {
        table.remove(table.size()-1);
    }
  
    public void addVar(String id, FieldDecl fieldDecl) throws UTDLangException {
        for (Pair<String, SymbolType> p : table.get(table.size()-1)) {
            if (p.getKey().equals(id)) {
                throw new UTDLangException("Tried to redeclare variable " + id + ".");
            }
        }

        SymbolType curSymbolType = new SymbolType(fieldDecl);
        table.get(table.size()-1).add(new Pair<String, SymbolType>(id, curSymbolType));
    }
  
    public void addMethod(String id, MethodDecl methodDecl) throws UTDLangException {
        for (Pair<String, SymbolType> p : table.get(table.size()-1)) {
            if (p.getKey().equals(id)) {
                throw new UTDLangException("Tried to redeclare method " + id + "().");
            }
        }

        SymbolType curSymbolType = new SymbolType(methodDecl);
        table.get(table.size()-1).add(new Pair<String, SymbolType>(id, curSymbolType));
    }

    public SymbolType get(String s) throws UTDLangException {
        for (int i = table.size()-1; i >= 0; --i) {
            for (Pair<String, SymbolType> p : table.get(i)) {
                if (p.getKey().equals(s)) {
                    return p.getValue();
                }
            }
        }

        throw new UTDLangException("Undeclared " + s + ".");
    }

    public int getScopeLevel() {
        return table.size();
    }

    public String toString()
    {
        String ret = "";
        String t = "";
        for (ArrayList<Pair<String, SymbolType>> v : table)
        {
            for (Pair<String, SymbolType> p : v)
                ret += t + p.getKey() + " " + p.getValue().toString() + "\n";

            t += "\t";
        }
        return ret;
    }
}