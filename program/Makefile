JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
		$(JAVAC) -cp $(CP) $*.java

FILE=    	Lexer.java      	parser.java    	sym.java \
    		LexerTest.java		ScannerTest.java 	TypeCheckingTest.java\
			Token.java Program.java Expr.java UnaryExpr.java BinaryExpr.java TernaryExpr.java \
			Name.java PrintList.java PrintLineList.java ArgList.java Type.java \
			IfEnd.java OptionalSemi.java Stmt.java StmtList.java ReadList.java \
			ArgDecl.java ArgDeclList.java ArgDeclS.java FieldDecl.java FieldDeclList.java \
			MemberDecls.java MethodDeclList.java OptionalExpr.java OptionalFinal.java ReturnType.java\
			UTDLangException.java SymbolTable.java SymbolType.java Pair.java

run: example_input.txt

all: Lexer.java parser.java $(FILE:java=class)

# basicTest.txt: all
# 		$(JAVA) -cp $(CP) LexerTest basicTest.txt > basicTest-output.txt
# 		cat basicTest.txt
# 		cat -n basicTest-output.txt

# custom_input.txt: all
# 	$(JAVA) -cp $(CP) ScannerTest custom_input.txt > custom_output.txt
# 	cat custom_input.txt
# 	cat -n custom_output.txt

# example_input.txt: all
# 	$(JAVA) -cp $(CP) ScannerTest example_input.txt > example_output.txt
# 	cat example_input.txt
# 	cat -n example_output.txt

example_input.txt: all
	$(JAVA) -cp $(CP) TypeCheckingTest example_input.txt > example_output.txt
	# cat example_input.txt
	cat -n example_output.txt


TEST_FILES = badDec.as badInc.as badNegation.as badString.as badTernaryCond.as badTernaryTypes.as boolToFloat.as \
			boolToInt.as callNonExistFunc.as charToFloat.as charToInt.as floatToInt.as fullValidProgram.as \
			incompatBinary.as intArrayToBoolArray.as noReturn.as reassignFinal.as redefMethod.as redefVar.as \
			redefVarAsMethod.as returnTypeBad.as

testAll: all
	@for file in $(TEST_FILES); do \
		$(JAVA) -cp $(CP) TypeCheckingTest $$file > $${file%.as}-output.txt; \
		cat $$file; \
		cat -n $${file%.as}-output.txt; \
	done


# Use loop to run on all the TEST_FILES
# testAll: all
# 	@for file in $(TEST_FILES); do \
# 		$(JAVA) -cp $(CP) ScannerTest $$file > $${file%.txt}-output.txt; \
# 		cat $$file; \
# 		cat -n $${file%.txt}-output.txt; \
# 	done

# Add additional file names to test (keep the files in the same directory)
# TEST_FILES = basicFails.txt basicRegex.txt basicTerminals.txt

# Use loop to run on all the TEST_FILES
# testAll: all
# 	@for file in $(TEST_FILES); do \
# 		$(JAVA) -cp $(CP) LexerTest $$file > $${file%.txt}-output.txt; \
# 		cat $$file; \
# 		cat -n $${file%.txt}-output.txt; \
# 	done

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
