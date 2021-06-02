package Monitoring.SensorFactory;

import Monitoring.Measurement;
import Monitoring.SensorFactory.Enums.ParametersUnits;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.enumerations.Unit;
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

    private final String sensorId;
    private final ICartesianCoordinates cartesianCoordinates;
    private final IGeographicCoordinates geographicCoordinates;

    private Parameter parameter;
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
    protected Sensor(String sensorId,
                     ICartesianCoordinates cartesianCoordinates,
                     IGeographicCoordinates geographicCoordinates
    ) {
        this.sensorId = sensorId;
        this.cartesianCoordinates = cartesianCoordinates;
        this.geographicCoordinates = geographicCoordinates;
        measurements = new Measurement[10];
    }

    public static Sensor SensorFactory(
            String sensorId,
            ICartesianCoordinates cartesianCoordinates,
            IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        if (!validateSensorId(sensorId)) {
            throw new SensorException("[Sensor] Sensor ID can't have more or less than 10 characters");
        }

        if (sensorId.contains("QA")) {
            return new AirSensor(sensorId, cartesianCoordinates, geographicCoordinates);
        } else if (sensorId.contains("RU")) {
            return new NoiseSensor(sensorId, cartesianCoordinates, geographicCoordinates);
        } else if (sensorId.contains("ME")) {
            return new WeatherSensor(sensorId, cartesianCoordinates, geographicCoordinates);
        } else {
            throw new SensorException("Invalid Sensor Type!");
        }
    }

    public static boolean validateSensorId(String sensorId) {
        return sensorId.length() == 10;
    }

    protected abstract Parameter identifySensorParameter(String sensorId);

    private boolean exists(Measurement measurement) {
        for (Measurement m : measurements) {
            if (measurement.equals(m)) return true;
        }

        return false;
    }

    private void grow() {
        Measurement[] copy = new Measurement[measurements.length * 2];
        System.arraycopy(measurements, 0, copy, 0, measurements.length);
        measurements = copy;
    }

    protected boolean addElement(Measurement measurement) {
        if (exists(measurement)) return false;

        // If array is full then grow array
        if (numMeasurements == measurements.length) {
            grow();
        }

        measurements[numMeasurements++] = measurement;
        return true;
    }

    public void setParameter(Parameter parameter) throws SensorException {
        if (parameter == null) throw new SensorException("Unrecognized Sensor Parameter");
        this.parameter = parameter;
    }

    @Override
    public abstract SensorType getType();

    @Override
    public String getId() {
        return sensorId;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public ICartesianCoordinates getCartesianCoordinates() {
        return cartesianCoordinates;
    }

    @Override
    public IGeographicCoordinates getGeographicCoordinates() {
        return geographicCoordinates;
    }

    @Override
    public boolean addMeasurement(double value, LocalDateTime localDateTime, String unitStr) throws SensorException, MeasurementException {
        Unit unit = Unit.getUnitFromString(unitStr);
        if (unit == null) {
            throw new SensorException("Invalid Unit of measure");
        }

        if (ParametersUnits.getUnitByParameter(parameter) != unit) {
            throw new SensorException("Unit parameters don't match with the sensor parameter");
        }

        return addElement(new Measurement(value, localDateTime, unit));
    }

    @Override
    public int getNumMeasurements() {
        return numMeasurements;
    }

    @Override
    public IMeasurement[] getMeasurements() {
        return measurements.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return sensorId.equals(sensor.sensorId);
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "type=" + getType() +
                ", sensorId='" + sensorId + '\'' +
                ", cartesianCoordinates=" + cartesianCoordinates +
                ", geographicCoordinates=" + geographicCoordinates +
                ", parameter=" + parameter +
                ", measurements=" + Arrays.toString(measurements) +
                ", numMeasurements=" + numMeasurements +
                '}';
    }
}
