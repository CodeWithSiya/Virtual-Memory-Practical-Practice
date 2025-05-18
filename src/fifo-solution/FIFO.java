import java.util.Scanner;
//
public class FIFO 
{
    private static int firstInFirstOut(final Memory frames, final Integer[] pageReferences) {
        // Initialise the number of page faults.
        int pageFaults = 0;

        // Initialise the frame index.
        int frame = 0;

        // Looping through each element in page references.
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];

            // Check if there is page fault and if the available frame is empty.
            if (!frames.contains(page)) {
                // Add the page reference to memory, increment the number of page faults and frame index.
                frames.put(frame, page); pageFaults++; frame = (frame + 1) % frames.size();
                System.out.println(page + ": " + frames);
            } else {
                // No page fault has occured.
                System.out.println(page + ": " + "-");
            }
        }   
        return pageFaults;
    }

    @SuppressWarnings("resource")
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