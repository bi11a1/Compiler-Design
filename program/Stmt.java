import java.util.ArrayList;

public class Stmt extends Token {
    Expr expr;
    StmtList stmtList;
    IfEnd ifEnd;
    Name name;
    ReadList readList;
    PrintList printList;
    PrintLineList printLineList;
    ArgList argList;
    OptionalSemi optionalSemi;
    String id;
    int productionNo = -1;
    FieldDeclList fieldDeclList;
    Stmt stmt;

    public Stmt(int productionNo) {
        this.productionNo = productionNo;
    }
    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void setStmtList(StmtList stmtList) {
        this.stmtList = stmtList;
    }

    public void setStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    public void setIfEnd(IfEnd ifEnd) {
        this.ifEnd = ifEnd;
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    public void setReadList(ReadList readList) {
        this.readList = readList;
    }

    public void setPrintList(PrintList printList) {
        this.printList = printList;
    }

    public void setPrintLineList(PrintLineList printLineList) {
        this.printLineList = printLineList;
    }

    public void setArgList(ArgList argList) {
        this.argList = argList;
    }

    public void setOptionalSemi(OptionalSemi optionalSemi) {
        this.optionalSemi = optionalSemi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFieldDeclList(FieldDeclList fieldDeclList) {
        this.fieldDeclList = fieldDeclList;
    }

    public Expr getExpr() {
        return this.expr;
    }
    
    public int getProductionNo() {
        return productionNo;
    }

    @Override
    public String toString(int t) {
        String result = "";

        if (productionNo == 1) {
            result = getTabs(t) + "if (" + expr.toString(0) + ") {\n";
            result += stmtList.toString(t+1);
            result += getTabs(t) + "}\n";
            result += ifEnd.toString(t);
        } else if (productionNo == 2) {
            result = getTabs(t) + "while (" + expr.toString(0) + ")\n";
            if (stmt.getProductionNo() == 13) {
                result += stmt.toString(t);
            } else {
                result += stmt.toString(t+1);
            }
            result += getTabs(t) + "\n";
        } else if (productionNo == 3) {
            result = getTabs(t) + name.toString(0) + " = " + expr.toString(0) + ";\n";
        } else if (productionNo == 4) {
            result = getTabs(t) + "read(" + readList.toString(0) + ");\n";
        } else if (productionNo == 5) {
            result = getTabs(t) + "print(" + printList.toString(0) + ");\n";
        } else if (productionNo == 6) {
            result = getTabs(t) + "printline(" + printLineList.toString(0) + ");\n";
        } else if (productionNo == 7) {
            result = getTabs(t) + id + "();\n";
        } else if (productionNo == 8) {
            result = getTabs(t) + id + "(" + argList.toString(0) + ");\n";
        } else if (productionNo == 9) {
            result = getTabs(t) + "return ;\n";
        } else if (productionNo == 10) {
            result = getTabs(t) + "return " + expr.toString(0) + ";\n";
        } else if (productionNo == 11) {
            result = getTabs(t) + name.toString(0) + "++;\n";
        } else if (productionNo == 12) {
            result = getTabs(t) + name.toString(0) + "--;\n";
        } else if (productionNo == 13) {
            result = getTabs(t) + "{\n";
            result += fieldDeclList.toString(t+1);
            result += stmtList.toString(t+1);
            result += getTabs(t) + "}";
            result += optionalSemi.toString(0);
        } else {
            result = "Assign the production number!\n";
        }

        return result;
    }
    
    public void typeCheck() throws UTDLangException {
        if (productionNo == 1) {
            expr.typeCheck();

            if (!SymbolType.isBool(expr)) {
                throw new UTDLangException("Expression " + expr.toString(0) + " can not be resolved to bool. Line: " + this.toString(0));
            }
            
            symbolTable.startScope();
            stmtList.typeCheck();
            symbolTable.endScope();

            symbolTable.startScope();
            ifEnd.typeCheck();
            symbolTable.endScope();
        } else if (productionNo == 2) {
            expr.typeCheck();

            if (!SymbolType.isBool(expr)) {
                throw new UTDLangException("Expression " + expr.toString(0) + " can not be resolved to bool. Line: " + 
                                                            this.toString(0));
            }
            
            symbolTable.startScope();
            stmt.typeCheck();
            symbolTable.endScope();
        } else if (productionNo == 3) {
            name.typeCheck();
            expr.typeCheck();

            if (name.getType().isArray() && expr.getType().isArray()) {
                if (!name.getType().getTypeName().equals(expr.getType().getTypeName())) {
                    throw new UTDLangException("Can not assign array of type " + expr.getType().toString(0) + " into " +
                                            name.getType().toString(0) + ". Line: " + this.toString(0));
                }
            }

            if (!SymbolType.equivalent(name.getType(), expr.getType())) {
                throw new UTDLangException("Can not assign " + expr.getType().toString(0) + " to " 
                                        + name.getType().toString(0) + ". Line: " + this.toString(0));
            }
            
            if (symbolTable.get(name.getId()).isFinal()) {
                throw new UTDLangException("Can not reassign in final type. Line: " + this.toString(0));
            }
        } else if (productionNo == 4) {
            readList.typeCheck();
        } else if (productionNo == 5) {
            printList.typeCheck();
        } else if (productionNo == 6) {
            printLineList.typeCheck();
        } else if (productionNo == 7) {
            SymbolType symbolType = symbolTable.get(id);
            if (!symbolType.isFunctionType()) {
                throw new UTDLangException(id + " is not a defined function name" + ". Line: " + this.toString(0));
            }
        } else if (productionNo == 8) {
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
        } else if (productionNo == 9) {
            /// TODO: match return type
            // result = getTabs(t) + "return ;\n";
        } else if (productionNo == 10) {
            expr.typeCheck();
            /// TODO: match return type
            // result = getTabs(t) + "return " + expr.toString(0) + ";\n";
        } else if (productionNo == 11) {
            name.typeCheck();

            SymbolType symbolType = symbolTable.get(name.getId());
            if (symbolType.isFinal()) {
                throw new UTDLangException("Can not increment final type. Line: " + this.toString(0));
            } else if (symbolType.isArray()) {
                throw new UTDLangException("Can not increment array type. Line: " + this.toString(0));
            } else if (!(symbolType.getType().getTypeName().equals("int") || symbolType.getType().getTypeName().equals("float"))) {
                throw new UTDLangException("Increment is not allowed on " + symbolType.getType().toString(0) + ". Line: " + this.toString(0));
            }
        } else if (productionNo == 12) {
            name.typeCheck();
            
            SymbolType symbolType = symbolTable.get(name.getId());
            if (symbolType.isFinal()) {
                throw new UTDLangException("Can not decrement final type. Line: " + this.toString(0));
            } else if (symbolType.isArray()) {
                throw new UTDLangException("Can not decrement array type. Line: " + this.toString(0));
            } else if (!(symbolType.getType().getTypeName().equals("int") || symbolType.getType().getTypeName().equals("float"))) {
                throw new UTDLangException("Decrement is not allowed on " + symbolType.getType().toString(0) + ". Line: " + this.toString(0));
            }
        } else if (productionNo == 13) {
            symbolTable.startScope();
            fieldDeclList.typeCheck();
            stmtList.typeCheck();
            symbolTable.endScope();            
        }
    }
}
