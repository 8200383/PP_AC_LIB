package Core;

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

    private void grow() {
        Sensor[] copy = new Sensor[sensors.length * 2];
        System.arraycopy(sensors, 0, copy, 0, sensors.length);
        sensors = copy;
    }

    private boolean exists(Sensor sensor) {
        if (sensor == null) return false;

        for (ISensor s : getSensors()) {
            if (s.equals(sensor)) {
                return true;
            }
        }

        return false;
    }

    private boolean addElement(Sensor sensor) {
        if (exists(sensor)) return false;

        // If array is full then grow array
        if (elements == sensors.length) {
            grow();
        }

        sensors[elements++] = sensor;
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean addSensor(ISensor sensor) throws StationException, SensorException {
        if (sensor == null) {
            throw new StationException("Sensor Interface can't NULL");
        }

        if (sensor instanceof Sensor s) {
            if (!Sensor.isSensorIdLengthValid(s.getId())) {
                throw new SensorException("[Station] Sensor ID can't have more or less than 10 characters");
            }

            return addElement(s);
        }

        return false;
    }

    @Override
    public boolean addMeasurement(String sensorId, double value, LocalDateTime date, String unit) throws StationException, SensorException, MeasurementException {
        if (sensorId == null || date == null || unit == null) {
            throw new StationException("Parameters can't be NULL");
        }

        for (ISensor sensor : getSensors()) {
            if (sensor.getId().equals(sensorId)) {
                return sensor.addMeasurement(value, date, unit);
            }
        }

        return false;
    }

    @Override
    public ISensor[] getSensors() {
        if (elements == 0) return new ISensor[]{};

        Sensor[] copy = new Sensor[elements];
        System.arraycopy(sensors, 0, copy, 0, elements);
        return copy.clone();
    }

    @Override
    public ISensor getSensor(String sensorId) {
        for (ISensor sensor : getSensors()) {
            if (sensor.getId().equals(sensorId)) return sensor;
        }

        return null;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", qteSensors=" + elements +
                '}';
    }
}