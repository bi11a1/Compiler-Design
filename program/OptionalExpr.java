public class OptionalExpr extends Token {
    Expr expr;

    public OptionalExpr() {
        expr = null;
    }
    public OptionalExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public String toString(int t) {
        if (expr == null) {
            return "";
        }
        String result = " = " + expr.toString(0);
        return result;
    }

    public Type getType() throws UTDLangException {
        if (expr != null) {
            return new Type(expr.getType());
        }
        return null;
    }

    public void typeCheck() throws UTDLangException {
        if (expr != null) {
            expr.typeCheck();
        }
    }
}
