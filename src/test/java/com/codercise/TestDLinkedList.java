package com.codercise;

import com.codercise.list.core.DLinkedList;
import com.codercise.list.core.LinkedList;
import com.codercise.list.core.List;

import java.util.Scanner;

public class TestDLinkedList {
    private static void testList(List<Integer> list) {
        {
            Scanner in = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Select an operation");
                System.out.println("1: Append");
                System.out.println("2: InsertFirst");
                System.out.println("3: Insert");
                System.out.println("4: Get");
                System.out.println("5: Delete");
                System.out.println("6: DeleteFirst");
                System.out.println("7: DeleteLast");
                System.out.println("8: Print");
                System.out.println("9: DeleteByKey");
                System.out.println("10: SearchKey");
                System.out.println();

                choice = in.nextInt();

                try {
                    switch (choice) {
                        case 1:
                            list.append(in.nextInt());
                            break;
                        case 2:
                            list.insertFirst(in.nextInt());
                            break;
                        case 3:
                            list.insert(in.nextLong(), in.nextInt());
                            break;
                        case 4:
                            System.out.println(list.get(in.nextLong()));
                            break;
                        case 5:
                            System.out.println(list.delete(in.nextLong()));
                            break;
                        case 6:
                            System.out.println(list.deleteFirst());
                            break;
                        case 7:
                            System.out.println(list.deleteLast());
                            break;
                        case 8:
                            System.out.println(list);
                            break;
                        case 9:
                            System.out.println(list.delete(in.nextInt()));
                            break;
                        case 10:
                            System.out.println(list.search(in.nextInt()));
                            break;
                        case -1:
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            } while (choice != -1);
        }
    }

    private static void testLinkedList() {
        testList(new LinkedList<>());
    }

    private static void testDLinkedList() {
        testList(new DLinkedList<>());
    }

    public static void main(String[] args) {
        testDLinkedList();
    }
}
