/*
 * CIS580 Assignment#1
 * Haci Karahasanoglu
 * CSU ID:2502553
 */

//Program accepts two boolean expression and test if they represent  the same
//function or not
import java.util.Scanner;

public class logiccompare
{
    public static void main(String[] args)
    {
        //Variable declaration and for some Initialization as well
        Scanner input = new Scanner(System.in);
        //creating two string array to assign each line into those arrays
        String[] exp = new String[2];
        //creating 2D char array for letter variables
        char[][] varname = new char[2][5];
        //creating 2D boolean array to assign letter boolean values
        boolean[][] varvalue = new boolean[2][5];
        int[] VarCount = {0,0};
        boolean temp;
        int checkSbrackets;
        int checkCbrackets;
        int checkbrackets;
        int[] num = new int[2];
        String[][] result = {{"","","",""},{"","","",""}};
        int slot,taken;
        int check;
        int val = 0;
        String line1="",line2="";
        boolean RLine1 = false,RLine2 = false;
        int cnt = 0;

        //Getting input from the user
        for(int i=0;i<2;i++)
        {
            //Ask for a Line to User
            System.out.print("Input Line "+(i+1)+" : ");
            exp[i] = input.nextLine();
            exp[i] += " ";

            //Initialize Variables to be used
            slot = 0;
            taken = 1;
            checkbrackets = 0;
            checkSbrackets = 0;
            checkCbrackets = 0;

            //Perform Operation on Entry Character by Character
            for(int j=0;j<exp[i].length();j++)
            {
                //Check Whether next Entry is a Opening Brackets
                //If Yes than shift the control for entry to next column for the row
                if(exp[i].charAt(j) == '(')
                {
                    slot += taken;
                    checkbrackets++;
                    checkSbrackets++;
                    continue;
                }
                //Check Whether next Entry is a Closing Brackets
                //If Yes than shift the control back to first row and Add all the Entries in next column back in Main Column i.e. 1st Column
                else if(exp[i].charAt(j) == ')' && (checkbrackets%2) != 0)
                {
                    slot -= taken;
                    result[i][slot] = result[i][slot] + result[i][slot + taken];
                    taken++;
                    checkbrackets++;
                    checkCbrackets++;
                    continue;
                }
                else if(exp[i].charAt(j) == ')' && (checkbrackets%2) == 0)
                {
                    checkCbrackets++;
                    continue;
                }
                //Check Whether next Entry is a NOT
                if(exp[i].charAt(j) == 'N' && exp[i].charAt(j+1) == 'O' && exp[i].charAt(j+2) == 'T' && exp[i].charAt(j+3) == ' ')
                {
                    result[i][slot] = result[i][slot] + "!";
                    j+=3;
                    continue;
                }
                //Check Whether next Entry is a AND
                if(exp[i].charAt(j) == 'A' && exp[i].charAt(j+1) == 'N' && exp[i].charAt(j+2) == 'D' && exp[i].charAt(j+3) == ' ')
                {
                    result[i][slot] = result[i][slot] + "&&";
                    j+=3;
                    continue;
                }
                //Check Whether next Entry is a OR
                if(exp[i].charAt(j) == 'O' && exp[i].charAt(j+1) == 'R' && exp[i].charAt(j+2) == ' ')
                {
                    result[i][slot] = result[i][slot] + "||";
                    j+=2;
                    continue;
                }
                //Check Whether next Entry is a Variable Character
                if(exp[i].charAt(j) != ' ' && (exp[i].charAt(j+1) == ' ' || exp[i].charAt(j+1) == ')'))
                {
                    check = 0;
                    //Checking Whether variable name is already in array varname[ ]
                    for(int k=0;k<VarCount[i];k++)
                    {
                        if(varname[i][k] == exp[i].charAt(j))
                            check = 1;
                    }
                    //If Not present and Entry for variable is done in Array varname[ ]
                    if(check == 0)
                    {
                        varname[i][VarCount[i]] = exp[i].charAt(j);
                        VarCount[i] = VarCount[i] + 1;
                    }
                    result[i][slot] = result[i][slot] + exp[i].charAt(j);
                    continue;
                }
            }
            //Checking if Number of Opening Brackets and Closing brackets are same or Not
            //If Not Exit.
            if(checkSbrackets != checkCbrackets)
            {
                System.out.println("No Of Open Brackets and Closed Brackets are not same in Expression "+(i+1));
                System.exit(0);
            }
        }
        //Exit if Number of variables are different
        if(VarCount[0] != VarCount[1])
        {
            System.out.println("Number Of Variables in Both the Loops Are Different.\nSo, End Of Program");
            System.exit(0);
        }
        //Otherwise process Expressions for each Boolean Value of Variables
        else
        {

            int j=(int)Math.pow(2, VarCount[1]);
            int[] k = new int[VarCount[1]];
            check = 0;
            //Initialize Boolean Array for Variables as well as Assign the break part where we will convert true to false and false to true for variables
            for(int i=0;i<VarCount[1];i++)
            {
                k[i] = (int)Math.pow(2, VarCount[1]-i-1);
                varvalue[0][i] = true;
                varvalue[1][i] = true;
            }
            //Display Header of Truth Table
            for(int i=0;i<VarCount[1];i++)
            {
                System.out.print("Var"+(i+1)+"\t");
            }
            System.out.print("Line1\tLine2\n");

            //Perform operation (2^number of variables) no of times
            for(int i=1;i<=j;i++)
            {
                //Initialize Expression and Result
                line1 = "";
                line2 = "";
                RLine1 = false;
                RLine2 = false;
                //Loop is responsible to keep the variables values changing based on k[] that we initialized before for this purpose.
                for(int l=0;l<VarCount[1];l++)
                {
                    if(i%k[l] == 0)
                    {
                        varvalue[0][l] = !varvalue[0][l];
                        varvalue[1][l] = !varvalue[1][l];
                    }
                    System.out.print(varvalue[0][l]+"\t");
                }
                //Replacing Variable Name by its value and store the complete expression in Logic form for Input 1
                for(int a=0;a<result[0][0].length();a++)
                {
                        check = 0;
                        for(int b=0;b<VarCount[0];b++)
                        {

                            if(result[0][0].charAt(a) == varname[0][b])
                            {
                                val = b;
                                check = 1;
                            }
                        }
                        if(check == 0)
                        {
                            line1 += result[0][0].charAt(a);
                        }
                        else
                        {
                            line1 += varvalue[0][val];
                        }
                }
                //Replacing Variable Name by its value and store the complete expression in Logic form for Input 2
                for(int a=0;a<result[1][0].length();a++)
                {
                        check = 0;
                        for(int b=0;b<VarCount[1];b++)
                        {
                            if(result[1][0].charAt(a) == varname[1][b])
                            {
                                check = 1;
                                val = b;
                            }
                        }
                        if(check == 0)
                        {
                            line2 += result[1][0].charAt(a);
                        }
                        else
                        {
                            line2 += varvalue[1][val];
                        }
                }
                //For Input 1 - Calculate The Boolean Equivalent Value for the Expression
                char hold='O';
                int extra = 0;
                for(int a = 0;a<line1.length();a++)
                {
                    if(line1.charAt(a) == 't' && hold == 'O' && extra == 0)
                    {
                        RLine1 = RLine1 || true;
                        a+= 3;
                        continue;
                    }
                    if(line1.charAt(a) == 't' && hold == 'O' && extra == 1)
                    {
                        RLine1 = RLine1 || !true;
                        a+= 3;
                        extra = 0;
                        continue;
                    }
                    if(line1.charAt(a) == 't' && hold == 'A' && extra == 0)
                    {
                        RLine1 = RLine1 && true;
                        a+= 3;
                        continue;
                    }
                    if(line1.charAt(a) == 't' && hold == 'A' && extra == 1)
                    {
                        RLine1 = RLine1 && !true;
                        a+= 3;
                        extra = 0;
                        continue;
                    }
                    if(line1.charAt(a) == 'f' && hold == 'O' && extra == 0)
                    {
                        RLine1 = RLine1 || false;
                        a+= 4;
                        continue;
                    }
                    if(line1.charAt(a) == 'f' && hold == 'O' && extra == 1)
                    {
                        RLine1 = RLine1 || !false;
                        a+= 4;
                        extra = 0;
                        continue;
                    }
                    if(line1.charAt(a) == 'f' && hold == 'A' && extra == 0)
                    {
                        RLine1 = RLine1 && false;
                        a+= 4;
                        continue;
                    }
                    if(line1.charAt(a) == 'f' && hold == 'A' && extra == 1)
                    {
                        RLine1 = RLine1 && !false;
                        a+= 4;
                        extra = 0;
                        continue;
                    }
                    if(line1.charAt(a) == '&')
                    {
                        hold = 'A';
                        a+=1;
                        continue;
                    }
                    if(line1.charAt(a) == '|')
                    {
                        hold = 'O';
                        a+=1;
                        continue;
                    }
                    if(line1.charAt(a) == '!')
                    {
                        extra = 1;
                    }
                }
                //For Input 2 - Calculate The Boolean Equivalent Value for the Expression
                hold = 'O';
                extra = 0;
                for(int a = 0;a<line2.length();a++)
                {
                    if(line2.charAt(a) == 't' && hold == 'O' && extra == 0)
                    {
                        RLine2 = RLine2 || true;
                        a+= 3;
                        continue;
                    }
                    if(line2.charAt(a) == 't' && hold == 'O' && extra == 1)
                    {
                        RLine2 = RLine2 || !true;
                        a+= 3;
                        extra = 0;
                        continue;
                    }
                    if(line2.charAt(a) == 't' && hold == 'A' && extra == 0)
                    {
                        RLine2 = RLine2 && true;
                        a+= 3;
                        continue;
                    }
                    if(line2.charAt(a) == 't' && hold == 'A' && extra == 1)
                    {
                        RLine2 = RLine2 && !true;
                        a+= 3;
                        extra = 0;
                        continue;
                    }
                    if(line2.charAt(a) == 'f' && hold == 'O' && extra == 0)
                    {
                        RLine2 = RLine2 || false;
                        a+= 4;
                        continue;
                    }
                    if(line2.charAt(a) == 'f' && hold == 'O' && extra == 1)
                    {
                        RLine2 = RLine2 || !false;
                        a+= 4;
                        extra = 0;
                        continue;
                    }
                    if(line2.charAt(a) == 'f' && hold == 'A' && extra == 0)
                    {
                        RLine2 = RLine2 && false;
                        a+= 4;
                        continue;
                    }
                    if(line2.charAt(a) == 'f' && hold == 'A' && extra == 1)
                    {
                        RLine2 = RLine2 && !false;
                        a+= 4;
                        extra = 0;
                        continue;
                    }
                    if(line2.charAt(a) == '&')
                    {
                        hold = 'A';
                        a+=1;
                        continue;
                    }
                    if(line2.charAt(a) == '|')
                    {
                        hold = 'O';
                        a+=1;
                        continue;
                    }
                    if(line2.charAt(a) == '!')
                    {
                        extra = 1;
                    }
                }
                //Display Result/Outcome of Expression for the particular variable Values in Truth Table
                System.out.print(RLine1+"\t"+RLine2+"\n");
                if(RLine1 == RLine2)
                {
                    cnt++;
                }
            }
        }


        //Display Expression in Symbol form
        System.out.println("\nExpression 1 : "+result[0][0]);
        System.out.println("Expression 2 : "+result[1][0]);

        //Display Result of the code
        if(cnt == (int)Math.pow(2, VarCount[1]))
        {
            System.out.println("Both the Input Line Expressions are SAME.");
        }
        else
        {
            System.out.println("Both the Input Line Expressions are NOT SAME.");
        }
    }
}
