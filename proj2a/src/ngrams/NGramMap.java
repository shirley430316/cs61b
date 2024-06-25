package ngrams;

import java.util.Collection;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static utils.Utils.TOTAL_COUNTS_FILE;
import java.util.Iterator;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    public TreeMap<String, TimeSeries> ngm = new TreeMap<>();
    public TimeSeries total_counts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.

        In wordFile = new In(wordsFilename);
        In countsFile = new In(countsFilename);

        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] tmp = line.split(",");
            total_counts.put(Integer.parseInt(tmp[0]),Double.parseDouble(tmp[1]));
        }

        TimeSeries ts = new TimeSeries();
        String line = wordFile.readLine();
        String[] word = line.split("\t");
        String lastWord = null;

        while (wordFile.hasNextLine()) {
            if (! word[0].equals(lastWord)) {
                if (lastWord != null)   ngm.put(lastWord,ts);
                ts = new TimeSeries();
            }

            lastWord = word[0];

            ts.put(Integer.parseInt(word[1]),Double.parseDouble(word[2]));
            line = wordFile.readLine();
            word = line.split("\t");

        }

        ngm.put(lastWord,ts);
        
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        TimeSeries ts = ngm.get(word);

        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return ngm.get(word);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        if (ngm.isEmpty()) {
            NGramMap newngm = new NGramMap(null, TOTAL_COUNTS_FILE);
            return newngm.total_counts;
        }
        return total_counts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries original = ngm.get(word);
        TimeSeries weights = new TimeSeries(original, startYear, endYear);

        for (int i = startYear; i <= endYear; i++) {
            if (original.containsKey(i)) {
                double ratio = original.get(i) / (double) total_counts.get(i);
                weights.put(i, ratio);
            }
        }
        return weights;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return this.weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {

        Iterator<String> iter = words.iterator();
        TimeSeries ts = ngm.get(iter.next());

        while(iter.hasNext()) {
            ts = ts.plus(weightHistory(iter.next()));
        }
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(ngm.keySet(), MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
