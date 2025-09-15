import java.util.ArrayList;

public class Expr extends Token {
    Name name;
    String id;
    ArgList argList;
    int intLit;
    char charLit;
    String strLit;
    float floatLit;
    Expr expr;
    UnaryExpr uexp;
    BinaryExpr bexp;
    TernaryExpr texp;

    String result;
    Type type;
    int productionNo;
    
    // 1
    public Expr(Name name) {
        result = name.toString(0);
        this.name = name;
        productionNo = 1;
    }
    // 2, 3
    public Expr(String id, ArgList argList) {
        result = id + "(" + argList.toString(0) + ")";
        this.id = id;
        this.argList = argList;
        productionNo = 2;
    }

    // 4
    public Expr(int intlit) {
        result = Integer.toString(intlit);
        this.intLit = intlit;
        productionNo = 4;
    }

    // 5
    public Expr(char charlit) {
        result = "\'" + charlit + "\'";
        this.charLit = charlit;
        productionNo = 5;
    }

    // 6
    public Expr(String strlit) {
        result = strlit;
        this.strLit = strlit;
        productionNo = 6;
    }

    // 7
    public Expr(float floatlit) {
        result = Float.toString(floatlit);
        this.floatLit = floatlit;
        productionNo = 7;
    }

    // 8, 9
    public Expr(Boolean b) {
        if (b) {
            result = "true";
        } else {
            result = "false";
        }
        productionNo = 8;
    }

    // 10
    public Expr(Expr expr) {
        result = expr.toString(0);
        this.expr = expr;
        productionNo = 10;
    }

    // 11, 12, 13, 14
    public Expr(UnaryExpr uexp) {
        result = uexp.toString(0);
        this.uexp = uexp;
        productionNo = 11;
    }

    // 15
    public Expr(BinaryExpr bexp) {
        result = bexp.toString(0);
        this.bexp = bexp;
        productionNo = 15;
    }

    // 16
    public Expr(TernaryExpr texp) {
        result = texp.toString(0);
        this.texp = texp;
        productionNo = 16;
    }

    @Override
    public String toString(int t) {
        return result;
    }

    public Type getType() throws UTDLangException {
        if (productionNo == 1) {
            return new Type(name.getType());
        } else if (productionNo == 2 || productionNo == 3) {
            return new Type(symbolTable.get(id).getType());
        } else if (productionNo == 4) {
            Type curType = new Type("int");
            return curType;
        } else if (productionNo == 5) {
            Type curType = new Type("char");
            return curType;
        } else if (productionNo == 6) {
            Type curType = new Type("string");
            return curType;
        } else if (productionNo == 7) {
            Type curType = new Type("float");
            return curType;
        } else if (productionNo == 8 || productionNo == 9) {
            Type curType = new Type("bool");
            return curType;
        } else if (productionNo == 10) {
            return new Type(expr.getType());
        } else if (productionNo >= 11 && productionNo <= 14) {
            return new Type(uexp.getType());
        } else if (productionNo == 15) {
            return new Type(bexp.getType());
        } else if (productionNo == 16) {
            return new Type(texp.getType());
        }
        return null;
    }

    public void typeCheck() throws UTDLangException {
        if (productionNo == 1) {
            name.typeCheck();
        } else if (productionNo == 2) {
            SymbolType symbolType = symbolTable.get(id);
            if (!symbolType.isFunctionType()) {
                throw new UTDLangException(id + " is not a defined function name" + ". Line: " + this.toString(0));
            }
        } else if (productionNo == 3) {
            SymbolType symbolType = symbolTable.get(id);
            if (!symbolType.isFunctionType()) {
                throw new UTDLangException(id + " is not a defined function name" + ". Line: " + this.toString(0));
            }

            argList.typeCheck();
            ArrayList<Expr> curArgList = argList.getArgList();
            ArrayList<ArgDecl> curArgDeclList = symbolType.getArgDeclList();

            if (curArgList.size() != curArgDeclList.size()) {
                throw new UTDLangException("Number of arguments does not match with function definition. Line: " + this.toString(0));
            }

            for (int idx = 0; idx < curArgList.size(); ++idx) {
                Type curArgType = curArgList.get(idx).getType();
                Type curArgDeclType = curArgDeclList.get(idx).getType();
                if (!SymbolType.equivalent(curArgDeclType, curArgType)) {
                    throw new UTDLangException("Argument type does not match with function definition. Line: " + this.toString(0));
                }
            }
        } else if (productionNo == 10) {
            expr.typeCheck();
        } else if (productionNo >= 11 && productionNo <= 14) {
            uexp.typeCheck();
        } else if (productionNo == 15) {
            bexp.typeCheck();
        } else if (productionNo == 16) {
            texp.typeCheck();
        }
    }
}
