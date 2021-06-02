package MA02;

import Monitoring.City;
import Monitoring.Coordinates.CartesianCoordinates;
import Monitoring.Coordinates.GeographicCoordinates;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        // Create a Logger
        Logger logger = Logger.getLogger(Main.class.getName());

       /*  Sensor sensor = Sensor.SensorFactory(
                "ME00PA0001",
                -92781,
                -106663,
                0,
                38.70263097,
                -9.199692206
        ); */

        City felgueiras = new City("1", "Felgueiras");

        try {
            felgueiras.addStation("Felgueiras Weather Station");
            logger.log(Level.INFO, "Station Created");
        } catch (CityException ce) {
            logger.log(Level.WARNING, ce.toString());
        }

        try {
            felgueiras.addSensor("Felgueiras Weather Station", "QA0NO20001",
                    new CartesianCoordinates(-89255.1331, -105323.8973, 0),
                    new GeographicCoordinates(38.71505459, -9.159338426)
            );
        } catch (CityException | StationException | SensorException e) {
            logger.log(Level.WARNING, e.toString());
        }

        System.out.println(Arrays.toString(felgueiras.getStations()));
    }
}
