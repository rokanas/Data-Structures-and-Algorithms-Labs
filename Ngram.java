import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A class representing an n-gram.
// You can complete the lab without looking at the code.
class Ngram implements Comparable<Ngram> {
    // An n-gram is just a list of tokens (strings) of length n.
    private List<String> tokens;

    public Ngram(String... tokens) {
        // Create a new list because the caller could modify the token array later.
        this.tokens = new ArrayList<String>(Arrays.asList(tokens));
    }

    private Ngram(List<String> tokens) {
        this.tokens = tokens;
    }

    // Return all n-grams of a given input string.
    // The space used is just that for a single list of all the n-grams in the input.
    public static Ngram[] ngrams(String input, int n) {
        List<String> tokens = Stream.of(input.split("\\W"))
            .filter(str -> !str.isEmpty())
            .map(String::toLowerCase)
            .toList();

        int count = Math.max(0, tokens.size() - n + 1);
        Ngram[] ngrams = new Ngram[count];
        for (int i = 0; i < count; i++)
            ngrams[i] = new Ngram(tokens.subList(i, i + n));
        return ngrams;
    }

    // Number of tokens in the n-gram.
    public int length() {
        return tokens.size();
    }

    // Accessing the individual tokens of the n-gram.
    public String at(int i) {
        return tokens.get(i);
    }

    public int compareTo(Ngram other) {
        if (length() != other.length())
            return length() - other.length();

        for (int i = 0; i < length(); i++) {
            int wordDiff = at(i).compareTo(other.at(i));
            if (wordDiff != 0)
                return wordDiff;
        }
        return 0;
    }

    public boolean equals(Object obj) {
        return obj != null && obj instanceof Ngram && tokens.equals(((Ngram) obj).tokens);
    }

    public int hashCode() {
        return tokens.hashCode();
    }

    public String toString() {
        return tokens.stream().collect(Collectors.joining("/"));
    }
}
