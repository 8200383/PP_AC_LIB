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
    private final String cityName;
    private Station[] stations;
    private int elements = 0;

    /**
     * Constructor for {@link City}
     *
     * @param name The name of the city
     */
    // TODO Perguntar ao prof sobre o cityId
    public City(String name) {
        cityName = name;
        stations = new Station[10];
    }

    /**
     * Grow the array of {@link Station stations}
     */
    private void grow() {
        Station[] copy = new Station[stations.length * 2];
        System.arraycopy(stations, 0, copy, 0, elements);
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

    private IStatistics[] addStatistic(IStatistics[] srcArray, IStatistics statistic) {
        IStatistics[] destArray = new IStatistics[srcArray.length + 1];

        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);
        // TODO Check this later...
        destArray[(srcArray.length == 0) ? 0 : srcArray.length - 1] = statistic;
        return destArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return cityName;
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
        if (elements == stations.length) {
            grow();
        }

        stations[elements++] = new Station(stationName);
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
        if (elements == 0) return new IStation[]{};

        Station[] copy = new Station[elements];
        System.arraycopy(stations, 0, copy, 0, elements);
        return copy.clone();
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
    public ISensor[] getSensorsByStation(String stationName) {
        IStation station = getStationByName(stationName);
        return (station != null) ? station.getSensors() : null;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter,
                                                  LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return getMeasurementsByStation(aggregationOperator, parameter);
        }

        IStatistics[] statistics = new IStatistics[]{};

        for (IStation station : getStations()) {
            for (ISensor sensor : station.getSensors()) {
                if (sensor.getParameter() == parameter) {

                    switch (aggregationOperator) {
                        case AVG -> {
                            int valueCount = 0;
                            double valuesSum = 0;

                            for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                                if (iMeasurement instanceof Measurement measurement) {
                                    if (measurement.getTime().compareTo(startDate) > 0 &&
                                            measurement.getTime().compareTo(endDate) < 0
                                    ) {
                                        valuesSum += measurement.getValue();
                                        valueCount++;
                                    }
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    sensor.getParameter().getUnit().toString(),
                                    sensor.getParameter().toString(),
                                    valuesSum / (double) valueCount));
                        }
                        case COUNT -> {
                            int valueCount = 0;

                            for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                                if (iMeasurement instanceof Measurement measurement) {
                                    if (measurement.getTime().compareTo(startDate) > 0 &&
                                            measurement.getTime().compareTo(endDate) < 0
                                    ) {
                                        valueCount++;
                                    }
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    sensor.getParameter().getUnit().toString(),
                                    sensor.getParameter().toString(),
                                    valueCount));
                        }
                        case MAX -> {
                            //TODO: Get first value max
                        }
                        case MIN -> {
                            //TODO: Get first value min
                        }
                    }
                }
            }
        }
        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter) {

        IStatistics[] statistics = new IStatistics[]{};

        for (IStation iStation : getStations()) {
            if (iStation instanceof Station station) {
                for (ISensor iSensor : station.getSensors()) {
                    if (iSensor instanceof Sensor sensor) {
                        if (sensor.getParameter() == parameter) {

                            switch (aggregationOperator) {
                                case AVG -> {
                                    double valuesSum = 0;

                                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                                        if (iMeasurement instanceof Measurement measurement) {
                                            valuesSum += measurement.getValue();
                                        }
                                    }

                                    statistics = addStatistic(statistics, new Statistic(
                                            sensor.getId(),
                                            station.getName(),
                                            sensor.getParameter().getUnit().toString(),
                                            sensor.getParameter().toString(),
                                            valuesSum / (double) sensor.getNumMeasurements()));
                                }
                                case COUNT -> statistics = addStatistic(statistics, new Statistic(
                                        sensor.getId(),
                                        station.getName(),
                                        sensor.getParameter().getUnit().toString(),
                                        sensor.getParameter().toString(),
                                        sensor.getNumMeasurements()));
                                case MAX -> {
                                    IMeasurement[] measurements = sensor.getMeasurements();
                                    if (measurements == null) break;

                                    double maxValue = measurements[0].getValue();
                                    for (IMeasurement measurement : sensor.getMeasurements()) {
                                        if (measurement.getValue() > maxValue) {
                                            maxValue = measurement.getValue();
                                        }
                                    }

                                    statistics = addStatistic(statistics, new Statistic(
                                            sensor.getId(),
                                            station.getName(),
                                            sensor.getParameter().getUnit().toString(),
                                            sensor.getParameter().toString(),
                                            maxValue));
                                }
                                case MIN -> {
                                    IMeasurement[] measurements = sensor.getMeasurements();
                                    if (measurements == null) break;

                                    double minValue = measurements[0].getValue();
                                    for (IMeasurement measurement : sensor.getMeasurements()) {
                                        if (measurement.getValue() < minValue) {
                                            minValue = measurement.getValue();
                                        }
                                    }

                                    statistics = addStatistic(statistics, new Statistic(
                                            sensor.getId(),
                                            station.getName(),
                                            sensor.getParameter().getUnit().toString(),
                                            sensor.getParameter().toString(),
                                            minValue));
                                }
                            }
                        }
                    }
                }
            }
        }
        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator,
                                                 Parameter parameter, LocalDateTime startDate, LocalDateTime endDate) {
        if (stationName == null) {
            return new IStatistics[]{};
        }

        if (startDate == null || endDate == null) {
            return getMeasurementsBySensor(stationName, aggregationOperator, parameter);
        }

        IStatistics[] statistics = new IStatistics[]{};
        IStation station = getStationByName(stationName);
        if (station == null) {
            return new IStatistics[]{};
        }

        for (ISensor sensor : station.getSensors()) {
            switch (aggregationOperator) {
                case AVG -> {
                    double valuesSum = 0;

                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getTime().compareTo(startDate) > 0 && measurement.getTime().compareTo(endDate) < 0) {
                                valuesSum += measurement.getValue();
                            }
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            sensor.getParameter().getUnit().toString(),
                            sensor.getParameter().toString(),
                            valuesSum / (double) sensor.getNumMeasurements()));
                }
                case COUNT -> {
                    int valueCount = 0;

                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getTime().compareTo(startDate) > 0 && measurement.getTime().compareTo(endDate) < 0) {
                                valueCount++;
                            }
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            sensor.getParameter().getUnit().toString(),
                            sensor.getParameter().toString(),
                            valueCount));
                }
                case MAX -> {
                    //TODO: Get first value max
                }
                case MIN -> {
                    //TODO: Get first value min
                }
            }
        }

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator, Parameter parameter) {
        if (stationName == null || aggregationOperator == null || parameter == null) {
            throw new IllegalArgumentException("None of the method parameters can be null");
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            return new IStatistics[]{}.clone();
        }

        ISensor[] compatibleSensors = new ISensor[station.getSensors().length];
        int sensorCounter = 0;
        for (ISensor iSensor : station.getSensors()) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {
                    compatibleSensors[sensorCounter++] = sensor;
                }
            }
        }

        switch (aggregationOperator) {
            case AVG -> {
                return avgMeasurementBySensor(compatibleSensors);
            }
            case MIN -> {
                return minMeasurementBySensor(compatibleSensors);
            }
            case MAX -> {
                return maxMeasurementsBySensor(compatibleSensors);
            }
            case COUNT -> {
                return countMeasurementsBySensor(compatibleSensors);
            }
        }

        return new IStatistics[]{}.clone();
    }

    private IStatistics[] avgMeasurementBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                double valuesSum = 0;

                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        valuesSum += measurement.getValue();
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), null, null, null,
                        valuesSum / (double) sensor.getNumMeasurements()));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] minMeasurementBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {

                if (sensor.getNumMeasurements() == 0) {
                    break;
                }

                IMeasurement[] measurements = sensor.getMeasurements();

                double minValue = measurements[0].getValue(); // TODO Posição 0 pode ser null...
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        if (measurement.getValue() < minValue) {
                            minValue = measurement.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), null, null, null, minValue));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] maxMeasurementsBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getNumMeasurements() == 0) {
                    break;
                }

                IMeasurement[] measurements = sensor.getMeasurements();

                double maxValue = measurements[0].getValue(); // TODO Posição 0 pode ser null...
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        if (measurement.getValue() > maxValue) {
                            maxValue = measurement.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), null, null, null, maxValue));
            }
        }

        return statistics.clone();
    }

    private IStatistics[] countMeasurementsBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), null, null, null,
                        sensor.getNumMeasurements()));
            }
        }

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", elements=" + elements +
                '}';
    }
}