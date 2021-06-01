package Monitoring;

import Monitoring.SensorFactory.Sensor;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;

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
public class Station implements IStation {
    // Stations only exists when framed within a city

    private final String name;
    private Sensor[] sensors;
    private int elements = 0;

    protected Station(String name) {
        this.name = name;
        sensors = new Sensor[10];
    }

    @Override
    public String getName() {
        return name;
    }

    // TODO: Array grow()

    @Override
    public boolean addSensor(ISensor sensor) throws StationException, SensorException {
        if (sensor == null) {
            throw new StationException("Sensor Interface can't NULL");
        }

        if (sensor instanceof Sensor s) {
            if (!Sensor.validateSensorId(s.getId())) {
                throw new SensorException("Sensor Id can't have less or more than 10 characters");
            }

            sensors[elements++] = s;
            return true;
        }

        return false;
    }

    @Override
    public boolean addMeasurement(String sensorId, double value, LocalDateTime date, String unit) throws StationException, SensorException, MeasurementException {
        if (sensorId == null || date == null || unit == null) {
            throw new StationException("Parameters can't be NULL");
        }

        for (Sensor sensor : sensors) {
            if (sensor != null && sensor.getId().equals(sensorId)) {
                return sensor.addMeasurement(value, date, unit);
            }
        }

        return false;
    }

    @Override
    public ISensor[] getSensors() {
        return sensors;
    }

    @Override
    public ISensor getSensor(String s) {
        for (Sensor sensor : sensors) {
            if (sensor != null && sensor.getId().equals(s)) {
                return sensor;
            }
        }

        return null;
    }
}