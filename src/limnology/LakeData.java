package limnology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import limnology.LakeDataPoint.FormatType;
import net.kenevans.jfreechart.jfreechartutils.Utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.RainbowColorScheme;

/*
 * Created on Aug 9, 2011
 * By Kenneth Evans, Jr.
 */

public class LakeData
{
    private static final String CSV_NAME = "C:/Scratch/Limnology/Sorted-Trout Lake-2010-Daily.csv";
    private String csvName = CSV_NAME;
    private static final String LAKE_NAME = "Trout Lake";
    private String lakeName = LAKE_NAME;
    private FormatType formatType = FormatType.MONTHDAY;

    /**
     * Methods used to select the data:<br>
     * <br>
     * DATASELECT: Use a DataSelect class to loop.<br>
     * DAYNUM: Loop over dayNum using DAYNUM_START, INCR_DAY_NUM, and
     * DAYNUM_END. Same as DAYSARRAY except the days array is generated.<br>
     * DAYSARRAY: Use the DAYS array to specify specific days.<br>
     */
    public enum METHOD {
        DATASELECT, DAYNUM, DAYSARRAY
    };

    /** The method used in run() to select the data for this instance. */
    private METHOD method = METHOD.DAYNUM;

    // These are used with METHOD.DATASELECT
    private static final int YEAR = 2010;
    private static final int DAY_INCR = 5;
    private int year = YEAR;
    private int dayIncr = DAY_INCR;
    private DataSelect dataSelect = new DataSelect(year, dayIncr);

    // These are used with METHOD.DAYNUM
    private static final int DAYNUM_START = 141;
    private static final int DAYNUM_INCR = 5;
    private static final int DAYNUM_END = 211;
    private int dayNumStart = DAYNUM_START;
    private int dayNumIncr = DAYNUM_INCR;
    private int dayNumEnd = DAYNUM_END;

    // This array is used with METHOD.DAYSARRAY
    // private static final String[] DAYS = {"05-21", "06-01", "06-10", "06-20",
    // "07-01", "07-10", "07-20", "07-30"};
    private static final String[] DAYS = {"07-01", "07-02", "07-03", "07-04",
        "07-05", "07-06", "07-07", "07-08", "07-09", "07-10", "07-11", "07-12"};
    private String[] days = DAYS;

    /** Whether to initially show markers or not. */
    private boolean doMarkers = false;

    /** Whether to use rainbow colors or not. */
    private boolean doRainbowColors = true;

    public static final String LS = System.getProperty("line.separator");

    /**
     * Reads a CSV file and converts it to a list of LakeDataPoint items.
     * 
     * @param name
     * @return
     */
    private List<LakeDataPoint> readCsv(String name) {
        File file = new File(name);
        if(!file.exists()) {
            Utils.errMsg("Does not exist" + LS + name);
            return null;
        }
        List<LakeDataPoint> dataList = new ArrayList<LakeDataPoint>();
        BufferedReader in = null;
        LakeDataPoint dataPoint = null;
        String[] tokens = null;
        int tokensLen = 0;
        String token;
        int dayNumIndex = -1;
        int depthIndex = -1;
        int tempIndex = -1;
        int hourIndex = -1;
        String tempStr = null;
        String hourStr = null;
        LakeDataPoint prevPoint = new LakeDataPoint("", "-1", "-1", "-1");
        try {
            in = new BufferedReader(new FileReader(name));
            String line;
            int lineNum = 0;
            while((line = in.readLine()) != null) {
                lineNum++;
                tokens = line.split("\t");
                tokensLen = tokens.length;
                if(lineNum == 1) {
                    // Find the indices
                    hourStr = null;
                    for(int i = 0; i < tokensLen; i++) {
                        token = tokens[i];
                        if(token.replaceAll("\"", "").equals("daynum")) {
                            dayNumIndex = i;
                        }
                        if(token.replaceAll("\"", "").equals("depth")) {
                            depthIndex = i;
                        }
                        if(token.replaceAll("\"", "").equals("wtemp")) {
                            tempIndex = i;
                        }
                        if(token.replaceAll("\"", "").equals("hour")) {
                            hourIndex = i;
                        }
                    }
                    if(dayNumIndex == -1 || depthIndex == -1 || tempIndex == -1) {
                        String msg = "Could not find columns \"daynum\","
                            + " \"depth\", and \"wtemp\" in" + LS;
                        for(int i = 0; i < tokensLen; i++) {
                            msg += "  " + tokens[i] + LS;
                        }
                        Utils.errMsg(msg);
                    }
                    continue;
                }
                tokensLen--;
                if(tokensLen < 0 || dayNumIndex > tokensLen
                    || depthIndex > tokensLen) {
                    // Incomplete line, skip
                    continue;
                }
                if(hourIndex == -1 || hourIndex > tokensLen) {
                    hourStr = null;
                } else {
                    hourStr = tokens[hourIndex];
                }
                if(tempIndex > tokensLen) {
                    // Missing temperature
                    tempStr = "Missing";
                } else {
                    tempStr = tokens[tempIndex];
                }
                dataPoint = new LakeDataPoint(tokens[0], tokens[dayNumIndex],
                    tokens[depthIndex], tempStr, hourStr);
                if(prevPoint.getTemp() == dataPoint.getTemp()
                    && prevPoint.getDepth() == dataPoint.getDepth()
                    && prevPoint.compareTo(dataPoint) == 0) {
                    // Duplicate, skip
                    continue;
                }
                dataList.add(dataPoint);
                prevPoint = dataPoint;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(in != null) in.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        // Sort it
        Collections.sort(dataList);
        if(false) {
            // DEBUG
            for(LakeDataPoint point : dataList) {
                System.out.println(point.getDate() + " " + point.getYear()
                    + " " + point.getMonth() + " " + point.getDay() + " "
                    + point.getHour() + " " + point.getMinute() + " "
                    + point.getSecond() + " " + point.getDepth());
            }
        }
        return dataList;
    }

    /**
     * Creates an array of month-date items from the given list of LakePointData
     * items using the start, incr, and end values of dayNums.
     * 
     * @param list
     * @param start Start dayNum.
     * @param incr Increment.
     * @param end End dayNum.
     * @return
     */
    private String[] createDaysArray(List<LakeDataPoint> list, int start,
        int incr, int end) {
        List<String> daysList = new ArrayList<String>();
        long curDayNum = start - incr;
        boolean doingSeries = true;
        boolean started = false;
        for(LakeDataPoint point : list) {
            if(false) {
                // DEBUG
                System.out.println(point.getDate() + " " + point.getYear()
                    + " " + point.getMonth() + " " + point.getDay() + " "
                    + point.getHour() + " " + point.getMinute() + " "
                    + point.getSecond() + " " + point.getDepth() + " - "
                    + point.getDayNum() + " " + curDayNum);

            }
            if(point.getDayNum() < start) {
                continue;
            }
            if(point.getDayNum() > end) {
                break;
            }
            if(!started) {
                started = true;
                curDayNum = (long)point.getDayNum() - incr;
            }
            // Add a new name whenever the DateNum changes
            if(doingSeries && point.getDayNum() != curDayNum) {
                curDayNum += incr;
                doingSeries = false;
            }
            if(!doingSeries) {
                if(point.getDayNum() > curDayNum) {
                    // Missed the next point, increment the curDayNum
                    curDayNum += incr;
                }
                if(point.getDayNum() == curDayNum) {
                    doingSeries = true;
                    daysList.add(point.getMonthDay());
                }
            }
        }
        String[] days = new String[daysList.size()];
        days = daysList.toArray(days);
        return days;
    }

    /**
     * Creates a Series from the depth and temperature lists.
     * 
     * @param name Name of the series.
     * @param depthList List of depths.
     * @param tempList List of temperatures.
     * @return
     */
    private XYSeries createSeries(String name, List<Double> depthList,
        List<Double> tempList) {
        int size = depthList.size();
        Double[] depthVals = new Double[size];
        Double[] tempVals = new Double[size];
        depthVals = depthList.toArray(depthVals);
        tempVals = tempList.toArray(tempVals);
        XYSeries series = new XYSeries(name);
        double x, y;
        for(int i = 0; i < size; i++) {
            x = tempVals[i];
            y = depthVals[i];
            series.add(-y, x);
        }
        return series;
    }

    /**
     * Creates a dataset from the given list of LakePointData items using the
     * list of month-date strings.
     * 
     * @param list List of LakeDataPoint items.
     * @param days List of of month-date strings.
     * @return
     */
    public XYSeriesCollection createDatasetFromDayNums(
        List<LakeDataPoint> list, String[] days) {
        // Traverse the data for dayNums that match
        int nDayNums = days.length;
        if(nDayNums == 0) {
            return null;
        }
        int dayIndex = 0;
        String curMonthDate = days[dayIndex];
        boolean doingSeries = false;
        XYSeries series = null;
        String seriesName = null;
        List<Double> depthList = null;
        List<Double> tempList = null;
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(LakeDataPoint point : list) {
            if(doingSeries) {
                if(!point.getMonthDay().equals(curMonthDate)) {
                    // Finish this series
                    seriesName = curMonthDate;
                    series = createSeries(seriesName, depthList, tempList);
                    // Add the series to the data set
                    dataset.addSeries(series);
                    // Set up for the next one
                    doingSeries = false;
                    dayIndex++;
                    if(dayIndex >= nDayNums) {
                        break;
                    }
                    curMonthDate = days[dayIndex];
                }
            }
            if(!doingSeries) {
                if(!point.getMonthDay().equals(curMonthDate)) {
                    continue;
                }
                // Start a new series
                doingSeries = true;
                depthList = new ArrayList<Double>();
                tempList = new ArrayList<Double>();
            }
            depthList.add(point.getDepth());
            tempList.add(point.getTemp());
        }
        // Cleanup any existing series
        if(doingSeries) {
            // Finish this series
            seriesName = curMonthDate;
            series = createSeries(seriesName, depthList, tempList);
            // Add the series to the data set
            dataset.addSeries(series);
        }

        return dataset;
    }

    /**
     * Creates a dataset from the given DataSelect. Loops over all values and
     * calls getData(), which does the actual works. This method only does the
     * looping..
     * 
     * @param list List of LakeDataPoint items.
     * @param select DataSelect specifying what to loop over.
     * @return
     */
    public XYSeriesCollection createDatasetFromDataSelect(
        List<LakeDataPoint> list, DataSelect select) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        int yearStart = select.getYearStart();
        int yearInc = select.getYearInc();
        int yearEnd = select.getYearEnd();
        int monthStart = select.getMonthStart();
        int monthInc = select.getMonthInc();
        int monthEnd = select.getMonthEnd();
        int dayStart = select.getDayStart();
        int dayInc = select.getDayInc();
        int dayEnd = select.getDayEnd();
        int hourStart = select.getHourStart();
        int hourInc = select.getHourInc();
        int hourEnd = select.getHourEnd();
        int minStart = select.getMinStart();
        int minInc = select.getMinInc();
        int minEnd = select.getMinEnd();
        int secStart = select.getSecStart();
        int secInc = select.getSecInc();
        int secEnd = select.getSecEnd();

        // Loop over all values
        for(int year = yearStart; year <= yearEnd; year += yearInc) {
            for(int month = monthStart; month <= monthEnd; month += monthInc) {
                for(int day = dayStart; day <= dayEnd; day += dayInc) {
                    for(int hour = hourStart; hour <= hourEnd; hour += hourInc) {
                        for(int min = minStart; min <= minEnd; min += minInc) {
                            for(int sec = secStart; sec <= secEnd; sec += secInc) {
                                // Add the series for this point if data exist
                                setDataSeries(list, dataset, year, month, day,
                                    hour, min, sec);
                            }
                        }
                    }
                }
            }
        }
        return dataset;
    }

    /**
     * Gets a single series corresponding to the given values and adds it to the
     * dataset.
     * 
     * @param list List of all LakeDataPoint points.
     * @param dataset The dataset to which to add the series.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @return If successful or not.
     */
    public boolean setDataSeries(List<LakeDataPoint> list,
        XYSeriesCollection dataset, int year, int month, int day, int hour,
        int min, int sec) {
        boolean retVal = true;
        String date = String.format("%04d-%02d-%02d %02d:%02d:%02d", year,
            month, day, hour, min, sec);

        // Traverse the data for items that match that match and create a new
        // list
        XYSeries series = null;
        String seriesName = null;
        List<Double> depthList = new ArrayList<Double>();
        List<Double> tempList = new ArrayList<Double>();
        for(LakeDataPoint point : list) {
            if(point.getYear() != year || point.getMonth() != month
                || point.getDay() != day || point.getHour() != hour
                || point.getMinute() != min || point.getSecond() != sec) {
                continue;
            }
            depthList.add(point.getDepth());
            tempList.add(point.getTemp());
        }

        // Quit if nothing was found
        if(depthList.size() == 0) {
            return false;
        }

        // Create the series and add it to the dataset
        seriesName = date;
        series = createSeries(seriesName, depthList, tempList);
        dataset.addSeries(series);
        return retVal;
    }

    /**
     * Reformat the series names according to the given FormatType. Assumes the
     * existing names are in the form : "yyyy-mm-dd hh:mm:ss". Does nothing if
     * the length of the existing name is not the right length (the length of
     * this string). Does nothing unless the series is of the class XYSeries.
     * 
     * @param dataset
     * @param formatType
     * @return If fully successful or not.
     */
    public boolean formatSeriesNames(XYSeriesCollection dataset, FormatType type) {
        if(dataset == null) {
            Utils.errMsg("formatSeriesName: dataset is null");
            return false;
        }
        List<?> seriesList = dataset.getSeries();
        XYSeries series;
        String key;
        boolean retVal = true;
        for(Object obj : seriesList) {
            if(!(obj instanceof XYSeries)) {
                continue;
            }
            series = (XYSeries)obj;
            key = series.getKey().toString();
            // At least check the length
            if(key.length() != FormatType.DATE_LENGTH) {
                retVal = false;
                continue;
            }
            series.setKey(type.format(key));
            // Doesn't seem to do anything. Is null if not set.
            series.setDescription(type.format(key));
            // System.out.println("description=" + series.getDescription());
            // System.out.println("key=" + series.getKey());
        }
        return retVal;
    }

    /**
     * Set the series colors using a rainbow scheme.
     * 
     * @param chart
     */
    public void setSeriesColorsRainbow(JFreeChart chart) {
        try {
            XYPlot plot = chart.getXYPlot();
            XYSeriesCollection dataset = (XYSeriesCollection)plot.getDataset();
            int nSeries = dataset.getSeriesCount();
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot
                .getRenderer();
            for(int i = 0; i < nSeries; i++) {
                renderer.setSeriesPaint(i,
                    RainbowColorScheme.defineColor(i, nSeries));
            }
        } catch(Exception ex) {
            Utils.excMsg("Error setting series rainbow colors", ex);
        }
    }

    /**
     * Set the series colors using the default scheme.
     * 
     * @param chart
     */
    public void setSeriesColorsDefault(JFreeChart chart) {
        try {
            XYPlot plot = chart.getXYPlot();
            XYSeriesCollection dataset = (XYSeriesCollection)plot.getDataset();
            int nSeries = dataset.getSeriesCount();
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot
                .getRenderer();
            // Apparently have to make a new DrawingSupplier to reset the
            // paintIndex
            DrawingSupplier supplier = new DefaultDrawingSupplier();
            plot.setDrawingSupplier(supplier, true);
            for(int i = 0; i < nSeries; i++) {
                renderer.setSeriesPaint(i, supplier.getNextPaint());
            }
        } catch(Exception ex) {
            Utils.excMsg("Error setting series default colors", ex);
        }
    }

    /**
     * Creates the chart from the given dataset.
     * 
     * @param dataset
     * @return
     */
    public JFreeChart createChart(String name, XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(name, // Title
            "Depth, m", // y-axis Label
            "Temperature, deg C", // x-axis Label
            dataset, // Dataset
            PlotOrientation.HORIZONTAL, // BasicImagePlot FormatType
            true, // Show Legend
            true, // Use tooltips
            false // Configure chart to generate URLs?
            );

        // Set whether to show markers or not
        if(doMarkers) {
            XYPlot plot = (XYPlot)chart.getPlot();
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot
                .getRenderer();
            int nSeries = dataset.getSeriesCount();
            for(int i = 0; i < nSeries; i++) {
                renderer.setSeriesShapesVisible(i, true);
            }
        }

        // Set rainbow colors
        if(doRainbowColors) {
            setSeriesColorsRainbow(chart);
        }

        return chart;
    }

    /**
     * Adds items to the context popup menu.
     * 
     * @param chartPanel
     */
    private void extendPopupMenu(final ChartPanel chartPanel) {
        JPopupMenu menu = chartPanel.getPopupMenu();
        if(menu == null) return;

        menu.add(new JSeparator());

        // Reset zoom
        final JMenuItem zoomResetItem = new JMenuItem();
        zoomResetItem.setText("Reset Zoom");
        zoomResetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                chartPanel.restoreAutoBounds();
            }
        });
        menu.add(zoomResetItem);

        // Show markers
        final JCheckBoxMenuItem markersVisibleItem = new JCheckBoxMenuItem();
        markersVisibleItem.setText("Show Markers");
        markersVisibleItem.setSelected(doMarkers);
        markersVisibleItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = markersVisibleItem.isSelected();
                JFreeChart chart = chartPanel.getChart();
                XYPlot plot = chart.getXYPlot();
                XYSeriesCollection dataset = (XYSeriesCollection)plot
                    .getDataset();
                int nSeries = dataset.getSeriesCount();
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot
                    .getRenderer();
                for(int i = 0; i < nSeries; i++) {
                    renderer.setSeriesShapesVisible(i, selected);
                }
            }
        });
        menu.add(markersVisibleItem);

        // Use rainbow colors
        final JCheckBoxMenuItem rainbowColorsItem = new JCheckBoxMenuItem();
        rainbowColorsItem.setText("Rainbow Colors");
        rainbowColorsItem.setSelected(doRainbowColors);
        rainbowColorsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean selected = rainbowColorsItem.isSelected();
                JFreeChart chart = chartPanel.getChart();
                if(selected) {
                    setSeriesColorsRainbow(chart);
                } else {
                    setSeriesColorsDefault(chart);
                }
            }
        });
        menu.add(rainbowColorsItem);

    }

    /**
     * Displays the given chart in a Frame.
     * 
     * @param chart
     */
    public void plot(JFreeChart chart) {
        try {
            ChartFrame frame = new ChartFrame(chart.getTitle().getText(), chart);
            // Note that there is a context menu. Drag rubber band zooms. Double
            // click un-zooms. They just don't work well. You may have to click
            // several times. Clicking outside plot area may be more effective.

            // Add our items to the context menu
            extendPopupMenu(frame.getChartPanel());

            if(false) {
                // DEBUG

                // Leave these commented out. The necessary context menu and
                // zooming are available. They just don't work well.
                ChartPanel chartPanel = frame.getChartPanel();
                chartPanel.setToolTipText(chart.getTitle().getText());
                System.out
                    .println("TooltipText=" + chartPanel.getToolTipText());
                chartPanel.setDisplayToolTips(true);
                chartPanel.setMouseZoomable(true, false);
                chartPanel.addChartMouseListener(new ChartMouseListener() {
                    @Override
                    public void chartMouseMoved(ChartMouseEvent event) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void chartMouseClicked(ChartMouseEvent event) {
                        ChartEntity entity = event.getEntity();
                        if(entity != null) {
                            Utils.infoMsg("Mouse clicked: " + entity.toString());
                        } else {
                            Utils.infoMsg("Mouse clicked: null entity.");
                        }
                    }
                });
            }

            // Set the chart size
            // frame.getChartPanel().setPreferredSize(new Dimension(1200, 800));
            frame.pack();
            // Another way to set the size. Sets the JFrame size.
            // Probably don't need pack.
            // frame.setSize(1200, 800);
            frame.setVisible(true);
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Generates a chart and displays it using the current settings.
     * 
     * @param args
     */
    public void run() {
        List<LakeDataPoint> list = this.readCsv(csvName);
        XYSeriesCollection dataset = null;
        String[] days = null;
        switch(this.method) {
        case DATASELECT:
            dataset = this.createDatasetFromDataSelect(list, dataSelect);
            break;
        case DAYNUM:
            days = this.createDaysArray(list, dayNumStart, dayNumIncr,
                dayNumEnd);
            dataset = this.createDatasetFromDayNums(list, days);
            break;
        case DAYSARRAY:
            days = DAYS;
            dataset = this.createDatasetFromDayNums(list, days);
            break;
        }
        this.formatSeriesNames(dataset, formatType);
        String datasetname = lakeName + " " + year + " Temperature vs. Depth";
        JFreeChart chart = this.createChart(datasetname, dataset);
        this.plot(chart);
    }

    /**
     * Receives chart mouse click events.
     * 
     * @param event the event.
     */
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();
        if(entity != null) {
            Utils.infoMsg("Mouse clicked: " + entity.toString());
        } else {
            Utils.infoMsg("Mouse clicked: null entity.");
        }

    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @return The value of csvName.
     */
    public String getCsvName() {
        return csvName;
    }

    /**
     * @param csvName The new value for csvName.
     */
    public void setCsvName(String csvName) {
        this.csvName = csvName;
    }

    /**
     * @return The value of lakeName.
     */
    public String getLakeName() {
        return lakeName;
    }

    /**
     * @param lakeName The new value for lakeName.
     */
    public void setLakeName(String lakeName) {
        this.lakeName = lakeName;
    }

    /**
     * @return The value of formatType.
     */
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * @param formatType The new value for formatType.
     */
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    /**
     * @return The value of method.
     */
    public METHOD getMethod() {
        return method;
    }

    /**
     * @param method The new value for method.
     */
    public void setMethod(METHOD method) {
        this.method = method;
    }

    /**
     * @return The value of year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year The new value for year.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return The value of dayIncr.
     */
    public int getDayIncr() {
        return dayIncr;
    }

    /**
     * @param dayIncr The new value for dayIncr.
     */
    public void setDayIncr(int dayIncr) {
        this.dayIncr = dayIncr;
    }

    /**
     * @return The value of dayNumStart.
     */
    public int getDayNumStart() {
        return dayNumStart;
    }

    /**
     * @param dayNumStart The new value for dayNumStart.
     */
    public void setDayNumStart(int dayNumStart) {
        this.dayNumStart = dayNumStart;
    }

    /**
     * @return The value of dayNumIncr.
     */
    public int getDayNumIncr() {
        return dayNumIncr;
    }

    /**
     * @param dayNumIncr The new value for dayNumIncr.
     */
    public void setDayNumIncr(int dayNumIncr) {
        this.dayNumIncr = dayNumIncr;
    }

    /**
     * @return The value of dayNumEnd.
     */
    public int getDayNumEnd() {
        return dayNumEnd;
    }

    /**
     * @param dayNumEnd The new value for dayNumEnd.
     */
    public void setDayNumEnd(int dayNumEnd) {
        this.dayNumEnd = dayNumEnd;
    }

    /**
     * @return The value of days.
     */
    public String[] getDays() {
        return days;
    }

    /**
     * @param days The new value for days.
     */
    public void setDays(String[] days) {
        this.days = days;
    }

    /**
     * @return The value of dataSelect.
     */
    public DataSelect getDataSelect() {
        return dataSelect;
    }

    /**
     * @param dataSelect The new value for dataSelect.
     */
    public void setDataSelect(DataSelect dataSelect) {
        this.dataSelect = dataSelect;
    }

    /**
     * @return The value of doMarkers.
     */
    public boolean isDoMarkers() {
        return doMarkers;
    }

    /**
     * @param doMarkers The new value for doMarkers.
     */
    public void setDoMarkers(boolean doMarkers) {
        this.doMarkers = doMarkers;
    }

    /**
     * @return The value of doRainbowColors.
     */
    public boolean isDoRainbowColors() {
        return doRainbowColors;
    }

    /**
     * @param doRainbowColors The new value for doRainbowColors.
     */
    public void setDoRainbowColors(boolean doRainbowColors) {
        this.doRainbowColors = doRainbowColors;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Main
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param args
     */
    public static void main(String[] args) {
        LakeData app = new LakeData();
        app.run();
    }

}
