def getTerminalName(text):
    """
    To ease the task of retrieving terminal name from the given pattern
    text: class                    { return newSym(sym.CLASS, "class"); }
    return: CLASS
    text: "<="                    { return newSym(sym.LESSEQUAL, "<="); }
    return: LESSEQUAL
    """
    start = text.index('.')
    end = text.index(',') # Note: Need to manually handle for comma
    return text[start+1:end]

res = []
patterns = []
while(True):
    text = input()
    if text == "stop":
        break
    patterns.append(text)

serial = 2
for pattern in patterns:
    # terminal NAME //serial number
    print("terminal ", getTerminalName(pattern), "; //", serial, sep='')
    serial += 1
