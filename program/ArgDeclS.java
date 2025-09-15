public class ArgDeclS extends Token {
    ArgDeclList argDeclList;
    public ArgDeclS() {
        argDeclList = null;
    }
    public ArgDeclS(ArgDeclList argDeclList) {
        this.argDeclList = argDeclList;
    }

    public ArgDeclList getArgDeclList() {
        return argDeclList;
    }

    @Override
    public String toString(int t) {
        if (argDeclList == null) {
            return "";
        }
        return argDeclList.toString(0);
    }
    
    public void typeCheck() throws UTDLangException {
        if (argDeclList != null) {
            argDeclList.typeCheck();
        }
    }
}
