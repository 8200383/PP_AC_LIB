package Storage;

import Core.Coordinates.CartesianCoordinates;
import Core.Coordinates.GeographicCoordinates;
import Storage.Exceptions.KeyNotFound;
import org.json.simple.JSONObject;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class Coordinates {
    private final GeographicCoordinates geographicCoordinates;
    private final CartesianCoordinates cartesianCoordinates;

    public Coordinates(JSONObject coordinates) throws KeyNotFound {
        if (!containsGeographicCoordinates(coordinates)) {
            throw new KeyNotFound("Geographic Coordinates not found");
        }

        geographicCoordinates = new GeographicCoordinates(
                Double.parseDouble(coordinates.get("lat").toString()),
                Double.parseDouble(coordinates.get("lng").toString())
        );

        if (!containsCartesianCoordinates(coordinates)) {
            throw new KeyNotFound("Cartesian Coordinates not found");
        }

        cartesianCoordinates = new CartesianCoordinates(
                Double.parseDouble(coordinates.get("x").toString()),
                Double.parseDouble(coordinates.get("y").toString()),
                Double.parseDouble(coordinates.get("z").toString())
        );
    }

    private boolean containsGeographicCoordinates(JSONObject coordinates) {
        return coordinates.containsKey("lat") && coordinates.containsKey("lng");
    }

    private boolean containsCartesianCoordinates(JSONObject coordinates) {
        return coordinates.containsKey("x") && coordinates.containsKey("y") && coordinates.containsKey("z");
    }

    public GeographicCoordinates getGeographicCoordinates() {
        return geographicCoordinates;
    }

    public CartesianCoordinates getCartesianCoordinates() {
        return cartesianCoordinates;
    }
}
