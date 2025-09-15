import java.util.ArrayList;

public class ReadList extends Token {
    ArrayList<Name> readList;
    
    public ReadList(Name name) {
        readList = new ArrayList<Name>();
        readList.add(name);
    }

    public ReadList prependReadList(Name name) {
        readList.add(0, name);
        return this;
    }

    @Override
    public String toString(int t) {
        String result = "";
        for (int idx = 0; idx < readList.size(); ++idx) {
            if (idx != 0) {
                result += ", ";
            }
            result += (readList.get(idx)).toString(0);
        }
        return result;
    }

    public void typeCheck() throws UTDLangException {
        for (int idx = 0; idx < readList.size(); ++idx) {
            Name curName = readList.get(idx);
            curName.typeCheck();

            SymbolType symbolType = symbolTable.get(curName.getId());
            if (symbolType.isFinal()) {
                throw new UTDLangException("Can not read final type " + curName.getId() + ". Line: " + this.toString(0));
            } else if (curName.getType().isArray()) {
                throw new UTDLangException("Can not read array type " + curName.getId() + "[]" + ". Line: " + this.toString(0));
            }
        }
    }
}
