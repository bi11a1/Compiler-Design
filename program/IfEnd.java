public class IfEnd extends Token {
    StmtList stmtList;

    public IfEnd() {
        stmtList = null;
    }
    public IfEnd(StmtList stmtList) {
        this.stmtList = stmtList;
    }

    @Override
    public String toString(int t) {
        if (stmtList == null) {
            return "";
        }
        String result = getTabs(t) + "else {\n";
        result += stmtList.toString(t+1);
        result += getTabs(t) + "}\n";
        return result;
    }

    public void typeCheck() throws UTDLangException {
        if (stmtList != null) {
            stmtList.typeCheck();
        }
    }
}
