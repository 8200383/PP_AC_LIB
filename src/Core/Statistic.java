package Core;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.interfaces.IStatistics;
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
public class Statistic implements IStatistics {

    JSONObject description = new JSONObject();
    double value;

    public Statistic(String sensorId, String stationName, Parameter sensorParameter, double value) {
        this.description.put("sensorId", sensorId);
        this.description.put("stationName", stationName);
        this.description.put("sensorParameter", sensorParameter);
        this.description.put("sensorUnit", sensorParameter.getUnit());

        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description.toJSONString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return value;
    }
}
