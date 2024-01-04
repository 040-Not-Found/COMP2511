package example;

import java.util.Scanner;

public class Sum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        // System.out.println(line);
        // 1 2 3 4 
        String[] words = line.split(" ");
        // ["1", "2", "3", "4"]
        int sum = 0;
        for (String word : words) {
            sum += Integer.parseInt(word);
        }
        System.out.println(sum);
        scanner.close();
    }
}