package Monitoring;

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

    double value;
    LocalDateTime localDateTime;
    Unit unit;

    public Measurement(double val, LocalDateTime dateTime, String unitString) throws MeasurementException, SensorException {
        if (val == -99) {
            throw new MeasurementException("Measurement Value Out of Bounds");
        }

        value = val;
        localDateTime = dateTime;
        unit = Unit.getUnitFromString(unitString);

        throw new SensorException("Invalid unit value for default sensor parameter!");
    }

    @Override
    public LocalDateTime getTime() {
        return localDateTime;
    }

    @Override
    public double getValue() {
        return value;
    }
}
