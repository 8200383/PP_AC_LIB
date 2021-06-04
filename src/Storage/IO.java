package Storage;

import Storage.Exceptions.KeyNotFound;
import Storage.Reports.ImportationReport;
import Utils.StorageLogger;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.io.interfaces.IExporter;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class IO implements IImporter, IExporter {

    public IO() {
    }

    @Override
    public String export() throws IOException {
        return null;
    }

    /* TODO Perguntar ao prof: There is a more generic FileNotFoundException */
    @Override
    public IOStatistics importData(ICity city, String path) throws IOException, CityException {
        if (city == null) throw new CityException("City can't be NULL");

        ImportationReport importationReport = new ImportationReport();

        JSONArray jsonArray = (JSONArray) JSONValue.parse(new FileReader(path));
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            try {
                if (!jsonObject.containsKey("address")) {
                    throw new KeyNotFound("Invalid Address field");
                }

                city.addStation(jsonObject.get("address").toString());

                Coordinates coordinatesObject = new Coordinates((JSONObject) jsonObject.get("coordinates"));
                city.addSensor(
                        jsonObject.get("address").toString(),
                        jsonObject.get("id").toString(),
                        coordinatesObject.getCartesianCoordinates(),
                        coordinatesObject.getGeographicCoordinates()
                );

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                LocalDateTime dateTime = LocalDateTime.parse(jsonObject.get("date").toString(), formatter);

                city.addMeasurement(
                        jsonObject.get("address").toString(),
                        jsonObject.get("id").toString(),
                        Double.parseDouble(jsonObject.get("value").toString()),
                        jsonObject.get("unit").toString(),
                        dateTime
                );
            } catch (CityException | SensorException | KeyNotFound | StationException | MeasurementException e) {
                importationReport.addException(e.getStackTrace(), e.getMessage());
            }
        }

        return importationReport;
    }

    // TODO Use this to obtain Coordinates Object
    private JSONObject getJsonObjectFromDocument(JSONObject doc, String key) throws KeyNotFound {
        if (!doc.containsKey(key)) throw new KeyNotFound();
        Object obj = doc.get(key);
        return (obj instanceof JSONObject) ? (JSONObject) obj : null;
    }

}
