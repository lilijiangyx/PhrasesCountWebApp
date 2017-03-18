package test;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.apache.poi.hssf.util.HSSFColor;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jianl018 on 3/16/17.
 */
public class GenerateMickeyMouseWordCloud {

    public static void main(String args[]) throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(4);
        frequencyAnalyzer.setStopWords(Collections.EMPTY_LIST);

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("input.txt");
        final Dimension dimension = new Dimension(700, 700);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackgroundColor(Color.WHITE);
        wordCloud.setBackground(new PixelBoundryBackground("mickey_head_no_background.png"));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        //wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.PINK, Color.MAGENTA, Color.GRAY, Color.BLACK));
//        wordCloudCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.LIGHT_GRAY, Color.BLACK, 30, 30));
        wordCloud.setFontScalar(new LinearFontScalar(20, 80));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("word_count_mickey_head.png");
    }
}
