import java.util.ArrayList;

public class SymbolType {
    FieldDecl fieldDecl;
    MethodDecl methodDecl;
    boolean functionType;
    
    public SymbolType(FieldDecl fieldDecl) {
        this.fieldDecl = fieldDecl;
        functionType = false;
    }

    public SymbolType(MethodDecl methodDecl) {
        this.methodDecl = methodDecl;
        functionType = true;
    }

    public FieldDecl getFieldDecl() {
        assert(functionType == false);
        return fieldDecl;
    }

    public MethodDecl getMethodDecl() {
        assert(functionType == true);
        return methodDecl;
    }

    public ArrayList<ArgDecl> getArgDeclList() {
        return methodDecl.getArgDeclList().getArgDeclList();
    }

    public boolean isFunctionType() {
        return functionType;
    }

    public Type getType() {
        if (functionType) {
            return methodDecl.getReturnType();
        } else {
            return fieldDecl.getType();
        }
    }

    public boolean isFinal() {
        return !functionType && fieldDecl.isFinal();
    }

    public boolean isArray() {
        return !functionType && fieldDecl.getType().isArray();
    }

    public static boolean isBool(Expr expr) throws UTDLangException {
        return SymbolType.equivalent(new Type("bool"), expr.getType());
    }

    public static boolean isInt(Expr expr) throws UTDLangException {
        return SymbolType.equivalent(new Type("int"), expr.getType());
    }

    public static boolean isFloat(Expr expr) throws UTDLangException {
        return SymbolType.equivalent(new Type("float"), expr.getType());
    }

    public static boolean equivalent(Type lhsType, Type rhsType) {
        if (lhsType.isArray() != rhsType.isArray()) {
            return false;
        }

        if (rhsType.getTypeName().equals(lhsType.getTypeName())) {
            return true;
        } else if (rhsType.getTypeName().equals("int")) {
            if (lhsType.getTypeName().equals("bool")) {
                return true;
            } else if (lhsType.getTypeName().equals("float")) {
                return true;
            } else if (lhsType.getTypeName().equals("string")) {
                return true;
            }
        } else if (lhsType.getTypeName().equals("string")) {
            if (!rhsType.isArray()) {
                return true;
            }
        }

        return false;
    }
}
