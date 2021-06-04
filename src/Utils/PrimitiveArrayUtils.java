package Utils;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class PrimitiveArrayUtils {

    public static Object[] grow(Object[] array) {
        Object[] copy = new Object[array.length * 2];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }
}
