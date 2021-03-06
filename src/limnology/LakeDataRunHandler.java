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
    public static final String troutLake2009Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2009-Daily.csv";
    public static final String troutLake2010Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2010-Daily.csv";
    public static final String troutLake2011Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2011-Daily.csv";
    public static final String troutLake2012Daily = "C:/Scratch/Limnology/Sorted-Trout Lake-2012-Daily.csv";
    public static final String troutLake2009Hourly = "C:/Scratch/Limnology/Sorted-Trout Lake-2009-Hourly.csv";
    public static final String sparklingLake2009Daily = "C:/Scratch/Limnology/Sorted-Sparkling Lake Raft-2009-Daily.csv";
    public static final String sparklingLake2011Daily = "C:/Scratch/Limnology/Sorted-Sparkling Lake Raft-2011-Daily.csv";
    public static final String crystalLakeBog2010Daily = "C:/Scratch/Limnology/Sorted-Crystal Lake Bog-2010-Daily.csv";
    public static final String crystalLakeBog2011Daily = "C:/Scratch/Limnology/Sorted-Crystal Lake Bog-2011-Daily.csv";
    public static final String crystalLakeBog2012Daily = "C:/Scratch/Limnology/Sorted-Crystal Lake Bog-2012-Daily.csv";
    public static final String crystalLake2011Daily = "C:/Scratch/Limnology/crystal_lake_water_temperature_data(2011).csv";
    public static final String crystalLake2012Daily = "C:/Scratch/Limnology/crystal_lake_water_temperature_data(2012).csv";
    public static final String crystalLake2013Daily = "C:/Scratch/Limnology/crystal_lake_water_temperature_data(2013).csv";

    // /////////////////////////////////////////////////////////////////////////
    // Specific Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Trout Lake 2010 first days in July.
     */
    public static void trout2010FirstDaysJuly() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        int year = 2012;
        String[] days = {"07-01", "07-02", "07-03", "07-04", "07-05", "07-06",
            "07-07", "07-08", "07-09", "07-10", "07-11", "07-12", "07-13"};
        LakeData app = new LakeData();
        app.setMethod(METHOD.DAYSARRAY);
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
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
     * Trout Lake 2009 Daily by DataSelect every 5 days.
     */
    public static void trout2010DailyBy5() {
        String csvName = troutLake2010Daily;
        String lakeName = "Trout Lake";
        int year = 2010;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2009 Daily by DataSelect every 5 days.
     */
    public static void trout2011DailyBy5() {
        String csvName = troutLake2011Daily;
        String lakeName = "Trout Lake";
        int year = 2011;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Trout Lake 2009 Daily by DataSelect every 5 days.
     */
    public static void trout2012DailyBy5() {
        String csvName = troutLake2012Daily;
        String lakeName = "Trout Lake";
        int year = 2012;
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

    // /////////////////////////////////////////////////////////////////////

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

    // /////////////////////////////////////////////////////////////////////

    /**
     * Crystal Lake Bog 2012 Daynum every 5 days.
     */
    public static void crystalBog2012DaynumBy5() {
        String csvName = crystalLakeBog2012Daily;
        String lakeName = "Crystal Lake Bog";
        int year = 2012;
        runAllDayNum(csvName, lakeName, year, 5);
    }

    /**
     * Crystal Lake Bog 2010 Daily by DataSelect every 5 days.
     */
    public static void crystalBog2010DailyBy5() {
        String csvName = crystalLakeBog2010Daily;
        String lakeName = "Crystal Lake Bog";
        int year = 2010;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Crystal Lake Bog 2012 Daily by DataSelect every 5 days.
     */
    public static void crystalBog2011DailyBy5() {
        String csvName = crystalLakeBog2011Daily;
        String lakeName = "Crystal Lake Bog";
        int year = 2011;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Crystal Lake Bog 2012 Daily by DataSelect every 5 days.
     */
    public static void crystalBog2012DailyBy5() {
        String csvName = crystalLakeBog2012Daily;
        String lakeName = "Crystal Lake Bog";
        int year = 2012;
        runAllDaily(csvName, lakeName, year, 5);
    }

    // /////////////////////////////////////////////////////////////////////

    /**
     * Crystal Lake 2011 Daily by DataSelect every 5 days.
     */
    public static void crystal2011DailyBy5() {
        String csvName = crystalLake2011Daily;
        String lakeName = "Crystal Lake";
        int year = 2011;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Crystal Lake 2012 Daily by DataSelect every 5 days.
     */
    public static void crystal2012DailyBy5() {
        String csvName = crystalLake2012Daily;
        String lakeName = "Crystal Lake";
        int year = 2012;
        runAllDaily(csvName, lakeName, year, 5);
    }

    /**
     * Crystal Lake 2013 Daily by DataSelect every 5 days.
     */
    public static void crystal2013DailyBy5() {
        String csvName = crystalLake2013Daily;
        String lakeName = "Crystal Lake";
        int year = 2013;
        runAllDaily(csvName, lakeName, year, 5);
    }

    // /////////////////////////////////////////////////////////////////////

    /**
     * Crystal Lake 2011 Daily by DayNum every 5 days.
     */
    public static void crystal2011RangeBy5() {
        String csvName = crystalLake2011Daily;
        String lakeName = "Crystal Lake";
        int year = 2011;
        int startDayNum = 130;
        runRangeDayNum(csvName, lakeName, year, startDayNum, 366, 5);
    }

    /**
     * Crystal Lake 2012 Daily by DayNum every 5 days.
     */
    public static void crystal2012RangeBy5() {
        String csvName = crystalLake2012Daily;
        String lakeName = "Crystal Lake";
        int year = 2012;
        int startDayNum = 131;
        runRangeDayNum(csvName, lakeName, year, startDayNum, 366, 5);
    }

    /**
     * Crystal Lake 2013 Daily by DayNum every 5 days.
     */
    public static void crystal2013RangeBy5() {
        String csvName = crystalLake2013Daily;
        String lakeName = "Crystal Lake";
        int year = 2013;
        int startDayNum = 130;
        runRangeDayNum(csvName, lakeName, year, startDayNum, 366, 5);
    }

    // /////////////////////////////////////////////////////////////////////

    /**
     * Crystal Lake 2011 specified days.
     */
    public static void crystal2011SpecifiedDays() {
        String csvName = crystalLake2011Daily;
        String lakeName = "Crystal Lake";
        int year = 2011;
        String[] days = {"07-24", "07-26", "07-28", "07-30", "08-02", "08-04",
            "08-06", "08-08", "08-10", "08-12", "08-14", "08-16", "08-18",
            "08-20", "08-22", "0824", "08-26", "08-28"};
        LakeData app = new LakeData();
        app.setMethod(METHOD.DAYSARRAY);
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setDays(days);
        app.run();
    }

    /**
     * Crystal Lake 2012 specified days.
     */
    public static void crystal2012SpecifiedDays() {
        String csvName = crystalLake2012Daily;
        String lakeName = "Crystal Lake";
        int year = 2012;
        String[] days = {"07-24", "07-26", "07-28", "07-30", "08-02", "08-04",
            "08-06", "08-08", "08-10", "08-12", "08-14", "08-16", "08-18",
            "08-20", "08-22", "0824", "08-26", "08-28"};
        LakeData app = new LakeData();
        app.setMethod(METHOD.DAYSARRAY);
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setDays(days);
        app.run();
    }

    /**
     * Crystal Lake 2013 specified days.
     */
    public static void crystal2013SpecifiedDays() {
        String csvName = crystalLake2013Daily;
        String lakeName = "Crystal Lake";
        int year = 2013;
        String[] days = {"07-24", "07-26", "07-28", "07-30", "08-02", "08-04",
            "08-06", "08-08", "08-10", "08-12", "08-14", "08-16", "08-18",
            "08-20", "08-22", "0824", "08-26", "08-28"};
        LakeData app = new LakeData();
        app.setMethod(METHOD.DAYSARRAY);
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setDays(days);
        app.run();
    }

    // /////////////////////////////////////////////////////////////////////////
    // General Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * /** Does all the available days in the given year, incrementing by the
     * given increment from the first one found. (e.g. 05-18, 05-23, 05-28,
     * 06-04, 06-09, ...).
     * 
     * @param csvName
     * @param lakeName
     * @param year
     * @param dayNumIncr
     */
    public static void runAllDayNum(String csvName, String lakeName, int year,
        int dayNumIncr) {
        runRangeDayNum(csvName, lakeName, year, 0, 366, dayNumIncr);
    }

    /**
     * /** Does the days in the given year in the range day number start to end,
     * incrementing by the given increment from the first one found. (e.g.
     * 05-18, 05-23, 05-28, 06-04, 06-09, ...).
     * 
     * @param csvName
     * @param lakeName
     * @param year
     * @param start
     * @param end
     * @param dayNumIncr
     */
    public static void runRangeDayNum(String csvName, String lakeName,
        int year, int start, int end, int dayNumIncr) {
        LakeData app = new LakeData();
        app.setCsvName(csvName);
        app.setLakeName(lakeName);
        app.setYear(year);
        app.setMethod(METHOD.DAYNUM);
        app.setDayNumStart(start);
        app.setDayNumIncr(dayNumIncr);
        app.setDayNumEnd(end);
        app.run();
    }

    /**
     * /** Does all the available days in the given year, incrementing by the
     * given increment from the first of the month (e.g. 05-15, 05-20, 05-30,
     * 06-05, 06-10 ...).
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

        // These are good to use
        // trout2009DailyBy5();
        // trout2010DailyBy5(); // Good to compare to Crystal Lake Daily by 5
        // trout2011DailyBy5();
        // trout2012DailyBy5();

        // trout2009DaynumBy5();
        // trout2009DailyBy5();
        // trout2009Early();
        // trout2009Hourly();
        // sparkling2009Daynum();
        // sparkling2009DaynumBy5();
        // crystalBog2012DaynumBy5();
        // crystalBog2010DailyBy5();
        // crystalBog2011DailyBy5();
        // crystalBog2012DailyBy5();

        // All data
        // crystal2011DailyBy5();
        // crystal2012DailyBy5();
        // crystal2013DailyBy5();

        // Good for comparing years, has same start day (limited by 2013)
//        crystal2011RangeBy5();
//        crystal2012RangeBy5();
//        crystal2013RangeBy5();

        crystal2011SpecifiedDays();
        crystal2012SpecifiedDays();
        crystal2013SpecifiedDays();
    }
}
