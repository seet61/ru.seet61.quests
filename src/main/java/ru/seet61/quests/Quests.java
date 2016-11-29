package ru.seet61.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by seet61 on 29.11.2016.
 */
public class Quests {
    public String checkQuests (String string) {
        //удаляем пробелы
        string = string.replaceAll(" ", "");

        boolean flag = checkSyntax(string);
        if (flag == true) {
            //Синтаксис корректный
            boolean logic = checkLogic(string);
            System.out.println("logic: " + logic);
            return String.valueOf(true ? logic : false);
        }
        else {
            //Синтаксис не корректный
            return "error";
        }
    }

    private boolean checkLogic(String string) {
        boolean result=false;
        String sym = "";
        Pattern patternLogic = Pattern.compile("\\([^()]*\\)");
        Pattern patterntBracketsSymbol = Pattern.compile("\\).\\(");
        List<String> values = new ArrayList<String>();

        System.out.println("string: " + string);
        //Получаем выражение для анализа между скобок

        Matcher m = patternLogic.matcher(string);
        while (m.find()) {
            System.out.println("subs: " + m.start() + " " + m.end());
            if (m.start() != m.end()) {
                String substring = string.substring(m.start()+1, m.end()-1);
                values.add(string.substring(m.start()+1, m.end()-1));
            }
        }

        //Проверяем сам символ
        Matcher m2 = patterntBracketsSymbol.matcher(string);
        while (m2.find()) {
            sym = string.substring(m2.start()+1, m2.end()-1);
            System.out.println("sym: " + sym);
            values.add(sym);
            result=true ? sym.charAt(0) == '&' : false;

        }

        if (values.size()>1) {
            System.out.println("more logic");
            for (int i=0; i< values.size(); i += 3) {
                System.out.println("xxxx: " + values.get(i) + values.get(i+2) + values.get(i+1));
                if (values.get(i+2).charAt(0) == '&')
                    result &= (true ? calculate(values.get(i)) : false) && (true ? calculate(values.get(i+1)) : false);
                else if (values.get(i+2).charAt(0) == '|')
                    result |= (true ? calculate(values.get(i)) : false) || (true ? calculate(values.get(i+1)) : false);
            }

        }
        else {
            System.out.println("one logic");
            result = calculate(values.get(0));
        }

        System.out.println("result: " + result);
        return result;
    }

    private boolean calculate(String s) {
        System.out.println("xstring: " + s);
        boolean res = false;
        char symbol = 'X';
        Pattern pattern = Pattern.compile("[&|]");
        //Символ логики
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            symbol = s.charAt(m.start());
            System.out.println("ls: " + symbol);
            res=true ? symbol == '&' : false;
        }

        //Вычисляем
        String[] vals = pattern.split(s);
        System.out.println("l: " + vals.length );
        if (vals.length > 1) {
            for (String v: vals) {
                System.out.println(v);
                if (symbol == '&')
                    res = res && (v.charAt(0) != '!' || false);
                else if (symbol == '|')
                    res = res || (v.charAt(0) != '!' || false);
            }
        }
        else {
            System.out.println(s.charAt(0) != '!' || false);
            res = s.charAt(0) != '!' || false;
        }

        System.out.println("res: "  + res);
        return res;
    }


    private boolean checkSyntax(String string) {
        int left = 0;
        int right = 0;
        Pattern patterntBrackets = Pattern.compile("\\)\\(");
        Pattern patterntBracketsSymbol = Pattern.compile("\\).\\(");

        //считаем скобки и проверяем их равенство
        for (char c: string.toCharArray()) {
            switch (c){
                case '(':
                    left += 1;
                    break;
                case ')':
                    right += 1;
                    break;
            }
        }
        if (left != right) {
            return false;
        }

        //проверяем что строка не начинается логическим символом & или |
        char first = string.charAt(0);
        char last = string.charAt(string.length()-1);
        if ((first == '&') || (first == '|') || (last == '&') || (last == '|')) {
            return false;
        }

        //проверяем логический символ между )(
        //Проверяем что у нас отсутствует
        String[] res = patterntBrackets.split(string);
        if (res.length > 1) {
            return false;
        }
        else {
            //Проверяем сам символ
            Matcher m = patterntBracketsSymbol.matcher(string);
            while (m.find()) {
                char sym = string.charAt(m.start()+1);
                if (sym != '&' && sym != '|') {
                    return false;
                }
            }
        }
        return true;
    }
}
