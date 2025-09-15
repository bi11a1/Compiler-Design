public class UnaryExpr extends Token {
    String op;
    Expr expr;
    Type dataType;
    public UnaryExpr(String op, Expr expr) {
        this.op = op;
        this.expr = expr;
        this.dataType = null;
    }

    public UnaryExpr(Type datatype, Expr expr) {
        this.op = "(" + datatype.toString(0) + ")";
        this.dataType = datatype;
        this.expr = expr;
    }

    public Type getType() throws UTDLangException {
        if (this.dataType != null) {
            return new Type(dataType);
        } else if (op.equals("~")) {
            return new Type("bool");
        } else {
            return new Type(expr.getType());
        }
    }

    @Override
    public String toString(int t) {
        return getTabs(t) + "(" + op + expr.toString(0) + ")";
    }
    
    public void typeCheck() throws UTDLangException {
        expr.typeCheck();

        if (dataType != null) {
            Type exprType = expr.getType();
            if (!SymbolType.equivalent(dataType, exprType)) {
                throw new UTDLangException("Can not convert " + exprType.toString(0) + " to " + dataType.toString(0) 
                                            + ". Line: " + this.toString(0));
            };
        } else if (op.equals("~")) {
            if (!SymbolType.isBool(expr)) {
                throw new UTDLangException("Can not convert " + expr.toString(0) + " to bool" + ". Line: " + this.toString(0));
            }
        } else {
            if (!SymbolType.isInt(expr) && !SymbolType.isFloat(expr)) {
                throw new UTDLangException("Unary " + op + " is only applicable to int or float" + ". Line: " + this.toString(0));
            }
        }
    }
}
