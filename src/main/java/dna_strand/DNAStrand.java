package dna_strand;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * Assessment 4 - DNA Strand
 *
 * This public class is designed to store a given DNA strand. This class is
 * capable of determining the number of Consecutive times a given sequence
 * of Nucleotides appear in this strand.
 */
public class DNAStrand {
    private Nucleotide front;
    private final int size;

    /**
     * Package-private constructor that constructs a new DNAStrand that
     * assigns each character in the String parameter 'dna' to a Nucleotide.
     * The parameter 'dna' may not be null. If 'dna' is empty, then a
     * DNAStrand containing zero nucleotides is created.
     *
     * @param dna a string containing any sequence of 'a', 'c', 't' or 'g'.
     *            Each letter representing a nucleotide.
     */
    public DNAStrand(String dna) {
        size = dna.length();
        if (dna.length() > 0) {
            front = new Nucleotide(dna.charAt(0));
            Nucleotide current = front;
            for (int index = 1; index < dna.length(); index++) {
                current.next = new Nucleotide(dna.charAt(index));
                current = current.next;
            }
        }
    }

    /**
     * Package-private method that Returns the maximum number of consecutive
     * times the parameter 'substrand' appears in this DNAStrand. The parameter
     * 'substrand' may not be null or empty.
     *
     * @throws IllegalArgumentException if 'substrand' is null or empty.
     * @param substrand any non-null or empty substrand.
     * @return the maximum number of times substrand appears consecutively in this.
     */
    public int maxConsecutiveRepeats(DNAStrand substrand) {
        ensureNotNull(substrand);
        int maxConsecutiveRepeats = 0;

        Nucleotide current = front;
        while (current != null) {
            int numRepeats = countRepeat(current, substrand);
            maxConsecutiveRepeats = Math.max(numRepeats, maxConsecutiveRepeats);
            current = pruneNucleotides(current,
                    numRepeats * substrand.size - (substrand.size / 2));
        }

        return maxConsecutiveRepeats;
    }

    /**
     * Private method that returns the number of consecutive times a given
     * substrand repeats starting from a given starting Nucleotide.
     *
     * @param originalCurrent the starting point to begin searching for 'substrand'
     * @param substrand any non-null and non-empty DNAStrand.
     * @return number of consecutive times 'substrand' appears starting at 'originalCurrent'
     */
    private int countRepeat(Nucleotide originalCurrent, DNAStrand substrand) {
        int numRepeats = 0;
        // Loop Until original current does not have next.
        while (originalCurrent != null) {
            Nucleotide substrandCurrent = substrand.front;
            // Loop until either originalCurrent or substrand dont have a next.
            while (originalCurrent != null && substrandCurrent != null) {
                if (originalCurrent.data == substrandCurrent.data) {
                    // If both nucleotides are the same, then set both to their next.
                    originalCurrent = originalCurrent.next;
                    substrandCurrent = substrandCurrent.next;
                } else {
                    // If both nucleotides are not the same, return numRepeats.
                    return numRepeats;
                }
            }
            numRepeats++;
        }
        return numRepeats;
    }

    /**
     * Private method that returns the nucleotide 'numPrune' times after
     * 'current'. If numPrune is less-than or equal to zero, the the
     * Nucleotide after current is returned.
     *
     * @param current the current Nucleotide.
     * @param numPrune the number of Nucleotides to skip over.
     * @return the Nucleotide 'numPrune' after current.
     */
    private Nucleotide pruneNucleotides(Nucleotide current, int numPrune) {
        if (numPrune <= 0 && current != null) {
            return current.next;
        }
        while (numPrune > 0 && current != null) {
            current = current.next;
            numPrune--;
        }
        return current;
    }

    /**
     * Private method that ensures the given DNAStrand is not null or empty.
     *
     * @throws IllegalArgumentException if 'substrand' is null or empty.
     * @param substrand DNAStrand to test if is null or empty.
     */
    private void ensureNotNull(DNAStrand substrand) {
        if (substrand == null) {
            throw new IllegalArgumentException("Substrand cannot be null.");
        } else if (substrand.front == null) {
            throw new IllegalArgumentException("Substrand cannot be empty.");
        }
    }

    /**
     * @return the string representation of this DNAStrand.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Nucleotide current = front; current != null; current = current.next) {
            result.append(current.data);
        }
        return result.toString();
    }

    /**
     * @param o other object to compare to.
     * @return true of o equals this.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof DNAStrand)) {
            return false;
        }
        return this.toString().equals(o.toString());
    }

    /**
     * @return the hashcode representation of this.
     */
    public int hashCode() {
        return toString().hashCode();
    }

    public static void main(String[] args) {
        DNAStrand dna = new DNAStrand("ATTATTAATTA");
        DNAStrand str = new DNAStrand("ATTA");
        System.out.println(dna.maxConsecutiveRepeats(str));
    }

    /**
     * Private class that contains a char and another Nucleotide.
     */
    private static class Nucleotide {
        // The character representation of this Nucleotide.
        public char data;
        // The next Nucleotide after this one.
        public Nucleotide next;

        /**
         * Public Constructor that  Constructs a new Nucleotide setting
         * this.data equal to 'data'. And it sets this.next equal to null.
         *
         * @param data Any character that represents a nucleotide.
         */
        public Nucleotide(char data) {
            this.data = data;
            this.next = null;
        }
    }
}
