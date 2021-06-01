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

    @Override
    public boolean addStation(String s) throws CityException {
        //TODO: Array problem
        return false;
    }

    @Override
    public boolean addSensor(String stationName, String sensorId, ICartesianCoordinates iCartesianCoordinates, IGeographicCoordinates iGeographicCoordinates) throws CityException, StationException, SensorException {
        //TODO: Adapt sensor constructor
        return false;
    }

    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value, String unit, LocalDateTime localDateTime) throws CityException, StationException, SensorException, MeasurementException {
        if (stationName == null) {
            throw new CityException();
        }
        IStation station = getStationByName(stationName);
        if (station == null) {
            throw new CityException();
        }

        for (ISensor sensor : station.getSensors()){
            for (IMeasurement measurement : sensor.getMeasurements()){
                //TODO: oooooooooooooof
            }
        }
        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    @Override
    public IStation[] getStations() {
        return stations;
    }

    private IStation getStationByName(String s) {
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null && stations[i].getName().equals(s)) {
                return stations[i];
            }
        }
        return null;
    }

    @Override
    public IStation getStation(String s) {
        return getStationByName(s);
    }

    @Override
    public ISensor[] getSensorsByStation(String stationName) {
        IStation station = getStationByName(stationName);
        return (station != null) ? station.getSensors() : null;
    }

    @Override
    public IMeasurement[] getMeasurementsBySensor(String s) {
        for (int i = 0; i < stations.length; i++) {
            ISensor[] tempSensors = stations[i].getSensors();
            for (int j = 0; j < tempSensors.length; j++) {
                if (stations[i] != null && tempSensors[j] != null && tempSensors[j].getId().equals(s)) {
                    return tempSensors[j].getMeasurements();
                }
            }
        }
        return null;
    }
}