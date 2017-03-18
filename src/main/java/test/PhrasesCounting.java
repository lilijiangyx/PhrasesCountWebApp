package test;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianl018 on 3/14/17.
 */

@Named(value = "phrases")
@RequestScoped
public class PhrasesCounting{
    private String input;
    private int maximumLength;
    private boolean showCount = false;
    private boolean showSummary = false;
    private boolean showEmptyMessage = true;
//    private boolean showWordCloud = false;

    ArrayList<ArrayList<String>> resultArrayList;


    public PhrasesCounting() {
    }

    public boolean isShowEmptyMessage() {
        return showEmptyMessage;
    }

    public boolean isShowCount() {
        return showCount;
    }

//    public boolean isShowWordCloud() {return showWordCloud;}

    public boolean isShowSummary() {
        return showSummary;
    }


    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    //Count the multiple phrases and assign the result to resultArrayList
    public void count() throws Exception {
//        showWordCloud = false;

        if (input.length() > 0) {
            showEmptyMessage = false;
        }

        ArrayList<ArrayList<String>> countArrayList = CountPhrases.countPhrases(input, 2, maximumLength, 2);
        resultArrayList = countArrayList;
        if (resultArrayList != null && resultArrayList.size() != 0) {
            showCount = true;
            showSummary = false;
        }
    }

    //Get the resultArrayList after multiple phrases counting
    public ArrayList<ArrayList<String>> getPhrasesCountArrayList() {
        if (resultArrayList != null && resultArrayList.size() != 0) {
            return resultArrayList;
        } else {
            ArrayList<String> emptyArrayList = new ArrayList<>(Arrays.asList("", "", ""));
            ArrayList<ArrayList<String>> emptyList = new ArrayList<>();
            emptyList.add(emptyArrayList);
            return emptyList;
        }
    }

    //Get the single word counting and assign result to resultArrayList
    public void summarize() throws Exception {
        showSummary = true;
//        showWordCloud = false;

        ArrayList<ArrayList<String>> tempResultArrayList = CountPhrases.countPhrases(input, 1, 1, 1);

        if (tempResultArrayList.size() > 0) {
            showCount = true;
            showEmptyMessage = false;
            ArrayList<ArrayList<String>> summarizeList = new ArrayList<>();
            for (int i = 0; i < 20 && i < tempResultArrayList.size(); i++) {
                summarizeList.add(tempResultArrayList.get(i));
            }
            resultArrayList = summarizeList;
        }

    }

    //Get Summary data
    public ArrayList<String[]> getSummary() {
        int totalWords;
        int uniqueWords;
        double vocabularyDensity;
        Pattern pattern = Pattern.compile("[,.?!;]");
        Matcher matcher = pattern.matcher(input);

        if (input == null || input.isEmpty() || input.trim().equals("") || input.trim().equals("\n")) {
            totalWords = 0;
            uniqueWords = 0;
            vocabularyDensity = 0;

        } else {
            String fixedInput = matcher.replaceAll(" ").toLowerCase();
            totalWords = (new ArrayList<>(Arrays.asList(fixedInput.split("\\s+")))).size();

            String[] allWords = fixedInput.split("\\s+");
            Set<String> wordsSet = new HashSet<>();
            for (int i = 0; i < allWords.length; i++) {
                wordsSet.add(allWords[i]);
            }
            uniqueWords = wordsSet.size();
            vocabularyDensity = (double) uniqueWords / (double) totalWords;
        }


        String[] total = {"Total Words", Integer.toString(totalWords)};
        String[] unique = {"Unique Words", Integer.toString(uniqueWords)};
        String[] vocabulary = {"Vocabulary Density", String.format("%.2f", vocabularyDensity)};

        ArrayList<String[]> summary = new ArrayList<>();
        summary.add(total);
        summary.add(unique);
        summary.add(vocabulary);

        return summary;

    }


//    public void wordCloud() throws Exception {
//        showWordCloud = true;
//
//        if (input != null && !input.isEmpty() && !input.trim().equals("") && !input.trim().equals("\n")) {
//            Pattern pattern = Pattern.compile("[,.?!;]");
//            Matcher matcher = pattern.matcher(input);
//            ArrayList<String> totalWords;
//            showWordCloud = true;
//            showEmptyMessage = false;
//            String fixedInput = matcher.replaceAll(" ").toLowerCase();
//            totalWords = new ArrayList<>(Arrays.asList(fixedInput.split("\\s+")));
//
//            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//            frequencyAnalyzer.setWordFrequenciesToReturn(300);
//            frequencyAnalyzer.setMinWordLength(3);
//            frequencyAnalyzer.setStopWords(Collections.EMPTY_LIST);
//
//            final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(totalWords);
//            final Dimension dimension = new Dimension(700, 700);
//            final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//            wordCloud.setPadding(2);
//            wordCloud.setBackgroundColor(Color.WHITE);
//            wordCloud.setBackground(new PixelBoundryBackground("web/resources/image/mickey_head_no_background.png"));
//            //wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//            //wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.PINK, Color.MAGENTA, Color.GRAY, Color.BLACK));
//            wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.LIGHT_GRAY, Color.BLACK, 30, 30));
//            wordCloud.setFontScalar(new LinearFontScalar(20, 80));
//            wordCloud.build(wordFrequencies);
//            wordCloud.writeToFile("web/resources/image/word_count_mickey_head.png");
//        }
//    }


}
