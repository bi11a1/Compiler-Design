public class ArgDecl extends Token {
    Type type;
    String id;
    boolean isArray;
    public ArgDecl(Type type, String id, boolean isArray) {
        this.type = type;
        this.id = id;
        this.isArray = isArray;
        if (isArray) {
            type.makeArray();
        }
    }

    @Override
    public String toString(int t) {
        String result = "";
        result += type.toString(0) + " " + id;
        if (isArray) {
            result += "[]";
        }
        return result;
    }

    public Type getType() {
        return new Type(type);
    }

    public void typeCheck() throws UTDLangException {
        FieldDecl fieldDecl;
        if (!isArray) {
            fieldDecl = new FieldDecl(false, type, id, null);
        } else {
            fieldDecl = new FieldDecl(type, id, 0);
        }
        symbolTable.addVar(id, fieldDecl);
    }
}
