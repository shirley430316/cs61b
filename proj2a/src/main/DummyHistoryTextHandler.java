package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;
import ngrams.NGramMap;

public class DummyHistoryTextHandler extends NgordnetQueryHandler {

    NGramMap nGramMap;

    public DummyHistoryTextHandler(NGramMap map) {
        nGramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            response += word + ": ";
            response += nGramMap.weightHistory(word, startYear, endYear).toString();
            response += "\n";
        }
        return response;


    }
}
