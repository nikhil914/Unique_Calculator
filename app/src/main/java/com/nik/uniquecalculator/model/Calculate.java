package com.nik.uniquecalculator.model;

import com.google.android.material.snackbar.Snackbar;

import java.util.Stack;

public class Calculate {

    public Calculate() {
    }

    public int calculateExpression(String input) {

        if (input.trim().isEmpty()){
            return 0;
        }

        input = handleString(input);


        return handleData(input);

    }

    public int handleData(String input) {

        char[] ar = input.toCharArray();

        Stack<Integer> noStack = new Stack<Integer>();
        Stack<Character> oprStack = new Stack<Character>();


        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == ' ')
                continue;

            if (ar[i] >= '0' && ar[i] <= '9') {
                StringBuffer buff_s = new StringBuffer();

                while (i < ar.length && ar[i] >= '0' && ar[i] <= '9')
                    buff_s.append(ar[i++]);
                noStack.push(Integer.parseInt(buff_s.toString()));
            } else if (ar[i] == '(')
                oprStack.push(ar[i]);

            else if (ar[i] == ')') {
                while (oprStack.peek() != '(')
                    noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));
                oprStack.pop();
            } else if (ar[i] == '+' || ar[i] == '-' ||
                    ar[i] == '*' || ar[i] == '/') {
                while (!oprStack.empty() && whoIsSuperior(ar[i], oprStack.peek()))
                    noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));

                oprStack.push(ar[i]);
            }
        }

        while (!oprStack.empty())
            noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));

        return noStack.pop();
    }

    private String handleString(String input) {

        StringBuilder sb = new StringBuilder();

        char[] ar = input.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            char a = ar[i];
            if (ar[i] == '+' || ar[i] == '-' ||
                    ar[i] == '*' || ar[i] == '/') {

                sb.append(" ");
                sb.append(ar[i]);
                sb.append(" ");

            } else {
                sb.append(ar[i]);
            }

        }
        return sb.toString();
    }



    public boolean whoIsSuperior(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        return (op1 != '*' && op1 != '+') || (op2 != '/' && op2 != '-');

    }


    private int calculateData(char opr, int a2, int a1) {

        switch (opr) {
            case '+':
                return a1 + a2;
            case '-':
                return a1 - a2;
            case '*':
                return a1 * a2;
            case '/':
                if (a2 == 0)
                    throw new
                            ArithmeticException("division by zero.. immpossible!");
                return a1 / a2;
        }
        return 0;

    }

}

