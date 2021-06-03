package Storage.Reports;

import edu.ma02.io.interfaces.IOStatistics;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class ImportationReport implements IOStatistics {
    private int nNewMeasurementsRead;
    private int nMeasurementsRead;
    private int nNewStationsRead;
    private int nStationsRead;
    private int nNewSensorRead;
    private int nSensorRead;
    private String[] caughtExceptions;

    public ImportationReport() {
    }

    // TODO Implement Array
    private void addException() {

    }

    @Override
    public int getNumberOfReadMeasurements() {
        return nNewMeasurementsRead;
    }

    @Override
    public int getNumberOfNewStationsRead() {
        return nMeasurementsRead;
    }

    @Override
    public int getNumberOfStationsRead() {
        return nNewStationsRead;
    }

    @Override
    public int getNumberOfSensorsRead() {
        return nStationsRead;
    }

    @Override
    public int getNumberOfNewSensorsRead() {
        return nNewSensorRead;
    }

    @Override
    public int getNumberOfNewMeasurementsRead() {
        return nSensorRead;
    }

    @Override
    public String[] getExceptions() {
        return new String[0];
    }
}
