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

    private final String description;
    private final double value;

    public Statistic(String description, double value) {
        this.description = description;
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
