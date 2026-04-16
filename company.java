class Employee {
    protected String name;
    public String Employee(){
        return name;
    }
    public String Employee(String name){
        this.name = name;
        return name;
    }
    public String setname(){
        this.name = name;
    }
    public String getname(){
        return this.name;
    }
    public double earning(double salary){
        this.salary = salary;
        
    }
    @Override
    public String toString(){
        return "姓名：" + name + "\n" +
               "年龄：" + age + "\n" +
               "年薪为：" + salary;
    }
}
class MonthWorker extends Employee{
    private double monthSal;
    public Monthworker(){

    }
    public MonthWorker(String name,double monthSal){
        this.name = name;
        this.monthSal = monthSal;
    }
    public double getmonthSal(){
        return this.monthSal;
    }
    public double setmonthSal(){
        this.monthSal = monthSal;
    }
    @Override
    public double earning(){
        double earning = monthSal * 12;
    }
    @Override
    public String toString(){
        return "姓名：" + name + "\n" +
               "年龄：" + age + "\n" +
               "年薪为：" + earning;
    }
}
class WeekWorker{
    private double weekSal;
    public WeekWorker(){

    }
    public WeekWorker(String name,double weekSal){
        this.name = name;
        this.weeksal = weeksal;
    }
    public double getweekSal(){
        return this.weekSal;
    }
    public double setweekSal(){
        this.weeksal = weekSal;
    }
    @Override
    public double earning(){
        double earning = weeksal * 52
    }
    @Override
    public String toString(){
        return "姓名：" + name + "\n" +
               "年龄：" + age + "\n" +
               "年薪为：" + earning;
    }

}
class Company{
    Employee[] Employee= new Employee[2];
    private double numberOfWorkers = 0;
    public double computeTotalsal(double monthSal,double weekSal){
        double totalSal = monthSal + weekSal;
        return totalSal;
    }
    public void addEmployee(Employee e) {
        if (count < employees.length) {
            employees[count] = e;
            count++;
        }
    }
    @Override
    public String toString(){
        return "公司年工资总额 " + totalSal;
    }

}
public class TestCompany{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Company mycom = new Company(2);

        // 读入月薪员工信息
        System.out.print("请输入月薪员工姓名：");
        String name1 = sc.next();
        System.out.print("请输入月薪：");
        double monthSal = sc.nextDouble();
        MonthWorker a = new MonthWorker(name1, monthSal);

        // 读入周薪员工信息
        System.out.print("请输入周薪员工姓名：");
        String name2 = sc.next();
        System.out.print("请输入周薪：");
        double weekSal = sc.nextDouble();
        WeekWorker b = new WeekWorker(name2, weekSal);

        // 添加到公司
        mycom.addEmployee(a);
        mycom.addEmployee(b);

        // 打印公司信息
        System.out.println(mycom);

        sc.close();
    }
}