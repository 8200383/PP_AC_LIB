package MA02;

import Core.*;
import IO.JsonImporter;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IOStatistics;

import java.io.IOException;
import java.util.Arrays;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class Main {

    public static void main(String[] args) throws CityException, IOException {

        City city = new City("Lisbon");

        JsonImporter jsonInputOutput = new JsonImporter();
        IOStatistics ioStatistics = jsonInputOutput.importData(city, "resources/sensorData.json");

        //QuickChart chart = new QuickChart();
        //chart.generateChart(ChartType.BAR, null);

        Menu menu = new Menu();
        menu.displayMenu(city, ioStatistics);

        //IStatistics[] measurementsByStation = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);
        //Arrays.stream(measurementsByStation).filter(m -> m != null).forEach(m -> System.out.println(m.getDescription()));

        //IStatistics[] measurementsBySensor = city.getMeasurementsBySensor("Jardim da Estrela", AggregationOperator.AVG, Parameter.LAEQ);
        //Arrays.stream(measurementsBySensor).filter(m -> m != null).forEach(m -> System.out.println(m.getDescription()));
    }


}
