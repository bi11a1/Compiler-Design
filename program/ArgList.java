import java.util.ArrayList;

public class ArgList extends Token {
    ArrayList<Expr> args;

    public ArgList() {
        args = new ArrayList<Expr>();
    }

    public ArgList prependArgList(Expr expr) {
        args.add(0, expr);
        return this;
    }

    public ArrayList<Expr> getArgList() {
        return args;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < args.size(); idx++) {
            if (idx != 0) result += ", ";
            result += (args.get(idx)).toString(0);
        }
        return result;
    }
    
    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < args.size(); idx++) {
            (args.get(idx)).typeCheck();
        }
    }
}
