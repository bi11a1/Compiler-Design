import java.util.ArrayList;

public class FieldDeclList extends Token {
    ArrayList<FieldDecl> fieldDeclList;

    public FieldDeclList() {
        fieldDeclList = new ArrayList<FieldDecl>();
    }
    public FieldDeclList prependFieldDeclList(FieldDecl fieldDecl) {
        fieldDeclList.add(0, fieldDecl);
        return this;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < fieldDeclList.size(); ++idx) {
            result += (fieldDeclList.get(idx)).toString(t);
        }
        return result;
    }
    
    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < fieldDeclList.size(); ++idx) {
            (fieldDeclList.get(idx)).typeCheck();
        }
    }
}
