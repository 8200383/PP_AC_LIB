package Monitoring.SensorFactory;

import Monitoring.Coordinates.ValueObjects.CartesianCoordinate;
import Monitoring.Coordinates.ValueObjects.GeographicCoordinate;
import Monitoring.SensorFactory.ValueObjects.SensorId;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;

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
public abstract class Sensor implements ISensor {

    private int sensorCapacity = 10;

    private final SensorId sensorId;
    private final CartesianCoordinate cartesianCoordinate;
    private final GeographicCoordinate geographicCoordinate;

    private Measurement[] measurements;
    private int numMeasurements;

    /**
     * Restrições:
     * O código do sensor tem apenas 10 caracteres
     * Primeiras letras representam o tipo de sensor (Ex: QA - Qualidade do Ar)
     * As seguintes representam o parâmetro associado ao sensor (Ex: NO2 - Sigla do Dióxido de Azoto)
     * Um sensor apenas tem um parâmetro!
     * <p>
     * Exemplos de códigos validos: QA0NO20001, METEMP0078, ME00PA0078
     */
    protected Sensor(String id,
                     double x, double y, double z,
                     double lat, double lng
    ) throws SensorException {
        sensorId = new SensorId(id);
        cartesianCoordinate = new CartesianCoordinate(x, y, z);
        geographicCoordinate = new GeographicCoordinate(lat, lng);
        measurements = new Measurement[sensorCapacity];
    }

    public static Sensor SensorFactory(
            String sensorId,
            double x, double y, double z,
            double lat, double lng
    ) throws SensorException {
        if (sensorId.contains("QA")) return new AirSensor(sensorId, x, y, z, lat, lng);
        else if (sensorId.contains("RU")) return new NoiseSensor(sensorId, x, y, z, lat, lng);
        else if (sensorId.contains("ME")) return new WeatherSensor(sensorId, x, y, z, lat, lng);
        else throw new SensorException("Invalid Sensor Type!");
    }

    protected abstract Parameter identifySensorParameter(String sensorId);

    private boolean exists(Measurement measurement) {
        for (Measurement m : measurements) {
            if (measurement.equals(m)) return true;
        }

        return false;
    }

    private void grow() {
        sensorCapacity *= 2;
        Measurement[] copy = new Measurement[sensorCapacity];
        System.arraycopy(measurements, 0, copy, 0, measurements.length);
        measurements = copy;
    }

    protected boolean addElement(Measurement measurement) {
        if (exists(measurement)) return false;

        if (numMeasurements == sensorCapacity) {
            grow();
        }

        measurements[numMeasurements++] = measurement;
        return true;
    }

    @Override
    public String getId() {
        return sensorId.toString();
    }

    @Override
    public abstract SensorType getType();

    @Override
    public abstract Parameter getParameter();

    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return cartesianCoordinate;
    }

    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return geographicCoordinate;
    }

    @Override
    public abstract boolean addMeasurement(double value, LocalDateTime localDateTime, String unit) throws SensorException, MeasurementException;

    @Override
    public int getNumMeasurements() {
        return numMeasurements;
    }

    @Override
    public IMeasurement[] getMeasurements() {
        return measurements.clone();
    }

    @Override
    public String toString() {
        return "Sensor{\n" +
                " sensorId=" + sensorId +
                ",\n cartesianCoordinate=" + cartesianCoordinate +
                ",\n geographicCoordinate=" + geographicCoordinate +
                ",\n measurements=" + Arrays.toString(measurements) +
                ",\n numMeasurements=" + numMeasurements +
                "\n}";
    }
}
