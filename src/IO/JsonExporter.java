package IO;

import Quickchart.ChartType;
import Quickchart.QuickChart;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
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
public class JsonExporter implements IExporter {

    private Parameter parameter;
    private IStatistics[] statistics;
    private String filenameOutputPath;
    private ChartType chartType;

    /**
     * Empty constructor for {@link JsonExporter}
     */
    public JsonExporter() {
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
     * Set the filename for exportation
     *
     * @param name The {@link String name} of the file
     * @implNote Call this function before {@link #export()}
     */
    public void setOutputFileName(String name) {
        filenameOutputPath = name;
    }

    /**
     * Set a {@link ChartType} for chart generation
     *
     * @param chartType The {@link ChartType chartType}
     * @implNote Call this function before {@link #export()}
     */
    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String export() throws IOException {

        JSONObject jsonObject = null;
        try {
            jsonObject = QuickChart.generateChartConfiguration(chartType, statistics, false, parameter);
        } catch (ParseException e) {
            throw new IOException(Arrays.toString(e.getStackTrace()));
        }

        try (FileWriter fos = new FileWriter(filenameOutputPath)) {
            fos.write(jsonObject.toJSONString());
        }

        return jsonObject.toJSONString();
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
