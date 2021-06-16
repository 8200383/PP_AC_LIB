package Core;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.*;

import java.time.LocalDateTime;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */

public class City implements ICity, ICityStatistics {
    private static Integer cityId = 0;
    private final String cityName;
    private Station[] stations;
    private int nStations = 0;

    /**
     * Constructor for {@link City}
     *
     * @param name The name of the city
     */
    public City(String name) {
        cityId = ++cityId;
        cityName = name;
        stations = new Station[10];
    }

    /**
     * Grow the array of {@link Station stations}
     */
    private void grow() {
        Station[] copy = new Station[stations.length * 2];
        System.arraycopy(stations, 0, copy, 0, nStations);
        stations = copy;
    }

    /**
     * Finds a {@link Station} by {@link String stationName}
     *
     * @param stationName The of the station to look for
     * @return Returns an instance of {@link Station}
     */
    private IStation getStationByName(String stationName) {
        if (stationName == null) return null;

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {
                if (station.getName().equals(stationName)) {
                    return station;
                }
            }
        }

        return null;
    }

    /**
     * Finds a {@link Sensor sensor} at {@link IStation station} by {@link String sensorId}
     *
     * @param station  The station where to look
     * @param sensorId The sensorId to look for
     * @return Returns an instance of a {@link Sensor}
     */
    private ISensor getSensorAtStationById(IStation station, String sensorId) {
        for (ISensor iSensor : station.getSensors()) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getId().equals(sensorId)) {
                    return sensor;
                }
            }
        }

        return null;
    }

    /**
     * Calculate the average of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] avgOfMeasurementsBySensor(ISensor[] sensors, Parameter parameter) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {
                    // Division by 0 in Java causes 'Not a Number' (NaN)
                    if (sensor.getNumMeasurements() == 0) {
                        statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                                sensor.getId(),
                                0
                        ));
                        continue;
                    }

                    double sumOfMeasurements = 0;
                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            sumOfMeasurements += measurement.getValue();
                        }
                    }

                    statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                            sensor.getId(),
                            sumOfMeasurements / (double) sensor.getNumMeasurements()
                    ));
                }
            }
        }

        return statistics.clone();
    }

    /**
     * Calculate the minimum of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] minOfMeasurementBySensor(ISensor[] sensors, Parameter parameter) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {

                    if (sensor.getNumMeasurements() == 0) {
                        continue;
                    }

                    IMeasurement[] measurements = sensor.getMeasurements();
                    double minValue = measurements[0].getValue();
                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getValue() < minValue) {
                                minValue = measurement.getValue();
                            }
                        }
                    }

                    statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                            sensor.getId(),
                            minValue
                    ));
                }
            }
        }

        return statistics.clone();
    }

    /**
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] maxOfMeasurementsBySensor(ISensor[] sensors, Parameter parameter) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {
                    if (sensor.getNumMeasurements() == 0) {
                        continue;
                    }

                    IMeasurement[] measurements = sensor.getMeasurements();
                    double maxValue = measurements[0].getValue();
                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getValue() > maxValue) {
                                maxValue = measurement.getValue();
                            }
                        }
                    }

                    statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                            sensor.getId(),
                            maxValue
                    ));
                }
            }
        }

        return statistics.clone();
    }

    /**
     * Count the number of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] countOfMeasurementsBySensor(ISensor[] sensors, Parameter parameter) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {
                    statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                            sensor.getId(),
                            sensor.getNumMeasurements()
                    ));
                }
            }
        }

        return statistics.clone();
    }

    private ISensor[] addSensor(ISensor[] srcArray, int sensorCount, ISensor sensor) {
        if (srcArray.length == sensorCount) {
            ISensor[] destArray = new ISensor[srcArray.length * 2];
            System.arraycopy(srcArray, 0, destArray, 0, sensorCount);
            destArray[sensorCount] = sensor;
            return destArray;
        }

        srcArray[sensorCount] = sensor;
        return srcArray;
    }

    /**
     * Adds an element {@link IStatistics} to an existing array and increments the size of that array by one
     *
     * @param srcArray  The {@link IStatistics srcArray} to grow
     * @param statistic The {@link IStatistics statistic} to add to the array
     * @return Returns an array of {@link IStatistics}
     */
    private IStatistics[] addStatistic(IStatistics[] srcArray, int statisticsCount, IStatistics statistic) {
        if (srcArray.length == statisticsCount) {
            IStatistics[] destArray = new IStatistics[srcArray.length * 2];
            System.arraycopy(srcArray, 0, destArray, 0, statisticsCount);
            destArray[statisticsCount] = statistic;
            return destArray;
        }

        srcArray[statisticsCount] = statistic;
        return srcArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return cityId.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return cityName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addStation(String stationName) throws CityException {
        if (stationName == null) throw new CityException("Station Name can't be NULL");

        // Check if Station already exists
        if (getStationByName(stationName) != null) {
            return false;
        }

        // If array is full then grow array
        if (nStations == stations.length) {
            grow();
        }

        stations[nStations++] = new Station(stationName);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addSensor(String stationName, String sensorId,
                             ICartesianCoordinates cartesianCoordinates,
                             IGeographicCoordinates geographicCoordinates
    ) throws CityException, StationException, SensorException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            throw new CityException("Station not found");
        }

        // If caught from City returns a StationException otherwise return a SensorException
        if (!Sensor.isSensorIdLengthValid(sensorId)) {
            throw new StationException("[City] Sensor ID can't have more or less than 10 characters");
        }

        ISensor sensor = getSensorAtStationById(station, sensorId);
        if (sensor != null) {
            throw new StationException("Sensor doesn't exist");
        }

        return station.addSensor(new Sensor(sensorId, cartesianCoordinates, geographicCoordinates));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value,
                                  String unit, LocalDateTime localDateTime
    ) throws CityException, StationException, SensorException, MeasurementException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            throw new CityException("Can't find any Station with that name");
        }

        /* Exceptions from Stations, Sensors and Measurement caught here
         * This also checks if the collections stores the measurement
         */
        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStation[] getStations() {
        if (nStations == 0) return new IStation[]{}.clone();

        return stations.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStation getStation(String stationName) {
        return getStationByName(stationName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // TODO adicionar ao Menu
    public ISensor[] getSensorsByStation(String stationName) {
        IStation station = getStationByName(stationName);
        return (station != null) ? station.getSensors().clone() : new ISensor[]{}.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMeasurement[] getMeasurementsBySensor(String sensorId) {
        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {
                for (ISensor iSensor : station.getSensors()) {
                    if (iSensor instanceof Sensor sensor) {
                        if (sensor.getId().equals(sensorId)) {
                            return sensor.getMeasurements();
                        }
                    }
                }
            }
        }

        return null;
    }

    //TODO comentar
    private IStatistics[] avgOfMeasurementsByStation(IStation[] stations, Parameter parameter,
                                                     LocalDateTime startDate, LocalDateTime endDate) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {

                IStatistics[] measurements;
                if (startDate == null && endDate == null) {
                    measurements = avgOfMeasurementsBySensor(station.getSensors(), parameter);
                } else {
                    ISensor[] sensorsBetweenDates = getSensorsBetweenDates(station.getSensors(), startDate, endDate);
                    measurements = avgOfMeasurementsBySensor(sensorsBetweenDates, parameter);
                }

                if (measurements[0] == null) continue;
                double sum = 0;

                for (IStatistics iStatistics : measurements) {
                    if (iStatistics instanceof Statistic avg) {
                        sum += avg.getValue();
                    }
                }

                statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                        station.getName(),
                        sum / (double) measurements.length
                ));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] minOfMeasurementsByStation(IStation[] stations, Parameter parameter,
                                                     LocalDateTime startDate, LocalDateTime endDate) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {

                IStatistics[] measurements;
                if (startDate == null && endDate == null) {
                    measurements = avgOfMeasurementsBySensor(station.getSensors(), parameter);
                } else {
                    ISensor[] sensorsBetweenDates = getSensorsBetweenDates(station.getSensors(), startDate, endDate);
                    measurements = avgOfMeasurementsBySensor(sensorsBetweenDates, parameter);
                }

                if (measurements[0] == null) continue;
                double min = measurements[0].getValue();

                for (IStatistics iStatistics : measurements) {
                    if (iStatistics instanceof Statistic avg) {
                        if (avg.getValue() < min) {
                            min = avg.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                        station.getName(),
                        min / measurements.length
                ));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] maxOfMeasurementsByStation(IStation[] stations, Parameter parameter,
                                                     LocalDateTime startDate, LocalDateTime endDate) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {

                IStatistics[] measurements;
                if (startDate == null && endDate == null) {
                    measurements = avgOfMeasurementsBySensor(station.getSensors(), parameter);
                } else {
                    ISensor[] sensorsBetweenDates = getSensorsBetweenDates(station.getSensors(), startDate, endDate);
                    measurements = avgOfMeasurementsBySensor(sensorsBetweenDates, parameter);
                }

                if (measurements[0] == null) continue;
                double max = measurements[0].getValue();

                for (IStatistics iStatistics : measurements) {
                    if (iStatistics instanceof Statistic avg) {
                        if (avg.getValue() > max) {
                            max = avg.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                        station.getName(),
                        max / measurements.length
                ));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] countOfMeasurementsByStation(IStation[] stations, Parameter parameter,
                                                       LocalDateTime startDate, LocalDateTime endDate) {
        int statisticsCount = 0;
        IStatistics[] statistics = new IStatistics[10];

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {

                int measurementsByStation = 0;
                for (ISensor iSensor : station.getSensors()) {
                    if (iSensor instanceof Sensor sensor) {
                        if (sensor.getParameter().equals(parameter)) {
                            if (startDate == null && endDate == null) {
                                measurementsByStation += sensor.getNumMeasurements();
                            } else {
                                IMeasurement[] measurements = sensor.getMeasurements();
                                for (IMeasurement iMeasurement : measurements) {
                                    if (iMeasurement instanceof Measurement measurement) {
                                        if (isMeasurementBetweenDates(measurement, startDate, endDate)) {
                                            measurementsByStation++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // If it's still 0 this station does not have any measurements stored
                if (measurementsByStation == 0) continue;

                statistics = addStatistic(statistics, statisticsCount++, new Statistic(
                        station.getName(),
                        measurementsByStation
                ));
            }
        }

        return statistics.clone();
    }

    /**
     * Get an array of {@link Sensor} between dates.
     *
     * @param sensors   The array of {@link Sensor sensors}.
     * @param startDate The {@link LocalDateTime startDate}.
     * @param endDate   The {@link LocalDateTime endDate}.
     * @return Return an array of compatible sensors between specified dates.
     */
    private ISensor[] getSensorsBetweenDates(ISensor[] sensors, LocalDateTime startDate, LocalDateTime endDate) {
        int sensorCount = 0;
        ISensor[] sensorsBetweenDates = new ISensor[10];
        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        if (isMeasurementBetweenDates(measurement, startDate, endDate)) {
                            sensorsBetweenDates = addSensor(sensorsBetweenDates, sensorCount, sensor);
                        }
                    }
                }
            }
        }

        return sensorsBetweenDates.clone();
    }

    /**
     * Verify if a {@link Measurement} is between specified dates.
     *
     * @param measurement The {@link Measurement measurement}.
     * @param startDate   The {@link LocalDateTime startDate} limit.
     * @param endDate     The {@link LocalDateTime endDate} limit.
     * @return Returns true if valid or false otherwise
     */
    private boolean isMeasurementBetweenDates(Measurement measurement, LocalDateTime startDate, LocalDateTime endDate) {
        return measurement.getTime().compareTo(startDate) > 0 && measurement.getTime().compareTo(endDate) < 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter,
                                                  LocalDateTime startDate, LocalDateTime endDate) {
        IStatistics[] statistics;

        //TODO: Aqui
        statistics = switch (aggregationOperator) {
            case AVG -> avgOfMeasurementsByStation(stations, parameter, startDate, endDate);
            case MIN -> minOfMeasurementsByStation(stations, parameter, startDate, endDate);
            case MAX -> maxOfMeasurementsByStation(stations, parameter, startDate, endDate);
            case COUNT -> countOfMeasurementsByStation(stations, parameter, startDate, endDate);
        };

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter) {
        IStatistics[] statistics;

        statistics = switch (aggregationOperator) {
            case AVG -> avgOfMeasurementsByStation(stations, parameter, null, null);
            case MIN -> minOfMeasurementsByStation(stations, parameter, null, null);
            case MAX -> maxOfMeasurementsByStation(stations, parameter, null, null);
            case COUNT -> countOfMeasurementsByStation(stations, parameter, null, null);
        };

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator,
                                                 Parameter parameter, LocalDateTime startDate, LocalDateTime endDate) {
        IStatistics[] statistics = new IStatistics[10];

        if (stationName == null) {
            return statistics.clone();
        }

        if (startDate == null || endDate == null) {
            return getMeasurementsBySensor(stationName, aggregationOperator, parameter);
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            return statistics.clone();
        }

        ISensor[] sensorsBetweenDates = getSensorsBetweenDates(station.getSensors(), startDate, endDate);

        statistics = switch (aggregationOperator) {
            case AVG -> avgOfMeasurementsBySensor(sensorsBetweenDates, parameter);
            case COUNT -> countOfMeasurementsBySensor(sensorsBetweenDates, parameter);
            case MAX -> maxOfMeasurementsBySensor(sensorsBetweenDates, parameter);
            case MIN -> minOfMeasurementBySensor(sensorsBetweenDates, parameter);
        };

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator
            aggregationOperator, Parameter parameter) {
        if (stationName == null || aggregationOperator == null || parameter == null) {
            throw new IllegalArgumentException("None of the method parameters can be null");
        }

        IStatistics[] statistics = new IStatistics[10];
        IStation station = getStationByName(stationName);
        if (station == null) {
            return statistics.clone();
        }

        statistics = switch (aggregationOperator) {
            case AVG -> avgOfMeasurementsBySensor(station.getSensors(), parameter);
            case MIN -> minOfMeasurementBySensor(station.getSensors(), parameter);
            case MAX -> maxOfMeasurementsBySensor(station.getSensors(), parameter);
            case COUNT -> countOfMeasurementsBySensor(station.getSensors(), parameter);
        };

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", elements=" + nStations +
                '}';
    }
}