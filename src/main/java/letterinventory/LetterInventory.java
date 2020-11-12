package letterinventory;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * <p>
 * This class generates and keeps track of a letter inventory. It
 * keeps track of how many times each letter appears in a given word.
 */
public class LetterInventory {

    // Array which contains the count of how many times each letter appears
    //  this inventory.
    private final int[] letterInventory = new int[26];
    // The sum of all the values in letterInventory.
    int size = 0;

    /**
     * Constructs a new LetterInventory object. This constructor uses the parameter
     * 'data' to keep track of each letter and is case independent.
     *
     * @param data any string consisting with letters in the english alphabet.
     */
    public LetterInventory(String data) {
        for (int index = 0; index < data.length(); index++) {
            int letterIndex = getLetterIndex(data.charAt(index));
            if (isInBounds(letterIndex)) {
                letterInventory[letterIndex] = letterInventory[letterIndex] + 1;
                size++;
            }
        }
    }

    /**
     * Constructs a new LetterInventory object with 0 for each
     * letter count.
     */
    private LetterInventory() {
        this("");
    }

    public static void main(String[] args) {
        LetterInventory inventory1 = new LetterInventory("");
        //LetterInventory inventory1 = new LetterInventory("WashingTon STATE");
        inventory1.get('a');

    }

    /**
     * Gets the index of letterInventory corresponding to the parameter 'letter'
     *
     * @param letter any alphabetic letter.
     * @return the index of 'letter' in letterInventory.
     */
    private static int getLetterIndex(char letter) {
        return Character.getNumericValue(letter) - 10;
    }

    /**
     * Gets the letter, as a char, represented by the given index.
     * Example: (index=0: a, index=1: b, index=25: z)
     *
     * @param index any integer ranging from 0 to 25.
     * @return the character representation of 'index'
     * @throws IllegalArgumentException if index is not in the range from 0 to 25.
     */
    private static char getLetter(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index is too small");
        } else if (25 < index) {
            throw new IllegalArgumentException("Index is too large.");
        }
        return (char) (index + 97);
    }

    /**
     * Returns the number of times the parameter 'letter' appears in
     * this LetterInventory.
     *
     * @param letter any alphabetic character (A-z).
     * @return the count of 'letter' in this LetterInventory
     * @throws IllegalArgumentException if a non-alphabetic character is passed.
     */
    int get(char letter) {
        int letterIndex = getLetterIndex(letter);
        checkAlphabetical(letterIndex);

        return letterInventory[letterIndex];
    }

    /**
     * Sets the given letter the the value given.
     *
     * @param letter any alphabetic letter.
     * @param value  any positive integer.
     * @throws IllegalArgumentException if value is negative or letter is non-alphabetic.
     */
    void set(char letter, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative.");
        }
        int index = getLetterIndex(letter);
        checkAlphabetical(index);

        size = size - letterInventory[index] + value;
        letterInventory[index] = value;
    }

    /**
     * @return the sum of all the letter counts in this LetterInventory.
     */
    int size() {
        return size;
    }

    /**
     * @return true if all the letter counts are 0, false otherwise.
     */
    boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a string of of letter inventory. This will print each letter the
     * number of times it appears in letter inventory.
     *
     * @return readable string of this LetterInventory.
     */
    @Override
    public String toString() {
        String letterInventoryString = "[";

        for (int letter = 0; letter < letterInventory.length; letter++) {
            for (int count = 0; count < letterInventory[letter]; count++) {
                letterInventoryString += getLetter(letter);
            }
        }

        return letterInventoryString + "]";
    }

    /**
     * Creates a new LetterInventory which is equal to the sum of this
     * LetterInventory and the parameter 'other'. The count of each letters are added.
     *
     * @param other any other non-null LetterInventory
     * @return the sum of 'this' and 'other'.
     */
    LetterInventory add(LetterInventory other) {
        LetterInventory sumInventory = new LetterInventory();

        for (int letter = 0; letter < letterInventory.length; letter++) {
            char currentLetter = getLetter(letter);
            sumInventory.set(currentLetter, letterInventory[letter] + other.get(currentLetter));
        }
        return sumInventory;
    }

    /**
     * Creates a new LetterInventory which is equal to the difference of
     * the parameter 'other' from this LetterInventory. The count of each
     * letters are subtracted
     *
     * @param other any other non-null LetterInventory.
     * @return the difference of 'other' from 'this'.
     */
    LetterInventory subtract(LetterInventory other) {
        LetterInventory differenceInventory = new LetterInventory();

        for (int letter = 0; letter < letterInventory.length; letter++) {
            char currentLetter = getLetter(letter);
            int letterDifference = letterInventory[letter] - other.get(currentLetter);

            if (letterDifference < 0) {
                return null;
            } else {
                differenceInventory.set(currentLetter, letterDifference);
            }
        }

        return differenceInventory;
    }

    /**
     * Checks if the given index is in the bounds of letterInventory.
     *
     * @param index any integer to check if it is in bounds.
     * @return true if index is in bounds, false otherwise.
     */
    private boolean isInBounds(int index) {
        return 0 <= index && index < letterInventory.length;
    }

    /**
     * Checks if the given index corresponds to an alphabetic letter
     * in LetterInventory.
     *
     * @param characterIndex any integer to check index.
     * @throws IllegalArgumentException if index is not a letter in the alphabet.
     */
    private void checkAlphabetical(int characterIndex) {
        if (!isInBounds(characterIndex)) {
            throw new IllegalArgumentException("Character is not in alphabet.");
        }
    }

    // Returns true if the other object stores the same character counts as this inventory.
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LetterInventory)) {
            return false;
        }
        LetterInventory other = (LetterInventory) o;
        LetterInventory diff = this.subtract(other);
        return diff != null && diff.isEmpty();
    }

    // Returns a hash code value for this letter inventory.
    @Override
    public int hashCode() {
        int result = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            result += c * get(c);
        }
        return result;
    }

    // Returns the cosine similarity between this inventory and the other inventory.
    public double similarity(LetterInventory other) {
        long product = 0;
        double thisNorm = 0;
        double otherNorm = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            int a = this.get(c);
            int b = other.get(c);
            product += a * (long) b;
            thisNorm += a * a;
            otherNorm += b * b;
        }
        if (thisNorm <= 0 || otherNorm <= 0) {
            return 0;
        }
        return product / (Math.sqrt(thisNorm) * Math.sqrt(otherNorm));
    }
}
