package Monitoring.SensorFactory;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
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

    protected WeatherSensor(String sensorId,
                            ICartesianCoordinates cartesianCoordinates,
                            IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        super(sensorId, cartesianCoordinates, geographicCoordinates);

        setParameter(identifySensorParameter(sensorId));
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
}
