package edu.usfca.cs.datamining.json2csv;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReviewParser implements Runnable {
	private String text;

	public ReviewParser(String text) {
		this.text = text;
	}

	@Override
	public void run() {
		JSONParser parser = new JSONParser();
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			JSONObject json = (JSONObject) parser.parse(text);
			// TODO: Classification or int value
			list.add(json.get("stars").toString() + "stars");
			
			// add text feature.
			ReviewFeatures feature = WeightedPositiveNegativeWordsProcssor.evaluate(json.get("text").toString().toLowerCase());

			list.add(feature.positive);
			list.add(feature.negative);
			list.add(feature.positiveNegativeSum);
			list.add(feature.positiveWords);
			list.add(feature.negativeWords);
			list.add(feature.wordCount);

			// add votes.
			JSONObject votes = (JSONObject) json.get("votes");
			list.add(votes.get("cool"));
			list.add(votes.get("funny"));
			list.add(votes.get("useful"));
			CsvWriter.getInstance().printRecord(list);
		} catch (ParseException e) {
			System.err.println("Parse Error");
		}
	}
}
