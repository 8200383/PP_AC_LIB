package Quickchart;

import edu.ma02.core.interfaces.IStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    public static JSONObject generateChartConfiguration(ChartType chartType, IStatistics[] statistics, boolean isSensorChart) throws ParseException {
        if (chartType == null || statistics == null) {
            throw new IllegalArgumentException("This method doesn't support null parameters");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", chartType.toString());

        JSONObject dataObject = new JSONObject();

        dataObject.put("labels", appendLabelsArray(statistics, isSensorChart));

        dataObject.put("datasets", appendDatasetsArray("some label", statistics));
        jsonObject.put("data", dataObject);


        jsonObject.put("options", appendChartOptions("Try 1"));
        return jsonObject;
    }

    private static JSONObject appendChartOptions(String title) {
        JSONObject options = new JSONObject();
        options.put("display", "true");
        options.put("text", title);
        return options;
    }

    private static JSONArray appendDatasetsArray(String label, IStatistics[] statistics) {
        JSONArray datasetsArray = new JSONArray();

        JSONObject datasetsObject = new JSONObject();
        datasetsObject.put("label", label);
        JSONArray dataArray = new JSONArray();

        for (IStatistics s : statistics) {
            dataArray.add(s.getValue());
        }

        datasetsObject.put("data", dataArray);
        datasetsArray.add(datasetsObject);
        return datasetsArray;
    }

    private static JSONArray appendLabelsArray(IStatistics[] statistics, boolean isSensorChart) {
        JSONArray labelsArray = new JSONArray();

        if (isSensorChart) {
            for (String stationId : getSensorIds(statistics)) {
                labelsArray.add(stationId);
            }
        } else {
            for (String stationName : getStationNames(statistics)) {
                labelsArray.add(stationName);
            }
        }

        return labelsArray;
    }

    private static String[] getSensorIds(IStatistics[] statistics) {
        int count = 0;
        String[] sensorIds = new String[statistics.length];

        for (IStatistics statistic : statistics) {
            JSONObject description = (JSONObject) JSONValue.parse(statistic.getDescription());
            sensorIds[count] = description.containsKey("sensorId") ? description.get("sensorId").toString() : "";
            count++;
        }

        return sensorIds.clone();
    }

    private static String[] getStationNames(IStatistics[] statistics) {
        int count = 0;
        String[] stationNames = new String[statistics.length];

        for (IStatistics statistic : statistics) {
            JSONObject description = (JSONObject) JSONValue.parse(statistic.getDescription());
            stationNames[count] = description.containsKey("stationName") ? description.get("stationName").toString() : "";
            count++;
        }

        return stationNames.clone();
    }
}