package Monitoring.SensorFactory;

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
class NoiseSensor extends Sensor {

    protected NoiseSensor(String sensorId,
                          ICartesianCoordinates cartesianCoordinates,
                          IGeographicCoordinates geographicCoordinates
    ) throws SensorException {
        super(sensorId, cartesianCoordinates, geographicCoordinates);

        setParameter(identifySensorParameter(sensorId));
    }

    @Override
    protected Parameter identifySensorParameter(String sensorId) {
        /* Single Exceptional Case for Noise Sensors*/
        return sensorId.contains("LAEQ") ? Parameter.LAEQ : null;
    }

    @Override
    public SensorType getType() {
        return SensorType.NOISE;
    }
}
