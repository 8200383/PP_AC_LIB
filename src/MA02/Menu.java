package MA02;

import Core.City;
import Core.Statistics;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IMeasurement;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IOStatistics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class Menu {
    private static final Logger logger = Logger.getLogger(Menu.class.getName());
    private final BufferedReader bufferedReader;

    public Menu() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public void displayMenu(City city, IOStatistics ioStatistics) throws IOException {
        while (true) {
            showMenu();
            String line = bufferedReader.readLine();
            switch (line) {
                case "2":
                    showAll(city);
                    break;
                case "3":
                    for (String s : ioStatistics.getExceptions()) {
                        logger.warning(s);
                    }
                    break;
                case "5":
                    showAggregationOperatorOptions();
                    IStatistics[] measurementsByStation = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);
                    for (IStatistics iStatistics : measurementsByStation) {
                        if (iStatistics instanceof Statistics statistics) {
                            logger.info(statistics.getValue() + " " + statistics.getDescription());
                        }
                    }
                    break;
                case "9":
                    System.out.println(
                            "Number of Measurements Read: " + ioStatistics.getNumberOfReadMeasurements() +
                                    "\nNumber of new Measurements Read: " + ioStatistics.getNumberOfNewMeasurementsRead() +
                                    "\nNumber of Stations Read: " + ioStatistics.getNumberOfStationsRead() +
                                    "\nNumber of new Stations Read: " + ioStatistics.getNumberOfNewStationsRead() +
                                    "\nNumber of Sensors Read: " + ioStatistics.getNumberOfSensorsRead() +
                                    "\nNumber of new Sensors Read: " + ioStatistics.getNumberOfNewSensorsRead() +
                                    "\nNumber of exceptions: " + ioStatistics.getExceptions().length);
                    break;
                case "0":
                    System.exit(0);
            }
        }
    }

    private void showAll(City city) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (IStation station : city.getStations()) {
            System.out.println(station);
            stringBuilder.append(station);

            for (ISensor sensor : station.getSensors()) {
                System.out.println(sensor);
                stringBuilder.append(sensor);

                for (IMeasurement measurement : sensor.getMeasurements()) {
                    System.out.println(measurement);
                    stringBuilder.append(measurement);
                }

                System.out.println();
                stringBuilder.append("\n");
            }
        }

        FileOutputStream fos = new FileOutputStream("temp/dataExport.txt");
        fos.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    private void showMenu() {
        System.out.println("""
                ========Menu========
                2. View All
                3. View Exceptions
                5. View Measurements By Station
                9. Visualizar Relatório de Importação""");

        System.out.print("> ");
    }

    private void showAggregationOperatorOptions() {
        System.out.println("========Chose an Aggregation Operator========");
        int n = 0;
        for (AggregationOperator ao : AggregationOperator.values()) {
            System.out.println(n + ". " + ao.toString());
            n++;
        }
    }
}
