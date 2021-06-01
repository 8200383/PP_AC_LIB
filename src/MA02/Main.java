package MA02;


import Utils.CustomArray;
import Monitoring.SensorFactory.Sensor;
import Utils.ICustomArray;
import edu.ma02.core.exceptions.MeasurementException;
import edu.ma02.core.exceptions.SensorException;

public class Main {
    public static void main(String[] args) throws SensorException, MeasurementException {

       /*  Sensor sensor = Sensor.SensorFactory(
                "ME00PA0001",
                -92781,
                -106663,
                0,
                38.70263097,
                -9.199692206
        ); */

        ICustomArray array = new CustomArray(10);
        Sensor[] sensors = (Sensor[]) array.getAll();

        for (Sensor o : sensors) {
            System.out.println(o.getType());
        }

        // Alternativa
        CustomArray arr = new CustomArray();
        for (Object o : arr.objects) {
            if (o instanceof Sensor) {
                Sensor s = (Sensor) o;
                System.out.println(s.getType());
            }
        }

    }
}
