package Quickchart;

import edu.ma02.dashboards.Dashboard;
import edu.ma02.io.interfaces.IOStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/*S
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class QuickChart {

    public QuickChart() {

    }

    public void generateChart(ChartType chartType, IOStatistics[] statistics) throws IOException {
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

        String[] demo = new String[1];
        demo[0] = jsonObject.toJSONString();
        Dashboard.render(demo);
    }

}
