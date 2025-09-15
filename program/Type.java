public class Type extends Token {
    // Type = ["void", "bool", "char", "int", "float", "string", "arr"];

    String datatype;
    boolean arrayType;

    public Type(String datatype) {
        this.datatype = datatype;
        this.arrayType = false;
    }

    public Type(Type newType) {
        this.datatype = newType.datatype;
        this.arrayType = newType.arrayType;
    }

    public void makeArray() {
        this.arrayType = true;
    }

    public String getTypeName() {
        return new String(datatype);
    }

    public boolean isArray() {
        return arrayType;
    }

    @Override
    public String toString(int t) {
        return getTabs(t) + datatype;
        //return getTabs(t) + datatype + (arrayType?"[]":"");
    }
}
