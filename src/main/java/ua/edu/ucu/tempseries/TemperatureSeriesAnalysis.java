package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private final double[] temperatureSeries;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[0];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double temperature : temperatureSeries) {
            if (temperature < -273) {
                throw new InputMismatchException();
            }
        }
        this.temperatureSeries = temperatureSeries;
    }

    public double average() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (double temperature: temperatureSeries) {
            sum += temperature;
        }
        return sum / temperatureSeries.length;
    }

    public double deviation() {
        double average = average();
        double squareDifferences = 0;
        for (double temperature: temperatureSeries) {
            squareDifferences += Math.pow((temperature - average), 2);
        }
        return Math.sqrt(squareDifferences / temperatureSeries.length);
    }

    public double min() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double min = temperatureSeries[0];
        for (double temperature: temperatureSeries) {
            if (temperature < min) {
                min = temperature;
            }
        }
        return min;
    }

    public double max() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double max = temperatureSeries[0];
        for (double temperature: temperatureSeries) {
            if (temperature > max) {
                max = temperature;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double minDifference = Math.abs(temperatureSeries[0] - tempValue);
        double closestToValue = temperatureSeries[0];
        boolean positive = temperatureSeries[0] > tempValue;
        for (double temperature: temperatureSeries) {
            if (Math.abs(temperature - tempValue) <= minDifference) {
                if (Math.abs(temperature - tempValue) == minDifference) {
                    if (temperature > tempValue && !positive) {
                        minDifference = Math.abs(temperature - tempValue);
                        closestToValue = temperature;
                        positive = true;
                    }
                } else {
                    minDifference = Math.abs(temperature - tempValue);
                    closestToValue = temperature;
                    positive = temperature > tempValue;
                }
            }
        }
        return closestToValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        int size = 0;
        for (double temperature: temperatureSeries) {
            if (temperature < tempValue) {
                ++size;
            }
        }
        double[] arr = new double[size];
        int counter = 0;
        for (double temperature: temperatureSeries) {
            if (temperature < tempValue) {
                arr[counter++] = temperature;
            }
        }
        return arr;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int size = 0;
        for (double temperature: temperatureSeries) {
            if (temperature >= tempValue) {
                ++size;
            }
        }
        double[] arr = new double[size];
        int counter = 0;
        for (double temperature: temperatureSeries) {
            if (temperature >= tempValue) {
                arr[counter++] = temperature;
            }
        }
        return arr;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < -273) {
                throw new InputMismatchException();
            }
        }
        int newSize = temperatureSeries.length;
        int size = newSize + temps.length;
        while (newSize < size) {
            newSize *= 2;
        }
        double[] newTemperatureSeries = new double[newSize];
        for (int i = 0; i < temperatureSeries.length; ++i) {
            newTemperatureSeries[i] = temperatureSeries[i];
        }
        for (int i = 0; i < temps.length; ++i) {
            newTemperatureSeries[i + temperatureSeries.length] = temps[i];
        }
        return newSize;
    }
}
