package Monitoring.Sensors;

import Monitoring.Sensors.Exceptions.UnrecognizedSensorParameter;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.SensorException;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
class NoiseSensor extends Sensor {

    private Parameter parameter;
    private Unit defaultUnit;

    protected NoiseSensor(String sensorId,
                          double x, double y, double z,
                          double lat, double lng
    ) throws SensorException {
        super(sensorId, x, y, z, lat, lng);
        identifySensorParameter(sensorId);
    }

    @Override
    protected void identifySensorParameter(String sensorId) throws UnrecognizedSensorParameter {

        /* Single Exceptional Case for Noise Sensors*/
        if (!sensorId.contains("LAEQ")) throw new UnrecognizedSensorParameter();

        parameter = Parameter.LAEQ;
        defaultUnit = Unit.DB;
    }

    @Override
    public SensorType getType() {
        return SensorType.NOISE;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }
}
