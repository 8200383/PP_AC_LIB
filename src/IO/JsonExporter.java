package IO;

import edu.ma02.core.interfaces.IStatistics;
import edu.ma02.io.interfaces.IExporter;

import java.io.IOException;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class JsonExporter implements IExporter {

    private IStatistics[] statistics;

    JsonExporter() {
    }

    public IStatistics[] getStatistics() {
        return statistics;
    }

    public void setStatistics(IStatistics[] statistics) {
        this.statistics = statistics;
    }

    @Override
    public String export() throws IOException {
        return null;
    }
}
