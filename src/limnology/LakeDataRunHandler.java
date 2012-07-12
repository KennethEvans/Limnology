package limnology;

import javax.swing.UIManager;

import net.kenevans.jfreechart.jfreechartutils.Utils;

import limnology.LakeData.METHOD;
import limnology.LakeDataPoint.FormatType;

/*
 * Created on Aug 12, 2011
 * By Kenneth Evans, Jr.
 */

public class LakeDataRunHandler
{
    public static final String troutLake2010Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2010-Daily.csv";
    public static final String troutLake2009Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2009-Daily.csv";
    public static final String troutLake2009Hourly = "C:/Scratch/Limnology/Sorted-Trout Lake-2009-Hourly.csv";
    public static final String sparklingLake2009Daily = "C:/Scratch/Limnology/Sorted-Sparkling Lake Raft-2009-Daily.csv";
    public static final String sparklingLake2011Daily = "C:/Scratch/Limnology/Sorted-Sparkling Lake Raft-2011-Daily.csv";

    // /////////////////////////////////////////////////////////////////////////
    // Specific Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Trout Lake 2010 first days in July.
     */
    public static void trout2010FirstDaysJuly() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        String[] days = {"07-01", "07-02", "07-03", "07-04", "07-05", "07-06",
            "07-07", "07-08", "07-09", "07-10", "07-11", "07-12", "07-13"};
        LakeData app = new LakeData();
        app.setMethod(METHOD.DAYSARRAY);
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setDays(days);
        app.run();

    }

    /**
     * Trout Lake 2010 Daynum.
     */
    public static void trout2010Daily() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        int year = 2010;
        runAllDayNum(csvName, lakeName, year, 1);
    }

    /**
     * Trout Lake 2010 Daynum every 5 days.
     */
    public static void trout2010DaynumBy5() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        int year = 2010;
        runAllDayNum(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2010 Daily by DataSelect every 5 days.
     */
    public static void trout2010DailyBy5() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        int year = 2010;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2009 Daynum every 5 days.
     */
    public static void trout2009DaynumBy5() {
        String csvName = troutLake2009Daily;
        String lakeName = "Trout Lake";
        int year = 2009;
        runAllDayNum(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2009 Daily by DataSelect every 5 days.
     */
    public static void trout2009DailyBy5() {
        String csvName = troutLake2009Daily;
        String lakeName = "Trout Lake";
        int year = 2009;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2009 early.
     */
    public static void trout2009Early() {
        String csvName = troutLake2009Daily;
        String lakeName = "Trout Lake";
        int year = 2009;
        LakeData app = new LakeData();
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setMethod(METHOD.DAYNUM);
        app.setDayNumStart(0);
        app.setDayNumIncr(4);
        // 196 is July 15
        app.setDayNumEnd(196);
        app.run();
    }

    /**
     * Trout Lake 2009 hourly.
     */
    public static void trout2009Hourly() {
        String csvName = troutLake2009Hourly;
        String lakeName = "Trout Lake";
        int year = 2009;
        int month = 7;
        int dayStart = 8;
        int dayInc = 1;
        int dayEnd = 10;
        LakeData app = new LakeData();
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setMethod(METHOD.DATASELECT);
        DataSelect dataSelect = new DataSelect(year, month, 1, month, dayInc);
        dataSelect.setDayStart(dayStart);
        dataSelect.setDayInc(dayInc); // Not necessary
        dataSelect.setDayEnd(dayEnd);
        dataSelect.setHourStart(0);
        dataSelect.setHourInc(1);
        dataSelect.setHourEnd(59);
        app.setDataSelect(dataSelect);
        app.setFormatType(FormatType.MONTHDAYHOUR);
        app.run();
    }

    /**
     * Sparkling Lake 2009 Daynum.
     */
    public static void sparkling2009Daynum() {
        String csvName = sparklingLake2009Daily;
        String lakeName = "Sparkling Lake Raft";
        int year = 2009;
        runAllDayNum(csvName, lakeName, year, 1);
    }

    /**
     * Sparkling Lake 2009 Daynum every 5 days.
     */
    public static void sparkling2009DaynumBy5() {
        String csvName = sparklingLake2009Daily;
        String lakeName = "Sparkling Lake Raft";
        int year = 2009;
        runAllDayNum(csvName, lakeName, year, 5);
    }

    // /////////////////////////////////////////////////////////////////////////
    // General Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * /** Does all the available days in the given year, incrementing by the
     * given increment from the first one found.
     * 
     * @param csvName
     * @param lakeName
     * @param year
     * @param dayNumIncr
     */
    public static void runAllDayNum(String csvName, String lakeName, int year,
        int dayNumIncr) {
        LakeData app = new LakeData();
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setMethod(METHOD.DAYNUM);
        app.setDayNumStart(0);
        app.setDayNumIncr(dayNumIncr);
        app.setDayNumEnd(366);
        app.run();
    }

    /**
     * /** Does all the available days in the given year, incrementing by the
     * given increment from the first one found.
     * 
     * @param csvName
     * @param lakeName
     * @param year
     * @param dayNumIncr
     */
    public static void runAllDaily(String csvName, String lakeName, int year,
        int dayIncr) {
        LakeData app = new LakeData();
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setMethod(METHOD.DATASELECT);
        DataSelect dataSelect = new DataSelect(year, dayIncr);
        app.setDataSelect(dataSelect);
        app.run();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Main
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Set the native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable t) {
            Utils.errMsg("Cannot set native look & feel");
        }

        // Set the runs to make
        // trout2010FirstDaysJuly();
        // trout2010Daily();
        // trout2010DaynumBy5();
        // trout2010DailyBy5();
        // trout2009DaynumBy5();
        trout2009DailyBy5();
        // trout2009Early();
        // trout2009Hourly();
        // sparkling2009Daynum();
        sparkling2009DaynumBy5();
    }
}
