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
    public static final int A_VALUE = 1;
    public static final int C_VALUE = 2;
    public static final int T_VALUE = 3;
    public static final int G_VALUE = 4;

    /**
     * Given a sequence of DNA, determines longest count of short tandem repeats in a given sub-sequence of DNA in the larger sequence
     * @param sequence entire sequence of DNA given
     * @param STR sub-sequence of DNA to be searched for
     * @return longest consecutive count of short tandem repeats
     */
    public static int STRCount(String sequence, String STR) {
        int totalCons = 0;
        int strLength = STR.length();
        int windowNum = convertToNum(sequence.substring(0, strLength), strLength);
        int strNum = convertToNum(STR, strLength);
        int seqLength = sequence.length();
        for (int i = 0; i < seqLength - strLength; i++) {
            int tempCount = 0;
            int currentNum = windowNum;
            int currentIndex = i;
            while (true) {
                if (currentNum == strNum) {
                    tempCount++;
                    currentIndex += strLength;
                    try {
                        currentNum = convertToNum(sequence.substring(currentIndex, currentIndex + strLength), strLength);
                    } catch (IndexOutOfBoundsException e) {
                        currentNum = -1;
                    }
                } else {
                    windowNum = calcNextWindow(sequence, i, windowNum, strLength);
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
    public static int convertToNum(String sequence, int strLength) {
        // Loops through each letter in the sequence
        int windowNum = 0;
        for (int i = 0; i < strLength; i++) {
            // Allocates space in the number for the DNA base
            windowNum = windowNum * 10;
            // Gets the letter and adds its number equivalent to the number representing the whole sequence
            char letter = sequence.charAt(i);
            windowNum = addLetter(letter, windowNum);
        }
        return windowNum;
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
    public static int calcNextWindow(String sequence, int currentIndex, int windowNum, int strLength) {
        // Determines modulus number in order to remove the digit furthest to the left
        int modulus = (int) Math.pow(10, strLength - 1);
        // Removes digit furthest to left in window number and allocates space for new DNA base
        int nextWindow = windowNum % modulus * 10;
        // Determines index of the next DNA base to be added
        int finalIndex = currentIndex + strLength;
        // Adds next DNA base to current window number
        nextWindow = addLetter(Character.toUpperCase(sequence.charAt(finalIndex)), nextWindow);
        return nextWindow;
    }

    /**
     * Given a letter, adds its number equivalent to the number representation of the current sequence
     * @param letter A, C, T, or G
     * @param windowNum current number representation of the sequence without newest base added
     * @return window number with base added
     */
    public static int addLetter(char letter, int windowNum) {
        // Add A onto number sequence
        if (letter == 'A') {
            windowNum = windowNum + A_VALUE;
        }
        // Add C onto number sequence
        else if (letter == 'C') {
            windowNum = windowNum + C_VALUE;
        }
        // Add T onto number sequence
        else if (letter == 'T') {
            windowNum = windowNum + T_VALUE;
        }
        // Add G onto number sequence
        else {
            windowNum = windowNum + G_VALUE;
        }
        return windowNum;
    }
}
