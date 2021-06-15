package Quickchart;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileWriter;
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
public class QuickChart implements IExporter {

    private String chartName;
    private IStatistics[] statistics;
    private Parameter parameter;
    private String outputPath;
    private ChartType chartType;

    public QuickChart() {
    }

    /**
     * Set an array of {@link IStatistics}
     *
     * @param statistics The array of {@link IStatistics}
     * @implNote Call this function before {@link #export()}
     */
    public void setStatistics(IStatistics[] statistics) {
        this.statistics = statistics;
    }

    /**
     * Set the {@link String path} for exportation
     *
     * @param path The {@link String path} of the file
     * @implNote Call this function before {@link #export()}
     */
    public void setOutputPath(String path) {
        this.outputPath = path;
    }

    /**
     * Set a {@link ChartType chartType} for chart generation
     *
     * @param chartType The {@link ChartType chartType}
     * @implNote Call this function before {@link #export()}
     */
    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    /**
     * Set a {@link Parameter parameter} for chart generation
     *
     * @param parameter The {@link Parameter parameter} of the chart
     */
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    /**
     * Set a {@link String chartName} for chart generation
     *
     * @param chartName The {@link String chartName} of the chart
     */
    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    /**
     * Generate a chart configuration object
     *
     * @param chartType  The {@link ChartType type} of the chart
     * @param statistics The {@link IStatistics data} to process
     * @return Returns the configuration of the chart
     */
    public JSONObject generateChartConfiguration(String title, ChartType chartType, IStatistics[] statistics, Parameter parameter) {
        if (chartType == null || statistics == null) {
            throw new IllegalArgumentException("This method doesn't support null parameters");
        }

        JSONObject dataObject = new JSONObject();
        dataObject.put("labels", appendLabelsArray(statistics));
        dataObject.put("datasets", appendDatasetsArray(parameter.getUnit().toString(), statistics));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", chartType.toString());
        jsonObject.put("data", dataObject);
        jsonObject.put("options", appendChartOptions(title));

        return jsonObject;
    }

    // TODO: The one

    /**
     * Appends an options object with a {@link String title}.
     *
     * @param title The {@link String title} of the chart.
     * @return Returns a {@link JSONObject} that contains the options object.
     */
    private JSONObject appendChartOptions(String title) {
        JSONObject titleObject = new JSONObject();
        titleObject.put("display", true);
        titleObject.put("text", title);

        JSONObject options = new JSONObject();
        options.put("title", titleObject);

        return options;
    }

    /**
     * Creates a {@link JSONArray} with all labels from {@link IStatistics statistics}.
     *
     * @param label      The {@link String label} of the chart.
     * @param statistics The array of {@link IStatistics statistics}.
     * @return Returns a {@link JSONArray} of all the values.
     */
    private JSONArray appendDatasetsArray(String label, IStatistics[] statistics) {

        JSONArray dataArray = new JSONArray();
        for (IStatistics s : statistics) {
            dataArray.add(s.getValue());
        }

        JSONObject datasetsObject = new JSONObject();
        datasetsObject.put("label", label);
        datasetsObject.put("data", dataArray);

        JSONArray datasetsArray = new JSONArray();
        datasetsArray.add(datasetsObject);

        return datasetsArray;
    }

    /**
     * Creates a {@link JSONArray} with all the labels from {@link IStatistics statistics}.
     *
     * @param statistics The array of {@link IStatistics statistics}.
     * @return Returns a {@link JSONArray} of all the labels.
     */
    private JSONArray appendLabelsArray(IStatistics[] statistics) {
        JSONArray labelsArray = new JSONArray();

        for (IStatistics s : statistics) {
            JSONObject description = (JSONObject) JSONValue.parse(s.getDescription());

            if (description.containsKey("stationName")) {
                labelsArray.add(description.get("stationName"));
            } else if (description.containsKey("sensorId")) {
                labelsArray.add(description.get("sensorId"));
            }
        }

        return labelsArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String export() throws IOException {
        if (chartName == null || chartType == null || statistics == null || parameter == null) {
            throw new IOException("JsonExporter Class has not been initialize properly!");
        }

        JSONObject jsonObject = generateChartConfiguration(chartName, chartType, statistics, parameter);

        try (FileWriter fos = new FileWriter(outputPath + chartName + ".json")) {
            fos.write(jsonObject.toJSONString());
        }

        return jsonObject.toJSONString();
    }
}