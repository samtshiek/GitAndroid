public class HighestSmallest {
    public static void main (String[] args) {
        int[] array = {1,7,4,0};
        /*int biggest = 0;
        int smallest = 0;

        for (int i = 0; i < array.length-1; i++) {
            if (i == 0) {
                biggest = array[0];
                smallest = array[0];
            }
            if(array[i+1] > biggest) {
                biggest = array[i+1];
            }
            if(array[i+1] < smallest) {
                smallest = array[i+1];
            }
        }
        System.out.println("Biggest is : " + biggest);
        System.out.println("smallest is : " + smallest);*/
        HighestSmallest(array);
    }
    public static void HighestSmallest(int[] array) {
        int biggest = 0;
        int smallest = 0;

        for (int i = 0; i < array.length-1; i++) {
            if (i == 0) {
                biggest = array[0];
                smallest = array[0];
            }
            if(array[i+1] > biggest) {
                biggest = array[i+1];
            }
            if(array[i+1] < smallest) {
                smallest = array[i+1];
            }
        }
        System.out.println("Biggest is : " + biggest);
        System.out.println("smallest is : " + smallest);
    }
}
