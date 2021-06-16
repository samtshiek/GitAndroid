public class Palindrome {
    public static void main (String[] args) {
        int num1 = 16461;
        int num2 = 2552;
        int num3 = 124;
        int num4 = 171;

        /*String numString = "" + num3;

        String reversed = "";
        System.out.println("String length : " + numString.length());

        for(int i = numString.length()-1; i >= 0; i--) {

            reversed += numString.substring(i, i+1);
            System.out.println("reversed letters " + reversed);

        }
        if (numString.equals(reversed)) {
            System.out.println("Is a palindrome");
        }
        else {
            System.out.println("Is NOT a palindrome");
        }*/
        Palindrome(num3);
    }
    public static void Palindrome (int num) {
        String numString = "" + num;

        String reversed = "";
        System.out.println("String length : " + numString.length());

        for(int i = numString.length()-1; i >= 0; i--) {

            reversed += numString.substring(i, i+1);
            System.out.println("reversed letters " + reversed);

        }
        if (numString.equals(reversed)) {
            System.out.println("Is a palindrome");
        }
        else {
            System.out.println("Is NOT a palindrome");
        }
    }
}
