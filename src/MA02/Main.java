package MA02;

import Core.City;
import Quickchart.ChartType;
import Quickchart.QuickChart;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.dashboards.Dashboard;

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
        //JsonImporter jsonInputOutput = new JsonImporter();
        // TODO ler caminho na consola
        //IOStatistics ioStatistics = jsonInputOutput.importData(city, "resources/sensorData.json");

        IStatistics[] measurementsTest = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);

        QuickChart qcExporter = new QuickChart();
        qcExporter.setStatistics(measurementsTest);
        qcExporter.setChartName("Measurements By Station");
        qcExporter.setOutputPath("resources/");
        qcExporter.setChartType(ChartType.BAR);
        qcExporter.setParameter(Parameter.LAEQ);
        String json = qcExporter.export();

        Dashboard.render(new String[]{json});
    }
}
