package Core.SensorFactory;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.SensorType;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
class AirSensor extends Sensor {

    protected AirSensor(String sensorId,
                        ICartesianCoordinates cartesianCoordinates,
                        IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        super(sensorId, cartesianCoordinates, geographicCoordinates);

        setParameter(identifySensorParameter(sensorId));
    }

    @Override
    public Parameter identifySensorParameter(String sensorId) {
        for (Parameter param : SensorType.AIR.getParameters()) {
            if (param == Parameter.PM2_5) continue;

            if (sensorId.contains(param.toString())) return param;
        }

        // TODO: Perguntar ao prof
        /* Special treatment for PM2_5*/
        return sensorId.contains("PM25") ? Parameter.PM2_5 : null;
    }

    @Override
    public SensorType getType() {
        return SensorType.AIR;
    }
}
