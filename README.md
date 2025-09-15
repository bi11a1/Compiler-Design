# UTDLang Compiler  

A simple compiler for the **UTDLang programming language**, built as part of a compiler design project. The compiler performs **lexical analysis, parsing, and semantic checks** to detect compilation errors in UTDLang programs.  

### Grammar  
The compiler follows the below grammar rules:  

![UTDLang Grammar](https://github.com/bi11a1/Compiler-Design/blob/main/Demo%20Images/UTDLang%20Grammar.png)

The compiler is designed in three main parts:  
1. **Scanner (Lexical Analysis):** Implemented using **JFlex** to tokenize source code.  
2. **Parser (Syntax Analysis):** Implemented using **CUP**, which parses tokens into a parse tree based on grammar rules.  
3. **Semantic Analysis:** Includes a **symbol table** and **type checking** to detect errors in source programs.  

### Execution  

To run the compiler, use the following commands:  
-- To run the code simply type the command: ``> make``
-- The default run contains 'example_input.txt' as the input file
-- To run on all the test cases run the command: ``> make testAll``
-- In the 'Makefile' additional test cases can be added in the 'TEST_FILES' variable



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
