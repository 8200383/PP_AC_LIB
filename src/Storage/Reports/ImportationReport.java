package Storage.Reports;

import edu.ma02.io.interfaces.IOStatistics;

import java.time.LocalDateTime;
import java.util.logging.Logger;

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
    private static Logger logger = Logger.getLogger(Storage.IO.class.getName());

    private int nNewMeasurementsRead;
    private int nMeasurementsRead;
    private int nNewStationsRead;
    private int nStationsRead;
    private int nNewSensorRead;
    private int nSensorRead;
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

    // TODO Implement Array
    public void addException(StackTraceElement[] stackTrace, String cause, boolean warning) {
        if (elements == caughtExceptions.length) {
            grow();
        }

        String warningMessage = "[" + stackTrace[0].getClassName() + "] " + cause;
        if (warning) logger.warning(warningMessage);

        caughtExceptions[elements++] = "[" + LocalDateTime.now() + "] " + warningMessage;
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
        String[] exceptions = new String[elements];
        System.arraycopy(caughtExceptions, 0, exceptions, 0, elements);
        return exceptions.clone();
    }
}
