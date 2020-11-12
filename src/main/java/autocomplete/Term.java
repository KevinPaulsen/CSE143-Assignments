package autocomplete;

import java.util.ArrayList;

/**
 * Kevin Paulsen
 * CSE 143 DE
 *
 * This term class contains two final data types a String and an integer.
 * Term implements comparable and can compare itself to any other Term. Term
 * can compare itself by comparing its string 'query' to the 'query' of another
 * Term or by comparing the integer 'weight.'
 */
public class Term implements Comparable<Term> {

    // Any not null string.
    private final String query;
    // Any integer.
    private final int weight;

    /**
     * Constructor that guarantees query is not null.
     *
     * @param query and non null string.
     * @param weight and integer.
     */
    public Term(String query, int weight) {
        if (query == null) {
            throw new IllegalArgumentException("query cannot be null.");
        }

        this.query = query;
        this.weight = weight;
    }

    /**
     * Compares the query of this term to the query of another term.
     * The comparison is done alphabetically and is case insensitive.
     *
     * @param other term to compare against
     * @return a negative integer if this query would appear first in a dictionary,
     * 0 if both words are the same, and is positive if this query would appear after
     * other.query in a dictionary.
     */
    @Override
    public int compareTo(Term other) {
        return query.compareToIgnoreCase(other.query);
    }

    /**
     * Compares this term's weight to a given term's weight.
     *
     * @throws IllegalArgumentException if
     * @param other another term.
     * @return integer comparison between this term and other. 1 if weight is
     * greater than other.weight, 0 if weight is equal to other.weight, -1 if
     * weight is less than other.weight.
     */
    public int compareToByReverseWeight(Term other) {
        return Integer.compare(weight, other.weight);
    }

    /**
     * @return the query of this term.
     */
    public String query() {
        return query;
    }

    /**
     * @return the weight of this term.
     */
    public int weight() {
        return weight;
    }

    /**
     * @return the query string.
     */
    @Override
    public String toString() {
        return query;
    }
}
