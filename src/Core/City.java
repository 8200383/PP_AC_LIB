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
    private IStation findStationByName(String stationName) {
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
     * @return Returns an instante of a {@link Sensor}
     */
    private ISensor findSensorAtStationById(IStation station, String sensorId) {
        for (ISensor iSensor : station.getSensors()) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getId().equals(sensorId)) {
                    return sensor;
                }
            }
        }

        return null;
    }

    @Override
    public String getId() {
        return cityName;
    }

    @Override
    public String getName() {
        return cityName;
    }

    @Override
    public boolean addStation(String stationName) throws CityException {
        if (stationName == null) throw new CityException("Station Name can't be NULL");

        // Check if Station already exists by it's name
        if (findStationByName(stationName) != null) {
            return false;
        }

        // If array is full then grow array
        if (elements == stations.length) {
            grow();
        }

        stations[elements++] = new Station(stationName);
        return true;
    }

    @Override
    public boolean addSensor(String stationName, String sensorId,
                             ICartesianCoordinates cartesianCoordinates,
                             IGeographicCoordinates geographicCoordinates
    ) throws CityException, StationException, SensorException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = findStationByName(stationName);
        if (station == null) {
            throw new CityException("Station not found");
        }

        if (findSensorAtStationById(station, sensorId) != null) {
            return false;
        }

        return station.addSensor(new Sensor(sensorId, cartesianCoordinates, geographicCoordinates));
    }

    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value,
                                  String unit, LocalDateTime localDateTime
    ) throws CityException, StationException, SensorException, MeasurementException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = findStationByName(stationName);
        if (station == null) {
            throw new CityException("Can't find any Station with that name");
        }

        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    @Override
    public IStation[] getStations() {
        if (elements == 0) return new IStation[]{}.clone();

        return stations.clone();
    }

    @Override
    public IStation getStation(String stationName) {
        return findStationByName(stationName);
    }

    @Override
    public ISensor[] getSensorsByStation(String stationName) {
        IStation station = findStationByName(stationName);
        return (station != null) ? station.getSensors() : null;
    }

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

    // TODO Search a Station compatible with those parameters
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter,
                                                  LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) {
            return getMeasurementsByStation(aggregationOperator, parameter);
        }

        return new IStatistics[0];
    }

    // TODO Start here before go up there, find a station compatible with the passed parameter
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter) {
        // TODO Implement a switch case operator for the aggregation operator

        Statistics[] statistics = new Statistics[10];
        int elements = 0;

        // Pode haver mais do que um sensor com o mesmo parameter mas noutra estação
        for (IStation station : getStations()) {
            for (ISensor sensor : station.getSensors()) {
                if (sensor.getParameter() == parameter) {

                    if (elements == statistics.length) {
                        Statistics[] copy = new Statistics[statistics.length * 2];
                        System.arraycopy(statistics, 0, copy, 0, statistics.length);
                        statistics = copy;
                    }

                    switch (aggregationOperator) {
                        case AVG -> {
                            double values = 0.0;

                            // Média de measurements por sensor
                            for (IMeasurement measurement : sensor.getMeasurements()) {
                                values += measurement.getValue();

                            }

                            double average = sensor.getNumMeasurements() / values;
                            statistics[elements++] = new Statistics(
                                    "Sensor=" + sensor.getId() +
                                            ", Station=" + station.getName() +
                                            ", Unit=" + sensor.getParameter().getUnit() +
                                            ", Parameter=" + sensor.getParameter().toString(), average);
                        }
                        case COUNT -> statistics[elements++] = new Statistics(
                                "Count of Measurements By Station" +
                                        ", Sensor=" + sensor.getId() +
                                        ", Station=" + station.getName(), sensor.getNumMeasurements());
                        case MAX -> {
                            IMeasurement[] measurements = sensor.getMeasurements();
                            if (measurements == null) break;

                            double max = measurements[0].getValue();
                            for (IMeasurement measurement : measurements) {
                                if (measurement.getValue() > max) {
                                    max = measurement.getValue();
                                }
                            }

                            statistics[elements++] = new Statistics(
                                    "Maximum of Measurements By Station" +
                                            ", Sensor=" + sensor.getId() +
                                            ", Station=" + station.getName(), max);
                        }
                        case MIN -> {
                            IMeasurement[] measurements = sensor.getMeasurements();
                            if (measurements == null) break;

                            double min = measurements[0].getValue();
                            for (IMeasurement measurement : measurements) {
                                if (measurement.getValue() < min) {
                                    min = measurement.getValue();
                                }
                            }

                            statistics[elements++] = new Statistics(
                                    "Minimum of Measurements By Station" +
                                            ", Sensor=" + sensor.getId() +
                                            ", Station=" + station.getName(), min);
                        }
                    }
                }
            }
        }

        return statistics.clone();
    }

    // TODO Those are relatively simple
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator,
                                                 Parameter parameter, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) {
            return getMeasurementsBySensor(stationName, aggregationOperator, parameter);
        }

        return new IStatistics[0];
    }

    // TODO Start here before go up there
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator
            aggregationOperator, Parameter parameter) {
        return new IStatistics[0];
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", elements=" + elements +
                '}';
    }
}