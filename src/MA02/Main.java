package MA02;

import Core.City;
import Core.Measurement;
import Core.Sensor;
import Core.Station;
import Storage.IO;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.io.interfaces.IOStatistics;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CityException, IOException {

        City lisbon = new City("Lisbon");

        IO jsonInputOutput = new IO();
        IOStatistics ioStatistics = jsonInputOutput.importData(lisbon, "resources/sensorData.json");

        for (String exception : ioStatistics.getExceptions()) {
            System.out.println(exception);
        }

        for (IStation iStation : lisbon.getStations()) {
            if (iStation instanceof Station station) {
                System.out.println(station);

                for (ISensor iSensor : station.getSensors()) {
                    if (iSensor instanceof Sensor sensor) {
                        System.out.println(sensor);

                        for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                            if (iMeasurement instanceof Measurement measurement) {
                                System.out.println(measurement);
                            }
                        }
                    }
                }

                System.out.println();
            }
        }
    }
}
