package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.TOP_14337_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

public class HistoryHandler extends NgordnetQueryHandler {

    ArrayList<TimeSeries> lts = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    NGramMap ngramMap;

    public HistoryHandler(NGramMap map) {
        ngramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for (String word : words) {
            labels.add(word);
            lts.add(ngramMap.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);

        return Plotter.encodeChartAsString(chart);
    }
}