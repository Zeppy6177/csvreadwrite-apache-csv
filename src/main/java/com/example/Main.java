package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    // method used to prompt user for input
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

    // checking if a string is an int
    public static Boolean isInt(String str)
    {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("The enterd value is not formated correctly.");
            return false;
        }
    }

    // checking if a string is a float
    public static Boolean isFloat(String str)
    {
        try {
            Float.parseFloat(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("The enterd value is not formated correctly.");
            return false;
        }
    }

    /*
     * This method below can write to any csv file but no input verification can be done
     */
    /* public static void writeCSV(String filePath)
    // {
    //     CSVParser csvParser = null;
    //     FileWriter writer = null;
    //     try
    //     {
    //         Reader reader = new FileReader(filePath);
    //         writer = new FileWriter(filePath, true);

    //         csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            
    //         ArrayList<CSVRecord> csvRecords = new ArrayList<>(csvParser.getRecords());

    //         ArrayList<String> newRecord = new ArrayList<>();
    
    //         if(csvRecords.size() != 0)
    //             for (int i = 0; i < csvRecords.get(0).values().length; i++)
    //             {
    //                 newRecord.add(getUserInput(csvRecords.get(0).values()[i].replaceAll("[\"]", "") + ":"));
    //             }
    //         else
    //             System.err.println("File Empty!");

    //         if (newRecord.size() != 0)
    //         {
    //             for (int i = 0; i < newRecord.size(); i++) {
    //                 if (i != newRecord.size() - 1)
    //                     writer.write('"' + newRecord.get(i) + "\", ");
    //                 else
    //                     writer.write('"' + newRecord.get(i) + '"');
    //             }

    //             writer.write("\r\n");
    //         }
    //         else
    //             System.err.println("Nothing written.");
                
    //     }
    //     catch (Exception e)
    //     {
    //         e.printStackTrace();
    //     }
    //     finally
    //     {
    //         try {
    //             writer.close();   
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
        } 
    */

    /*
     * This method is used to get user input for a 
     * new record for the inventory.csv file.
     */
    public static void writeCSV(String filePath)
    {
        // FileWriter for writing to files later
        FileWriter writer = null;

        // pattern for the id
        Pattern idPattern = Pattern.compile("\\b\\d{5}\\b");
        
        // Exception handling for the writer
        try
        {
            // assigning 'writer' to a new instance of FileWriter with the passed file path
            writer = new FileWriter(filePath, true);
            
            // ArrayList to store the data collected from below
            ArrayList<String> newRecord = new ArrayList<>();
    
            // prompting the user to enter the product name
            newRecord.add(getUserInput("Enter the following:\nProduct Name: "));

            // prompting the user to enter the product ID
            while (true) {
                // getting the input
                String userProductID = getUserInput("Product ID: ");
                
                // matching regex
                Matcher idMatcher = idPattern.matcher(userProductID);
                
                // checking if userProductID matches the regex
                if(idMatcher.find())
                {
                    // adding to the record
                    newRecord.add(userProductID);
                    break;
                }
                else
                {
                    System.out.println("Input did not match \"12345\", try again.");
                    continue;
                }
            }

            // prompting the user to enter the price
            while (true) {
                // getting the input
                String userPrice = getUserInput("Price (Don't enter a $): ");

                // checking if the entered value is a float
                if(isFloat(userPrice))
                {
                    // adding to the record
                    newRecord.add("$"+userPrice);
                    break;
                }
                else
                {
                    System.out.println("Input was not a number, try again.");
                    continue;
                }
            }

            // prompting the user to enter the quantity in stock
            while (true) {
                // getting the input
                String userStock = getUserInput("Quantity in Stock: ");
                
                // checking if the entered value is an int
                if(isInt(userStock))
                {
                    // adding to the record
                    newRecord.add(userStock);
                    break;
                }
                else
                {
                    System.out.println("Input was not a number, try again.");
                    continue;
                }
            }

            // getting manufacturer for the product
            newRecord.add(getUserInput("Manufacturer: "));

            for (int i = 0; i < newRecord.size(); i++) {
                if (i != newRecord.size() - 1)
                    writer.write('"' + newRecord.get(i) + "\", ");
                else
                    writer.write('"' + newRecord.get(i) + '"');
            }

            writer.write("\r\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                writer.close();   
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void printCSV(String filePath)
    {
        CSVParser csvParser = null;
        
        try
        {
            Reader reader = new FileReader(filePath);

            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                for(String test : csvRecord)
                {
                    System.out.printf("%-23s", test.replace("\"", ""));
                }
                System.out.println();
            }
            System.out.println();
        }    
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String filePath = "inventory.csv";
            
        printCSV(filePath);
        writeCSV(filePath);
        printCSV(filePath);
   
        scanner.close();
    }
}
