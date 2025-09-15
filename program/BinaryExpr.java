public class BinaryExpr extends Token {
    Expr lhs, rhs;
    String bop;
    BinaryExpr(Expr lhs, String bop, Expr rhs) {
        this.lhs = lhs;
        this.bop = bop;
        this.rhs = rhs;
    }

    public boolean isArithmetic() {
        return (bop.equals("*") || bop.equals("/") || bop.equals("+") || bop.equals("-"));
    }
    public boolean isRelational() {
        return (bop.equals("<") || bop.equals(">") || bop.equals("<=")
                        || bop.equals(">=") || bop.equals("==") || bop.equals("<>"));
    }
    public boolean isLogical() {
        return (bop.equals("||") || bop.equals("&&"));
    }

    public Type getType() throws UTDLangException {
        if (isArithmetic()) {
            if (lhs.getType().getTypeName().equals("float") || rhs.getType().getTypeName().equals("float")) {
                return new Type("float");
            } else if (lhs.getType().getTypeName().equals("string") || rhs.getType().getTypeName().equals("string")) {
                return new Type("string");
            } else {
                return new Type("int");
            }
        } else  {
            return new Type("bool");
        }
    }

    @Override
    public String toString(int t) {
        return getTabs(t) + "(" + lhs.toString(0) + " " + bop + " " + rhs.toString(0) + ")";
    }
    
    public void typeCheck() throws UTDLangException {
        lhs.typeCheck();
        rhs.typeCheck();
        boolean valid = true;

        if (isArithmetic()) {
            if (!SymbolType.equivalent(lhs.getType(), rhs.getType()) && !SymbolType.equivalent(rhs.getType(), lhs.getType())) {
                valid = false;
            } else if (lhs.getType().getTypeName().equals("string") || rhs.getType().getTypeName().equals("string")) {
                if (!bop.equals("+")) {
                    valid = false;
                }
            } else if (lhs.getType().isArray() || rhs.getType().isArray()) {
                valid = false;
            }
        } else if (isLogical()) {
            if (!SymbolType.isBool(lhs) || !SymbolType.isBool(rhs)) {
                valid = false;
            }
        } else {
            if (!SymbolType.isFloat(lhs) || !SymbolType.isFloat(rhs)) {
                valid = false;
            }
        }

        if (!valid) {
            throw new UTDLangException("Can not perform " + lhs.getType().toString(0) + " " + bop + " " +
                                                    rhs.getType().toString(0) + ". Line: " + this.toString(0));
        }
    }
}
