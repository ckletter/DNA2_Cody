/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: [YOUR NAME HERE]
 *</p>
 */

public class DNA {

    public static final int A_VALUE = 1;
    public static final int C_VALUE = 2;
    public static final int T_VALUE = 3;
    public static final int G_VALUE = 4;
    public static int STRCount(String sequence, String STR) {
        int totalCons = 0;
        int strLength = STR.length();
        int windowNum = convertToNum(sequence.substring(0, strLength), strLength);
        int strNum = convertToNum(STR, strLength);
        int seqLength = sequence.length();
        for (int i = 0; i < seqLength - strLength; i++) {
            int tempCount = 0;
            while (true) {
                if (windowNum == strNum) {
                    tempCount++;
                    windowNum = calcNextWindow(sequence, i, windowNum, strLength);
                }
                else {
                    totalCons = Math.max(tempCount, totalCons);
                    break;
                }
            }
        }
//        while (sequence.length() >= strLength) {
//        for (int i = 0; i < seqLength; i++) {
//            int tempCount = 0;
//            String copy = sequence.substring(i);
//            while (true) {
//                if (compareSTR(copy, STR, strLength)) {
//                    tempCount++;
//                    copy = copy.substring(strLength);
////                    lastIndex += strLength;
//                } else  {
//                    if (tempCount > totalCons) {
//                        totalCons = tempCount;
//                    }
//                    break;
//                }
//            }
//        }
//            sequence = sequence.substring(lastIndex + 1);
//        }

        return totalCons;
    }
//    public static boolean compareSTR(String sequence, String STR, int strLength) {
//        if (strLength > sequence.length()) {
//            return false;
//        }
//        String subsequence = sequence.substring(0, strLength);
//        return subsequence.equals(STR);
//    }
    public static int convertToNum(String sequence, int strLength) {
        int windowNum = 0;
        for (int i = 0; i < strLength; i++) {
            char letter = sequence.charAt(i);
            windowNum = addLetter(letter, windowNum);
            windowNum = windowNum * 10;
        }
        return windowNum;
    }
    public static int calcNextWindow(String sequence, int currentIndex, int windowNum, int strLength) {
        int modulus = (int) Math.pow(10, strLength - 1);
        int nextWindow = windowNum % modulus * 10;
        int finalIndex = currentIndex + strLength + 1;
        nextWindow = addLetter(sequence.charAt(finalIndex), nextWindow);
        return nextWindow;
    }
    public static int addLetter(char letter, int windowNum) {
        if (letter == 'A') {
            windowNum = windowNum + A_VALUE;
        }
        else if (letter == 'C') {
            windowNum = windowNum + C_VALUE;
        }
        else if (letter == 'T') {
            windowNum = windowNum + T_VALUE;
        }
        else {
            windowNum = windowNum + G_VALUE;
        }
        return windowNum;
    }
}
