package pozhidaev;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int sorterThreadsCount = 2;
        int delayMillis = 100;
        int listType = 1; // 1 - MySynchronizedList, 2 - Collections.synchronizedList(new ArrayList<>())
        AtomicInteger swapNumbers = new AtomicInteger(0);

        if (args.length >= 1) {
            try {
                sorterThreadsCount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат <threads_count>");
            }
        }

        if (args.length >= 2) {
            try {
                delayMillis = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат<delay_millis>");
            }
        }

        if (args.length >= 3) {
            try {
                listType = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат <list_type>");
            }
        }

        System.out.printf("Запуск с %d сортирующими потоками и задержкой %d мс%n",
                sorterThreadsCount, delayMillis);

        MySynchronizedList myList;
        List<String> list = null;
        if (listType == 1) {
            myList = new MySynchronizedList();
            System.out.println("Используется собственная реализация списка");
        } else if (listType == 2) {
            list = Collections.synchronizedList(new ArrayList<String>());
            myList = null;
            System.out.println("Используется стандартная реализация списка (Collections.synchronizedList)");
        } else {
            myList = new MySynchronizedList();
            System.out.println("Неизвестный тип списка, используется собственная реализация списка");
        }

        for (int i = 0; i < sorterThreadsCount; i++) {
            Thread sorterThread;
            if (myList == null) {
                sorterThread = new Thread(new StandartSorter(list, delayMillis));
            } else {
                sorterThread = new Thread(new Sorter((MySynchronizedList) myList, delayMillis));
            }
            sorterThread.setDaemon(true);
            sorterThread.start();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите строки (пустая строка — показать текущее состояние):");

        while (true) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {

                System.out.println("\n--- Текущее состояние списка ---");
                if (listType == 2) {
                    for (String value : list) {
                        System.out.println(value);
                    }
                } else {
                    for (Node node : (MySynchronizedList) myList) {
                        System.out.println(node.value);
                    }
                }
                System.out.println("--------------------------------\n");
                continue;
            }

            List<String> parts = splitByLength(line, 80);
            for (String part : parts) {
                if (listType == 2) {
                    list.add(0, part);
                } else {
                    ((MySynchronizedList) myList).addFirst(part);
                }
            }
        }
    }

    private static List<String> splitByLength(String text, int maxLength) {
        List<String> parts = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + maxLength, text.length());
            parts.add(text.substring(start, end));
            start = end;
        }
        return parts;
    }
}