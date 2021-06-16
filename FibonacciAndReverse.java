import java.util.ArrayList;
import java.util.LinkedList;

public class FibonacciAndReverse {
    public static void main(String[] args) {
        int f0 = 0;
        int f1 = 1;
        int sum = 0;
        //0, 1, 1, 2, 3,5,8,13
        LinkedList<Integer> myArray = new LinkedList<Integer>();
        LinkedList<Integer> reverseArray = new LinkedList<Integer>();


        for (int i = 0; i < 10; i++) {
            if(i == 0) {
                sum = f0+f1;
                f0 = f1;
                f1 = sum;
                System.out.println(sum);
                myArray.add(sum);
            }
            else {
                sum = f0 + f1;
                f0 = f1;
                f1 = sum;
                System.out.println(sum);
                myArray.add(sum);
            }

        }
        int size = myArray.size();
        for(int i = 0; i < size; i++) {
            reverseArray.add(myArray.getLast());
            myArray.removeLast();
        }

        System.out.println("Reverse fibonacci : " + reverseArray);

    }
}
