public class Program extends Token {
    String id;
    MemberDecls memberDecls;

    public Program(String id, MemberDecls memberDecls) {
        this.id = id;
        this.memberDecls = memberDecls;
        symbolTable = new SymbolTable();
        symbolTable.setClassName(this.id);
    }

    @Override
    public String toString(int t) {
        String result = getTabs(t) + "class ";
        result += id + " {\n";
        result += memberDecls.toString(t+1);
        result += getTabs(t) + "}\n";
        return result;
    }

    public void typeCheck() throws UTDLangException {
        memberDecls.typeCheck();
    }
}
