package Core.Coordinates;

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
public class GeographicCoordinates implements IGeographicCoordinates {

    /**
     * Geographic Coordinates
     */
    private final double latitude;
    private final double longitude;

    /**
     * Constructor for a Geographic Coordinate System
     */
    public GeographicCoordinates(double lat, double lng) {
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
        return "GeographicCoordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}