import java.util.ArrayList;

public class ArgDeclList extends Token {
    ArrayList<ArgDecl> argDeclList;

    public ArgDeclList(ArgDecl argDecl) {
        argDeclList = new ArrayList<ArgDecl>();
        argDeclList.add(argDecl);
    }
    public ArgDeclList prependArgDeclList(ArgDecl argDecl) {
        argDeclList.add(0, argDecl);
        return this;
    }

    public ArrayList<ArgDecl> getArgDeclList() {
        return argDeclList;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < argDeclList.size(); ++idx) {
            if (idx != 0) {
                result += ", ";
            }
            result += (argDeclList.get(idx)).toString(0);
        }
        return result;
    }

    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < argDeclList.size(); ++idx) {
            (argDeclList.get(idx)).typeCheck();
        }
    }
}
