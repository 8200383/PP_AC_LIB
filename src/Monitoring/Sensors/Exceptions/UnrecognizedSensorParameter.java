package Monitoring.Sensors.Exceptions;

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
public class UnrecognizedSensorParameter extends SensorException {

    public UnrecognizedSensorParameter() {
        super("Unrecognized Sensor Parameter");
    }
}
