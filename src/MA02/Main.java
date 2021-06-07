package MA02;

import Core.*;
import Storage.JsonImporter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.io.interfaces.IOStatistics;

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
public class Main {

    public static void main(String[] args) throws CityException, IOException {

        City city = new City("Lisbon");

        JsonImporter jsonInputOutput = new JsonImporter();
        IOStatistics ioStatistics = jsonInputOutput.importData(city, "resources/sensorData.json");

        Menu menu = new Menu();
        menu.displayMenu(city, ioStatistics);
    }


}
