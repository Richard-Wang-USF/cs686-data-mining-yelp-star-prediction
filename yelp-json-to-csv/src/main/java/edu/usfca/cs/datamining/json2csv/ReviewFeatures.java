package edu.usfca.cs.datamining.json2csv;

public class ReviewFeatures {
	public int negative = 0, positive = 0, positiveNegativeSum = 0, negativeWords = 0, positiveWords = 0, wordCount = 0;

	@Override
	public String toString() {
		return "negative: " + negative + "\n" + "positive: " + positive + "\n" + "positiveNegativeSum: "
				+ positiveNegativeSum + "\n" + "word count: " + wordCount + "\n";
	}
}
