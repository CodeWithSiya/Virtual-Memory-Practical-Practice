import java.util.Arrays;
import java.util.Scanner;
//
public class Clock 
{
    private static int clockReplacement(final Memory frames, final Integer[] pageReferences) {
        // Initialise number of page faults, clock pointer and use bits to zero.
        int pageFaults = 0;
        int frame = 0;
        int[] usedBits = new int[frames.size()];
        for (int i = 0; i < usedBits.length; i++) usedBits[i] = 0;

        // Loop through each page in page references.
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];

            // Check if there is no page fault and add the page to the memory frames.
            if (!frames.contains(page) && frames.isEmpty(frame)) {
                // Add the page to the frame, and set its used bit to one.
                frames.put(frame, page); usedBits[frame] = 1; pageFaults++; frame = (frame + 1) % frames.size();
                System.out.println(page + ": " + frames); 
                //System.out.println(Arrays.toString(usedBits));  // Debugging.
            } else if (!frames.contains(page) && !frames.isEmpty(frame)) {
                // Select a victim page and replace it.
                while (true) {
                    // Check if the used bit for the current page is zero.
                    if (usedBits[frame] == 0) {
                        // Put the replace the victim frame, increment the frame pointer and increment the number of faults.
                        frames.put(frame, page); 
                        usedBits[frame] = 1;
                        frame = (frame + 1) % frames.size();
                        break;
                    } else {
                        // Give a second chance.
                        usedBits[frame] = 0;
                        frame = (frame + 1) % frames.size();
                    }
                } 
                pageFaults++; 
                System.out.println(page + ": " + frames); 
                //System.out.println(Arrays.toString(usedBits));  // Debugging.
            } else {
                // No page fault.
                int index = frames.indexOf(page);
                usedBits[index] = 1;
                System.out.println(page + ":-");
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

        System.out.printf("Page faults: %d.\n", clockReplacement(new Memory(numFrames), toArray(referenceString)));
    }

    private static Integer[] toArray(final String referenceString) {
        final Integer[] result = new Integer[referenceString.length()];
        
        for(int i=0; i < referenceString.length(); i++) {
            result[i] = Character.digit(referenceString.charAt(i), 10);
        }
        return result;
    }
}