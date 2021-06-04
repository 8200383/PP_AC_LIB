package Core;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.*;

import java.time.LocalDateTime;
import java.util.Arrays;

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

    public City(String name) {
        cityName = name;
        stations = new Station[10];
    }

    private Station findStationByName(String stationName) {
        if (stationName == null) return null;

        for (Station station : stations) {
            if (station != null && stationName.equals(station.getName())) return station;
        }

        return null;
    }

    private ISensor findSensorAtStationById(Station station, String sensorId) {
        for (ISensor sensor : station.getSensors()) {
            if (sensor != null && sensor.getId().equals(sensorId)) return sensor;
        }

        return null;
    }

    private void grow() {
        Station[] copy = new Station[stations.length * 2];
        System.arraycopy(stations, 0, copy, 0, stations.length);
        stations = copy;
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

        Station station = findStationByName(stationName);
        if (station == null) {
            throw new CityException("Station not found");
        }

        // If caught from City returns a StationException otherwise return a SensorException
        if (!Sensor.isSensorIdLengthValid(sensorId)) {
            throw new StationException("[City] Sensor ID can't have more or less than 10 characters");
        }

        ISensor sensor = findSensorAtStationById(station, sensorId);
        if (sensor != null) {
            throw new StationException("Sensor doesn't exist");
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

        /* Exceptions from Stations, Sensors and Measurement caught here
         * This also checks if the collections stores the measurement
         */
        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    @Override
    public IStation[] getStations() {
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
        for (Station station : stations) {
            if (station != null) {
                for (ISensor sensor : station.getSensors()) {
                    if (sensor != null && sensor.getId().equals(sensorId)) {
                        return sensor.getMeasurements();
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

        return new IStatistics[0];
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
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator, Parameter parameter) {
        return new IStatistics[0];
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stations=" + Arrays.toString(stations) +
                ", qteStations=" + elements +
                '}';
    }
}