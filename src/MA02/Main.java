package MA02;

import Core.City;
import Storage.IO;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.io.interfaces.IOStatistics;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws CityException, IOException {

        // TODO Test all Core Package

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

        City lisbon = new City("Lisbon");

        IO IO = new IO();
        IOStatistics ioStatistics = IO.importData(lisbon, "resources/sensorData.json");
        System.out.println(lisbon.getStations()[0].getSensors()[0].getId());

        for (String exception : ioStatistics.getExceptions()) {
            System.out.println(exception);
        }
    }
}
