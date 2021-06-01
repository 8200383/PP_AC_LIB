package Monitoring.SensorFactory.ValueObjects;

import edu.ma02.core.exceptions.MeasurementException;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class MeasurementValue {
    private final double value;

    public MeasurementValue(double val) throws MeasurementException {
        if (val == -99) throw new MeasurementException("Measurement Value Out of Bounds");

        value = val;
    }

    public double getValue() {
        return value;
    }
}
