package Monitoring.Coordinates;

import Monitoring.Coordinates.Exceptions.LatitudeCoordinateException;
import Monitoring.Coordinates.Exceptions.LongitudeCoordinateException;
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
public class GeographicCoordinate implements IGeographicCoordinates {

    /**
     * Geographic Bounds
     */
    public static final double MIN_LATITUDE = -90.0;
    public static final double MAX_LATITUDE = 90.0;
    public static final double MIN_LONGITUDE = -180.0;
    public static final double MAX_LONGITUDE = 180.0;

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
    public GeographicCoordinate(double lat, double lng) throws LatitudeCoordinateException, LongitudeCoordinateException {

        if (lat < MIN_LONGITUDE || lat > MAX_LONGITUDE) {
            throw new LatitudeCoordinateException("Latitude Out of Bounds");
        }

        if (lng < MIN_LONGITUDE || lng > MAX_LONGITUDE) {
            throw new LongitudeCoordinateException("Longitude Out of Bounds");
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
