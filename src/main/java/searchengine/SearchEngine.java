package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Kevin Paulsen
 * CSE 143 DE
 * Assessment 3 - Search Engine
 * <p>
 * This public class creates a search engine that is capable of quickly
 * scanning through many documents for keywords. Add documents to this
 * Search Engine, and this class can be receive a search query and scan
 * all provided documents. It will then return all documents that contain
 * all of the given query terms.
 */
public class SearchEngine {

    // Maps a term to the set of documents containing that term
    private final Map<String, Set<String>> index;

    /**
     * Constructs a new SearchEngine initially with an empty InvertedIndex.
     */
    public SearchEngine() {
        index = new HashMap<>();
    }

    /**
     * Package private method that Accepts a document and indexes each term,
     * or word, in the document and maps it to the document itself. Words are
     * separated with one or more spaces.
     *
     * @param document Any string with words to index.
     */
    void index(String document) {
        for (String term : split(document)) {
            Set<String> pageSet = index.get(term);
            if (pageSet == null) {
                pageSet = new HashSet<>();
                index.put(term, pageSet);
            }
            pageSet.add(document);
        }
    }

    /**
     * Package private method that Uses a given String 'query' to search the
     * inverted index. Each term, or word, in query is used to search for
     * documents containing that word. Words are any string of characters
     * separated with one or more spaces. A set of documents that contain ALL
     * of the terms in 'query' is returned.
     *
     * @param query Any string with words to search.
     * @return A set of all documents containing every word in query.
     */
    Set<String> search(String query) {
        Set<String> results = null;

        // Iterate though queryTerms until finished, or we have added a document to results.
        for (String word : split(query)) {
            Set<String> documents = index.get(word);
            if (documents != null) {
                results = getMatchingDocuments(results, documents);
            }
        }
        return results == null ? new HashSet<>() : results;
    }

    /**
     * Private method to get all the documents that match between the
     * two parameters 'matchingDocuments' and 'documents'. If first
     * parameter 'matchingDocuments' is null, a new Set is returned
     * that contains all of the documents in the second parameter
     * 'documents'.
     *
     * @param matchingDocuments A set of documents. Can be null, or empty.
     * @param documents         A non-null set of documents.
     * @return the set of documents that are in both parameters.
     */
    private Set<String> getMatchingDocuments(Set<String> matchingDocuments, Set<String> documents) {
        final Set<String> result;
        if (matchingDocuments == null) {
            // if matchingDocuments is null, instantiate it with 'documents'
            result = new HashSet<>(documents);
        } else {
            // else, set result to the set containing all words that contain
            //  all the documents in 'matchingDocuments, and documents.
            result = removeNonMatchingDocuments(matchingDocuments, documents);
        }
        return result;
    }

    /**
     * Private method that removes all documents in the first parameter
     * 'matchingDocuments' that are not in the second parameter 'documents'.
     *
     * @param matchingDocuments A non-null set containing documents.
     * @param documents         A non-null set containing documents.
     * @return the set of documents that are in both parameters.
     */
    private Set<String> removeNonMatchingDocuments(Set<String> matchingDocuments,
                                                   Set<String> documents) {
        // Iterate though matchingDocuments
        Iterator<String> documentIterator = matchingDocuments.iterator();
        while (documentIterator.hasNext()) {
            // If 'documents' does not contain the next document in matchingDocuments,
            //  remove it from 'matchingDocuments'.
            if (!documents.contains(documentIterator.next())) {
                documentIterator.remove();
            }
        }
        return matchingDocuments;
    }

    /**
     * Private method that splits any text into each term, or word. A term is
     * determined by any non-space characters separated by 1 or more whitespaces.
     *
     * @param text any non-null string.
     * @return the set of normalized terms split from the given text.
     */
    private static Set<String> split(String text) {
        Set<String> result = new HashSet<>();
        for (String term : text.split("\\s+")) {
            term = normalize(term);
            if (!term.isEmpty()) {
                result.add(term);
            }
        }
        return result;
    }

    /**
     * Private method that normalizes the given string. A String is considered
     * normalized if there are no leading or trailing non-alphabetic characters,
     * and all letters are lowercase.
     *
     * @param s any non-null string.
     * @return a standardized lowercase representation of the given string.
     */
    private static String normalize(String s) {
        return s.toLowerCase().replaceAll("(^\\p{P}+\\s*)|(\\s*\\p{P}+$)", "");
    }
}
