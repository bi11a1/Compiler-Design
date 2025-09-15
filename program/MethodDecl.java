import java.util.ArrayList;

public class MethodDecl extends Token {
    Type returnType;
    String id;
    ArgDeclS argDeclS;
    FieldDeclList fieldDeclList;
    StmtList stmtList;
    OptionalSemi optionalSemi;

    public MethodDecl(Type returnType, String id, ArgDeclS argDeclS, FieldDeclList fieldDeclList, StmtList stmtList, OptionalSemi optionalSemi) {
        this.returnType = returnType;
        this.id = id;
        this.argDeclS = argDeclS;
        this.fieldDeclList = fieldDeclList;
        this.stmtList = stmtList;
        this.optionalSemi = optionalSemi;
    }

    public MethodDecl(String id, ArgDeclS argDeclS, FieldDeclList fieldDeclList, StmtList stmtList, OptionalSemi optionalSemi) {
        this.returnType = null; // means void data type
        this.id = id;
        this.argDeclS = argDeclS;
        this.fieldDeclList = fieldDeclList;
        this.stmtList = stmtList;
        this.optionalSemi = optionalSemi;
    }

    public Type getReturnType() {
        Type type = returnType;
        if (type == null) { // null means void type
            type = new Type("void");
        }
        return type;
    }

    public ArgDeclList getArgDeclList() {
        return argDeclS.getArgDeclList();
    }

    @Override
    public String toString(int t) {
        String result = getTabs(t);
        if (returnType == null) {
            result += "void";
        } else {
            result += returnType.toString(0);
        }
        result += " " + id + "(";
        result += argDeclS.toString(0) + ") {\n";
        result += fieldDeclList.toString(t+1);
        result += stmtList.toString(t+1);
        result += getTabs(t) + "}";
        result += optionalSemi.toString(0);
        return result;
    }

    public void typeCheck() throws UTDLangException {
        symbolTable.addMethod(id, this);
        
        symbolTable.startScope();
        symbolTable.addScopeName(new Pair<String, String>(getReturnType().toString(0), id));

        argDeclS.typeCheck();
        fieldDeclList.typeCheck();
        stmtList.typeCheck();
        
        ArrayList<Stmt> allStmtList = stmtList.getStmtList();
        boolean returnedVal = false;
        for (Stmt curStmt : allStmtList) {
            if (curStmt.getProductionNo() == 9) {
                if (!getReturnType().getTypeName().equals("void")) {
                    throw new UTDLangException("Return type should be " + getReturnType().toString(0) + " instead of void " +
                                                ". Line: " + curStmt.toString(0));
                }
            } else if (curStmt.getProductionNo() == 10) {
                Expr curExpr = curStmt.getExpr();
                if (!SymbolType.equivalent(getReturnType(), curExpr.getType())) {
                    throw new UTDLangException("Return type should be " + getReturnType().toString(0) + " instead of "
                                                + curExpr.getType().toString(0) + ". Line: " + curStmt.toString(0));
                } else {
                    returnedVal = true;
                }
            }
        }
        if (!getReturnType().getTypeName().equals("void")) {
            if (returnedVal == false) {
                throw new UTDLangException("Function " + id + " should return a value of type " + getReturnType().toString(0) + ".");
            }
        }

        symbolTable.removeScopeName();
        symbolTable.endScope();
    }
}
