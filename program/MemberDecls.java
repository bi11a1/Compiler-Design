import java.util.ArrayList;

public class MemberDecls extends Token {
    ArrayList<FieldDecl> fieldDecl = new ArrayList<FieldDecl>();
    MemberDecls memberDecls;
    MethodDeclList methodDeclList;

    public MemberDecls(FieldDecl newFieldDecl, MemberDecls memberDecls) {
        fieldDecl.add(0, newFieldDecl);
        this.memberDecls = memberDecls;
        this.methodDeclList = null;
    }

    public MemberDecls(MethodDeclList methodDeclList) {
        this.methodDeclList = methodDeclList;
    }

    // public MemberDecls(FieldDeclList fieldDeclList, MethodDeclList methodDeclList) {
    //     this.fieldDeclList = fieldDeclList;
    //     this.methodDeclList = methodDeclList;
    // }

    @Override
    public String toString(int t) {
        String result = "";
        if (methodDeclList == null) {
            for (int idx = 0; idx < fieldDecl.size(); ++idx) {
                result += (fieldDecl.get(idx)).toString(t);
            }
            result += memberDecls.toString(t);
        } else {
            result = methodDeclList.toString(t);
        }
        return result;
    }

    public void typeCheck() throws UTDLangException {
        symbolTable.startScope();
        startChecking();
        symbolTable.endScope();
    }

    public void startChecking() throws UTDLangException {
        if (methodDeclList == null) {
            for (int idx = 0; idx < fieldDecl.size(); ++idx) {
                (fieldDecl.get(idx)).typeCheck();
            }
            memberDecls.startChecking();
        } else {
            methodDeclList.typeCheck();
        }
    }
}
