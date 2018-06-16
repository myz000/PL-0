

import java.util.ArrayList;

public class VerbAnalysis {
	ArrayList TokenList=new ArrayList();
	int line=0;
	
	
	
	
	// String stayWord[]= {"const","var","procedure","begin","end","odd","if","then","call"," while","do","read","write"};
	 String stayWord[]= {"CONST","VAR","PROCEDURE","BEGIN","END","ODD","IF","THEN","ELSE","CALL","WHILE","DO","READ","WRITE"};
		
	 String enumStayWord[]= {"constsym","varsym","proceduresym","beginsym","endsym",
				"oddsym"," ifsym","thensym","elsesym","callsym","whilesym","dosym","readsym","writesym"};
	
	public ArrayList returnTokenList() {
		return TokenList;
	}
	
	
	 
	   public boolean isNumber(char ch) {
		   if(ch>='0'&&ch<='9')  return true;
	        else return false;
		   
	   };

		public boolean isCase(char ch) {
			

	        if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z'))
	            return true;
	        else return false;
		};

		public boolean isCaculationSymbol(char ch) {
	        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='>'||ch=='<'||ch=='='||ch=='#'||ch==':')

	            return true;

	        else return false;
		};

		public boolean isBandSymbol(char ch) {

		    if(ch=='('||ch==')'||ch==','||ch==';'||ch=='.')

		        return true;

		    else return false;
		};
		
		public void saveBandSymbol(char ch){
			String a="null";
			switch(ch) {
			case '(':a="leftbrackets";break;
			case ')':a="rightbrackets";break;
			case ',':a="comma";break;
			case ';':a="semicolon";break;
			case '.':a="period";break;
			default:a="error";
			}

			token t=new token(a,line,ch+"");
			TokenList.add(t);
			
		}
		

		public int isStayWord(String str) {
		    int aa;
		    for(aa=0;aa<stayWord.length;aa++)
		    {
		    	if(str.equals(stayWord[aa])) 
		    	break;
		    	}
		    System.out.println("aa:"+aa);
		    return aa;
		};

		public void getInputStreamFromFile(String str) {
			//���ļ��л�ȡ���������Ĵ����
			
		};
		

		public void calulationString(String str) {
			
		};

		
		
		
		public void Analysis(String str2[]) {
	
			for(int j=0;j<str2.length;j++) {
			    String str1[]=str2[j].split("\\s+");
			       line++;
			for(int i=0;i<str1.length;i++) {
				String str=str1[i];
				System.out.println(str);
		  int bb=str.length();
		  int cc=0;
		  while(cc<bb){
			  System.out.println(str.charAt(cc));
          if(isCase(str.charAt(cc))){
			   if(cc==0&&isStayWord(str)<14){
				    int aa=isStayWord(str);
			
				    System.out.println(str);
				  
					 token t=new token(enumStayWord[aa],line,str);
						TokenList.add(t);
				    break;
			   }
			   int m=cc;
			   while(cc<bb&&(!isCaculationSymbol(str.charAt(cc)))&&(!isBandSymbol(str.charAt(cc)))) {
				     cc++;   
			   }
			   String s1=str.substring(m, cc);
			   System.out.println("aaa:"+s1);
				if(isStayWord(s1)<14){
					 int aa=isStayWord(s1);
					 token t=new token(enumStayWord[aa],line,s1);
					   TokenList.add(t);
				}
				else {
				 token t=new token("ident",line,s1);
					TokenList.add(t);
				}
          }
          else if(isNumber(str.charAt(cc))){
        	  int s=0;
        	  while(cc<bb&&isNumber(str.charAt(cc))){
        		  s=s*10+str.charAt(cc)-'0';
        		  cc++;
        	  }
   
				 token t=new token("number",line,s+"");
					TokenList.add(t);
          }
          else if(isBandSymbol(str.charAt(cc))) {
               saveBandSymbol(str.charAt(cc));
               cc++;
          }
          else if(isCaculationSymbol(str.charAt(cc))) {
        	  char m=str.charAt(cc);
        	  String s=m+"";
        	  String dd = null;
        	  if(m=='+') dd="plus";  
              else if(m== '-') dd="minus";  
              else if(m== '*') dd="multiply";  
              else if(m== '/') dd="divide";  
              else if(m== '=') dd="equal";  
              else if(m == ':'){  
                  if(cc+1<bb && str.charAt(cc+1) == '='){  
                      dd="assignment";
                      s=":=";
                      cc++;  
                  }  
                  else  
                     dd="error";  
              }  
              else if(m== '#') dd="hashtag";  
              else if(m == '>'){  
                  if(cc+1<bb&&str.charAt(cc+1)== '='){  
                     dd="moreorequal";
                     s=">=";
                      cc++;  
                  }  
                  else  
                     dd="more";
              }  
              else if(m == '<'){  
                  if(cc+1<bb && str.charAt(cc+1)=='='){  
                      dd="lessorequal";
                      s="<=";
                      cc++;  
                  }  
                  else  
                     dd="less";
              }  
        
				 token t=new token(dd,line,s);
					TokenList.add(t);
				 cc++;
          }
          else {
				 token t=new token("error",line,str.charAt(cc)+"");
					TokenList.add(t);
				 cc++;
          }
		  }	 
	     }  
			}
		}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		     

		

	}

	
	
}
