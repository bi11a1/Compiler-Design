public class ReturnType extends Token {
    String result;
    
    public ReturnType(Type t) {
        result = t.toString(0);
    }
    public ReturnType(String voidType) {
        result = voidType;
    }

    @Override
    public String toString(int t) {
        return result;
    }
}
