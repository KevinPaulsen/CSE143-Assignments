package election_simulator;

import java.util.*;
import java.io.*;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * Assessment 6 - Election Simulator
 *
 * This Class is designed to simulate an elction to determine the
 * minimum votes a can re
 */
public class ElectionSimulator {

    private final List<State> states;
    private final Map<Arguments, Node> combinations;

    ElectionSimulator(List<State> states) {
        this.states = states;
        this.combinations = new HashMap<>();
    }

    Set<State> simulate() {
        Node node = simulate(minElectoralVotes(states), 0);
        if (node != null) {
            return node.toHashSet();
        }
        return null;
    }

    private Node simulate(int electoralVotesRemaining, int stateIndex) {
        Arguments arguments = new Arguments(electoralVotesRemaining, stateIndex);
        if (combinations.containsKey(arguments)) {
            return combinations.get(arguments);
        } else if (electoralVotesRemaining <= 0) {
            return new Node(null, null);
        } else if (stateIndex == states.size()) {
            combinations.put(arguments, null);
            return null;
        }

        int minVotes = Integer.MAX_VALUE;
        Node result = null;

        for (int currentIdx = stateIndex; currentIdx < states.size(); currentIdx++) {
            State currentState = states.get(currentIdx);
            Node minRemainingStates = simulate(
                    electoralVotesRemaining - currentState.electoralVotes,
                    currentIdx + 1);
            if (minRemainingStates != null) {
                if (minRemainingStates.state == null) {
                    minRemainingStates.state = currentState;
                } else {
                    minRemainingStates = new Node(currentState, minRemainingStates);
                }
                int numVotes = minPopularVotes(minRemainingStates);
                if (numVotes < minVotes) {
                    minVotes = numVotes;
                    result = minRemainingStates;
                }
            }
        }
        combinations.put(arguments, result);
        return result;
    }

    public static int minElectoralVotes(List<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.electoralVotes;
        }
        return total / 2 + 1;
    }

    public static int minPopularVotes(Node node) {
        int total = 0;
        while (node != null) {
            total += node.state.popularVotes / 2 + 1;
            node = node.next;
        }
        return total;
    }

    public static int minPopularVotes(Collection<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.popularVotes / 2 + 1;
        }
        return total;
    }

    private static int totalVotes(List<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.popularVotes;
        }
        return total;
    }

    private static int totalElectoralVotes(Set<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.electoralVotes;
        }
        return total;
    }

    private static class Arguments implements Comparable<Arguments> {
        public final int electoralVotes;
        public final int index;

        public Arguments(int electoralVotes, int index) {
            this.electoralVotes = electoralVotes;
            this.index = index;
        }

        public int compareTo(Arguments other) {
            int cmp = Integer.compare(this.electoralVotes, other.electoralVotes);
            if (cmp == 0) {
                cmp = Integer.compare(this.index, other.index);
            }
            return cmp;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Arguments)) {
                return false;
            }
            Arguments other = (Arguments) o;
            return this.electoralVotes == other.electoralVotes && this.index == other.index;
        }

        public int hashCode() {
            return Objects.hash(electoralVotes, index);
        }
    }

    private static class Node {
        private State state;
        private Node next;

        private Node(State state, Node next) {
            this.state = state;
            this.next = next;
        }

        private Set<State> toHashSet() {
            Set<State> result = new HashSet<>();
            Node current = this;
            while (current != null) {
                result.add(current.state);
                current = current.next;
            }
            return result;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<State> states = new ArrayList<>(51);
        try (Scanner input = new Scanner(new File("/Users/kevinpaulsen/dev/cse143_projects/src/main/java/election_simulator/data/2016.csv"))) {
            while (input.hasNextLine()) {
                states.add(State.fromCsv(input.nextLine()));
            }
        }
        new ElectionSimulator(states).simulate();
        new ElectionSimulator(states).simulate();

        long startTime = System.nanoTime();
        Set<State> result = new ElectionSimulator(states).simulate();
        long endTime = System.nanoTime();
        System.out.println(((double) endTime - startTime) * 0.000001 + " milliseconds\n\n");


        /*System.out.println(result);
        System.out.println(minPopularVotes(result) + " votes");
        System.out.println(minElectoralVotes(states) + " min electoral votes");
        System.out.println(totalElectoralVotes(result) + " electoral votes");
        System.out.println(((double) minPopularVotes(result)) / totalVotes(states) * 100 + "%");//*/
    }
}
