package Core.Coordinates;

import edu.ma02.core.interfaces.ICartesianCoordinates;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class CartesianCoordinates implements ICartesianCoordinates {
    private final double xAxis;
    private final double yAxis;
    private final double zAxis;

    /**
     * Constructor for {@link CartesianCoordinates}
     *
     * @param x The value of the {@link Double x} axe
     * @param y The value of the {@link Double y} axe
     * @param z The value of the {@link Double z} axe
     */
    public CartesianCoordinates(double x, double y, double z) {
        xAxis = x;
        yAxis = y;
        zAxis = z;
    }

    @Override
    public double getX() {
        return xAxis;
    }

    @Override
    public double getY() {
        return yAxis;
    }

    @Override
    public double getZ() {
        return zAxis;
    }

    @Override
    public String toString() {
        return "CartesianCoordinates{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", zAxis=" + zAxis +
                '}';
    }
}
