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

    /**
     * Grow an array of {@link Sensor}
     */
    private void grow() {
        Sensor[] copy = new Sensor[sensors.length * 2];
        System.arraycopy(sensors, 0, copy, 0, sensors.length);
        sensors = copy;
    }

    /**
     * Check if a {@link Sensor} exists
     *
     * @param sensor The {@link Sensor sensor} to match
     * @return Return true if found, false otherwise
     */
    private boolean exists(Sensor sensor) {
        if (sensor == null) return false;

        for (int i = 0; i < elements; i++) {
            if (sensors[i].equals(sensor)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Find {@link ISensor} by it's {@link String sensorId}
     *
     * @param sensorId The {@link String sensorId} to match
     * @return Returns an instance of {@link ISensor} if found, null otherwise
     */
    private ISensor getSensorById(String sensorId) {
        for (int i = 0; i < elements; i++) {
            if (sensors[i].getId().equals(sensorId)) {
                return sensors[i];
            }
        }

        return null;
    }

    /**
     * Add an element of type {@link Sensor}
     *
     * @param sensor The {@link Sensor}
     * @return Return true if the element is added otherwise return false
     */
    private boolean addElement(Sensor sensor) {
        if (exists(sensor)) return false;

        // If array is full then grow array
        if (elements == sensors.length) {
            grow();
        }

        sensors[elements++] = sensor;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addSensor(ISensor sensor) throws StationException {
        if (sensor == null) {
            throw new StationException("Sensor Interface can't NULL");
        }

        if (sensor instanceof Sensor s) {
            return addElement(s);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMeasurement(String sensorId, double value, LocalDateTime date, String unit) throws StationException, SensorException, MeasurementException {
        if (sensorId == null || date == null || unit == null) {
            throw new StationException("Parameters can't be NULL");
        }

        ISensor sensor = getSensorById(sensorId);
        if (sensor == null) {
            throw new StationException("Sensor doesn't exists");
        }

        return sensor.addMeasurement(value, date, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISensor[] getSensors() {
        if (elements == 0) return new ISensor[]{}.clone();

        return sensors.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISensor getSensor(String sensorId) {
        return getSensorById(sensorId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", qteSensors=" + elements +
                '}';
    }
}