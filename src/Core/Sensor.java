package Core;

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
     * Constructor for {@link Sensor}
     *
     * @param sensorId              The Sensor Id
     * @param cartesianCoordinates  An interface of {@link ICartesianCoordinates}
     * @param geographicCoordinates An interface of {@link IGeographicCoordinates}
     * @apiNote O código do sensor tem apenas 10 caracteres
     * Primeiras letras representam o tipo de sensor (Ex: QA - Qualidade do Ar)
     * As seguintes representam o parâmetro associado ao sensor (Ex: NO2 - Sigla do Dióxido de Azoto)
     * Um sensor apenas tem um parâmetro!
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
        if (sensorType == null) {
            throw new SensorException("Sensor Type couldn't be identified");
        }

        Parameter parameter = identifySensorParameter(sensorType, sensorId);
        if (parameter == null) {
            throw new SensorException("Sensor Parameter couldn't be identified");
        }

        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.parameter = parameter;
        this.cartesianCoordinates = cartesianCoordinates;
        this.geographicCoordinates = geographicCoordinates;

        measurements = new Measurement[10];
    }

    /**
     * Validate if {@link String sensorId} has a valid length
     *
     * @param sensorId The {@link String sensorId}
     * @return Returns true if the @link String sensorId} has a valid length
     * @apiNote Used in {@link City}, {@link Station} and {@link Sensor}
     */
    public static boolean isSensorIdLengthValid(String sensorId) {
        return sensorId.length() == 10;
    }

    /**
     * Identify the {@link SensorType}
     *
     * @param sensorId The {@link String sensorId} to validate
     * @return Returns a {@link SensorType} if the {@link String sensorId} is valid
     */
    private SensorType identifySensorType(String sensorId) {
        if (sensorId.startsWith("QA")) return SensorType.AIR;
        else if (sensorId.startsWith("RU")) return SensorType.NOISE;
        else if (sensorId.startsWith("ME")) return SensorType.WEATHER;

        return null;
    }

    /**
     * Identify the sensor {@link Parameter} from the {@link String sensorId}
     *
     * @param sensorId   The {@link String sensorId} to validate
     * @param sensorType The previous identified {@link SensorType sensorType}
     * @return Returns a {@link Parameter} if the parameter is successful identified or null if no parameter was found
     * @implNote Call this method after call {@link #identifySensorType(String sensorId)}  }
     */
    // TODO Um Sensor pode ter mais do que um parameter
    private Parameter identifySensorParameter(SensorType sensorType, String sensorId) {
        for (Parameter param : sensorType.getParameters()) {
            if (sensorId.contains(param.toString())) return param;
        }

        return sensorId.contains("PM25") ? Parameter.PM2_5 : null;
    }

    /**
     * Checks if a {@link Measurement} already exists
     *
     * @param measurement The {@link Measurement measurement} to be validated
     * @return true if a {@link Measurement} is found or false if nothing is found
     */
    private boolean exists(Measurement measurement) {
        for (IMeasurement m : getMeasurements()) {
            if (measurement.equals(m)) return true;
        }

        return false;
    }

    /**
     * Adds a new element to an array of {@link #measurements}
     *
     * @param measurement The {@link Measurement measurement} to be added
     * @return true if the {@link Measurement} was inserted in the collection or false if the {@link Measurement} already exists
     */
    private boolean addElement(Measurement measurement) {
        if (exists(measurement)) return false;

        // If array is full then grow array
        if (numMeasurements == measurements.length) {
            grow();
        }

        measurements[numMeasurements++] = measurement;
        return true;
    }

    /**
     * Grow an array of {@link Measurement}
     */
    private void grow() {
        Measurement[] copy = new Measurement[measurements.length * 2];
        System.arraycopy(measurements, 0, copy, 0, numMeasurements);
        measurements = copy;
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
    public boolean addMeasurement(double value, LocalDateTime localDateTime, String u) throws SensorException, MeasurementException {
        // TODO Considerar Mbar para lower case
        if (parameter.getUnit() != Unit.getUnitFromString(u)) {
            throw new SensorException("Invalid Unit of measure for this sensor");
        }

        return addElement(new Measurement(value, localDateTime));
    }

    @Override
    public int getNumMeasurements() {
        return numMeasurements;
    }

    /**
     * Return a copy of the existing {@link #measurements}
     *
     * @return A trimmed clone of {@link #measurements}
     * @apiNote Use this function to prevent null checks
     */
    @Override
    public IMeasurement[] getMeasurements() {
        if (numMeasurements == 0) return new IMeasurement[]{};

        Measurement[] copy = new Measurement[numMeasurements];
        System.arraycopy(measurements, 0, copy, 0, numMeasurements);
        return copy.clone();
    }

    /**
     * Compare {@link Sensor} by {@link #sensorId}
     *
     * @param o The {@link Object object} to compare
     * @return Return true if {@link #sensorId} is equals in both classes
     */
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
                "type=" + sensorType +
                ", parameter=" + parameter +
                ", sensorId='" + sensorId + '\'' +
                ", cartesianCoordinates=" + cartesianCoordinates +
                ", geographicCoordinates=" + geographicCoordinates +
                ", numMeasurements=" + numMeasurements +
                '}';
    }
}
