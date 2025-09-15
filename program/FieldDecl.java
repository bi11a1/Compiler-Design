public class FieldDecl extends Token {
    boolean optionalFinal;
    Type type;
    String id;
    OptionalExpr optionalExpr;
    int intLit;
    boolean isArray;

    public FieldDecl(boolean optionalFinal, Type type, String id, OptionalExpr optionalExpr) {
        this.optionalFinal = optionalFinal;
        this.type = type;
        this.id = id;
        this.optionalExpr = optionalExpr;
        this.isArray = false;
    }

    public FieldDecl(Type type, String id, int intLit) {
        this.type = type;
        this.id = id;
        this.intLit = intLit;
        this.isArray = true;
        this.optionalExpr = null;
        type.makeArray();
    }

    public boolean isFinal() {
        return optionalFinal;
    }

    public boolean isArray() {
        return isArray;
    }

    public Type getType() {
        return new Type(type);
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString(int t) {
        String result;
        if (isArray == false) {
            result = getTabs(t);
            if (optionalFinal) {
                result += "final ";
            }
            result += type.toString(0);
            result += " " + id + optionalExpr.toString(0) + ";\n";
        } else {
            result = getTabs(t);
            result += type.toString(0) + " " + id;
            result += "[" + Integer.toString(intLit) + "];\n";
        }
        return result;
    }

    public void typeCheck() throws UTDLangException {
        symbolTable.addVar(id, this);
        // System.out.println(this.toString(0));
        if (!isArray && optionalExpr != null) {
            optionalExpr.typeCheck();

            Type optionalExprType = optionalExpr.getType();
            // if (optionalExprType == null) {
            //     System.out.println(this.toString(0));
            // }
            if (optionalExprType != null && !SymbolType.equivalent(type, optionalExprType)) {
                throw new UTDLangException("Can not assign " + optionalExprType.toString(0) + " to " + type.toString(0) + ". Line: " + this.toString(0));
            }
        }
    }

}
