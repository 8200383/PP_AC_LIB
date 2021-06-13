package Quickchart;

import edu.ma02.core.interfaces.IStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
        jsonObject.put("type", chartType);

        JSONObject dataObject = new JSONObject();

        JSONArray labelsArray = new JSONArray();
        if (checkSensorChart(statistics[0])){
            for (String stationId : getSensorIds(statistics)){
                labelsArray.add(stationId);
            }
        }
        else {
            for (String stationName : getStationNames(statistics)){
                labelsArray.add(stationName);
            }
        }
        dataObject.put("labels", labelsArray);

        JSONArray datasetsArray = new JSONArray();
        JSONObject datasetsObject = new JSONObject();
        datasetsObject.put("label", "Values");
        JSONArray dataArray = new JSONArray();
        for (IStatistics statistic : statistics) {
            dataArray.add(statistic.getValue());
        }
        dataObject.put("data", dataArray);
        datasetsArray.add(datasetsObject);

        dataObject.put("datasets", datasetsArray);
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
            //sensorIds = addStringToArray(sensorIds, description.containsKey("sensorId") ? description.get("sensorId").toString() : "");
            sensorIds[count] = description.containsKey("sensorId") ? description.get("sensorId").toString() : "";
            count++;
        }

        return sensorIds.clone();
    }

    private static String[] getStationNames(IStatistics[] statistics){
        int count = 0;
        String[] stationNames = new String[statistics.length];

        for (IStatistics statistic : statistics){
            JSONObject description = (JSONObject) JSONValue.parse(statistic.getDescription());
            //stationNames = addStringToArray(stationNames, description.containsKey("stationName") ? description.get("stationName").toString() : "");
            stationNames[count] = description.containsKey("stationName") ? description.get("stationName").toString() : "";
            count++;
        }

        return stationNames.clone();
    }

    /*private static String[] addStringToArray(String[] srcArray, String name){
        String[] destArray = new String[srcArray.length + 1];
        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);
        destArray[destArray.length - 1] = name;
        return destArray;
    }*/

    private static boolean checkSensorChart(IStatistics statistics){
        JSONObject description = (JSONObject) JSONValue.parse(statistics.getDescription());
        if (description.containsKey("sensorId")){ return true; }
        return false;
    }
}