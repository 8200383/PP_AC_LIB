package Monitoring.Coordinates.ValueObjects;

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
public class CartesianCoordinate implements ICartesianCoordinates {
    private final double xAxis;
    private final double yAxis;
    private final double zAxis;

    public CartesianCoordinate(double x, double y, double z) {
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
        return "CartesianCoordinate{\n" +
                "   xAxis=" + xAxis +
                ",  yAxis=" + yAxis +
                ",  zAxis=" + zAxis +
                "\n }";
    }
}
