package Core;

import edu.ma02.core.enumerations.AggregationOperator;
import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.exceptions.CityException;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;
import edu.ma02.core.exceptions.StationException;
import edu.ma02.core.interfaces.*;

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

public class City implements ICity, ICityStatistics {
    private final String cityName;
    private Station[] stations;
    private int nStations = 0;

    /**
     * Constructor for {@link City}
     *
     * @param name The name of the city
     */
    // TODO Perguntar ao prof sobre o cityId
    public City(String name) {
        cityName = name;
        stations = new Station[10];
    }

    /**
     * Grow the array of {@link Station stations}
     */
    private void grow() {
        Station[] copy = new Station[stations.length * 2];
        System.arraycopy(stations, 0, copy, 0, nStations);
        stations = copy;
    }

    /**
     * Finds a {@link Station} by {@link String stationName}
     *
     * @param stationName The of the station to look for
     * @return Returns an instance of {@link Station}
     */
    private IStation getStationByName(String stationName) {
        if (stationName == null) return null;

        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {
                if (station.getName().equals(stationName)) {
                    return station;
                }
            }
        }

        return null;
    }

    /**
     * Finds a {@link Sensor sensor} at {@link IStation station} by {@link String sensorId}
     *
     * @param station  The station where to look
     * @param sensorId The sensorId to look for
     * @return Returns an instance of a {@link Sensor}
     */
    private ISensor getSensorAtStationById(IStation station, String sensorId) {
        for (ISensor iSensor : station.getSensors()) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getId().equals(sensorId)) {
                    return sensor;
                }
            }
        }

        return null;
    }

    /**
     * Calculate the average of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] avgOfMeasurementsBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {

                // Division by 0 in Java causes 'Not a Number' (NaN)
                if (sensor.getNumMeasurements() == 0) {
                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(), "", 0));
                    break;
                }

                double sumOfMeasurements = 0;
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        sumOfMeasurements += measurement.getValue();
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), "",
                        sumOfMeasurements / (double) sensor.getNumMeasurements()));
            }
        }

        return statistics.clone();
    }

    /**
     * Calculate the minimum of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] minOfMeasurementBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {

                if (sensor.getNumMeasurements() == 0) {
                    break;
                }

                IMeasurement[] measurements = sensor.getMeasurements();
                double minValue = measurements[0].getValue();
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        if (measurement.getValue() < minValue) {
                            minValue = measurement.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), "", minValue));
            }
        }

        return statistics.clone();
    }

    /**
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] maxOfMeasurementsBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getNumMeasurements() == 0) {
                    break;
                }

                IMeasurement[] measurements = sensor.getMeasurements();
                double maxValue = measurements[0].getValue();
                for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                    if (iMeasurement instanceof Measurement measurement) {
                        if (measurement.getValue() > maxValue) {
                            maxValue = measurement.getValue();
                        }
                    }
                }

                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), "", maxValue));
            }
        }

        return statistics.clone();
    }

    /**
     * Count the number of measurements by {@link ISensor sensor}
     *
     * @param sensors The array of {@link ISensor[] sensors}
     * @return Return an array of {@link IStatistics}
     */
    private IStatistics[] countOfMeasurementsBySensor(ISensor[] sensors) {
        IStatistics[] statistics = new IStatistics[]{};

        for (ISensor iSensor : sensors) {
            if (iSensor instanceof Sensor sensor) {
                statistics = addStatistic(statistics, new Statistic(
                        sensor.getId(), "",
                        sensor.getNumMeasurements()));
            }
        }

        return statistics.clone();
    }

    /**
     * // TODO Hugo
     * @param srcArray
     * @param statistic
     * @return
     */
    private IStatistics[] addStatistic(IStatistics[] srcArray, IStatistics statistic) {
        IStatistics[] destArray = new IStatistics[srcArray.length + 1];

        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);
        destArray[destArray.length - 1] = statistic;
        return destArray;
    }

    /**
     * Adds an element to an already existing array and increments the size of that array by one
     * // TODO Hugo, este inglês
     * @param srcArray    ...
     * @param measurement ...
     * @return ...
     */
    private IMeasurement[] addMeasurement(IMeasurement[] srcArray, IMeasurement measurement) {
        IMeasurement[] destArray = new IMeasurement[srcArray.length + 1];

        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);
        destArray[destArray.length - 1] = measurement;

        return destArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return cityName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return cityName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addStation(String stationName) throws CityException {
        if (stationName == null) throw new CityException("Station Name can't be NULL");

        // Check if Station already exists
        if (getStationByName(stationName) != null) {
            return false;
        }

        // If array is full then grow array
        if (nStations == stations.length) {
            grow();
        }

        stations[nStations++] = new Station(stationName);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addSensor(String stationName, String sensorId,
                             ICartesianCoordinates cartesianCoordinates,
                             IGeographicCoordinates geographicCoordinates
    ) throws CityException, StationException, SensorException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            throw new CityException("Station not found");
        }

        // If caught from City returns a StationException otherwise return a SensorException
        if (!Sensor.isSensorIdLengthValid(sensorId)) {
            throw new StationException("[City] Sensor ID can't have more or less than 10 characters");
        }

        ISensor sensor = getSensorAtStationById(station, sensorId);
        if (sensor != null) {
            throw new StationException("Sensor doesn't exist");
        }

        return station.addSensor(new Sensor(sensorId, cartesianCoordinates, geographicCoordinates));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMeasurement(String stationName, String sensorId, double value,
                                  String unit, LocalDateTime localDateTime
    ) throws CityException, StationException, SensorException, MeasurementException {
        if (stationName == null) {
            throw new CityException("Station Name can't be NULL");
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            throw new CityException("Can't find any Station with that name");
        }

        /* Exceptions from Stations, Sensors and Measurement caught here
         * This also checks if the collections stores the measurement
         */
        return station.addMeasurement(sensorId, value, localDateTime, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStation[] getStations() {
        if (nStations == 0) return new IStation[]{};

        Station[] copy = new Station[nStations];
        System.arraycopy(stations, 0, copy, 0, nStations);
        return copy.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStation getStation(String stationName) {
        return getStationByName(stationName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISensor[] getSensorsByStation(String stationName) {
        IStation station = getStationByName(stationName);
        return (station != null) ? station.getSensors() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMeasurement[] getMeasurementsBySensor(String sensorId) {
        for (IStation iStation : stations) {
            if (iStation instanceof Station station) {
                for (ISensor iSensor : station.getSensors()) {
                    if (iSensor instanceof Sensor sensor) {
                        if (sensor.getId().equals(sensorId)) {
                            return sensor.getMeasurements();
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter,
                                                  LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return getMeasurementsByStation(aggregationOperator, parameter);
        }

        IStatistics[] statistics = new IStatistics[]{};

        for (IStation station : getStations()) {
            for (ISensor sensor : station.getSensors()) {
                if (sensor.getParameter() == parameter) {

                    switch (aggregationOperator) {
                        case AVG -> {
                            int valueCount = 0;
                            double valuesSum = 0;

                            for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                                if (iMeasurement instanceof Measurement measurement) {
                                    if (measurement.getTime().compareTo(startDate) > 0 &&
                                            measurement.getTime().compareTo(endDate) < 0
                                    ) {
                                        valuesSum += measurement.getValue();
                                        valueCount++;
                                    }
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    valuesSum / (double) valueCount));
                        }
                        case COUNT -> {
                            int valueCount = 0;

                            for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                                if (iMeasurement instanceof Measurement measurement) {
                                    if (measurement.getTime().compareTo(startDate) > 0 &&
                                            measurement.getTime().compareTo(endDate) < 0) {
                                        valueCount++;
                                    }
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    valueCount));
                        }
                        case MAX -> {
                            IMeasurement[] measurementArray = sensor.getMeasurements();
                            if (measurementArray == null) break;

                            measurementArray = getMeasurementsWithinDates(measurementArray, startDate, endDate);
                            if (measurementArray.length == 0) break;

                            double maxValue = measurementArray[0].getValue();
                            for (IMeasurement measurement : measurementArray) {
                                if (measurement.getValue() > maxValue) {
                                    maxValue = measurement.getValue();
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    maxValue));
                        }
                        case MIN -> {
                            IMeasurement[] measurementArray = sensor.getMeasurements();
                            if (measurementArray == null) break;

                            measurementArray = getMeasurementsWithinDates(measurementArray, startDate, endDate);
                            if (measurementArray.length == 0) break;

                            double minValue = measurementArray[0].getValue();
                            for (IMeasurement iMeasurement : measurementArray) {
                                if (iMeasurement instanceof Measurement measurement) {
                                    if (measurement.getValue() < minValue) {
                                        minValue = measurement.getValue();
                                    }
                                }
                            }

                            statistics = addStatistic(statistics, new Statistic(
                                    sensor.getId(),
                                    station.getName(),
                                    minValue));
                        }
                    }
                }
            }
        }
        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    public IStatistics[] getMeasurementsByStation(AggregationOperator aggregationOperator, Parameter parameter) {
        IStatistics[] statistics = new IStatistics[]{};

        switch (aggregationOperator) {
            case AVG -> {
                for (int i = 0; i < nStations; i++) {
                    double sum = 0;

                    IStatistics[] measurements = avgOfMeasurementsBySensor(stations[i].getSensors());
                    for (IStatistics avg : measurements) {
                        sum += avg.getValue();
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            "",
                            stations[i].getName(),
                            sum / (double) measurements.length
                    ));

                }
            }
            case MIN -> {
                for (int i = 0; i < nStations; i++) {
                    IStatistics[] measurements = minOfMeasurementBySensor(stations[i].getSensors());
                    double min = measurements[0].getValue();

                    for (IStatistics avg : measurements) {
                        if (avg.getValue() < min) {
                            min = avg.getValue();
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            "", stations[i].getName(),
                            min / measurements.length
                    ));
                }
            }
            case MAX -> {
                for (int i = 0; i < nStations; i++) {
                    IStatistics[] measurements = maxOfMeasurementsBySensor(stations[i].getSensors());
                    double max = measurements[0].getValue();

                    for (IStatistics avg : measurements) {
                        if (avg.getValue() > max) {
                            max = avg.getValue();
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            "", stations[i].getName(),
                            max / measurements.length
                    ));
                }
            }
            case COUNT -> {
                for (int i = 0; i < nStations; i++) {
                    int measurementsByStation = 0;
                    for (ISensor iSensor : stations[i].getSensors()) {
                        if (iSensor instanceof Sensor sensor) {
                            measurementsByStation += sensor.getNumMeasurements();
                        }
                    }

                    addStatistic(statistics, new Statistic(
                            "",
                            stations[i].getName(),
                            measurementsByStation
                    ));
                }

            }
        }

        return statistics.clone();
    }

        /*
            FIXME Hugo
            1. Ao exportar esta visualização o gráfico tera de estar orientado por estações
               ex: a estação "estrela" tem 5 measurements
               1.1 FIXME Neste momento ja perdestes essa noção pois tu adicionas a cada sensor que passas ao invés de
                   de adicionares a cada estação que passas
                   1.1.1 O getMeasurementsBySensor(), both, estão direitos pois adicionas por casa sensor que passas
                   1.1.2 FIXME getMeasurementsByStation(), both!
            2. Para o gráfico deveria ir para lá a seguinte informação:
               2.1 O operador de agregação, explanation: para saber se por exemplo se trata de uma média
               2.2 Outros detalhes inclui:
                   2.2.1 Parameter
                   2.2.2 O nome da estação
                   2.2.3 O range das datas
                   2.2.4 O id do sensor
               2.3 Feel free para adaptar a class Statistic para satisfazer as necessidades do gráfico
               2.4 O gráfico tanto pode ser de barras ou linhas
                   2.4.1 O gráfico dos detalhes de importação IOStatistics poderá ser radar ou pie
                   2.4.2 https://quickchart.io/documentation/#bar-graph
               2.5 Existem outros detalhes do gráfico mas so depois de o ver construido é que posso dizer mais...
            3. No City ficas com os func que tem as datas e eu fico com as outras duas.
         */

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator,
                                                 Parameter parameter, LocalDateTime startDate, LocalDateTime endDate) {
        IStatistics[] statistics = new IStatistics[]{};

        if (stationName == null) {
            return statistics.clone();
        }

        if (startDate == null || endDate == null) {
            return getMeasurementsBySensor(stationName, aggregationOperator, parameter);
        }

        IStation station = getStationByName(stationName);
        if (station == null) {
            return statistics.clone();
        }

        for (ISensor sensor : station.getSensors()) {
            switch (aggregationOperator) {
                case AVG -> {
                    int measurementCount = 0;
                    double valuesSum = 0;

                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getTime().compareTo(startDate) > 0 && measurement.getTime().compareTo(endDate) < 0) {
                                valuesSum += measurement.getValue();
                                measurementCount++;
                            }
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            valuesSum / (double) measurementCount
                    ));
                }
                case COUNT -> {
                    int valueCount = 0;

                    for (IMeasurement iMeasurement : sensor.getMeasurements()) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getTime().compareTo(startDate) > 0 &&
                                    measurement.getTime().compareTo(endDate) < 0) {
                                valueCount++;
                            }
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            valueCount));
                }
                case MAX -> {
                    IMeasurement[] measurementArray = sensor.getMeasurements();
                    if (measurementArray == null) break;

                    measurementArray = getMeasurementsWithinDates(measurementArray, startDate, endDate);
                    if (measurementArray.length == 0) break;

                    double maxValue = measurementArray[0].getValue();
                    for (IMeasurement measurement : measurementArray) {
                        if (measurement.getValue() > maxValue) {
                            maxValue = measurement.getValue();
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            maxValue));
                }
                case MIN -> {
                    IMeasurement[] measurementArray = sensor.getMeasurements();
                    if (measurementArray == null) break;

                    measurementArray = getMeasurementsWithinDates(measurementArray, startDate, endDate);
                    if (measurementArray.length == 0) break;

                    double minValue = measurementArray[0].getValue();
                    for (IMeasurement iMeasurement : measurementArray) {
                        if (iMeasurement instanceof Measurement measurement) {
                            if (measurement.getValue() < minValue) {
                                minValue = measurement.getValue();
                            }
                        }
                    }

                    statistics = addStatistic(statistics, new Statistic(
                            sensor.getId(),
                            station.getName(),
                            minValue));
                }
            }
        }

        return statistics.clone();
    }

    //TODO: Comentar isto direito

    /**
     * Returns an array of measurements within the specified dates
     * // TODO Depois passa a função para cima, não deixes misturado com os overrides
     * @param measurements The array of {@link IMeasurement[] measurements}
     * @param startDate    ... {@link LocalDateTime startDate}
     * @param endDate      ... {@link LocalDateTime endDate}
     * @return Return an array of {@link IMeasurement}
     */
    private IMeasurement[] getMeasurementsWithinDates(IMeasurement[] measurements, LocalDateTime startDate, LocalDateTime endDate) {
        IMeasurement[] array = new IMeasurement[]{};

        for (IMeasurement iMeasurement : measurements) {
            if (iMeasurement instanceof Measurement measurement) {
                if (measurement.getTime().compareTo(startDate) > 0 && measurement.getTime().compareTo(endDate) < 0) {
                    array = addMeasurement(array, measurement);
                }
            }
        }

        return array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatistics[] getMeasurementsBySensor(String stationName, AggregationOperator aggregationOperator, Parameter parameter) {
        if (stationName == null || aggregationOperator == null || parameter == null) {
            throw new IllegalArgumentException("None of the method parameters can be null");
        }

        IStatistics[] statistics = new IStatistics[]{};
        IStation station = getStationByName(stationName);
        if (station == null) {
            return statistics.clone();
        }

        ISensor[] compatibleSensors = new ISensor[station.getSensors().length];
        int sensorCounter = 0;
        for (ISensor iSensor : station.getSensors()) {
            if (iSensor instanceof Sensor sensor) {
                if (sensor.getParameter().equals(parameter)) {
                    compatibleSensors[sensorCounter++] = sensor;
                }
            }
        }

        statistics = switch (aggregationOperator) {
            case AVG -> avgOfMeasurementsBySensor(compatibleSensors);
            case MIN -> minOfMeasurementBySensor(compatibleSensors);
            case MAX -> maxOfMeasurementsBySensor(compatibleSensors);
            case COUNT -> countOfMeasurementsBySensor(compatibleSensors);
        };

        return statistics.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", elements=" + nStations +
                '}';
    }
}