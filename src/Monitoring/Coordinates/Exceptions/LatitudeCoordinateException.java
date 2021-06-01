package Monitoring.Coordinates.Exceptions;

import edu.ma02.core.exceptions.SensorException;

import static Monitoring.Coordinates.ValueObjects.GeographicCoordinate.MAX_LATITUDE;
import static Monitoring.Coordinates.ValueObjects.GeographicCoordinate.MIN_LATITUDE;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class LatitudeCoordinateException extends SensorException {

    public LatitudeCoordinateException(String msg) {
        super(msg + " Valid ranges include " + MIN_LATITUDE + " to " + MAX_LATITUDE);
    }
}
