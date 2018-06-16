import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class grammerAnalysis {
	private VerbAnalysis VA;
	private List<token> allToken;
	private List<String> errorMessage; 
	private boolean errorHappen = false;
	private int tokenPtr = 0; 
	
	grammerAnalysis() {
		 VA=new VerbAnalysis(); 
		 errorMessage = new ArrayList<String>();
	}
	
	public boolean Analysis(String mm[]) {
		    VA.Analysis(mm);
		    allToken=VA.returnTokenList();
		    return compile();
	}
	
	
	public boolean compile() {
		program();
		return (!errorHappen);
	}

	private void program() {
		block();
		if (allToken.get(tokenPtr).getType().equals("period")) {
			tokenPtr++;
			if (tokenPtr<allToken.size()) {
				errorHandle(18, "");
			}
		} else {
			errorHandle(17, "");
			System.out.println("17");
		}
	}

	private void block() {
		if (allToken.get(tokenPtr).getType().equals("constsym")) {
			conDeclare();
		}
		if (allToken.get(tokenPtr).getType().equals("varsym")) {
			varDeclare();
		}
		if (allToken.get(tokenPtr).getType().equals("proceduresym")) {
			proc();
		}
		statement();
	}

	private void conDeclare() {
		if (allToken.get(tokenPtr).getType() .equals("constsym")){
			tokenPtr++;
			conHandle();
			while (allToken.get(tokenPtr).getType().equals("comma")||allToken.get(tokenPtr).getType().equals("ident")) {
				if (allToken.get(tokenPtr).getType().equals("comma")) {
					tokenPtr++;
				} else {
					errorHandle(23, "");
				}
				conHandle();
			}
			if (allToken.get(tokenPtr).getType().equals("semicolon")) {
				tokenPtr++;
			} else { 
				errorHandle(0, "");
			}
		} else {
			errorHandle(-1, "");
		}
	}

	private void conHandle() {
	
		String name;
		int value;
		if (allToken.get(tokenPtr).getType().equals("ident")) {
			name = allToken.get(tokenPtr).getValue();
			tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("equal")|| allToken.get(tokenPtr).getType().equals("assignment")) {
				if (allToken.get(tokenPtr).getType().equals("assignment")) {
					errorHandle(3, "");
				}
				tokenPtr++;
				if (allToken.get(tokenPtr).getType().equals("number")) {
					tokenPtr++;
				}
			} else { 
				errorHandle(3, "");
			}
		} else {
			errorHandle(1, "");
		}
	}

	private void varDeclare() {
		String name;
		int value;
		if (allToken.get(tokenPtr).getType().equals("varsym")) {
			    tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("ident")) {
				tokenPtr++;
				while (allToken.get(tokenPtr).getType().equals("comma")|| allToken.get(tokenPtr).getType().equals("ident")) {
					if (allToken.get(tokenPtr).getType().equals("comma")) {
						tokenPtr++;
					} else {
						errorHandle(23, "");
					}
					if(allToken.get(tokenPtr).getType().equals("ident")) {
						tokenPtr++;
					} else { 
						errorHandle(1, "");
						return;
					}
				}
				if (!allToken.get(tokenPtr).getType().equals("semicolon")) { //缂哄皯锛�
					errorHandle(0, "");
					return;
				} else {
					tokenPtr++;
				}
			} else { 
				errorHandle(1, "");
				return;
			}
		} else { 
			errorHandle(-1, "");
			return;
		}
	}

	private void proc() {
		
		if (allToken.get(tokenPtr).getType().equals("proceduresym")) {
			tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("ident")) {
				tokenPtr++;
				
				if (allToken.get(tokenPtr).getType().equals("semicolon")) {
					tokenPtr++;
				} else {
					errorHandle(0, "");
				}
				block();
				while (allToken.get(tokenPtr).getType().equals("semicolon")|| allToken.get(tokenPtr).getType().equals("proceduresym")) {
					if (allToken.get(tokenPtr).getType().equals("semicolon")) {
						tokenPtr++;
					} else {
						errorHandle(0, "");
					}
					proc();
				}
			} else {
				errorHandle(-1, "");
				return;
			}
		}
	}

	private void body() {
		if (allToken.get(tokenPtr).getType().equals("beginsym")) {
			tokenPtr++;
			statement();
			while (allToken.get(tokenPtr).getType().equals("semicolon")|| isHeadOfStatement()) {
				if (allToken.get(tokenPtr).getType().equals("semicolon")) {
					tokenPtr++;
				} else {
					if (!allToken.get(tokenPtr).getType().equals("endsym")) {
						errorHandle(0, "");
					}
				}
				if (allToken.get(tokenPtr).getType().equals("endsym")) {
					errorHandle(21, "");
					break;
				}
				statement();
			}
			if (allToken.get(tokenPtr).getType().equals("endsym")) {
				tokenPtr++;
			} else { 
				errorHandle(7, "");
				return;
			}
		} else {
			errorHandle(6, "");
			return;
		}
	}

	private void statement() {
	if (allToken.get(tokenPtr).getType().equals("ifsym")){
			tokenPtr++;
			condition();
			if (allToken.get(tokenPtr).getType().equals("thensym")) {
				tokenPtr++;
				statement();
				if (allToken.get(tokenPtr).getType().equals("elsesym")) {
					tokenPtr++;
					statement();
				}
			} else {
				errorHandle(8, "");
				return;
			}
		} else if (allToken.get(tokenPtr).getType().equals("whilesym")) {
			tokenPtr++;
			condition();
			if (allToken.get(tokenPtr).getType().equals("dosym")) {
				tokenPtr++;
				statement();
			} else {
				errorHandle(9, "");
				return;
			}
		} else if (allToken.get(tokenPtr).getType().equals("callsym")) {
			tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("ident")) {
				tokenPtr++;
			} else {
				errorHandle(1, "");
				return;
			}
		} else if (allToken.get(tokenPtr).getType().equals("readsym")) {
			tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("leftbrackets")) {
				tokenPtr++;
				if (allToken.get(tokenPtr).getType().equals("ident")) {
					tokenPtr++;
				}
				
				while (allToken.get(tokenPtr).getType().equals("comma")) {
					tokenPtr++;
					if (allToken.get(tokenPtr).getType().equals("ident")) {
						tokenPtr++;
					} else {
						errorHandle(1, "");
						return;
					}
				}
				if (allToken.get(tokenPtr).getType().equals("rightbrackets")) {
					tokenPtr++;
				} else {
					errorHandle(5, "");
					return;
				}
			} else {
				errorHandle(4, "");
				return;
			}
		} else if (allToken.get(tokenPtr).getType().equals("writesym")) {
	     	tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("leftbrackets")) {
				tokenPtr++;
				expression();
				while (allToken.get(tokenPtr).getType().equals("comma")) {
					tokenPtr++;
					expression();
				}
				if (allToken.get(tokenPtr).getType().equals("rightbrackets")) {
					tokenPtr++;
				} else { 
					errorHandle(5, "");
					return;
				}
			} else { 
				errorHandle(4, "");
			}
		} else if (allToken.get(tokenPtr).getType().equals("beginsym")) {
			body();
		} else if (allToken.get(tokenPtr).getType().equals("ident")) {
			tokenPtr++;
			if (allToken.get(tokenPtr).getType().equals("assignment")|| allToken.get(tokenPtr).getType().equals("equal")) {
				if (allToken.get(tokenPtr).getType().equals("equal")) {
					errorHandle(3, "");
				}
				tokenPtr++;
				expression();
			} else {
				errorHandle(3, "");
				return;
			}
		}else {
			errorHandle(1, "");
			return;
		}
	}

	private void condition() {
		if (allToken.get(tokenPtr).getType().equals("oddsym")) {
			tokenPtr++;
			expression();
		} else {
			expression();
			String tmp = allToken.get(tokenPtr).getType();
			tokenPtr++;
			expression();
			if(!tmp.equals("equal")&&!tmp.equals("less")&&!tmp.equals("lessorequal")&&!tmp.equals("more")&&!tmp.equals("moreorequal"))	
				errorHandle(2, ""); 
		}
	}

	private void expression() {
		String tmp = allToken.get(tokenPtr).getType();
		if (tmp .equals("plus")||tmp .equals("minus")) {
			tokenPtr++;
		}
		term();
		while (allToken.get(tokenPtr).getType().equals("plus")||allToken.get(tokenPtr).getType().equals("minus")) {
			tmp = allToken.get(tokenPtr).getType();
			tokenPtr++;
			term();
		}
	}

	private void term() {
	    factor();
		while (allToken.get(tokenPtr).getType().equals("multiply")||allToken.get(tokenPtr).getType().equals("divide")) {
			String tmp = allToken.get(tokenPtr).getType();
			tokenPtr++;
			factor();
		}
	}

	private void factor() {
		if (allToken.get(tokenPtr).getType().equals("ident")) {
			tokenPtr++;
		} else if (allToken.get(tokenPtr).getType().equals("leftbrackets")) {
			tokenPtr++;
			expression();
			if (allToken.get(tokenPtr).getType().equals("rightbrackets")) {
				tokenPtr++;
			} else { 
				errorHandle(5, "");
			}
		} else if (allToken.get(tokenPtr).getType().equals("number")) {
			tokenPtr++;
		} else {
			errorHandle(1, "");
			return;
		}
	}

	private boolean isHeadOfStatement() {
		return (allToken.get(tokenPtr).getType().equals("ifsym")||
				allToken.get(tokenPtr).getType().equals("whilesym")||
				allToken.get(tokenPtr).getType().equals("callsym")||
				allToken.get(tokenPtr).getType().equals("readsym")||
				allToken.get(tokenPtr).getType().equals("writesym")||
				allToken.get(tokenPtr).getType().equals("beginsym")||
				allToken.get(tokenPtr).getType().equals("ident"));
	}

	private void errorHandle(int k, String name) {
		errorHappen = true;
		String error = "";
		switch(k) {
			case -1: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "wrong token";
				break;
			case 0: 
				if (allToken.get(tokenPtr).getType().equals("ident")) {
					error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing ; before " + allToken.get(tokenPtr).getValue(); 
				} else {
					error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing ; before " + allToken.get(tokenPtr).getType(); 
				}
				break;
			case 1:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Identifier illegal";
				break;
			case 2:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "illegal compare symbol"; 
				break;
			case 3: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Const assign must be =";
				break;
			case 4: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing ("; 
				break;
			case 5:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missind )";
				break;
			case 6: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing begin";
				break;
			case 7: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing end";
				break;
			case 8:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing then";
				break;
			case 9: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing do";
				break;
			case 10:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Not exist" + allToken.get(tokenPtr).getValue();
				break;
			case 11:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + allToken.get(tokenPtr).getValue() + "is not a procedure"; 
				break;
			case 12: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + allToken.get(tokenPtr).getValue() + "is not a variable";
				break;
			case 13: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + name + "is not a varible";
				break;
			case 14: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "not exist " + name; 
				break;
			case 15: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Already exist " + name; 
				break;
			case 16:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Number of parameters of procedure " + name + "is incorrect"; 
				break;
			case 17: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing .";
				break;
			case 18: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "too much code after .";
				break;
			case 19: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing until"; 
				break;
			case 20: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Assign must be :=";
				break;
			case 21:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "; is no need before end";
				break;
			case 22: 
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "; is no need before ubtil";
				break;
			case 23:
				error = "Error happened in line " + allToken.get(tokenPtr).getLine() + ":" + "Missing ,";
				break;
		}
		errorMessage.add(error);
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public void showAllToken() {
		for (int i = 0; i < allToken.size(); i++) {
			System.out.println(allToken.get(i).getType() + " " + allToken.get(i).getLine() + " " + allToken.get(i).getValue());
		}
	}

	public List<token> getAllToken() {
		return allToken;
	}
}
