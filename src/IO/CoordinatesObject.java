package IO;

import Core.Coordinates.CartesianCoordinates;
import Core.Coordinates.GeographicCoordinates;
import IO.Exceptions.KeyNotFound;
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
public class CoordinatesObject {
    private final GeographicCoordinates geographicCoordinates;
    private final CartesianCoordinates cartesianCoordinates;

    public CoordinatesObject(JSONObject coordinates) throws KeyNotFound {
        boolean coordinatesOk = coordinates.containsKey("lat") &&
                coordinates.containsKey("lng") &&
                coordinates.containsKey("x") &&
                coordinates.containsKey("y") &&
                coordinates.containsKey("z");

        if (!coordinatesOk) {
            throw new KeyNotFound("Invalid Coordinates JsonObject");
        }

        geographicCoordinates = new GeographicCoordinates(
                Double.parseDouble(coordinates.get("lat").toString()),
                Double.parseDouble(coordinates.get("lng").toString())
        );

        cartesianCoordinates = new CartesianCoordinates(
                Double.parseDouble(coordinates.get("x").toString()),
                Double.parseDouble(coordinates.get("y").toString()),
                Double.parseDouble(coordinates.get("z").toString())
        );
    }


    public GeographicCoordinates getGeographicCoordinates() {
        return geographicCoordinates;
    }

    public CartesianCoordinates getCartesianCoordinates() {
        return cartesianCoordinates;
    }
}
