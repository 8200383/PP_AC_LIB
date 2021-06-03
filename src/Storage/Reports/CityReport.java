package Storage.Reports;

import Core.City;
import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.ICityStatistics;
import edu.ma02.core.interfaces.IStatistics;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class CityReport implements ICityStatistics {
    private final City city;
    private ImportationReport importationReport;

    /*
     * When import it should only have a CityReport for a City
     * If there are many sensors in the same City then use the same CityReport to a Sensor to that City
     * Eventually you will need an Array of CityReport elsewhere
     *
     * Also when import a City the CityReport produces an ImportReport that should be return to the importData method
     */
    public CityReport(JSONObject cityData) {
        if (!cityData.containsKey("address")) {
            // TODO Add KeyNotFound Exception to Import Report
        }

        city = new City((String) cityData.get("address"));
    }

    public ImportationReport getImportationReport() {
        return importationReport;
    }

    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return new IStatistics[0];
    }

    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter) {
        return new IStatistics[0];
    }

    @Override
    public IStatistics[] getMeasurementsBySensor(String s, AggregationOperator aggregationOperator, Parameter parameter, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return new IStatistics[0];
    }

    @Override
    public IStatistics[] getMeasurementsBySensor(String s, AggregationOperator aggregationOperator, Parameter parameter) {
        return new IStatistics[0];
    }
}
