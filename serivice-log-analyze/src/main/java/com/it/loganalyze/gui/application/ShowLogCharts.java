package com.it.loganalyze.gui.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;

import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import com.it.loganalyze.log.Log;
import com.it.loganalyze.log.LogData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

import javafx.scene.control.Tooltip;

public class ShowLogCharts {
    private LogData logData;
    private String pieChartField;
    private String timeSeriesChartField;
    private PieChartFX pieChartFX;
    private TimeSeriesChartFX timeSeriesChartFX;

    public ShowLogCharts(LogData logData, String pieChartField, String timeSeriesChartField) {
        this.logData = logData;
        this.pieChartField = pieChartField;
        this.timeSeriesChartField = timeSeriesChartField;
    }
    

    public HBox createCharts() {
        try {
            // Create the pie chart and time series chart
            pieChartFX = new PieChartFX(logData, pieChartField);
            PieChart pieChart = pieChartFX.createChart();
            timeSeriesChartFX = new TimeSeriesChartFX(logData, timeSeriesChartField);
            LineChart<String, Number> timeSeriesChart = timeSeriesChartFX.createChart();

            // Add an event handler to update the charts when a day is clicked in the time series chart
            timeSeriesChartFX.setDayClickedHandler(day -> {
                try {
                    pieChartFX.updateChart(day);
                    timeSeriesChartFX.updateChart(day, true); // pass a flag to indicate that hourly data should be displayed
                } catch (Exception e) {
                 
                }
            });

            // Create a layout to hold both charts
            HBox hbox = new HBox(pieChart, timeSeriesChart);
            return hbox;
        } catch (Exception e) {
          
        }
        return null;
    }

    // Include the updated PieChartFX and TimeSeriesChartFX classes here



        public class PieChartFX {
            private LogData logData;
            private String field;
            private PieChart chart;

            public PieChartFX(LogData logData, String field) {
                this.logData = logData;
                this.field = field;
            }

            public PieChart createChart() {
                // Create a dataset for the pie chart
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                // Create a map to keep track of the counts of each unique value of the given field
                Map<String, Integer> map = new HashMap<>();

                // Loop through all the logs in the LogData object
                for (Log log : logData.getData()) {
                    // Get the value of the given field
                    String value = log.getField(field);

                    // Update the counts in the map
                    if (map.containsKey(value)) {
                        map.put(value, map.get(value) + 1);
                    } else {
                        map.put(value, 1);
                    }
                }

                // Add the data from the map to the dataset
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }

                // Create the pie chart
                chart = new PieChart(pieChartData);
                chart.setTitle("Pie Chart Example");

                // Add an event handler to display a tooltip when a pie slice is pointed at
                for (PieChart.Data data : pieChartData) {
                    Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue());
                    Tooltip.install(data.getNode(), tooltip);
                }

                return chart;
            }

            public void updateChart(String day) throws Exception {
                // Clear the existing data from the chart
                chart.getData().clear();

                // Create a dataset for the pie chart
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                // Create a map to keep track of the counts of each unique value of the given field for the given day
                Map<String, Integer> map = new HashMap<>();

                // Loop through all the logs in the LogData object
                for (Log log : logData.getData()) {
                    // Get the value of the timestamp field
                    String value = log.getField("Timestamp");
                    if (value == null ) {
                        value = log.getField("Date");
                    }
                    if (value == null ) {
                        value = log.getField("Time");
                    }


                 // Parse the value as a date
                    Date date = null;
                    if (value.contains("/")) {
                        try {
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss.SSSSSS Z");
                            date = dateFormat1.parse(value);
                        } catch (ParseException e) {
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
                            date = dateFormat2.parse(value);
                        }
                    } else if (value.contains("ICT")) {
                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        dateFormat3.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
                        date = dateFormat3.parse(value);
                    } else if (value.contains(":")) {
                        SimpleDateFormat dateFormat4 = new SimpleDateFormat("MMM d HH:mm:ss");
                        date = dateFormat4.parse(value);
                    } else {
                        SimpleDateFormat dateFormat5 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
                        date = dateFormat5.parse(value);
                    }
                    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String currentDay = dayFormat.format(date);

                    // Check if the date is within the given day
                    if (currentDay.equals(day)) {
                        // Get the value of the given field
                        String fieldValue = log.getField(field);

                        // Update the counts in the map
                        if (map.containsKey(fieldValue)) {
                            map.put(fieldValue, map.get(fieldValue) + 1);
                        } else {
                            map.put(fieldValue, 1);
                        }
                    }
                }

                // Add the data from the map to the dataset
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }

                // Update the pie chart data
                chart.setData(pieChartData);

                // Update the tooltips
                for (PieChart.Data data : pieChartData) {
                    Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue());
                    Tooltip.install(data.getNode(), tooltip);
                }
            }

        }




        public class TimeSeriesChartFX {
            private LogData logData;
            private String field;
            private LineChart<String, Number> chart;
            private Consumer<String> dayClickedHandler;

            public TimeSeriesChartFX(LogData logData, String field) {
                this.logData = logData;
                this.field = field;
            }

            public LineChart<String, Number> createChart() throws Exception {
                // Create the axes for the chart
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Time");
                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Value");

                // Create a line chart
                chart = new LineChart<>(xAxis, yAxis);
                chart.setTitle("Time Series Chart Example");

                // Create a series for the chart
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Time Series Example");

                // Create a map to keep track of the counts of logs for each day
                Map<String, Integer> map = new HashMap<>();

                // Loop through all the logs in the LogData object
                for (Log log : logData.getData()) {
                    // Get the value of the given field
                    String value = log.getField(field);

                    // Parse the value as a date
                    Date date = null;
                    if (value.contains("/")) {
                        try {
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss.SSSSSS Z");
                            date = dateFormat1.parse(value);
                        } catch (ParseException e) {
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
                            date = dateFormat2.parse(value);
                        }
                    } else if (value.contains("ICT")) {
                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        dateFormat3.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
                        date = dateFormat3.parse(value);
                    } else if (value.contains(":")) {
                        SimpleDateFormat dateFormat4 = new SimpleDateFormat("MMM d HH:mm:ss");
                        date = dateFormat4.parse(value);
                    } else {
                        SimpleDateFormat dateFormat5 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
                        date = dateFormat5.parse(value);
                    }
                    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String day = dayFormat.format(date);

                    // Update the counts in the map
                    if (map.containsKey(day)) {
                        map.put(day, map.get(day) + 1);
                    } else {
                        map.put(day, 1);
                    }
                }

                // Add the data from the map to the series
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }

                // Add an event handler to update the chart when a data point is clicked
                Platform.runLater(() -> {
                    for (XYChart.Data<String, Number> data : series.getData()) {
                        data.getNode().setOnMouseClicked(event -> {
                            if (dayClickedHandler != null) {
                                dayClickedHandler.accept(data.getXValue());
                            }
                        });
                    }
                });

                // Add the series to the chart
                chart.getData().add(series);
                return chart;
            }


            public void updateChart(String day, boolean showHourlyData) throws Exception {
                // Clear the existing data from the chart
                chart.getData().clear();

                // Create a series for the chart
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Time Series Example");

                // Create a map to keep track of the counts of logs for each hour or day
                Map<String, Integer> map = new HashMap<>();

                // Loop through all the logs in the LogData object
                for (Log log : logData.getData()) {
                    // Get the value of the timestamp field
                    String value = log.getField("Timestamp");
                    if (value == null ) {
                        value = log.getField("Date");
                    }
                    if (value == null) {
                        value = log.getField("Time");
                    }

                    // Parse the value as a date
                    Date date = null;
                    if (value.contains("/")) {
                        try {
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss.SSSSSS Z");
                            date = dateFormat1.parse(value);
                        } catch (ParseException e) {
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
                            date = dateFormat2.parse(value);
                        }
                    } else if (value.contains("ICT")) {
                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        dateFormat3.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
                        date = dateFormat3.parse(value);
                    } else if (value.contains(":")) {
                        SimpleDateFormat dateFormat4 = new SimpleDateFormat("MMM d HH:mm:ss");
                        date = dateFormat4.parse(value);
                    } else {
                        SimpleDateFormat dateFormat5 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
                        date = dateFormat5.parse(value);
                    }
                    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String currentDay = dayFormat.format(date);

                    // Check if the date is within the given day
                    if (currentDay.equals(day)) {
                        Object key;
                        if (showHourlyData) {
                            // Group by hour
                            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
                            key = hourFormat.format(date);
                        } else {
                            // Group by day
                            key = currentDay;
                        }

                        // Update the counts in the map
                        if (map.containsKey(key)) {
                            map.put((String) key, map.get(key) + 1);
                        } else {
                            map.put((String) key, 1);
                        }
                    }
                }

                // Add the data from the map to the series
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }

                // Add the series to the chart
                chart.getData().add(series);
            }

            public void setDayClickedHandler(Consumer<String> handler) {
                this.dayClickedHandler = handler;
            }
        }

}
