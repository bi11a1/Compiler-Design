import java.util.ArrayList;

public class StmtList extends Token {
    ArrayList<Stmt> stmtList;
    
    public StmtList() {
        stmtList = new ArrayList<Stmt>();
    }

    public StmtList prependStmtList(Stmt stmt) {
        stmtList.add(0, stmt);
        return this;
    }

    public ArrayList<Stmt> getStmtList() {
        return stmtList;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < stmtList.size(); ++idx) {
            result += (stmtList.get(idx)).toString(t);
        }
        return result;
    }
    
    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < stmtList.size(); ++idx) {
            (stmtList.get(idx)).typeCheck();
        }
    }
}
