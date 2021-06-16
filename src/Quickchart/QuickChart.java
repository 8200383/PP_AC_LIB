package Quickchart;

import Core.Statistic;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    /**
     * Empty constructor for {@link QuickChart}
     */
    public QuickChart() {
    }

    private ChartConfiguration chartConfiguration;
    private String outputPath;

    /**
     * Specification of a chart configuration
     */
    private static class ChartConfiguration {
        private final String chartName;
        private final IStatistics[] statistics;
        private final Parameter parameter;
        private final ChartType chartType;

        /**
         * Default constructor for {@link ChartConfiguration}
         *
         * @param name      The {@link String name} of the chart
         * @param parameter The {@link Parameter parameter} of the chart
         * @param stats     The {@link IStatistics stats} of the chart
         * @param type      The {@link ChartType type} of the char
         */
        private ChartConfiguration(String name, Parameter parameter, IStatistics[] stats, ChartType type) {
            this.chartName = name;
            this.parameter = parameter;
            this.statistics = stats;
            this.chartType = type;
        }
    }

    /**
     * Set a {@link ChartConfiguration}
     *
     * @param chartName      The {@link String chartName}
     * @param chartParameter The {@link Parameter chartParameter}
     * @param chartData      The {@link IStatistics chartData}
     * @param chartType      The {@link ChartType chartType}
     */
    public void setChartConfiguration(String chartName, Parameter chartParameter, IStatistics[] chartData, ChartType chartType) {
        chartConfiguration = new ChartConfiguration(chartName, chartParameter, chartData, chartType);
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
     * Generate a chart configuration object
     *
     * @param config The {@link ChartConfiguration configuration} of the char
     * @return Returns the configuration of the chart
     */
    private JSONObject generateChartConfiguration(ChartConfiguration config) {
        JSONObject dataObject = new JSONObject();
        dataObject.put("labels", appendLabelsArray(config.statistics));
        dataObject.put("datasets", appendDatasetsArray(
                Parameter.getParameterName(config.parameter),
                config.statistics
        ));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", config.chartType.toString());
        jsonObject.put("data", dataObject);
        jsonObject.put("options", appendChartOptions(config.chartName));

        return jsonObject;
    }

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
        for (IStatistics iStatistics : statistics) {
            if (iStatistics instanceof Statistic s){
                dataArray.add(s.getValue());
            }
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

        for (IStatistics iStatistics : statistics) {
            if (iStatistics instanceof Statistic s){
                labelsArray.add(s.getDescription());
            }
        }

        return labelsArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String export() throws IOException {
        if (chartConfiguration == null) {
            return "";
        }

        JSONObject jsonObject = generateChartConfiguration(chartConfiguration);

        String fileName = outputPath + chartConfiguration.chartName + ".json";
        try (FileWriter fos = new FileWriter(fileName)) {
            fos.write(jsonObject.toJSONString());
        }

        return jsonObject.toJSONString();
    }
}