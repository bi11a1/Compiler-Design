# UTDLang Compiler  

A simple compiler for the **UTDLang programming language**, built as part of a compiler design project. The compiler performs **lexical analysis, parsing, and semantic checks** to detect compilation errors in UTDLang programs.  

### Grammar  
The compiler follows the grammar rules of UTDLang (see diagram below):  

![UTDLang Grammar](https://github.com/bi11a1/Bengali-Digit-Recognizer/blob/master/Demo%20Images/UTDLang%20Grammar.png)

### Components  
The compiler is designed in three main parts:  
1. **Scanner (Lexical Analysis):** Implemented using **JFlex** to tokenize source code.  
2. **Parser (Syntax Analysis):** Implemented using **CUP**, which parses tokens into a parse tree based on grammar rules.  
3. **Semantic Analysis:** Includes a **symbol table** and **type checking** to detect errors in source programs.  

### Example Programs  

**❌ Code with Error**  
```utdlang
class x {
    void foo() {
    }
    int foo(int x) {
        return 1;
    }
}

// Output:
// Error: class<x>: Tried to redeclare method foo().
