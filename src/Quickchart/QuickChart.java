package Quickchart;

import edu.ma02.core.interfaces.IStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class QuickChart {

    /**
     * Generate a chart configuration object
     *
     * @param chartType  The {@link ChartType type} of the chart
     * @param statistics The {@link IStatistics data} to process
     * @return Returns the configuration of the chart
     */
    public static JSONObject generateChartConfiguration(ChartType chartType, IStatistics[] statistics) {
        if (chartType == null || statistics == null) {
            throw new IllegalArgumentException("This method doesn't support null parameters");
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", chartType.toString());

        JSONObject dataObject = new JSONObject();

        JSONArray labelsArray = new JSONArray();
        labelsArray.add("2021");
        dataObject.put("labels", labelsArray);

        JSONArray dataSetsArray = new JSONArray();
        JSONObject demoDataSet = new JSONObject();
        demoDataSet.put("label", "XP");
        JSONArray dataArray = new JSONArray();
        dataArray.add("25");
        demoDataSet.put("data", dataArray);
        dataSetsArray.add(demoDataSet);

        dataObject.put("datasets", dataSetsArray);
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

}
