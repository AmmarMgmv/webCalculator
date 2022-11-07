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
            String postfix = convertToPostfix(input);
            if(postfix.contains("Error: Something went wrong!") || postfix.contains("Log 0 is undefined!")){
                return postfix;
            }
            result = evaluateExpression(postfix, "#.###");
        } else {
            result = "This is not a valid expression. Please try again and make sure there are no spaces in your expression!";
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
            char current = input.charAt(i);
            if(Character.isDigit(current) || current == '.'){
                postfixExpression.append(current);
            }

            else if(current == '('){
                operators.push(current);
            }

            else if(current == ')'){
                while(!operators.isEmpty() && operators.peek() != '('){
                    char popped = operators.pop();
                    postfixExpression.append(' ');
                    postfixExpression.append(popped);
                }
                operators.pop();
            }

            else if(isOperator(current)) {
                if(current == '-'){
                    int charBeforeCurrent = i - 1;
                    if(charBeforeCurrent > -1){
                        if((isOperator(input.charAt(charBeforeCurrent))) || (input.charAt(charBeforeCurrent) == '(')){
                            postfixExpression.append(current);
                            continue;
                        }
                    }
                    else{
                        postfixExpression.append(current);
                        continue;
                    }
                }
                postfixExpression.append(' ');
                while(!operators.isEmpty() && getPrecedence(current) <= getPrecedence(operators.peek())){
                    char popped = operators.pop();
                    postfixExpression.append(popped);
                    postfixExpression.append(' ');
                }
                operators.push(current);
            }
            else if(current == 'e' || current == 'l'){
                StringBuilder expLogExpr = new StringBuilder();
                char thisChar = current;
                i = i + 4;
                int openingBrackets = 1;
                int closingBrackets = 0;

                for(int j = i; j < input.length(); j++){
                    current = input.charAt(j);
                    if(current == '('){
                        openingBrackets++;
                    }
                    else if(current == ')'){
                        closingBrackets++;
                    }

                    if(openingBrackets == closingBrackets){
                        i = j;
                        break;
                    }

                    expLogExpr.append(current);
                }
                String expLogExprStr = expLogExpr.toString();
                String postfixExpLogExpr = convertToPostfix(expLogExprStr);
                String expLogResult = evaluateExpression(postfixExpLogExpr, "#.###");

                double result;

                if(thisChar == 'e'){
                    result = exp(Double.parseDouble(expLogResult));
                }
                else {
                    if(Double.parseDouble(expLogResult) == 0.0){
                        return "Log 0 is undefined!";
                    }
                    result = log(Double.parseDouble(expLogResult));
                }
                postfixExpression.append(result);
            }
        }
        postfixExpression.append(' ');
        while (!operators.isEmpty()) {
            if(operators.peek() == '('){
                return "Error: Something went wrong!";
            }
            char popped = operators.pop();
            postfixExpression.append(popped);
            postfixExpression.append(' ');
        }
        return postfixExpression.toString();
    }

    public static String evaluateExpression(String input, String threeDecimalPlaces){
        Stack <Float> operands = new Stack <> ();

        for (int i = 0; i < input.length(); i++){
            char current = input.charAt(i);

            if(current == '-'){
                int charAfterCurrent = i + 1;
                if(Character.isDigit(input.charAt(charAfterCurrent))){
                    continue;
                }
            }

            if(Character.isDigit(current)){
                StringBuilder floatNumber = new StringBuilder();
                int charBeforeCurrent = i -1;

                if(charBeforeCurrent > -1){
                    if(input.charAt(charBeforeCurrent) == '-'){
                        floatNumber.append('-');
                    }
                }

                while(input.charAt(i) != ' '){
                    floatNumber.append(input.charAt(i));
                    i++;
                }
                operands.push(Float.parseFloat(floatNumber.toString()));
            }

            else if (isOperator(current)){
                float result = 0.0F;
                float firstNum = operands.pop();
                float secondNum = operands.pop();

                if(current == '*'){
                    result = firstNum * secondNum;
                }
                else if (current == '+'){
                    result = firstNum + secondNum;
                }
                else if (current == '-'){
                    result = secondNum - firstNum;
                }
                else if (current == '/') {
                    result = secondNum / firstNum;
                }
                else if (current == '^') {
                    double power = Math.pow(secondNum, firstNum);
                    result = (float)power;
                }
                operands.push(result);
            }
        }
        DecimalFormat threeDP = new DecimalFormat(threeDecimalPlaces);
        return Float.toString(Float.parseFloat(threeDP.format(operands.pop())));
    }
    
    public static int getPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        else if ((operator == '*' || operator == '/' || operator == '^')){
            return 2;
        }
        else{
            return 0;
        }
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