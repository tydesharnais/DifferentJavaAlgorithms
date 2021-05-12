import java.util.Scanner;

public class towersofHanoi {

    public static void theTowers(int numDisks, char from, char intermediary, char to) {
        //Base case
        if (numDisks == 1) {
            System.out.println("Disk 1 from " + from + " to " + to);
        }
        //Recurisve execution of the towers of Hanoi
        else {
            theTowers(numDisks - 1, from, to, intermediary);
            System.out.println("Disk " + numDisks + " from " + from + " to " + to);
            theTowers(numDisks - 1, intermediary, from, to);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Number of Disks to integrate");
        System.out.print(">");
        int numDisks = scan.nextInt();
        theTowers(numDisks, 'A', 'B', 'C');
    }

}