package edu.usfca.cs.datamining.json2csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;

public class WeightedPositiveNegativeWordsProcssor {

	private static final HashMap<String, Integer> dic;
	private static final char[] ordinaryChars = new char[] { '\'', '\"', '/', '.' };

	static {
		dic = new HashMap<String, Integer>();
		try (Scanner scanner = new Scanner(new File("res/AFINN.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				dic.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ReviewFeatures evaluate(String text) {
		Reader reader = new StringReader(text);
		StreamTokenizer st = new StreamTokenizer(new BufferedReader(reader));

		for (char c : ordinaryChars) {
			st.ordinaryChar(c);
		}

		ReviewFeatures features = new ReviewFeatures();
		try {
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				switch (st.ttype) {
				case StreamTokenizer.TT_EOL:
					break;
				case StreamTokenizer.TT_NUMBER:
					break;
				case StreamTokenizer.TT_WORD:
					if (dic.containsKey(st.sval)) {
						if (dic.get(st.sval) > 0) {
							features.positive += dic.get(st.sval);
							features.positiveWords++;
						} else {
							features.negative -= dic.get(st.sval);
							features.negativeWords++;
						}
						features.positiveNegativeSum += dic.get(st.sval);
					}
					features.wordCount++;
					break;
				default:
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return features;
	}
}
