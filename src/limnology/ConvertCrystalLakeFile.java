package limnology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import utils.Histogram1D;

/*
 * Created on Oct 9, 2013
 * By Kenneth Evans, Jr.
 */

public class ConvertCrystalLakeFile
{
    public static final String LS = System.getProperty("line.separator");
    private static final int N_CELLS = 19;
    private static final double CELL_START = -0.5;
    private static final double CELL_END = 18.5;
    private static final String DIRECTORY = "C:/Scratch/Limnology";

    // Original 2012 data files
    // private static final File INPUT_FILE = new File(DIRECTORY,
    // "CRsonde.csv");
    // private static final File OUTPUT_FILE = new File(DIRECTORY,
    // "crystal_lake_water_temperature_data(2012).csv");

    // Data files from Colin Smith on 7-14-2014 for years 2011-2013
    private static String year = "2013";
    private static final File INPUT_FILE = new File(DIRECTORY, year
        + "CrystalLake_SondeTable.csv");
    private static final File OUTPUT_FILE = new File(DIRECTORY,
        "crystal_lake_water_temperature_data(" + year + ").csv");

    private boolean run() {
        boolean abort = false;
        BufferedReader in = null;
        PrintWriter out = null;
        String delimiter = ",";
        String[] tokens = null;
        int tokensLen = 0;
        int nSkipped = 0;
        int nInvalid = 0;
        int year, month, day, dayNum;
        int prevYear = -1, prevMonth = -1, prevDay = -1, prevDayNum = -1;
        String yearStr, monthStr, dayStr, depthStr, tempStr;
        double depth, temp;
        try {
            in = new BufferedReader(new FileReader(INPUT_FILE));
            out = new PrintWriter(new FileWriter(OUTPUT_FILE));
            out.println("sampledate,daynum,depth,wtemp");
            String line;
            int lineNum = 0;
            String dateStr;
            Histogram1D countHist = null, tempHist = null;
            while((line = in.readLine()) != null) {
                // Determine the separator
                if(lineNum == 0) {
                    if(line.contains("\t")) {
                        delimiter = "\t";
                    }
                }
                lineNum++;
                // Skip 4 header lines
                if(lineNum < 5) {
                    continue;
                }
                // Skip blank lines
                if(line.length() == 0) {
                    continue;
                }
                tokens = line.split(delimiter);
                tokensLen = tokens.length;
                // Expect 9 tokens
                if(tokensLen < 9) {
                    nSkipped++;
                    continue;
                }
                dateStr = tokens[0].replaceAll("\"", "");
                yearStr = LakeDataPoint.FormatType.YEAR.format(dateStr);
                monthStr = LakeDataPoint.FormatType.MONTH.format(dateStr);
                dayStr = LakeDataPoint.FormatType.DAY.format(dateStr);
                year = LakeDataPoint.convertToInt(yearStr);
                month = LakeDataPoint.convertToInt(monthStr);
                day = LakeDataPoint.convertToInt(dayStr);
                GregorianCalendar gCal = new GregorianCalendar(year, month - 1,
                    day);
                dayNum = gCal.get(Calendar.DAY_OF_YEAR);

                // Check if this is a new day
                if(dayNum != prevDayNum || year != prevYear) {
                    // Write out the previous day
                    if(prevDayNum != -1) {
                        writePreviousDay(out, countHist, tempHist, prevYear,
                            prevMonth, prevDay, prevDayNum);
                    }
                    // Start processing a new day
                    prevDayNum = dayNum;
                    prevYear = year;
                    prevMonth = month;
                    prevDay = day;
                    countHist = new Histogram1D("Count", N_CELLS, CELL_START,
                        CELL_END);
                    tempHist = new Histogram1D("Count", N_CELLS, CELL_START,
                        CELL_END);
                }
                // Bin the temp values according to depth
                depthStr = tokens[2];
                tempStr = tokens[3];
                depth = LakeDataPoint.convertToDouble(depthStr);
                temp = LakeDataPoint.convertToDouble(tempStr);
                if(Double.isNaN(depth) || Double.isNaN(temp)) {
                    nInvalid++;
                    continue;
                }
                countHist.fill(depth, 1);
                tempHist.fill(depth, temp);
            }
            // Write out the last day
            if(prevDayNum != -1) {
                writePreviousDay(out, countHist, tempHist, prevYear, prevMonth,
                    prevDay, prevDayNum);
            }

            // Cleanup
            in.close();
            out.close();
            in = null;
            out = null;
            System.out.println("Input: " + INPUT_FILE);
            System.out.println("Output: " + OUTPUT_FILE);
            System.out.println("Lines read: " + lineNum);
            System.out.println("Skipped: " + nSkipped);
            System.out.println("Invalid depth or temperature: " + nInvalid);
            System.out.println(LS + "All Done");
        } catch(Exception ex) {
            ex.printStackTrace();
            abort = true;
            System.out.println(LS + "Aborted");
        } finally {
            try {
                if(in != null) in.close();
                if(out != null) out.close();
            } catch(IOException ex) {
                ex.printStackTrace();
                abort = true;
                System.out.println(LS + "Aborted");
            }
        }
        return abort;
    }

    /**
     * Writes out the previous day
     * 
     * @param out
     * @param countHist
     * @param tempHist
     * @param prevYear
     * @param prevMonth
     * @param prevDay
     * @param prevDayNum
     */
    private void writePreviousDay(PrintWriter out, Histogram1D countHist,
        Histogram1D tempHist, int prevYear, int prevMonth, int prevDay,
        int prevDayNum) {
        double[] counts = countHist.getCellVals();
        double[] temps = tempHist.getCellVals();
        int count;
        double depth, temp;
        for(int i = 0; i < N_CELLS; i++) {
            count = (int)counts[i];
            temp = temps[i];
            depth = CELL_START + (i + .5) * (CELL_END - CELL_START)
                / (double)N_CELLS;
            if(count > 0) {
                out.println(String.format("\"%04d-%02d-%02d\",%d,%.2f,%.2f",
                    prevYear, prevMonth, prevDay, prevDayNum, depth, temp
                        / count));
            } else {
                out.println(String.format("\"%04d-%02d-%02d\",%d,%.2f,",
                    prevYear, prevMonth, prevDay, prevDayNum, depth));
            }
        }
    }

    public static void main(String[] args) {
        ConvertCrystalLakeFile app = new ConvertCrystalLakeFile();
        app.run();
        System.out.println();
    }

}
