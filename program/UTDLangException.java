class UTDLangException extends Exception
{
    String error;
    
    public UTDLangException(String s) {
        error = s;
    }

    public String toString() {
        String errorMsg = "Error: ";
        errorMsg = errorMsg + "class<" + SymbolTable.getClassName() + ">: ";

        SymbolTable symbolTable = Token.symbolTable;
        for (Pair<String, String> curScopeName : symbolTable.getScopeName()) {
            errorMsg = errorMsg + curScopeName.getKey() + " " + curScopeName.getValue() + ": ";
        }
        errorMsg = errorMsg + error;
        return errorMsg;
    }
}