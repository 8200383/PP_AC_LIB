import Core.City;
import Quickchart.ChartType;
import Quickchart.QuickChart;
import SensorDataInput.JsonImporter;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.dashboards.Dashboard;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CityException, IOException {
        City city = new City("Felgueiras");
        JsonImporter importer = new JsonImporter();
        importer.importData(city, "resources/sensorData.json");

        IStatistics[] statistics0 = city.getMeasurementsBySensor("Entrecampos", AggregationOperator.AVG, Parameter.LAEQ);
        QuickChart quickChart = new QuickChart();
        quickChart.setOutputPath("./");
        quickChart.setChartConfiguration("ChartName", Parameter.LAEQ, statistics0, ChartType.BAR);
        String s = quickChart.export();
        Dashboard.render(new String[]{s});
    }
}