public class Name extends Token {
    String id;
    Expr expr;

    public Name(String id) {
        this.id = id;
        this.expr = null;
    }

    public Name(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(int t) {
        if (expr == null) {
            return getTabs(t) + id;
        } else {
            return getTabs(t) +  id + "[" + expr.toString(0) + "]";
        }
    }

    public Type getType() throws UTDLangException {
        SymbolType symbolType = symbolTable.get(id);
        Type curType = new Type(symbolType.getType());
        if (this.expr != null) {
            curType = new Type(symbolType.getType().getTypeName());
        }
        return new Type(curType);
    }
    
    public void typeCheck() throws UTDLangException {
        SymbolType symbolType = symbolTable.get(id);
        if (symbolType.isFunctionType()) {
            throw new UTDLangException("Function call requires arguments. Line: " + this.toString(0));
        }
        if (expr != null && !SymbolType.isInt(expr)) {
            throw new UTDLangException("Only int type indexing allowed. Line: " + this.toString(0));
        }

    }
}
