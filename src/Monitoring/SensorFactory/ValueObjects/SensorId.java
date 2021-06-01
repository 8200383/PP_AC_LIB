package Monitoring.SensorFactory.ValueObjects;

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
public class SensorId {
    private final String sensorId;

    public SensorId(String id) throws SensorException {
        if (id.length() != 10) throw new SensorException("Invalid Sensor Id Length");

        sensorId = id;
    }

    @Override
    public String toString() {
        return sensorId;
    }
}
