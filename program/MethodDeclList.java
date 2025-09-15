import java.util.ArrayList;

public class MethodDeclList extends Token {
    ArrayList<MethodDecl> methodDeclList;

    public MethodDeclList() {
        methodDeclList = new ArrayList<MethodDecl>();
    }
    public MethodDeclList prependMethodDeclList(MethodDecl methodDecl) {
        methodDeclList.add(0, methodDecl);
        return this;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < methodDeclList.size(); ++idx) {
            result += (methodDeclList.get(idx)).toString(t);
        }
        return result;
    }

    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < methodDeclList.size(); ++idx) {
            (methodDeclList.get(idx)).typeCheck();
        }
    }
}
