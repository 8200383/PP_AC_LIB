package Monitoring;

import Monitoring.SensorFactory.Sensor;
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

public class City implements ICity {
    private String cityId;
    private String cityName;
    private Station[] stations;

    @Override
    public String getId() {
        return cityId;
    }

    @Override
    public String getName() {
        return cityName;
    }

    private Station findStationByName(String stationName) {
        if (stationName == null) return null;

        for (Station station : stations) {
            if (stationName.equals(station.getName())) return station;
        }

        return null;
    }

    private ISensor findSensorAtStationById(Station station, String sensorId) {
        for (ISensor sensor : station.getSensors()) {
            if (sensorId.equals(sensor.getId())) return sensor;
        }

        return null;
    }

    // TODO: Array grow()

    @Override
    public boolean addStation(String stationName) throws CityException {
        if (stationName == null) throw new CityException("Station Name can't be NULL");

        // Check if Station already exists by it's name
        if (findStationByName(stationName) != null) {
            return false;
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
        if (Sensor.validateSensorId(sensorId)) {
            throw new StationException("Invalid Sensor ID");
        }

        ISensor sensor = findSensorAtStationById(station, sensorId);
        if (sensor != null) {
            throw new StationException("Sensor doesn't exist");
        }

        // Create Sensor Object
        return station.addSensor(Sensor.SensorFactory(sensorId, cartesianCoordinates, geographicCoordinates));
    }

    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value,
                                  String unit, LocalDateTime localDateTime
    ) throws CityException, StationException, SensorException, MeasurementException {
        if (stationName == null) {
            throw new CityException();
        }

        IStation station = findStationByName(stationName);
        if (station == null) {
            throw new CityException();
        }

        for (ISensor sensor : station.getSensors()) {
            for (IMeasurement measurement : sensor.getMeasurements()) {
                // TODO Visit Docs
            }
        }
        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    @Override
    public IStation[] getStations() {
        return stations;
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
    public IMeasurement[] getMeasurementsBySensor(String s) {
        for (Station station : stations) {
            if (station != null) {
                for (ISensor tempSensor : station.getSensors()) {
                    if (tempSensor != null && tempSensor.getId().equals(s)) {
                        return tempSensor.getMeasurements();
                    }
                }
            }
        }

        return null;
    }
}