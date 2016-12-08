package edu.usfca.cs.datamining.json2csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class App {
	private static final String REVIEW_DATASET_PATH = "/Users/richard/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_review.json";
	private static final String TEST_DATASET_PATH = "input/yelp_test_data.json";
	private final ExecutorService executorService;

	public App() {
		executorService = Executors.newFixedThreadPool(8);
	}

	/**
	 * @param path : the path of data-set file
	 * @param fraction : e.g. fraction = 3 generate 1/3 of the total data set
	 * */
	public void parseSample(String path, int fraction) {
		final long startTime = System.currentTimeMillis();

		try (FileReader reader = new FileReader(path)) {
			try (Scanner file = new Scanner(reader)) {
				int count = 0;
				while (file.hasNextLine()) {
					String line = file.nextLine();
					if (count % fraction == 0) {
						executorService.execute(new ReviewParser(line));
					}
					count++;
				}
			}
			System.out.println("Done");
		} catch (IOException e) {
			System.err.println("Can't find File in path:" + path.toString());
		}

		try {
			executorService.shutdown();
			executorService.awaitTermination(30, TimeUnit.MINUTES);
			CsvWriter.getInstance().flush();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) % 1000 + " Seconds");
	}

	public static void main(String[] args) {
		new App().parseSample(App.REVIEW_DATASET_PATH, 1);
	}
}
