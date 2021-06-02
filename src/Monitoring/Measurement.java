package Monitoring;

import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.interfaces.IMeasurement;

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
public class Measurement implements IMeasurement {

    private final double value;
    private final LocalDateTime localDateTime;
    private final Unit unit;

    public Measurement(double value, LocalDateTime localDateTime, Unit unit) throws MeasurementException {
        if (!validateValueBounds(value)) {
            throw new MeasurementException("Measurement Value Out of Bounds");
        }

        if (unit == null) {
            throw new MeasurementException("Unit can't be NULL");
        }

        this.value = value;
        this.localDateTime = localDateTime;
        this.unit = unit;
    }

    public boolean validateValueBounds(double val) {
        return val != -99;
    }

    @Override
    public LocalDateTime getTime() {
        return localDateTime;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Measurement m = (Measurement) obj;
        return m.localDateTime == this.localDateTime && m.value == this.value;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "localDateTime=" + localDateTime.toString() +
                ", value=" + value + Unit.getUnitString(unit) +
                "}";
    }
}
