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

    // FIXME Add another constructor later
    public Statistic(String sensorId, String stationName, double value) {
        if (!sensorId.isEmpty()) {
            this.description.put("sensorId", sensorId);
        } else if (!stationName.isEmpty()) {
            this.description.put("stationName", stationName);
        }

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
