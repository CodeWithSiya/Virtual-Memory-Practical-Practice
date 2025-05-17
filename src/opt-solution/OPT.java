import java.util.Scanner;
//
public class OPT 
{
    private static int optimal(final Memory frames, final Integer[] pageReferences) {
        // Initialise the number of page faults.
        int pageFaults = 0;

        // Initialising the frame pointer.
        int frame = 0;

        // Looping through each page in the reference string.
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];

            // Check if we have a page fault and an empty space available.
            if (!frames.contains(page) && frames.isEmpty(frame)) {
                // Add the page to memory, increment the number of page faults and frame pointer.
                frames.put(frame, page); pageFaults++; frame = (frame + 1) % frames.size();
                System.out.println(page + ": " + frames);
            } else if (!frames.contains(page) && !frames.isEmpty(frame)) {
                // Find and replace the victim page.
                int victim = findVictim(i, frames, pageReferences);
                frames.replace(victim, page); pageFaults++;
                System.out.println(page + ": " + frames);
            } 
            else {
                // No page fault.
                System.out.println(page + ": " + "-"); 
            }
        }
        return pageFaults;
    }

    /**
     * Helper function which finds the victim page from the given start point.
     */
    private static int findVictim(int startIndex, Memory frames, Integer[] pageReferences) {
        // Initialise the victim page to the first frame and initialise the max until use count.
        int victim = frames.get(0);
        int maxUntilUse = -1;

        // Loop through each frame and find the victim frame.
        for (int i = 0; i < frames.size(); i++) {
            int frame = frames.get(i);
            int timeUntilUse = Integer.MAX_VALUE;

            // Look into the future and find the victim page.
            for (int j = startIndex + 1; j < pageReferences.length; j++) {
                int page = pageReferences[j];
                // Check if found first usage and stop the loop if found.
                if (page == frame) {
                    timeUntilUse = j - startIndex;
                    break;
                }
            }
            
            // Check if this frame has the longest time until use and set it to victim if true.
            if (timeUntilUse > maxUntilUse) {
                maxUntilUse = timeUntilUse;
                victim = frame;
            }
        }
        return victim;
    }

    public static void main(final String[] args) {
        final Scanner stdIn = new Scanner(System.in);

        System.out.println("Enter the physical memory size (number of frames):");
        final int numFrames = stdIn.nextInt();
        stdIn.nextLine();

        System.out.println("Enter the string of page references:");
        final String referenceString = stdIn.nextLine();

        System.out.printf("Page faults: %d.\n", optimal(new Memory(numFrames), toArray(referenceString)));
    }

    private static Integer[] toArray(final String referenceString) {
        final Integer[] result = new Integer[referenceString.length()];
        
        for(int i=0; i < referenceString.length(); i++) {
            result[i] = Character.digit(referenceString.charAt(i), 10);
        }
        return result;
    }
}