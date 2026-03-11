import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Double> nums = new ArrayList<>();

        System.out.println("请输入若干数字，按 Ctrl+Z(Windows) 后回车结束：");
        int count = 0;
        while (input.hasNextDouble()) {
            double x = input.nextDouble();
            nums.add(x);
            count++;
        }

        // 示例：求和
        double sum = 0;
        for (double num : nums) {
            sum += num;
        }

        System.out.println("共输入 " + count + " 个数");
        System.out.println("总和 = " + sum);
    }
}