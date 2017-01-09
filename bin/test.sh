#!/bin/bash

export NER_RES=../src/test/resources/nl-ner
export TIKA_APP=../target/nlp-tika-1.0-SNAPSHOT-jar-with-dependencies.jar

java -classpath $NER_RES:$TIKA_APP org.apache.tika.cli.TikaCLI --config=../src/main/resources/tika-config.xml -m ../src/test/resources/zin.txt

java -Dner.impl.class=org.apache.tika.parser.ner.regex.RegexNERecogniser \
    -classpath $NER_RES:$TIKA_APP org.apache.tika.cli.TikaCLI \
    --config=../src/main/resources/tika-config.xml -m ../src/test/resources/metkenteken.txt
