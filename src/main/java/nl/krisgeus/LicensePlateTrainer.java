package nl.krisgeus;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class LicensePlateTrainer {

//    static String onlpModelPath = "/Users/kgeusebroek/dev/xebia/godatadriven/projects/nlp/nl-ner-plates.bin";
//    // training data set
//    static String trainingDataFilePath = "/Users/kgeusebroek/dev/xebia/godatadriven/projects/nlp/textplates.txt";
    static String onlpModelPath = "nl-ner-plates-sample.bin";
    // training data set
    static String trainingDataFilePath = "src/test/resources/textplates-sample.txt";

    public static void main(String[] args) throws IOException {
        Charset charset = Charset.forName("UTF-8");
        ObjectStream<String> lineStream = new PlainTextByLineStream(
                new FileInputStream(trainingDataFilePath), charset);
        ObjectStream<NameSample> sampleStream = new NameSampleDataStream(
                lineStream);
        TokenNameFinderModel model = null;
        HashMap<String, Object> mp = new HashMap<String, Object>();
        try {
            model = NameFinderME.train("nl", "licenseplate", sampleStream, Collections.<String, Object>emptyMap(), 100, 4);
        } finally {
            sampleStream.close();
        }
        BufferedOutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(onlpModelPath));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null)
                modelOut.close();
        }
    }
}

