package Core;

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

    /**
     * Constructor for {@link Measurement}
     *
     * @param value         The read value
     * @param localDateTime The time of the read
     * @throws MeasurementException If the {@link LocalDateTime localDateTime} is null or
     *                              {@link Double value} is out of bounds
     */
    public Measurement(double value, LocalDateTime localDateTime) throws MeasurementException {
        if (localDateTime == null) {
            throw new MeasurementException("DateTime can't be NULL");
        }

        if (!validateValueBounds(value)) {
            throw new MeasurementException("Measurement Value Out of Bounds");
        }

        this.value = value;
        this.localDateTime = localDateTime;
    }

    /**
     * Validate bounds for the measurement
     * @param val The read value
     * @return Return if the {@link Double val} is valid
     */
    private boolean validateValueBounds(double val) {
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
                "value=" + value +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
