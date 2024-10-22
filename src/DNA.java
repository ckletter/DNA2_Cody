/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Cody Kletter
 *</p>
 */

public class DNA {
    // Constant values representing each DNA base (A, T, C, G)
    public static final int RADIX = 256;
    public static final long P_VALUE = 54321102419;

    /**
     * Given a sequence of DNA, determines longest count of short tandem repeats in a given sub-sequence of DNA in the larger sequence
     * @param sequence entire sequence of DNA given
     * @param STR sub-sequence of DNA to be searched for
     * @return longest consecutive count of short tandem repeats
     */
    public static int STRCount(String sequence, String STR) {
        int totalCons = 0;
        int strLength = STR.length();
        long seqHash = hash(sequence.substring(0, strLength), strLength);
        long strHash = hash(STR, strLength);
        int seqLength = sequence.length();
        for (int i = 0; i < seqLength - strLength; i++) {
            int tempCount = 0;
            long currentNum = seqHash;
            int currentIndex = i;
            while (true) {
                if (currentNum == strHash) {
                    tempCount++;
                    currentIndex += strLength;
                    try {
                        currentNum = hash(sequence.substring(currentIndex, currentIndex + strLength), strLength);
                    } catch (IndexOutOfBoundsException e) {
                        currentNum = -1;
                    }
                } else {
                    seqHash = nextHash(sequence, i, seqHash, strLength);
                    totalCons = Math.max(tempCount, totalCons);
                    break;
                }
            }
        }
        return totalCons;
    }

    /**
     * Given a sequence of DNA, determines and returns the number equivalent of that sequence using our number constants
     * @param sequence sequence of DNA (a, t, c, g)
     * @param strLength the length of the STR being searched for
     * @return the number representation of the sequence
     */
    public static long hash(String sequence, int strLength) {
        // Loops through each letter in the sequence
        int hash = 0;
        for (int i = 0; i < strLength; i++) {
            // Allocates space in the number for the DNA base
            hash = (hash * RADIX + Character.toUpperCase(sequence.charAt(i))) % P_VALUE;
        }
        return hash;
    }
    /**
     * Takes in a number representation of a sequence and a current index, and outputs the "next window," or the
     * number representation of the sequence beginning at index one to the right
     * @param sequence entire sequence of DNA
     * @param currentIndex index of the most recent window of DNA looked at
     * @param windowNum the number representation of the last looked at window of DNA
     * @param strLength length of given STR being searched for
     * @return number representation of next window
     */
    public static long nextHash(String sequence, int currentIndex, long seqHash, int strLength) {
        int firstCharVal = sequence.charAt(currentIndex);
        // Removes first term
        seqHash = (seqHash + P_VALUE) - (firstCharVal * (long) Math.pow(RADIX, strLength - 1) % P_VALUE) % P_VALUE;
        // Determines index of the next DNA base to be added
        int finalIndex = currentIndex + strLength;
        // Adds next term by Horner's Method
        seqHash = (seqHash * RADIX + Character.toUpperCase(sequence.charAt(finalIndex))) % P_VALUE;
        return seqHash;
    }
}
