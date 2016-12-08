package edu.usfca.cs.datamining.json2csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriter {
	private static CsvWriter instance = null;
	private static final String WRITE_CSV_FILE_NAME = "/Users/richard/Desktop/reviews.csv";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "Stars", "Positive", "Negative", "PositiveNegativeSum",
			"PositiveWords", "NegativeWords", "TotalWords", "Cool", "Funny", "Useful" };
	private static final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
	private int count = 0;
	private CSVPrinter writer;

	private CsvWriter() {
		try {
			writer = new CSVPrinter(new FileWriter(WRITE_CSV_FILE_NAME), csvFileFormat);
			writer.printRecord(FILE_HEADER);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CsvWriter getInstance() {
		if (instance == null) {
			instance = new CsvWriter();
		}
		return instance;
	}

	public synchronized void printRecord(ArrayList<Object> list) {
		try {
			writer.printRecord(list);
			count++;
			if (count % 1000 == 0) {
				System.out.println(count);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void flush() {
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
