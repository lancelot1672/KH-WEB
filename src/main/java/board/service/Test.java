package board.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int input = Integer.parseInt(br.readLine());

        int num1 = input % 5;
        int num2 = input % 5 % 3;

        int result = num2 != 0? -1 : (input / 5) + (num1 / 3);
        System.out.println(result);

    }
}
