import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Java_LexAnalysis
{
    private static StringBuffer prog = new StringBuffer();

    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
    {
/**
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            prog.append(sc.nextLine());
        }
**/
        /**
         for(int i=0;i<prog.length();++i)
         {
         System.out.println(prog.charAt(i));
         }
         **/
        prog.append("#include <stdio.h> int main ()"+
                "{" +
                "  /* 局部变*量声明 */" +
                "  int a, b;" +
                "  int c;" +
                " " +
                "  /* 实际初始化 */" +
                "  a = 10;" +
                "  b = 20;" +
                "  c = a + b;" +
                " " +
                "  printf (\"value of a = %d, b = %d and c = %d\\n\", a, b, c);" +
                " " +
                "  return 0;" +
                "}");
    }


    // add your method here!!

    static String[] reservedWords = new String[]{"auto","break","case","char","const","continue",
            "default","do","double","else","enum","extern","float",
            "for","goto","if","int","long","register","return","short","signed","sizeof",
            "static","struct","switch","typedef","union","unsigned","void","volatile","while","true","false"};

    static String[]  symbols = new String[]{"-","--","-=","->","!","!=","%","%=","&","&&","&=",
            "(",")","*","*=",",",".","/","/=",":",";","?","[","]","^","^=","{","|","||",
            "|=","}","~","+","++","+=","<","<<","<<=","<=","=","==",">",">=",">>",">>=","\""};



    static List<String> tokens = new ArrayList<>();
    static List<Integer> tokenNums = new ArrayList<>();
    /**
     *  you should add some code in this method to achieve this lab
     */
    static boolean isLetter(char ch)
    {
        return ch>='a'&&ch<='z'||ch>='A'&&ch<='Z';
    }

    static boolean isDigit(char ch)
    {
        return ch>='0'&&ch<='9';
    }

    private static void analysis()
    {
        read_prog();
        //System.out.print(prog);
        int length=prog.length();
        //将识别出的token加入token数组中
        int index=0;
        while(index<prog.length())
        {
            StringBuilder token = new StringBuilder("");
            char ch = prog.charAt(index);
            if(ch==' '||ch=='\t'){
                index++;
                continue;
            }
            /*////sd*/
            else if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||ch=='_')
            {
                token.append(ch);
                index++;
                char nextCh= prog.charAt(index);
                while(isDigit(nextCh)||isLetter(nextCh)||nextCh=='_')
                {
                    token.append(nextCh);
                    index++;
                    nextCh=prog.charAt(index);
                }
                tokens.add(token.toString());
                tokenNums.add(0);
            }
            else if(isDigit(ch))
            {
                token.append(ch);
                index++;
                char nextCh=prog.charAt(index);
                while(isDigit(nextCh)||nextCh=='.')
                {
                    token.append(nextCh);
                    index++;
                    nextCh=prog.charAt(index);
                }
                tokens.add(token.toString());
                tokenNums.add(80);
            }
            else if(ch=='/')
            {
                token.append(ch);
                index++;
                char nextCh=prog.charAt(index);
                if(nextCh=='/')
                {
                    do{
                        token.append(nextCh);
                        index++;
                        nextCh=prog.charAt(index);
                    }while(nextCh!='\t');
                    tokens.add(token.toString());
                    tokenNums.add(79);
                }
                else if(nextCh=='*')
                {
                    do{
                        token.append(nextCh);
                        index++;
                        nextCh=prog.charAt(index);
                        if(nextCh=='*')
                        {
                            char nextNextCh = prog.charAt(index+1);
                            if(nextNextCh=='/'){
                                index+=2;
                                token.append(nextCh);
                                token.append(nextNextCh);
                                break;
                            }
//                            else{
//                               index--;
//                            }
                        }
                    }while(true);
                    tokens.add(token.toString());
                    tokenNums.add(79);
                }
                else if(nextCh=='='){
                    token.append(nextCh);
                    index++;
                    tokens.add(token.toString());
                    tokenNums.add(1);
                }
                else
                {
                    tokens.add(token.toString());
                    tokenNums.add(1);
                }
            }
            else
            {
                token.append(ch);
                index++;
                char nextCh;
                if(index<length)
                    nextCh = prog.charAt(index);
                else {
                    tokens.add(token.toString());
                    tokenNums.add(1);
                    break;
                }
                if(ch=='-')
                {
                    switch (nextCh){
                        case '-':
                        case '=':
                        case '>':
                            token.append(nextCh);
                            index++;
                            break;
                    }
                }
                if(ch=='!')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='%')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='&')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                    else if(nextCh=='&')
                    {
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='*')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='^')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='|')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                    else if(nextCh=='|')
                    {
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='+')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                    else if(nextCh=='+')
                    {
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='<')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                    else if(nextCh=='<')
                    {
                        token.append(nextCh);
                        index++;
                        nextCh=prog.charAt(index);
                        if(nextCh=='=')
                        {
                            token.append(nextCh);
                            index++;
                        }
                    }
                }
                if(ch=='=')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                }
                if(ch=='>')
                {
                    if(nextCh=='='){
                        token.append(nextCh);
                        index++;
                    }
                    else if(nextCh=='>')
                    {
                        token.append(nextCh);
                        index++;
                        nextCh=prog.charAt(index);
                        if(nextCh=='=')
                        {
                            token.append(nextCh);
                            index++;
                        }
                    }
                }
                if(ch=='\"')
                {
                    tokens.add(token.toString());
                    tokenNums.add(1);
                    StringBuilder tokenIn = new StringBuilder(""); //在双引号内部的标识符
                    while(nextCh!='\"')
                    {
                        tokenIn.append(nextCh);
                        index++;
                        nextCh=prog.charAt(index);
                    }
                    tokens.add(tokenIn.toString());
                    tokenNums.add(81);
                    index++;
                    tokens.add("\"");
                    tokenNums.add(1);


                    continue;
                }
//                if(ch=='%')
//                {
//                    if(nextCh=='d'||nextCh=='u'||nextCh=='f'
//                    ||nextCh=='x'||nextCh=='c'||nextCh=='o'||nextCh=='s')
//                    {
//                        token.append(nextCh);
//                        index++;
//                        tokens.add(token.toString());
//                        tokenNums.add(81);
//                        continue;
//                    }
//                }
                tokens.add(token.toString());
                tokenNums.add(1);
            }
        }


    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        analysis();
        //遍历token数组，匹配标号输出
        for(int i = 0 ;i<tokens.size();++i)
        {
            int num=0;
            if(tokenNums.get(i)==0)
            {
                num=81;
                for(int j=0;j<reservedWords.length;++j)
                {
                    if(tokens.get(i).equals(reservedWords[j]))
                    {
                        if(j<32)num=j+1;
                        //else num=80;   //识别布尔常数
                    }
                }
            }
            else if(tokenNums.get(i)==1)
            {
                for(int j=0;j<symbols.length;++j)
                {
                    if(tokens.get(i).equals(symbols[j]))num=j+33;
                }
            }
            else{
                num=tokenNums.get(i);
            }
            int count=i+1;
            System.out.println(count+": <"+tokens.get(i)+","+num+">");

        }
    }
}