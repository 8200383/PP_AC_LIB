package Core;

import edu.ma02.core.interfaces.IStatistics;

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

    String description;
    double value;

    public Statistic(String sensorId, String stationName, String sensorParameter, String sensorUnit, double value) {
        this.description = "Sensor=" + sensorId +
                ", Station=" + stationName +
                ", Unit=" + sensorUnit +
                ", Parameter=" + sensorParameter;

        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return value;
    }
}
