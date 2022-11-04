package assignment;

import java.util.Scanner;
import java.util.Stack;

public class App
{
    public static void main( String[] args )
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the string you want to be evaluated: ");
        String expression = input.nextLine();
        System.out.println("The answer is: " + calculate(expression));
    }

    public static String calculate(String input) {
        String result = null;
        if (validInput(input)) {
            String postfix= convertToPostfix(input);
            result = evaluateExpression(postfix);
        } else {
            result = "This is not a valid expression. A valid input contains only integers and operands such as +, - and *";
        }
        return result;
    }

    public static boolean validInput (String input){
        if(!Character.isDigit(input.charAt(0))){
            return false;
        }
        else {
            boolean lastOperator = false;

            for (int i = 0; i < input.length()-1; i++) {
                while ((Character.isDigit(input.charAt(i))) && (i < input.length()-1)) {
                    i++;
                    lastOperator = false;
                }
                if ((input.charAt(i) == '*' || input.charAt(i) == '+' || input.charAt(i) == '-') && !lastOperator) {
                    lastOperator = true;
                }
                else if (Character.isDigit(input.charAt(i))) {
                    return true;
                }
                else{
                    return false;
                }
            }
            return true;
        }
    }

    public static String convertToPostfix(String input){
        Stack <Character> operators = new Stack <> ();
        StringBuilder postfixExpression = new StringBuilder();

        for (int i = 0; i < input.length(); i++){
            if(Character.isDigit(input.charAt(i))){
                postfixExpression.append(input.charAt(i));
            }
            else if(input.charAt(i) == '*' || input.charAt(i) == '+' || input.charAt(i) == '-' ) {
                postfixExpression.append(' ');
                while(!operators.isEmpty() && hasPrecedence(input.charAt(i), operators.peek())){
                    char popped = operators.pop();
                    postfixExpression.append(popped);
                    postfixExpression.append(' ');
                }
                operators.push(input.charAt(i));
            }
        }
        postfixExpression.append(' ');
        while (!operators.isEmpty()) {
            postfixExpression.append(operators.pop());
            postfixExpression.append(' ');
        }
        return postfixExpression.toString();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        else {
            return true;
        }
    }

    public static String evaluateExpression(String input){
        Stack <Integer> operands = new Stack <> ();

        for (int i = 0; i < input.length(); i++){
            if(Character.isDigit(input.charAt(i))){
                StringBuilder multiDigitNumber = new StringBuilder();
                while(input.charAt(i) != ' '){
                    multiDigitNumber.append(input.charAt(i));
                    i++;
                }
                operands.push(Integer.parseInt(multiDigitNumber.toString()));
            }
            else if (input.charAt(i) == '*' || input.charAt(i) == '+' || input.charAt(i) == '-' ){
                int result = 0;
                int firstNum = operands.pop();
                int secondNum = operands.pop();

                if(input.charAt(i) == '*'){
                    result = firstNum * secondNum;
                }
                else if (input.charAt(i) == '+'){
                    result = firstNum + secondNum;
                }
                else if (input.charAt(i) == '-'){
                    result = secondNum - firstNum;
                }
                operands.push(result);
            }
        }
        return Integer.toString(operands.pop());
    }
}
