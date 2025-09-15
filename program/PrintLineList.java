public class PrintLineList extends Token {
    PrintList printList;

    public PrintLineList() {
        printList = null;
    }

    public PrintLineList(PrintList printList) {
        this.printList = printList;
    }

    @Override
    public String toString(int t) {
        if (printList == null) {
            return "";
        } else {
            return printList.toString(t);
        }
    }
    
    public void typeCheck() throws UTDLangException {
        if (printList != null) {
            printList.typeCheck();
        }
    }
}
