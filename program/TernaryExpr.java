public class TernaryExpr extends Token {
    Expr lhs, mhs, rhs;
    public TernaryExpr(Expr lhs, Expr mhs, Expr rhs) {
        this.lhs = lhs;
        this.mhs = mhs;
        this.rhs = rhs;
    }

    public Type getType() throws UTDLangException {
        if (mhs.getType().getTypeName().equals("string")) {
            return new Type("string");
        } else if (mhs.getType().getTypeName().equals("float")) {
            return new Type("float");
        } else if (mhs.getType().getTypeName().equals("bool")) {
            return new Type("bool");
        } 
        return new Type(mhs.getType());
    }

    @Override
    public String toString(int t) {
        return getTabs(t) + "(" + lhs.toString(0) + " ? " + mhs.toString(0) + " : " + rhs.toString(0) + ")";
    }

    public void typeCheck() throws UTDLangException {
        lhs.typeCheck();
        mhs.typeCheck();
        rhs.typeCheck();

        if (!SymbolType.isBool(lhs)) {
            throw new UTDLangException("Condition of ternary expression must be bool. Line: " + this.toString(0));
        } else if (!mhs.getType().getTypeName().equals(rhs.getType().getTypeName())) {
            throw new UTDLangException("Type mismatch on the operand of ternary operator. Line: " + this.toString(0));
        }
    }
}
