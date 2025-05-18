import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//
public class LRU 
{
    private static int leastRecentlyUsed(final Memory frames, final Integer[] pageReferences) {
        // Initialise the number of page faults and frame pointer.
        int pageFaults = 0;
        int frame = 0;

        // Initialise a stack for the LRU algorithm.
        List<Integer> stack = new ArrayList<>();

        // Loop through each page in page references.
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];

            // Check if there is a page fault, add to memory and stack.
            if (!frames.contains(page) && frames.isEmpty(frame)) {
                // Add the page to memory, increment the number of page faults and pointer.
                frames.put(frame, page); pageFaults++; frame = (frame + 1) % frames.size();
                System.out.println(page + ": " + frames);
            } else if (!frames.contains(page) && !frames.isEmpty(frame)) {
                // There is a page fault, select a victim page from the stack and replace it.
                int victim = stack.remove(stack.size() - 1);
                frames.replace(victim, page); pageFaults++;
                System.out.println(page + ": " + frames);
            } else {
                // No page fault.
                System.out.println(page + ":-");
            }
            
            // Check if the stack already contains the page, remove it if it does.
            if (!stack.contains(page)) {
                stack.add(0, page);
            } else {
                stack.remove((Integer)page);
                stack.add(0, page);
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

        System.out.printf("Page faults: %d.\n", leastRecentlyUsed(new Memory(numFrames), toArray(referenceString)));
    }

    private static Integer[] toArray(final String referenceString) {
        final Integer[] result = new Integer[referenceString.length()];
        
        for(int i=0; i < referenceString.length(); i++) {
            result[i] = Character.digit(referenceString.charAt(i), 10);
        }
        return result;
    }
}