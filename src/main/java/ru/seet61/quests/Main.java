package ru.seet61.quests;

import java.io.Console;

/**
 * Реализация работы с классом через консоль.
 * При необходимости можно собрать в jar.
 * Created by dmitry.arefyev on 29.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        String string = null;

        //Подключаемся к консоли.
        Console c = System.console();
        if (c == null) {
            System.err.println("Не удалось подключитсья к консоли");
            System.exit(1);
        }

        System.out.println("Добро пожаловать!");
        while (true) {
            string = c.readLine("Введите, пожалуйста, строку: ");
            if (!string.equals("exit") && string.length()>2) {
                Quests quests = new Quests();
                String result = quests.checkQuests(string);
                System.out.println("Результат: " + result);
            }
            else if (string.equals("exit")){
                System.out.println("Работа окончена");
                System.exit(0);
            }
        }
    }
}
