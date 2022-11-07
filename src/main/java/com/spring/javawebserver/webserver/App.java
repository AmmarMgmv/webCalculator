package com.spring.javawebserver.webserver;

import java.util.Scanner;
import java.util.Stack;

public class App
{
//    public static void main( String[] args )
//    {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the string you want to be evaluated: ");
//        String expression = input.nextLine();
//        System.out.println("The answer is: " + calculate(expression));
//    }

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
        char first = input.charAt(0);
        char last = input.charAt(input.length() - 1);

        if(first == '*' || first == '/' || first == '^') {
            return false;
        }

        if(isOperator(last) || errorInString(input) || !validBrackets(input) || (input == "()")) {
            return false;
        }

        else {
            boolean doubleOperator = false;

            for (int i = 0; i <= input.length()-1; i++) {
                char current = input.charAt(i);

                while (((Character.isDigit(current))) && (i < input.length()-1)) {
                    i++;
                    current = input.charAt(i);
                    doubleOperator = false;
                }

                if (current == '.' && input.length() != 1){
                    if(!Character.isDigit(input.charAt(i+1))){
                        return false;
                    }
                    else{
                        i++;
                        current = input.charAt(i);
                    }
                }

                else if(current == '(' || current == ')' ){
                    continue;
                }

                else if ((isOperator(current)) && !doubleOperator) {
                    doubleOperator = true;
                    if(input.charAt(i+1) == '-'){
                        if((!Character.isDigit(input.charAt(i+2))) && (input.charAt(i+2) != '(') && (input.charAt(i+2) != 'e') && (input.charAt(i+2) != 'l')){
                            return false;
                        }
                        else {
                            doubleOperator = false;
                        }
                    }
                }

                else if (current == 'e' && input.charAt(i+1) == 'x' && input.charAt(i+2) == 'p' && input.charAt(i+3) == '('){
                    if(input.charAt(i+4) == ')') {
                        return false;
                    }
                    else {
                        i = i + 3;
                        current = input.charAt(i);
                        continue;
                    }
                }

                else if (current == 'l' && input.charAt(i+1) == 'o' && input.charAt(i+2) == 'g' && input.charAt(i+3) == '('){
                    if(input.charAt(i+4) == ')') {
                        return false;
                    }
                    else {
                        i = i + 3;
                        current = input.charAt(i);
                        continue;
                    }
                }

                else if (Character.isDigit(current)) {
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
    
    public static boolean isOperator(char current) {
        if (current == '-' || current == '/' || current == '+' || current == '*' || current == '^') {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean errorInString(String input) {
        if (input.contains("(+") || input.contains("(*") || input.contains("(/") || input.contains("(^")) {
            return true;
        }
        if (input.contains("+)") || input.contains("-)") || input.contains("*)") || input.contains("/)") || input.contains("^)")) {
            return true;
        }
        if (input.contains("..")) {
            return true;
        }
        return false;
    }
    
    private static boolean validBrackets(String input){
        int openingBrackets = 0;
        int closingBrackets = 0;

        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '('){
                openingBrackets++;
            }

            else if (input.charAt(i) == ')') {
                closingBrackets++;
            }
        }
        if(openingBrackets == closingBrackets){
            return true;
        }
        else{
            return false;
        }
    }
}
