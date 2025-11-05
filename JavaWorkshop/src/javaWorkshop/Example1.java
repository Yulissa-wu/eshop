package javaWorkshop;

public class Example1 {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};

        int len = numbers.length;
        System.out.println("陣列長度: " + len);

        for (int i = 0; i < len; i++) {
            System.out.println("numbers[" + i + "] = " + numbers[i]);
        }
    }
}

