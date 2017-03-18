package test;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianl018 on 3/2/17.
 */
public class CountPhrases {


    /**
     * split the sourcefile into phrases which have 2 to 10 words and count its frequency
     */
    public static ArrayList<ArrayList<String>> countPhrases(String source, int phraseMinimumLength, int phrasesMaximumLength, int minimumCount) throws Exception {

        //Create punctuation check pattern
        Pattern pattern = Pattern.compile("[,.?!;]");
        //Create array with stop words
        String[] stopWords = new String[]{"i", "a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an", "and", "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};

        //Create result ArrayList
        ArrayList<ArrayList<String>> resultArrayList = new ArrayList<>();

        //Array list to hold all combined phrases
        ArrayList<String[]> combinedPhrases = new ArrayList<>();

        //Split the String into lines
        String[] lines = source.split(System.getProperty("line.separator"));

        for (int i = 0; i < lines.length; i++) {
            //Read each line
            String tempLine = lines[i];

            //Only read the line which is not empty
            if (tempLine != null && !tempLine.isEmpty() && !tempLine.trim().equals("") && !tempLine.trim().equals("\n")) {
                //Adding spaces around punctuations in this line
                Matcher matcher = pattern.matcher(tempLine);
                String fixedLine = matcher.replaceAll(" $0 ").trim().toLowerCase();

                //Parse the line into words
                ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(fixedLine.split("\\s+")));

                //Create temp combined phrases for each line
                ArrayList<String[]> tempCombinedWords = combinePhrases(listOfWords, phrasesMaximumLength, phraseMinimumLength, pattern, stopWords);
                //Add temp combined words into combinedPhrases
                combinedPhrases.addAll(tempCombinedWords);

            }
        }


        //HashMap to count phrases
        Map<String, Integer[]> phrasesCountMap = new HashMap<>();

        for (String[] phrase : combinedPhrases) {
            if (phrase[0].matches(".*[a-zA-Z]+.*")) {
                if (phrasesCountMap.containsKey(phrase[0])) {
                    Integer count = phrasesCountMap.get(phrase[0])[1];
                    phrasesCountMap.put(phrase[0], new Integer[]{Integer.valueOf(phrase[1]), count + 1});
                } else {
                    phrasesCountMap.put(phrase[0], new Integer[]{Integer.valueOf(phrase[1]), 1});
                }
            }
        }


        //read HashMap result to ArrayList
        ArrayList<String[]> phrasesCountList = new ArrayList<>();
        phrasesCountMap.forEach((k, v) -> phrasesCountList.add(new String[]{k, Integer.toString(v[0]), Integer.toString(v[1])}));

        //sort by phrases length
        phrasesCountList.sort((String[] phrase1, String[] phrase2) -> Integer.parseInt(phrase2[1]) - Integer.parseInt(phrase1[1]));

        //sort by phrases count
        phrasesCountList.sort((String[] phrase1, String[] phrase2) -> Integer.parseInt(phrase2[2]) - Integer.parseInt(phrase1[2]));


        //Output the results to target file
//        try (
//                //Create output file
//                PrintWriter output = new PrintWriter(outputFile);
//        ) {
//            output.printf("%-70s%-10s%s%n", "Phrases", "Length", "Count");
//            phrasesCountList.forEach(s -> {
//                if (Integer.parseInt(s[2]) > 1) {
//                    output.printf("%-70s%-10s%s%n", s[0], s[1], s[2]);
//                }
//            });
//        }

        //return the result ArrayList
        for (String[] phrases : phrasesCountList) {
            if (Integer.parseInt(phrases[2]) >= minimumCount) {
                ArrayList<String> tempList = new ArrayList<>(Arrays.asList(phrases));
                resultArrayList.add(tempList);
            }
        }

        return resultArrayList;

    }


    /**
     * Combine a list of words to every possible phrases and return the result
     */
    public static ArrayList<String[]> combinePhrases(ArrayList<String> listOfWords,
                                                     int phrasesMaximumLength,
                                                     int phraseMinimumLength,
                                                     Pattern pattern,
                                                     String[] stopWords) {
        //Array list to hold all combined phrases
        ArrayList<String[]> combinedPhrases = new ArrayList<>();

        for (int i = 0; i < listOfWords.size(); i++) {
            String tempPhrase = "";

            for (int j = 0; j < phrasesMaximumLength && i + j < listOfWords.size(); j++) {

                //Check the single word if it is punctuation
                if (listOfWords.get(i + j).length() == 1) {
                    Matcher singleCharMatcher = pattern.matcher(listOfWords.get(i + j));
                    if (singleCharMatcher.matches()) {
                        break;
                    }
                }

                //If the first word of the phrases is stop word, do not add this single stop word as a phrase
                if (j == 0 && Arrays.asList(stopWords).contains(listOfWords.get(i + j).toLowerCase())) {
                    tempPhrase += " " + listOfWords.get(i + j);
                    continue;
                }

                tempPhrase += " " + listOfWords.get(i + j);

                //Add phrases which has more than one word
                if (j >= phraseMinimumLength - 1) {
                    combinedPhrases.add(new String[]{tempPhrase.trim(), Integer.toString(j + 1)});
                }

                //combinedPhrases.add(new String[]{tempPhrase.trim(), Integer.toString(j + 1)});
            }
        }

        return combinedPhrases;
    }


}
