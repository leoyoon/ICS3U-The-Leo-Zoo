import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Part of Leo's ICS3U Summative.
 * The IO class for the save/load feature of the Zoo.
 * 
 * @author Mr. Hudson or Ms. Cianci
 * @since I'm not sure, but imported on Wednesday, January 7th of 2015
 */

public class IO
{
  private static PrintWriter fileOut;
  private static BufferedReader fileIn;
  
  public static void createOutputFile(String fileName)
  {
    try
    {
      fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
    }
    catch (IOException e)
    {
      System.out.println("*** Cannot create file: " + fileName + " ***");
    }
  }
  
  public static void openOutputFile(String fileName)
  {
    try
    {
      fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
    }
    catch (IOException e)
    {
      System.out.println("*** Cannot open file: " + fileName + " ***");
    }
  }
  
  public static void print(String text)
  {
    fileOut.print(text);
  }
  
  public static void println(String text)
  {
    fileOut.println(text);
  }
  
  public static void closeOutputFile()
  {
    fileOut.close();
  }
  
  public static void openInputFile(String fileName)
  {
    try
    {
      fileIn = new BufferedReader(new FileReader(fileName));
    }
    catch (FileNotFoundException e)
    {
      System.out.println("***Cannot open " + fileName + "***");
    }
  }
  
  public static String readLine()
    throws IOException
  {
    return fileIn.readLine();
  }
  
  public static void closeInputFile()
    throws IOException
  {
    fileIn.close();
  }
  
}

/*
- fileOut: PrintWriter
- fileIn: BufferedReader

+ createOutputFile(String fileName): void
+ openOutputFile(String fileName): void
+ print(String text): void
+ println(String text): void
+ closeOutputFile(): void
+ openInputFile(String fileName): void
+ readLine(): String
+ closeInputFile(): void
*/
