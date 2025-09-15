public class OptionalFinal extends Token {
    String result;

    public OptionalFinal() {
        result = "";
    }
    public OptionalFinal(String finalType) {
        result = finalType + " ";
    }

    @Override
    public String toString(int t) {
        return result;
    }
}
