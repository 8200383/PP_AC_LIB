package Monitoring.Coordinates;

import Monitoring.Coordinates.Exceptions.LatitudeCoordinatesException;
import Monitoring.Coordinates.Exceptions.LongitudeCoordinatesException;
import edu.ma02.core.interfaces.IGeographicCoordinates;

import static Monitoring.Coordinates.GeographicBounds.MAX_LONGITUDE;
import static Monitoring.Coordinates.GeographicBounds.MIN_LONGITUDE;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class GeographicCoordinates implements IGeographicCoordinates {

    /**
     * Geographic Coordinates
     */
    private final double latitude;
    private final double longitude;

    /**
     * Constructor for a Geographic Coordinate System
     *
     * @param lat Range from -90 to 90
     * @param lng Range from -180 to 180
     * @apiNote The numbers are in decimal degrees format.
     * @link https://docs.mapbox.com/help/glossary/lat-lon/
     */
    public GeographicCoordinates(double lat, double lng) throws LatitudeCoordinatesException, LongitudeCoordinatesException {

        if (lat < MIN_LONGITUDE || lat > MAX_LONGITUDE) {
            throw new LatitudeCoordinatesException("Latitude Out of Bounds");
        }

        if (lng < MIN_LONGITUDE || lng > MAX_LONGITUDE) {
            throw new LongitudeCoordinatesException("Longitude Out of Bounds");
        }

        latitude = lat;
        longitude = lng;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "GeographicCoordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
