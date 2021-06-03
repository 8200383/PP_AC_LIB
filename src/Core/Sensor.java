package Core;

import Core.Enums.ParametersUnits;
import Utils.PrimitiveArrayUtils;
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
public class Sensor implements ISensor {

    private final String sensorId;
    private final ICartesianCoordinates cartesianCoordinates;
    private final IGeographicCoordinates geographicCoordinates;
    private final SensorType sensorType;
    private final Parameter parameter;

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
    public Sensor(String sensorId,
                  ICartesianCoordinates cartesianCoordinates,
                  IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        if (!isSensorIdLengthValid(sensorId)) {
            throw new SensorException("SensorId can't have more or less then 10 characters");
        }

        SensorType sensorType = identifySensorType(sensorId);
        Parameter parameter = identifySensorParameter(sensorId);
        if (sensorType == null || parameter == null) {
            throw new SensorException("Invalid SensorId");
        }

        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.parameter = parameter;
        this.cartesianCoordinates = cartesianCoordinates;
        this.geographicCoordinates = geographicCoordinates;

        measurements = new Measurement[10];
    }

    public static boolean isSensorIdLengthValid(String sensorId) {
        return sensorId.length() == 10;
    }

    private SensorType identifySensorType(String sensorId) {
        if (sensorId.startsWith("QA")) return SensorType.AIR;
        else if (sensorId.startsWith("RU")) return SensorType.NOISE;
        else if (sensorId.startsWith("ME")) return SensorType.WEATHER;

        return null;
    }

    private Parameter identifySensorParameter(String sensorId) {
        for (Parameter param : sensorType.getParameters()) {
            if (sensorId.contains(param.toString())) return param;
        }

        // TODO Perguntar ao professor se podemos validar o PM25
        return sensorId.contains("PM25") ? Parameter.PM2_5 : null;
    }

    private boolean exists(Measurement measurement) {
        for (Measurement m : measurements) {
            if (measurement.equals(m)) return true;
        }

        return false;
    }

    private boolean addElement(Measurement measurement) {
        if (exists(measurement)) return false;

        // If array is full then grow array
        if (numMeasurements == measurements.length) {
            measurements = (Measurement[]) PrimitiveArrayUtils.grow(measurements);
        }

        measurements[numMeasurements++] = measurement;
        return true;
    }

    @Override
    public SensorType getType() {
        return sensorType;
    }

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
