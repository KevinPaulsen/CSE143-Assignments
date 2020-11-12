package language_generator;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * Assessment 4 - DNA Strand
 *
 * This class is capable of generating a random string in a Language.
 * To generate the String, a Grammar object is required to specify the
 * production rules for the given language.
 */
public class LanguageGenerator {

    private final Grammar grammar;
    private final Random random;

    /**
     * Constructs a new LanguageGenerator with the given Grammar.
     *
     * @param grammar any non-null Grammar Object.
     */
    public LanguageGenerator(Grammar grammar) {
        this(grammar, new Random());
    }

    /**
     * Constructs a new LanguageGenerator with the given
     * Grammar and Random Objects.
     *
     * @param grammar Any non-null Grammar Object.
     * @param random Any non-null Random Object.
     */
    public LanguageGenerator(Grammar grammar, Random random) {
        this.grammar = grammar;
        this.random = random;
    }

    /**
     * Generates and returns a random phrase using the given Grammar. A
     * Single space will be between each word in the generated String. If
     * 'target' is terminal, target is returned. If 'target' is non-terminal,
     * then a random production rule is chosen from the parameter 'target'.
     * For each symbol in the production rule, a new String is generated with
     * this symbol as the given target symbol.
     *
     * @param target any non-null symbol.
     * @return a randomly generated String that follows the rules specified in this grammar.
     */
    String generate(String target) {
        String[] productionRule = grammar.productionRules.get().get(target);
        String result = "";

        if (productionRule == null) {
            // If no production rule exists for this target, then it is non terminal.
            result = target;
        } else {
            // Randomly select a symbol from production rule, and split the symbol at each space.
            String[] newTargets = productionRule[random.nextInt(productionRule.length)]
                    .split("\\s+");
            // For each symbol in newTargets generate a new randomized String from that symbol.
            for (String newTarget : newTargets) {
                result += " " + generate(newTarget);
            }
        }

        return result.strip();
    }

    /**
     * This enum holds three different grammar rules. One for Math formulas,
     * One for Music notation, and one for english words.
     */
    public enum Grammar {
        FORMULA(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("E", "T, E OP T".split(", "));
            result.put("T", "x, y, 1, 2, 3, ( E ), F1 ( E ), - T, F2 ( E . E )".split(", "));
            result.put("OP", "+, -, *, %, /".split(", "));
            result.put("F1", "sin, cos, tan, sqrt, abs".split(", "));
            result.put("F2", "max, min, pow".split(", "));
            return result;
        }),
        MUSIC(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("measure", "pitch-w, half half".split(", "));
            result.put("half", "pitch-h, quarter quarter".split(", "));
            result.put("quarter", "pitch-q, pitch pitch".split(", "));
            result.put("pitch", "C, D#, F, F#, G, A#, C6".split(", "));
            result.put("chordmeasure", "chord-w, halfchord halfchord".split(", "));
            result.put("halfchord", "chord-h, chord-q chord-q".split(", "));
            result.put("chord", "Cmin, Cmin7, Fdom7, Gdom7".split(", "));
            result.put("bassdrum", "O..o, O..., O..o, OO..".split(", "));
            result.put("snare", "..S., S..s, .S.S".split(", "));
            result.put("crash", "...*, *...".split(", "));
            result.put("claps", "x..x, xx..x".split(", "));
            return result;
        }),
        ENGLISH(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("SENTENCE", "NOUNP VERBP".split(", "));
            result.put("NOUNP", "DET ADJS NOUN, PROPNOUN".split(", "));
            result.put("PROPNOUN", "Seattle, Matisse, Kim, Zela, Nia, Remi, Alonzo".split(", "));
            result.put("ADJS", "ADJ, ADJ ADJS".split(", "));
            result.put("ADJ", "fluffy, bright, colorful, beautiful, purple, calming".split(", "));
            result.put("DET", "the, a".split(", "));
            result.put("NOUN", "cat, dog, bagel, apple, person, school, car, train".split(", "));
            result.put("VERBP", "TRANSVERB NOUNP, INTRANSVERB".split(", "));
            result.put("TRANSVERB", "ate, followed, drove, smacked, embraced, helped".split(", "));
            result.put("INTRANSVERB", "shined, smiled, laughed, sneezed, snorted".split(", "));
            return result;
        });

        public final Supplier<Map<String, String[]>> productionRules;

        Grammar(Supplier<Map<String, String[]>> productionRules) {
            this.productionRules = productionRules;
        }
    }

    public static void main(String[] args) {
        LanguageGenerator generator = new LanguageGenerator(Grammar.MUSIC);
        System.out.println(generator.generate("measure"));
    }
}
