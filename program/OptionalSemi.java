public class OptionalSemi extends Token {
    boolean semiColon;
    public OptionalSemi(boolean semiColon) {
        this.semiColon = semiColon;
    }

    @Override
    public String toString(int t) {
        if (semiColon) {
            return ";\n";
        } else {
            return "\n";
        }
    }
}
