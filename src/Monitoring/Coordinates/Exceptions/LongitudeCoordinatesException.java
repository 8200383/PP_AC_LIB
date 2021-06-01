package Monitoring.Coordinates.Exceptions;

import edu.ma02.core.exceptions.SensorException;

import Monitoring.Coordinates.GeographicBounds;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class LongitudeCoordinatesException extends SensorException {

    public LongitudeCoordinatesException(String msg) {
        super(msg + " Valid ranges include " +
                GeographicBounds.MIN_LONGITUDE + " to " + GeographicBounds.MAX_LONGITUDE);
    }
}