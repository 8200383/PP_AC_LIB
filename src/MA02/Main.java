package MA02;

import Core.City;
import Quickchart.ChartType;
import Quickchart.QuickChart;
import SensorDataInput.JsonImporter;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IOStatistics;

import java.io.IOException;
import java.time.LocalDateTime;
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

        QuickChart qcExporter = new QuickChart();
        qcExporter.setOutputPath("resources/");

        JsonImporter jsonInputOutput = new JsonImporter();
        // TODO ler caminho na consola
        IOStatistics ioStatistics = jsonInputOutput.importData(city, "resources/sensorData.json");

        IStatistics[] m0 = city.getMeasurementsByStation(AggregationOperator.AVG, Parameter.LAEQ);
        qcExporter.setChartConfiguration(
                "Average of Measurements By Station",
                Parameter.LAEQ,
                m0,
                ChartType.BAR
        );
        String export0 = qcExporter.export();

        IStatistics[] m1 = city.getMeasurementsByStation(AggregationOperator.MIN, Parameter.LAEQ);
        qcExporter.setChartConfiguration(
                "Minimum of Measurements By Station",
                Parameter.LAEQ,
                m1,
                ChartType.BAR
        );
        String export1 = qcExporter.export();

        IStatistics[] m2 = city.getMeasurementsByStation(AggregationOperator.MAX, Parameter.LAEQ);
        qcExporter.setChartConfiguration(
                "Maximum of Measurements By Station",
                Parameter.LAEQ,
                m2,
                ChartType.BAR
        );
        String export2 = qcExporter.export();

        IStatistics[] m3 = city.getMeasurementsByStation(AggregationOperator.COUNT, Parameter.LAEQ);
        qcExporter.setChartConfiguration(
                "Count of Measurements By Station",
                Parameter.LAEQ,
                m3,
                ChartType.BAR
        );
        String export2_0 = qcExporter.export();

        IStatistics[] s0 = city.getMeasurementsBySensor("Calçada da Ajuda", AggregationOperator.AVG, Parameter.NO2);
        qcExporter.setChartConfiguration(
                "Calçada da Ajuda - Average of Measurements By Sensor",
                Parameter.NO2,
                s0,
                ChartType.BAR
        );
        String export3 = qcExporter.export();

        IStatistics[] s1 = city.getMeasurementsBySensor("Calçada da Ajuda", AggregationOperator.MIN, Parameter.NO2);
        qcExporter.setChartConfiguration(
                "Calçada da Ajuda - Minimum of Measurements By Sensor",
                Parameter.NO2,
                s1,
                ChartType.BAR
        );
        String export4 = qcExporter.export();

        IStatistics[] s2 = city.getMeasurementsBySensor("Calçada da Ajuda", AggregationOperator.MAX, Parameter.NO2);
        qcExporter.setChartConfiguration(
                "Calçada da Ajuda - Maximum of Measurements By Sensor",
                Parameter.NO2,
                s2,
                ChartType.BAR
        );
        String export5 = qcExporter.export();

        IStatistics[] s3 = city.getMeasurementsBySensor("Calçada da Ajuda", AggregationOperator.COUNT, Parameter.NO2);
        qcExporter.setChartConfiguration(
                "Calçada da Ajuda - Count of Measurements By Sensor",
                Parameter.NO2,
                s3,
                ChartType.BAR
        );
        String export6 = qcExporter.export();

        IStatistics[] d0 = city.getMeasurementsBySensor(
                "Calçada da Ajuda",
                AggregationOperator.AVG,
                Parameter.TEMP,
                LocalDateTime.of(2021, 5, 1, 0, 0, 0),
                LocalDateTime.now()
        );
        qcExporter.setChartConfiguration(
                "Calçada da Ajuda - Average of Measurements By Sensor Between Dates",
                Parameter.TEMP,
                d0,
                ChartType.BAR
        );
        String export7 = qcExporter.export();

        Dashboard.render(new String[]{export0, export1, export2, export2_0, export3, export4, export5, export6, export7});
    }
}
