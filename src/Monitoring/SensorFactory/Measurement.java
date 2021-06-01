package Monitoring.SensorFactory;

import Monitoring.SensorFactory.Enums.ParametersUnits;
import Monitoring.SensorFactory.ValueObjects.MeasurementValue;
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

    MeasurementValue value;
    LocalDateTime localDateTime;
    Unit unit;

    public Measurement(double val, LocalDateTime dateTime, String unitMeasurement, Parameter sensorParameter) throws MeasurementException, SensorException {

        value = new MeasurementValue(val);
        localDateTime = dateTime;
        unit = Unit.getUnitFromString(unitMeasurement);

        if (!validateUnitMeasure(sensorParameter, unit)) {
            throw new SensorException("Invalid Unit Measure for this Sensor!");
        }
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
        return value.getValue();
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "localDateTime=" + localDateTime.toString() +
                ", value=" + value.getValue() + Unit.getUnitString(unit) +
                "}";
    }
}
