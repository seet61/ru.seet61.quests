package ru.seet61.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс реализует логику работы с выражениями зависимостей квестов.
 * Created by seet61 on 29.11.2016.
 */
public class Quests {
    /**
     * Проверяем передаваемую строку с квестами
     *
     * @param string
     * @return String "true", "fasle" или "error"
     */
    public String checkQuests (String string) {
        //удаляем пробелы
        string = string.replaceAll(" ", "");

        boolean flag = checkSyntax(string);
        if (flag == true) {
            //Синтаксис корректный
            boolean logic = checkLogic(string);
            return String.valueOf(true ? logic : false);
        }
        else {
            //Синтаксис не корректный
            return "error";
        }
    }

    /**
     * Разбиение строки на выражения, вызов метода расчета выражения и вычисление конечного результата.
     * @param string
     * @return boolean
     */
    private boolean checkLogic(String string) {
        boolean result=false;
        String sym = "";
        Pattern patternLogic = Pattern.compile("\\([^()]*\\)");
        Pattern patterntBracketsSymbol = Pattern.compile("\\).\\(");
        List<String> values = new ArrayList<String>();

        //Получаем выражение для анализа между скобок
        Matcher m = patternLogic.matcher(string);
        while (m.find()) {
            if (m.start() != m.end()) {
                String substring = string.substring(m.start()+1, m.end()-1);
                values.add(string.substring(m.start()+1, m.end()-1));
            }
        }

        //Проверяем сам символ
        Matcher m2 = patterntBracketsSymbol.matcher(string);
        while (m2.find()) {
            sym = string.substring(m2.start()+1, m2.end()-1);
            values.add(sym);
            result=true ? sym.charAt(0) == '&' : false;

        }

        if (values.size()>1) {
            for (int i=0; i< values.size(); i += 3) {
                if (values.get(i+2).charAt(0) == '&')
                    result &= (true ? calculate(values.get(i)) : false) && (true ? calculate(values.get(i+1)) : false);
                else if (values.get(i+2).charAt(0) == '|')
                    result |= (true ? calculate(values.get(i)) : false) || (true ? calculate(values.get(i+1)) : false);
            }

        }
        else {
            result = calculate(values.get(0));
        }
        return result;
    }

    /**
     * Вычисление конкретного выражения.
     * @param s
     * @return boolean
     */
    private boolean calculate(String s) {
        boolean res = false;
        char symbol = 'X';
        Pattern pattern = Pattern.compile("[&|]");
        //Символ логики
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            symbol = s.charAt(m.start());
            res=true ? symbol == '&' : false;
        }

        //Вычисляем
        String[] vals = pattern.split(s);
        if (vals.length > 1) {
            for (String v: vals) {
                if (symbol == '&')
                    res = res && (v.charAt(0) != '!' || false);
                else if (symbol == '|')
                    res = res || (v.charAt(0) != '!' || false);
            }
        }
        else {
            res = s.charAt(0) != '!' || false;
        }

        return res;
    }

    /**
     * Проверка синтаксиса выражения.
     * В случае ошибки возвращается "false" и в обработчике меняется на "error"
     * @param string
     * @return boolean
     */
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
