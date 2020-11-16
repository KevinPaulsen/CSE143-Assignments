package election_simulator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * Assessment 6 - Election Simulator
 *
 * This Class is designed to simulate an election to determine the
 * minimum number of votes a candidate can receive while still winning
 * a majority of the elctoral votes.
 */
public class ElectionSimulator {

    // The List of states in this Election.
    private final List<State> states;
    // The Map of the minimum popular vote set given a given argument.
    private final Map<Arguments, Set<State>> combinations;

    /**
     * Constructs a new ElectionSimulator with the given list of states.
     *
     * @param states any non-null List of states.
     */
    public ElectionSimulator(List<State> states) {
        this.states = states;
        this.combinations = new HashMap<>();
    }

    /**
     * Simulates the election and returns the Set of states that minimizes
     * the popular vote while still winning the general election.
     *
     * @return Set of states need to win the election with the minimum number of votes.
     */
    public Set<State> simulate() {
        return simulate(minElectoralVotes(states), 0);
    }

    /**
     * Private method to get the Set of states the minimizes the popular vote
     * while still winning the majority of the electoral votes.
     *
     * @param electoralVotesRemaining the number of needed electoral votes to win
     *                                the election.
     * @param stateIndex              the current index in 'states' to continue searching.
     * @return the Set of optimal states to minimize the popular vote while still
     * receiving at least 'electoralVotesRemaining;
     */
    private Set<State> simulate(int electoralVotesRemaining, int stateIndex) {
        Arguments arguments = new Arguments(electoralVotesRemaining, stateIndex);
        if (combinations.containsKey(arguments)) {
            return combinations.get(arguments);
        } else if (electoralVotesRemaining <= 0) {
            return new HashSet<>();
        } else if (stateIndex == states.size()) {
            combinations.put(arguments, null);
            return null;
        }

        int minVotes = Integer.MAX_VALUE;
        Set<State> result = null;
        for (int currentIdx = stateIndex; currentIdx < states.size(); currentIdx++) {
            State currentState = states.get(currentIdx);
            Set<State> minRemainingStates = simulate(
                    electoralVotesRemaining - currentState.electoralVotes,
                    currentIdx + 1);
            if (minRemainingStates != null) {
                minRemainingStates.add(currentState);
                int numVotes = minPopularVotes(minRemainingStates);
                if (numVotes < minVotes) {
                    minVotes = numVotes;
                    result = new HashSet<>(minRemainingStates);
                }
            }
        }
        combinations.put(arguments, result);
        return result;
    }

    /**
     * Calculate and return minimum number of electoral votes needed to win the election.
     *
     * @param states any non-null list of states.
     * @return the minimum number of electoral votes needed to win the election.
     */
    public static int minElectoralVotes(List<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.electoralVotes;
        }
        return total / 2 + 1;
    }

    /**
     * Calculate and return the minimum number of votes needed to win
     * all of the given 'states'.
     *
     * @param states any non-null list of states.
     * @return the number of votes needed to win all of the given states.
     */
    public static int minPopularVotes(Set<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.popularVotes / 2 + 1;
        }
        return total;
    }

    /**
     * Private class that stores the two integers electoral votes and index.
     */
    private static class Arguments implements Comparable<Arguments> {
        public final int electoralVotes;
        public final int index;

        /**
         * Constructs an Arguments instance with the given 'electoralVotes' and 'index'
         *
         * @param electoralVotes any integer
         * @param index any integer
         */
        public Arguments(int electoralVotes, int index) {
            this.electoralVotes = electoralVotes;
            this.index = index;
        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * @param other any other non-null Arguments instance.
         * @return integer comparison.
         */
        @Override
        public int compareTo(Arguments other) {
            int cmp = Integer.compare(this.electoralVotes, other.electoralVotes);
            if (cmp == 0) {
                cmp = Integer.compare(this.index, other.index);
            }
            return cmp;
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         *
         * @param o any other object to compare against.
         * @return true if this equals 'o', false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Arguments)) {
                return false;
            }
            Arguments other = (Arguments) o;
            return this.electoralVotes == other.electoralVotes && this.index == other.index;
        }

        /**
         * The hash code value for this object.
         *
         * @return the hash code value for this object.
         */
        @Override
        public int hashCode() {
            return Objects.hash(electoralVotes, index);
        }
    }
}
