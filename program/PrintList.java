import java.util.ArrayList;

public class PrintList extends Token {
    ArrayList<Expr> printList;
    
    public PrintList(Expr expr) {
        printList = new ArrayList<Expr>();
        printList.add(expr);
    }

    public PrintList prependPrintList(Expr expr) {
        printList.add(0, expr);
        return this;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < printList.size(); ++idx) {
            if (idx != 0) {
                result += ", ";
            }
            result += (printList.get(idx)).toString(0);
        }
        return getTabs(t) + result;
    }

    
    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < printList.size(); ++idx) {
            Expr curExpr = printList.get(idx);
            curExpr.typeCheck();
            if (curExpr.getType().isArray()) {
                throw new UTDLangException("Can not print array type " + curExpr.getType().toString(0) + ". Line: " + this.toString(0));
            }
        }
    }
}
