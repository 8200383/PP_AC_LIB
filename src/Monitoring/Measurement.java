package Monitoring;

import Monitoring.SensorFactory.Enums.ParametersUnits;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.Unit;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
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

    public Measurement(double val, LocalDateTime dateTime,
                       String unitMeasurement, Parameter sensorParameter
    ) throws MeasurementException, SensorException {

        if (!validateValueBounds(val)) {
            throw new MeasurementException("Measurement Value Out of Bounds");
        }

        value = val;
        localDateTime = dateTime;
        unit = Unit.getUnitFromString(unitMeasurement);

        if (!validateUnitMeasure(sensorParameter, unit)) {
            throw new SensorException("Invalid Unit Measure for this Sensor!");
        }
    }

    public boolean validateValueBounds(double val) {
        return val != -99;
    }

    private boolean validateUnitMeasure(Parameter parameter, Unit unit) {
        return ParametersUnits.getUnitByParameter(parameter) == unit;
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

        if (obj instanceof Measurement m) {
            return m.localDateTime == this.localDateTime && m.value == this.value;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "localDateTime=" + localDateTime.toString() +
                ", value=" + value + Unit.getUnitString(unit) +
                "}";
    }
}
