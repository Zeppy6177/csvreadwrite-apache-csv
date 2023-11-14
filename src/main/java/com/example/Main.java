package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static String getUserInput(String msg) {
        if (msg != null) {
            System.out.println(msg);
        }

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("")) {
                System.out.println("The input was blank.");
                continue;
            }

            return input;
        }
    }

    static ArrayList<CSVRecord> csvRecords = new ArrayList<>();

    public static void writeCSV(CSVParser csvParser)
    {
        
    }

    public static void printCSV(CSVParser csvParser)
    {
        for (CSVRecord csvRecord : csvParser) {
            for(String test : csvRecord)
            {
                System.out.printf("%-23s", test.replace("\"", ""));
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        String filePath = "inventory.csv";

        try (Reader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            
            printCSV(csvParser);
        } 
        catch (IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            scanner.close();
        }
    }
}
