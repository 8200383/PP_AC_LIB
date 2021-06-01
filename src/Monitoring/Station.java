package Monitoring;

import Monitoring.SensorFactory.Sensor;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ISensor;
import edu.ma02.core.interfaces.IStation;

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

public class Station implements IStation {
    private String name;
    private Sensor[] sensors;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean addSensor(ISensor iSensor) throws StationException, SensorException {
        //TODO: Array
        return false;
    }

    @Override
    public boolean addMeasurement(String s, double v, LocalDateTime localDateTime, String s1) throws StationException, SensorException, MeasurementException {
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null && sensors[i].getId().equals(s)) {
                sensors[i].addMeasurement(v, localDateTime, s1);
                return true;
            }
        }
        return false;
    }

    @Override
    public ISensor[] getSensors() {
        return sensors;
    }

    @Override
    public ISensor getSensor(String s) {
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null && sensors[i].getId().equals(s)) {
                return sensors[i];
            }
        }
        return null;
    }
}