package limnology;

/*
 * Created on Aug 11, 2011
 * By Kenneth Evans, Jr.
 */

public class DataSelect
{
    private int yearStart = 2010;
    private int yearInc = 1;
    private int yearEnd = 2010;

    private int monthStart = 0;
    private int monthInc = 1;
    private int monthEnd = 12;

    private int dayStart = 0;
    private int dayInc = 1;
    private int dayEnd = 31;

    private int hourStart = 0;
    private int hourInc = 1;
    private int hourEnd = 59;

    private int minStart = 0;
    private int minInc = 1;
    private int minEnd = 59;

    private int secStart = 0;
    private int secInc = 1;
    private int secEnd = 59;

    /**
     * DataSelect constructor that will loop over all the months and days in the
     * specified year and nothing lower.
     * 
     * @param year The year.
     * @param dayInc The increment for days.
     */
    public DataSelect(int year, int dayInc) {
        this.dayInc = dayInc;
        // Don't loop over these
        setYear(year);
        setHour(0);
        setMin(0);
        setSec(0);
    }

    /**
     * DataSelect constructor that will loop over all the days in the specified
     * months and nothing lower for the given year.
     * 
     * @param year The year.
     * @param monthStart
     * @param monthInc
     * @param monthEnd
     * @param dayInc The increment for days.
     */
    public DataSelect(int year, int monthStart, int monthInc, int monthEnd,
        int dayInc) {
        this.monthStart = monthStart;
        this.monthInc = monthInc;
        this.monthEnd = monthEnd;
        this.dayInc = dayInc;
        // Don't loop over these
        setYear(year);
        setHour(0);
        setMin(0);
        setSec(0);
    }

    /**
     * Set to do a single year.
     * 
     * @param year The year to do.
     */
    public void setYear(int year) {
        this.yearStart = this.yearEnd = year;
    }

    /**
     * Set to do a single month.
     * 
     * @param month The month to do.
     */
    public void setMonth(int month) {
        this.monthStart = this.monthEnd = month;
    }

    /**
     * Set to do a single day.
     * 
     * @param day The day to do.
     */
    public void setDay(int day) {
        this.dayStart = this.dayEnd = day;
    }

    /**
     * Set to do a single hour.
     * 
     * @param hour The hour to do.
     */
    public void setHour(int hour) {
        this.hourStart = this.hourEnd = hour;
    }

    /**
     * Set to do a single min.
     * 
     * @param min The min to do.
     */
    public void setMin(int min) {
        this.minStart = this.minEnd = min;
    }

    /**
     * Set to do a single sec.
     * 
     * @param sec The sec to do.
     */
    public void setSec(int sec) {
        this.secStart = this.secEnd = sec;
    }

    /**
     * @return The value of yearStart.
     */
    public int getYearStart() {
        return yearStart;
    }

    /**
     * @param yearStart The new value for yearStart.
     */
    public void setYearStart(int yearStart) {
        this.yearStart = yearStart;
    }

    /**
     * @return The value of yearInc.
     */
    public int getYearInc() {
        return yearInc;
    }

    /**
     * @param yearInc The new value for yearInc.
     */
    public void setYearInc(int yearInc) {
        this.yearInc = yearInc;
    }

    /**
     * @return The value of yearEnd.
     */
    public int getYearEnd() {
        return yearEnd;
    }

    /**
     * @param yearEnd The new value for yearEnd.
     */
    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    /**
     * @return The value of monthStart.
     */
    public int getMonthStart() {
        return monthStart;
    }

    /**
     * @param monthStart The new value for monthStart.
     */
    public void setMonthStart(int monthStart) {
        this.monthStart = monthStart;
    }

    /**
     * @return The value of monthInc.
     */
    public int getMonthInc() {
        return monthInc;
    }

    /**
     * @param monthInc The new value for monthInc.
     */
    public void setMonthInc(int monthInc) {
        this.monthInc = monthInc;
    }

    /**
     * @return The value of monthEnd.
     */
    public int getMonthEnd() {
        return monthEnd;
    }

    /**
     * @param monthEnd The new value for monthEnd.
     */
    public void setMonthEnd(int monthEnd) {
        this.monthEnd = monthEnd;
    }

    /**
     * @return The value of dayStart.
     */
    public int getDayStart() {
        return dayStart;
    }

    /**
     * @param dayStart The new value for dayStart.
     */
    public void setDayStart(int dayStart) {
        this.dayStart = dayStart;
    }

    /**
     * @return The value of dayInc.
     */
    public int getDayInc() {
        return dayInc;
    }

    /**
     * @param dayInc The new value for dayInc.
     */
    public void setDayInc(int dayInc) {
        this.dayInc = dayInc;
    }

    /**
     * @return The value of dayEnd.
     */
    public int getDayEnd() {
        return dayEnd;
    }

    /**
     * @param dayEnd The new value for dayEnd.
     */
    public void setDayEnd(int dayEnd) {
        this.dayEnd = dayEnd;
    }

    /**
     * @return The value of hourStart.
     */
    public int getHourStart() {
        return hourStart;
    }

    /**
     * @param hourStart The new value for hourStart.
     */
    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    /**
     * @return The value of hourInc.
     */
    public int getHourInc() {
        return hourInc;
    }

    /**
     * @param hourInc The new value for hourInc.
     */
    public void setHourInc(int hourInc) {
        this.hourInc = hourInc;
    }

    /**
     * @return The value of hourEnd.
     */
    public int getHourEnd() {
        return hourEnd;
    }

    /**
     * @param hourEnd The new value for hourEnd.
     */
    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    /**
     * @return The value of minStart.
     */
    public int getMinStart() {
        return minStart;
    }

    /**
     * @param minStart The new value for minStart.
     */
    public void setMinStart(int minStart) {
        this.minStart = minStart;
    }

    /**
     * @return The value of minInc.
     */
    public int getMinInc() {
        return minInc;
    }

    /**
     * @param minInc The new value for minInc.
     */
    public void setMinInc(int minInc) {
        this.minInc = minInc;
    }

    /**
     * @return The value of minEnd.
     */
    public int getMinEnd() {
        return minEnd;
    }

    /**
     * @param minEnd The new value for minEnd.
     */
    public void setMinEnd(int minEnd) {
        this.minEnd = minEnd;
    }

    /**
     * @return The value of secStart.
     */
    public int getSecStart() {
        return secStart;
    }

    /**
     * @param secStart The new value for secStart.
     */
    public void setSecStart(int secStart) {
        this.secStart = secStart;
    }

    /**
     * @return The value of secInc.
     */
    public int getSecInc() {
        return secInc;
    }

    /**
     * @param secInc The new value for secInc.
     */
    public void setSecInc(int secInc) {
        this.secInc = secInc;
    }

    /**
     * @return The value of secEnd.
     */
    public int getSecEnd() {
        return secEnd;
    }

    /**
     * @param secEnd The new value for secEnd.
     */
    public void setSecEnd(int secEnd) {
        this.secEnd = secEnd;
    }

}
