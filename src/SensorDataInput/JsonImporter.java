package SensorDataInput;

import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.ICity;
import edu.ma02.io.interfaces.IImporter;
import edu.ma02.io.interfaces.IOStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
public class JsonImporter implements IImporter {

    private int nImportsMade = 0;

    /**
     * Empty constructor for {@link JsonImporter}
     */
    public JsonImporter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IOStatistics importData(ICity city, String path) throws IOException, CityException {
        if (city == null) throw new CityException("City can't be NULL");

        ImportationReport report = new ImportationReport();

        // Windows encoding is windows-1252 and Java default encoding is not UTF-8
        FileInputStream fileOutputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileOutputStream, StandardCharsets.UTF_8);

        JSONArray jsonArray = (JSONArray) JSONValue.parse(inputStreamReader);
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
                    throw new IOException("Invalid JsonObject");
                }

                if (city.addStation(jsonObject.get("address").toString())) {
                    report.increaseReadStation(nImportsMade > 0);
                }

                CoordinatesObject coordinatesObject = new CoordinatesObject((JSONObject) jsonObject.get("coordinates"));
                if (city.addSensor(
                        jsonObject.get("address").toString(),
                        jsonObject.get("id").toString(),
                        coordinatesObject.getCartesianCoordinates(),
                        coordinatesObject.getGeographicCoordinates()
                )) {
                    report.increaseReadSensor(nImportsMade > 0);
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
                    report.increaseReadMeasurement(nImportsMade > 0);
                }
            } catch (CityException | SensorException | IOException | StationException | MeasurementException e) {
                report.addException(e.getStackTrace(), e.getMessage());
            }
        }

        nImportsMade++;
        return report;
    }
}
