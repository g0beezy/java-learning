import java.util.Scanner;

public class Switchdemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please input a number between 1 and 7:");
        int day = input.nextInt();

        String greeting;
        switch (day) {
            case 1:
                greeting = "good monday";
                break;
            case 2:
                greeting = "good tuesday";
                break;
            case 3:
                greeting = "good wednesday";
                break;
            case 4:
                greeting = "good thursday";
                break;
            case 5:
                greeting = "good friday";
                break;
            case 6:
                greeting = "good saturday";
                break;
            case 7:
                greeting = "good sunday";
                break;
            default:
                greeting = "Error: day must be 1-7";
                break;
        }

        System.out.println(greeting);
        input.close();
    }
}