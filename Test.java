package HW1;

public class Test {
    public static void main(String[] args) {

        //Student class
        Student student1 = new Student();
        Student student2 = new Student();

        student1.setName("John");
        student1.setRoll_no(2);
        student1.setPhone_no("1234567890");
        student1.setAddress("North pole");
        student2.setName("Sam");
        student2.setRoll_no(3);
        student2.setPhone_no("0987654321");
        student2.setAddress("South pole");

        System.out.println("Student1 name: "+ student1.getName());
        System.out.println("Student1 roll num: "+ student1.getRoll_no());
        System.out.println("Student1 phone num: "+ student1.getPhone_no());
        System.out.println("Student1 address: " + student1.getAddress());
        System.out.println("Student2 name: " + student2.getName());
        System.out.println("Student2 roll num " + student2.getRoll_no());
        System.out.println("Student2 phone num: " + student2.getPhone_no());
        System.out.println("Student2 address: " + student2.getAddress());


        //Triangle class
        Triangle triangle1 = new Triangle();
        Triangle triangle2 = new Triangle(3,4,5);

        System.out.println("Triangle1 " + triangle1.getArea());
        System.out.println("Triangle1 " + triangle1.getPerimeter());
        System.out.println("Triangle2 " + triangle2.getArea());
        System.out.println("Triangle2 " + triangle2.getPerimeter());


        //Rectangle class
        Rectangle rectangle1 = new Rectangle(4,5);
        Rectangle rectangle2 = new Rectangle(5,8);

        System.out.println("Rectangle1 " + rectangle1.getArea());
        System.out.println("Rectangle2 " + rectangle2.getArea());

    }
}
