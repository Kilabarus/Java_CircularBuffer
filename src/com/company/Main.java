package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 2. Кольцевой буфер
 * Вариант 1. Реализация должна использовать массив
 *
 * Реализовать кольцевой буфер (или циклический буфер)
 * Это структура данных, использующая единственный буфер фиксированного размера,
 *      как будто бы после последнего элемента сразу же снова идет первый
 *
 * Данильченко Роман, 9 гр.
 */

public class Main {
    /**
     * Ввод булиновского значения с русской/английской клавиатуры без учета регистра с выводом, если требуется, сообщения
     *
     * @param msg   сообщение, печатающееся перед вводом
     * @return      true, если был введен один из символов "y", "Y", "н", "Н"
     *              false, если был введен один из символов "n", "N", "т", "Т"
     * @throws IOException
     */
    public static boolean inputBoolean(String msg) throws IOException {
        BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));

        if (msg != null && !msg.isEmpty()) {
            System.out.println(msg);
        }

        while (true) {
            switch (bR.readLine()) {
                case "y", "Y", "н", "Н" -> {
                    return true;
                }
                case "n", "N", "т", "Т" -> {
                    return false;
                }
                default -> System.out.println("Введён некорректный символ, повторите ввод");
            }
        }
    }

    /**
     * Ввод целого числа в диапазоне с выводом, если требуется, сообщения
     *      Могут быть заданы как и 2 границы диапазона, так и одна, также границы диапазона могут быть не заданы вообще
     *
     * @param msg   сообщение, печатающееся перед вводом
     * @param min   нижняя граница диапазона
     * @param max   верхняя граница диапазона
     *
     * @return      целое число в заданном дапазоне
     * @throws IOException
     */
    public static Integer inputInteger(String msg, Integer min, Integer max)
            throws IOException {
        if ((min != null) && (max != null) && (min > max)) {
            throw new IllegalArgumentException("Параметр min должен быть не больше max");
        }

        BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        String strWithInt;
        int resInt;

        if (msg != null && !msg.isEmpty()) {
            System.out.println(msg);
        }

        while (true) {
            try {
                strWithInt = bR.readLine();
                resInt = Integer.parseInt(strWithInt);

                if ((min != null && resInt < min) || (max != null && resInt > max)) {
                    System.out.println("Введено некорректное число, повторите ввод");
                } else {
                    return resInt;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Не удалось распознать число, повторите ввод");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int capacity = inputInteger("Введите вместимость кольцевого буфера", 1, null);
        CircularBuffer<Integer> circularBuffer = new CircularBuffer<>(capacity);
        System.out.println("Создан кольцевой буфер вместимостью " + circularBuffer.getCapacity());

        while (true) {
            System.out.println(
                    "\nВыберите действие:\n" +
                    "1 - Вывести количество элементов в буфере\n" +
                    "2 - Добавить элемент в конец буфера\n" +
                    "3 - Удалить элемент из начала буфера и вывести его в консоль\n" +
                    "4 - Вывести элемент, находящийся в начале буфера\n" +
                    "5 - Вывести содержимое буфера через цикл for-each\n" +
                    "6 - Вывести на экран внутреннее представление циклического буфера в виде массива\n" +
                    "0 - Закончить работу с программой"
                );

            switch (inputInteger(null, 0, 6)) {
                case 1 -> System.out.println("Количество элементов в буфере: " + circularBuffer.getSize());
                case 2 -> {
                    if (circularBuffer.isFull()) {
                        if (inputBoolean("Буфер полностью заполнен, перезаписать элемент в начале буфера? (y/n)")) {
                            circularBuffer.add(inputInteger("Введите элемент, который заменит элемент в начале буфера", null, null));
                            System.out.println("Элемент был добавлен в буфер");
                        }
                    } else {
                        circularBuffer.add(inputInteger("Введите элемент, который необходимо добавить в конец буфера", null, null));
                        System.out.println("Элемент был добавлен в буфер");
                    }
                }
                case 3 -> {
                    if (circularBuffer.isEmpty()) {
                            System.out.println("Буфер пуст");
                        } else {
                            System.out.println("Удалённый из начала буфера элемент: " + circularBuffer.pop());
                        }
                }
                case 4 -> {
                    if (circularBuffer.isEmpty()) {
                        System.out.println("Буфер пуст");
                    } else {
                        System.out.println("Находящийся в начале буфера элемент: " + circularBuffer.peek());
                    }
                }
                case 5 -> {
                    System.out.println("Содержимое буфера: ");
                    for (Integer i : circularBuffer) {
                        System.out.print(i.toString() + ' ');
                    }
                }
                case 6 -> System.out.println("Внутреннее представление циклического буфера в виде массива:\n" + circularBuffer.toStringShowingStructure());
                case 0 -> System.exit(0);
            }
        }
    }
}
