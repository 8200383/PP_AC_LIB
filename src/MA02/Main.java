package MA02;

import Core.*;
import IO.JsonExporter;
import IO.JsonImporter;
import Quickchart.ChartType;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IOStatistics;
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
public class Main {

    public static void main(String[] args) throws CityException, IOException {

        City city = new City("Lisbon");

        JsonImporter jsonInputOutput = new JsonImporter();
        IOStatistics ioStatistics = jsonInputOutput.importData(city, "resources/sensorData.json");

        //QuickChart chart = new QuickChart();
        //chart.generateChart(ChartType.BAR, null);

        //Menu menu = new Menu();
        //menu.displayMenu(city, ioStatistics);

        //IStatistics[] measurementsByStation = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);
        //Arrays.stream(measurementsByStation).filter(m -> m != null).forEach(m -> System.out.println(m.getDescription()));

        /*IStatistics[] measurementsBySensor = city.getMeasurementsBySensor("Jardim da Estrela", AggregationOperator.AVG, Parameter.LAEQ);
        System.out.println(measurementsBySensor.length);
        Arrays.stream(measurementsBySensor).filter(Objects::nonNull).forEach(m -> System.out.println(m.getValue() + " " + m.getDescription()));
        System.out.println("------------");
        IStatistics[] measurementsBySensor1 = city.getMeasurementsBySensor("Jardim da Estrela", AggregationOperator.MAX, Parameter.LAEQ);
        System.out.println(measurementsBySensor1.length);
        Arrays.stream(measurementsBySensor1).filter(Objects::nonNull).forEach(m -> System.out.println(m.getValue() + " " + m.getDescription()));
        System.out.println("------------");
        IStatistics[] measurementsBySensor2 = city.getMeasurementsBySensor("Jardim da Estrela", AggregationOperator.MIN, Parameter.LAEQ);
        System.out.println(measurementsBySensor2.length);
        Arrays.stream(measurementsBySensor2).filter(Objects::nonNull).forEach(m -> System.out.println(m.getValue() + " " + m.getDescription()));
        System.out.println("------------");
        IStatistics[] measurementsBySensor3 = city.getMeasurementsBySensor("Jardim da Estrela", AggregationOperator.COUNT, Parameter.LAEQ);
        System.out.println(measurementsBySensor3.length);
        Arrays.stream(measurementsBySensor3).filter(Objects::nonNull).forEach(m -> System.out.println(m.getValue() + " " + m.getDescription()));*/

        IStatistics[] measurementsTest = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);

        JsonExporter exporter = new JsonExporter();
        exporter.setStatistics(measurementsTest);
        exporter.setOutputFileName("resources/index.json");
        exporter.setChartType(ChartType.BAR);
        exporter.setParameter(Parameter.LAEQ);
        String json = exporter.export();

        Dashboard.render(new String[]{json});
    }
}