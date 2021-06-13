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

        JSONObject optionsObject = new JSONObject();
        optionsObject.put("display", "true");
        optionsObject.put("text", "Title");

        jsonObject.put("options", optionsObject);
        return jsonObject;
    }

    private static String[] getSensorIds(IStatistics[] statistics){
        int count = 0;
        String[] sensorIds = new String[statistics.length];

        for (IStatistics statistic : statistics){
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