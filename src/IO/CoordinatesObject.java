package IO;

import Core.Coordinates.CartesianCoordinates;
import Core.Coordinates.GeographicCoordinates;
import edu.ma02.core.interfaces.ICartesianCoordinates;
import edu.ma02.core.interfaces.IGeographicCoordinates;
import org.json.simple.JSONObject;

import java.io.IOException;

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

    /**
     * Constructor to parse the coordinates object
     *
     * @param coordinates The {@link JSONObject coordinates}
     * @throws IOException Throws this exception if the object is invalid
     */
    public CoordinatesObject(JSONObject coordinates) throws IOException {
        boolean coordinatesOk = coordinates.containsKey("lat") &&
                coordinates.containsKey("lng") &&
                coordinates.containsKey("x") &&
                coordinates.containsKey("y") &&
                coordinates.containsKey("z");

        if (!coordinatesOk) {
            throw new IOException("Invalid Coordinates JsonObject");
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

    /**
     * Get the parsed {@link GeographicCoordinates}
     *
     * @return Return an instance of {@link IGeographicCoordinates}
     */
    public IGeographicCoordinates getGeographicCoordinates() {
        return geographicCoordinates;
    }

    /**
     * Get the parsed {@link CartesianCoordinates}
     *
     * @return Return an instance of {@link ICartesianCoordinates}
     */
    public ICartesianCoordinates getCartesianCoordinates() {
        return cartesianCoordinates;
    }
}
