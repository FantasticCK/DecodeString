package com.CK;

import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        String s = "100[leetcode]";
        Solution solution = new Solution();
        System.out.println(solution.decodeString(s));
    }
}

class Solution {
    public String decodeString(String s) {
        if (s.length() == 0) return "";
        Stack<String> stack = new Stack<>();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ']') stack.add(String.valueOf(s.charAt(i)));
            else {
                StringBuilder stringInBracket = new StringBuilder();
                StringBuilder completedString = new StringBuilder();
                StringBuilder times = new StringBuilder();
                while (!stack.peek().equals("[")) stringInBracket.insert(0, stack.pop());
                stack.pop(); // remove "["
                while (!stack.isEmpty() && isNumeric(stack.peek())) times.insert(0, stack.pop());
                for (int t = 0; t < Integer.parseInt(times.toString()); t++) completedString.insert(0, stringInBracket);
                stack.add(completedString.toString());
            }
        }
        while (!stack.isEmpty()) res.insert(0, stack.pop());
        return res.toString();
    }

    private boolean isNumeric(String str) {
        return str.compareTo("0") >= 0 && str.compareTo("9") <= 0;
    }
}

// Two Stack
class Solution2 {
    public String decodeString(String s) {
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    count = 10 * count + (s.charAt(idx) - '0');
                    idx++;
                }
                countStack.push(count);
            }
            else if (s.charAt(idx) == '[') {
                resStack.push(res);
                res = "";
                idx++;
            }
            else if (s.charAt(idx) == ']') {
                StringBuilder temp = new StringBuilder (resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res);
                }
                res = temp.toString();
                idx++;
            }
            else {
                res += s.charAt(idx++);
            }
        }
        return res;
    }
}