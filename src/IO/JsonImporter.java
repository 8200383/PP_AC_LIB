package IO;

import IO.Exceptions.KeyNotFound;
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

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class JsonImporter implements IImporter, IExporter {

    @Override
    public IOStatistics importData(ICity city, String path) throws IOException, CityException {
        if (city == null) throw new CityException("City can't be NULL");

        ImportationReport importationReport = new ImportationReport();

        JSONArray jsonArray = (JSONArray) JSONValue.parse(new FileReader(path));
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            try {
                boolean jsonOk = jsonObject.containsKey("address") &&
                        jsonObject.containsKey("coordinates") &&
                        jsonObject.containsKey("id") &&
                        jsonObject.containsKey("date") &&
                        jsonObject.containsKey("value") &&
                        jsonObject.containsKey("unit");

                if (!jsonOk) {
                    throw new KeyNotFound("Invalid JsonObject");
                }

                if (city.addStation(jsonObject.get("address").toString())) {
                    importationReport.increaseReadStation();
                }

                CoordinatesObject coordinatesObject = new CoordinatesObject((JSONObject) jsonObject.get("coordinates"));
                if (city.addSensor(
                        jsonObject.get("address").toString(),
                        jsonObject.get("id").toString(),
                        coordinatesObject.getCartesianCoordinates(),
                        coordinatesObject.getGeographicCoordinates()
                )) {
                    importationReport.increaseReadSensor();
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                LocalDateTime dateTime = LocalDateTime.parse(jsonObject.get("date").toString(), formatter);

                if (city.addMeasurement(
                        jsonObject.get("address").toString(),
                        jsonObject.get("id").toString(),
                        Double.parseDouble(jsonObject.get("value").toString()),
                        jsonObject.get("unit").toString(),
                        dateTime
                )) {
                    importationReport.increaseReadMeasurement();
                }
            } catch (CityException | SensorException | KeyNotFound | StationException | MeasurementException e) {
                importationReport.addException(e.getStackTrace(), e.getMessage());
            }
        }

        return importationReport;
    }

    @Override
    public String export() throws IOException {
        return null;
    }
}
