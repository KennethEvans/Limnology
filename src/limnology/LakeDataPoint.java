package limnology;

/*
 * Created on Aug 9, 2011
 * By Kenneth Evans, Jr.
 */

public class LakeDataPoint implements Comparable<LakeDataPoint>
{
    private String date;
    private double dayNum;
    private double depth;
    private double temp;

    /**
     * FormatType is a complex enum that represents ways for formatting the
     * date, assumed to be a String of the form "yyyy-mm-dd hh:mm:ss".
     * 
     * @author Kenneth Evans, Jr.
     */
    public static enum FormatType {
        FULL(0, 19), YEAR(0, 4), MONTH(5, 7), DAY(8, 10), HOUR(11, 13), MIN(14,
            16), SEC(17, 19), MONTHDAY(5, 10), MONTHDAYHOUR(5, 16);
        /** The expected length of a date string. */
        public static final int DATE_LENGTH = "yyyy-mm-dd hh:mm:ss".length();
        /** Start index of the substring to use. */
        private final int start;
        /** End index of the substring to use. */
        private final int end;

        /**
         * FormatType constructor.
         * 
         * @param name
         */
        private FormatType(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * Reformatted string.
         * 
         * @param date A string of the form "yyyy-mm-dd hh:mm:ss".
         * @return
         */
        public String format(String date) {
            return date.substring(start, end);
        }

    };

    /**
     * LakeDataPoint constructor to use for daily data.
     * 
     * @param dateStr
     * @param dayStr
     * @param depthStr
     * @param tempStr
     */
    public LakeDataPoint(String dateStr, String dayStr, String depthStr,
        String tempStr) {
        this(dateStr, dayStr, depthStr, tempStr, null);
    }

    /**
     * LakeDataPoint constructor to use for hourly data.
     * 
     * @param dateString
     * @param dayStr
     * @param depthStr
     * @param tempStr
     * @param hourStr May be null.
     */
    public LakeDataPoint(String dateString, String dayStr, String depthStr,
        String tempStr, String hourStr) {
        // Remove any embedded quotes
        date = dateString.replaceAll("\"", "");
        dayNum = convertToDouble(dayStr.replaceAll("\"", ""));
        depth = convertToDouble(depthStr.replaceAll("\"", ""));
        temp = convertToDouble(tempStr.replaceAll("\"", ""));
        if(false) {
            // DEBUG
            if(Double.isNaN(temp)) {
                System.out.println(this.getDate() + " " + this.getYear() + " "
                    + this.getMonth() + " " + this.getDay() + " "
                    + this.getHour() + " " + this.getMinute() + " "
                    + this.getSecond() + " " + this.getDepth() + " "
                    + this.getTemp());
                System.out.println("  tempStr=" + tempStr);
            }
        }
        if(hourStr != null) {
            // The timestamp in the hourly data seems to always have 00:00:00
            // and the hour is given in the hourStr.
            // Use the hourStr to put the numbers in the date.
            int intVal = convertToInt(hourStr);
            int hour = intVal / 100;
            int min = intVal % 100;
            String stringVal = String.format("%02d:%02d:00", hour, min);
            date = date.substring(0, 11) + stringVal;
            // DEBUG
            // System.out.println(hourStr + ": " + date);
        }
    }

    /**
     * Converts a String number to a double. Returns Double.NaN on error.
     * 
     * @param stringVal
     * @return
     */
    private double convertToDouble(String stringVal) {
        double val = Double.NaN;
        try {
            val = Double.valueOf(stringVal);
        } catch(NumberFormatException ex) {
            val = Double.NaN;
        }
        return val;
    }

    /**
     * Converts a String number to an int. Returns -1 on error.
     * 
     * @param stringVal
     * @return
     */
    private int convertToInt(String stringVal) {
        int val = -1;
        try {
            val = Integer.valueOf(stringVal);
        } catch(NumberFormatException ex) {
            val = -1;
        }
        return val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(LakeDataPoint point) {
        int diff = this.getYear() - point.getYear();
        if(diff != 0) {
            return diff;
        }
        diff = this.getMonth() - point.getMonth();
        if(diff != 0) {
            return diff;
        }
        diff = this.getDay() - point.getDay();
        if(diff != 0) {
            return diff;
        }
        diff = this.getHour() - point.getHour();
        if(diff != 0) {
            return diff;
        }
        diff = this.getMinute() - point.getMinute();
        if(diff != 0) {
            return diff;
        }
        diff = this.getSecond() - point.getSecond();
        if(diff != 0) {
            return diff;
        }
        double ddiff = this.getDepth() - point.getDepth();
        if(ddiff < 0) {
            return -1;
        }
        if(ddiff > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * @return The value of date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the date formatted according to the given FormatType.
     * 
     * @param type
     * @return
     */
    public String getDate(FormatType type) {
        return type.format(date);
    }

    /**
     * Gets the value of the date in the given FormatType as an int.
     * 
     * @return The integer value or -1 on failure.
     */
    public int getIntVal(FormatType type) {
        int val = -1;
        try {
            val = Integer.valueOf(type.format(date));
        } catch(NumberFormatException ex) {
        }
        return val;
    }

    /**
     * @return The year or -1 on failure.
     */
    public int getYear() {
        return getIntVal(FormatType.YEAR);
    }

    /**
     * @return The month number or -1 on failure.
     */
    public int getMonth() {
        return getIntVal(FormatType.MONTH);
    }

    /**
     * @return The day of the month or -1 on failure.
     */
    public int getDay() {
        return getIntVal(FormatType.DAY);
    }

    /**
     * @return The hour or -1 on failure.
     */
    public int getHour() {
        return getIntVal(FormatType.HOUR);
    }

    /**
     * @return The minute or -1 on failure.
     */
    public int getMinute() {
        return getIntVal(FormatType.MIN);
    }

    /**
     * @return The second or -1 on failure.
     */
    public int getSecond() {
        return getIntVal(FormatType.SEC);
    }

    /**
     * @return The year or -1 on failure.
     */
    public String getYearStr() {
        return getDate(FormatType.YEAR);
    }

    /**
     * @return The month number or -1 on failure.
     */
    public String getMonthStr() {
        return getDate(FormatType.MONTH);
    }

    /**
     * @return The day of the month or -1 on failure.
     */
    public String getDayStr() {
        return getDate(FormatType.DAY);
    }

    /**
     * @return The hour or -1 on failure.
     */
    public String getHourStr() {
        return getDate(FormatType.HOUR);
    }

    /**
     * @return The minute or -1 on failure.
     */
    public String getMinuteStr() {
        return getDate(FormatType.MIN);
    }

    /**
     * @return The second or -1 on failure.
     */
    public String getSecondStr() {
        return getDate(FormatType.SEC);
    }

    /**
     * @return The the month and date in the form mm-dd.
     */
    public String getMonthDay() {
        return getDate(FormatType.MONTHDAY);
    }

    /**
     * @param date The new value for date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The value of dayNum.
     */
    public double getDayNum() {
        return dayNum;
    }

    /**
     * @param dayNum The new value for dayNum.
     */
    public void setDayNum(double dayNum) {
        this.dayNum = dayNum;
    }

    /**
     * @return The value of depth.
     */
    public double getDepth() {
        return depth;
    }

    /**
     * @param depth The new value for depth.
     */
    public void setDepth(double depth) {
        this.depth = depth;
    }

    /**
     * @return The value of temp.
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @param temp The new value for temp.
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

}
