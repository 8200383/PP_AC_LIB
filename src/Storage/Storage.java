package Storage;

import Storage.Exceptions.KeyNotFound;
import Storage.Reports.CityReport;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.io.interfaces.IExporter;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
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
public class Storage implements IImporter, IExporter {
    CityReport[] citiesReports;

    @Override
    public String export() throws IOException {
        return null;
    }

    /* TODO Perguntar ao prof: There is a more generic FileNotFoundException */
    @Override
    public IOStatistics importData(ICity city, String path) throws IOException, CityException {
        if (city == null) throw new CityException("City can't be NULL");

        // TODO Should loop on this
        JSONArray jsonArray = (JSONArray) JSONValue.parse(new FileReader(path));

        // CityReport cityReport = new CityReport(jsonObject);
        // return cityReport.getImportationReport();

        return null;
    }

    // TODO Use this to obtain Coordinates Object
    private JSONObject getJsonObjectFromDocument(JSONObject doc, String key) throws KeyNotFound {
        if (!doc.containsKey(key)) throw new KeyNotFound();
        Object obj = doc.get(key);
        return (obj instanceof JSONObject) ? (JSONObject) obj : null;
    }

    // TODO Use this functions below for the Menu
    public CityReport getCityReportById() {
        return null;
    }

    public CityReport[] getCitiesReports() {
        return citiesReports;
    }
}
