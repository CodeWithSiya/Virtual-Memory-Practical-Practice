import java.util.Scanner;
//
public class FIFO {


    private static int firstInFirstOut(final Memory frames, final Integer[] pageReferences) {
        int pageFaults = 0;
        /**
         * Your code here.
         * 
         * Using the frames memory object, process the pageReferences using the FIFO paging algorithm, returning the number of page faults.
         */
        return pageFaults;
    }


    public static void main(final String[] args) {
        final Scanner stdIn = new Scanner(System.in);

        System.out.println("Enter the physical memory size (number of frames):");
        final int numFrames = stdIn.nextInt();
        stdIn.nextLine();

        System.out.println("Enter the string of page references:");
        final String referenceString = stdIn.nextLine();

        System.out.printf("Page faults: %d.\n", firstInFirstOut(new Memory(numFrames), toArray(referenceString)));
    }

    private static Integer[] toArray(final String referenceString) {
        final Integer[] result = new Integer[referenceString.length()];
        
        for(int i=0; i < referenceString.length(); i++) {
            result[i] = Character.digit(referenceString.charAt(i), 10);
        }
        return result;
    }
}
