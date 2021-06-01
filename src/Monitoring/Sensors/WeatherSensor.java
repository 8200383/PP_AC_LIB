package Monitoring.Sensors;

import Monitoring.Sensors.Enums.ParametersUnits;
import Monitoring.Sensors.Exceptions.UnrecognizedSensorParameter;
import Monitoring.Sensors.Exceptions.UnrecognizedSensorUnit;
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
class WeatherSensor extends Sensor {

    private Parameter parameter;
    private Unit defaultUnit;

    protected WeatherSensor(String sensorId,
                            double x, double y, double z,
                            double lat, double lng
    ) throws SensorException {
        super(sensorId, x, y, z, lat, lng);
        identifySensorParameter(sensorId);
    }

    @Override
    protected void identifySensorParameter(String sensorId) throws SensorException {

        for (Parameter param : SensorType.WEATHER.getParameters()) {
            if (sensorId.contains(param.toString())) {
                parameter = param;
                break;
            }
        }

        /* Make him stops if the loop doesn't stop it self */
        if (parameter == null) throw new UnrecognizedSensorParameter();

        /* Get the Unit by the parameter otherwise throw an exception */
        if ((defaultUnit = ParametersUnits.getUnitByParameter(parameter)) == null) {
            throw new UnrecognizedSensorUnit();
        }
    }

    @Override
    public SensorType getType() {
        return SensorType.WEATHER;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }
}
