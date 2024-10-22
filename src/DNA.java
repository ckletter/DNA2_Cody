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
    // Constant value for radix size, in this case 4
    public static final int RADIX = 4;

    /**
     * Given a sequence of DNA, determines longest count of short tandem repeats in a given sub-sequence of DNA
     * in the larger sequence
     * @param sequence entire sequence of DNA given
     * @param STR sub-sequence of DNA to be searched for
     * @return longest consecutive count of short tandem repeats
     */
    public static int STRCount(String sequence, String STR) {
        int totalCons = 0;
        int strLength = STR.length();
        // Finds the hash for the initial set of characters in the sequence (length of STR)
        int seqHash = hash(sequence.substring(0, strLength), strLength);
        // Finds the hash for the STR given
        int strHash = hash(STR, strLength);
        // Calculates number to shift the char by for removal of left-most letter
        int bitShift = (strLength - 1) * 2;
        int seqLength = sequence.length();
        // Loop through each character in the sequence
        for (int i = 0; i < seqLength - strLength; i++) {
            int tempCount = 0;
            int currentHash = seqHash;
            int currentIndex = i;
            // Loops for each number of occurrences of STR from starting index
            while (true) {
                // If the str matches the current hash, increases count by 1
                if (currentHash == strHash) {
                    tempCount++;
                    currentIndex += strLength;
                    // Hash the next digits in the sequence if possible
                    try {
                        currentHash = hash(sequence.substring(currentIndex, currentIndex + strLength), strLength);
                    }
                    // Sets current hash to -1 so no match will be found on next iteration
                    catch (IndexOutOfBoundsException e) {
                        currentHash = -1;
                    }
                }
                // If the sequence doesn't match, finds the next hash in constant time and updates total STR counts
                else {
                    seqHash = nextHash(sequence, i, seqHash, strLength, bitShift);
                    totalCons = Math.max(tempCount, totalCons);
                    break;
                }
            }
        }
        return totalCons;
    }

    /**
     * Given a sequence of DNA, determines and returns the hash of that sequence using Polynomial Rolling Hash Function
     * @param sequence sequence of DNA (a, t, c, g)
     * @param strLength the length of the STR being searched for
     * @return the number representation of the sequence
     */
    public static int hash(String sequence, int strLength) {
        // Loops through each letter in the sequence
        int hash = 0;
        for (int i = 0; i < strLength; i++) {
            // Adds to hash using Horner's method
            hash = (hash * RADIX + Character.toUpperCase(sequence.charAt(i)));
        }
        return hash;
    }
    /**
     * Takes in a hash of a sequence and a current index, and outputs the "next hash," the
     * number representation of the sequence beginning at index one to the right
     * @param sequence entire sequence of DNA
     * @param currentIndex index of the most recent window of DNA looked at
     * @param seqHash the number representation of the last looked at window of DNA
     * @param strLength length of given STR being searched for
     * @param bitShift number to bit shift by to remove left-most value
     * @return hash of next sequence
     */
    public static int nextHash(String sequence, int currentIndex, int seqHash, int strLength, int bitShift) {
        int firstCharVal = sequence.charAt(currentIndex);
        // Removes first term
//        seqHash = (seqHash + P_VALUE) - (firstCharVal << ((int) Math.pow(RADIX, strLength - 1) % P_VALUE) % P_VALUE;
        seqHash = seqHash - (firstCharVal << bitShift);
        // Determines index of the next DNA base to be added
        int finalIndex = currentIndex + strLength;
        // Adds next term by Horner's Method
        seqHash = (seqHash * RADIX + Character.toUpperCase(sequence.charAt(finalIndex)));
        return seqHash;
    }
}
