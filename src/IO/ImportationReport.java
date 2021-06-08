package IO;

import edu.ma02.io.interfaces.IOStatistics;

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
public class ImportationReport implements IOStatistics {
    private int nNewMeasurementsRead;
    private int nMeasurementsRead;
    private int nNewStationsRead;
    private int nStationsRead;
    private int nNewSensorsRead;
    private int nSensorsRead;
    private String[] caughtExceptions;
    private int elements = 0;

    public ImportationReport() {
        caughtExceptions = new String[10];
    }

    private void grow() {
        String[] copy = new String[caughtExceptions.length * 2];
        System.arraycopy(caughtExceptions, 0, copy, 0, caughtExceptions.length);
        caughtExceptions = copy;
    }

    public void addException(StackTraceElement[] stackTrace, String cause) {
        if (elements == caughtExceptions.length) {
            grow();
        }

        String exceptionMessage = "[" + LocalDateTime.now() + "] [" + stackTrace[0].getClassName() + "] " + cause;

        caughtExceptions[elements++] = exceptionMessage;
    }

    public void increaseReadStation() {
        nNewStationsRead++;
    }

    public void increaseReadSensor() {
        nNewSensorsRead++;
    }

    public void increaseReadMeasurement() {
        nNewMeasurementsRead++;
    }

    @Override
    public int getNumberOfReadMeasurements() {
        return nMeasurementsRead;
    }

    @Override
    public int getNumberOfNewStationsRead() {
        return nNewStationsRead;
    }

    @Override
    public int getNumberOfStationsRead() {
        return nStationsRead;
    }

    @Override
    public int getNumberOfSensorsRead() {
        return nSensorsRead;
    }

    @Override
    public int getNumberOfNewSensorsRead() {
        return nNewSensorsRead;
    }

    @Override
    public int getNumberOfNewMeasurementsRead() {
        return nNewMeasurementsRead;
    }

    @Override
    public String[] getExceptions() {
        if (elements == 0) return new String[]{};

        String[] exceptions = new String[elements];
        System.arraycopy(caughtExceptions, 0, exceptions, 0, elements);
        return exceptions.clone();
    }
}
