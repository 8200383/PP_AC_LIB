package Monitoring.SensorFactory;

import Monitoring.Measurement;
import Monitoring.SensorFactory.Exceptions.UnrecognizedSensorParameter;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;

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
class WeatherSensor extends Sensor {

    private final Parameter parameter;

    protected WeatherSensor(String sensorId,
                            ICartesianCoordinates cartesianCoordinates,
                            IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        super(sensorId, cartesianCoordinates, geographicCoordinates);


        if ((parameter = identifySensorParameter(sensorId)) == null) {
            throw new UnrecognizedSensorParameter();
        }
    }

    @Override
    protected Parameter identifySensorParameter(String sensorId) {

        for (Parameter param : SensorType.WEATHER.getParameters()) {
            if (sensorId.contains(param.toString())) return param;
        }

        return null;
    }

    @Override
    public SensorType getType() {
        return SensorType.WEATHER;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public boolean addMeasurement(double value, LocalDateTime localDateTime, String unit) throws SensorException, MeasurementException {
        return super.addElement(new Measurement(value, localDateTime, unit, parameter));
    }
}
