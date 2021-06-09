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

    private final double latitude;
    private final double longitude;

    /**
     * Constructor for {@link GeographicCoordinates}
     *
     * @param lat The value of the {@link Double latitude}
     * @param lng The value of the {@link Double longitude}
     */
    public GeographicCoordinates(double lat, double lng) {
        latitude = lat;
        longitude = lng;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GeographicCoordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
