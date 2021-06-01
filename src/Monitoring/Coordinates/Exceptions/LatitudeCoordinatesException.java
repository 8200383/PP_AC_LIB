package Monitoring.Coordinates.Exceptions;

import Monitoring.Coordinates.GeographicBounds;
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
public class LatitudeCoordinatesException extends SensorException {

    public LatitudeCoordinatesException(String msg) {
        super(msg + " Valid ranges include " +
                GeographicBounds.MIN_LATITUDE + " to " + GeographicBounds.MAX_LATITUDE);
    }
}
