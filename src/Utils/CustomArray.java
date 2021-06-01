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
public class CustomArray implements ICustomArray {

    private final int DEFAULT_CAPACITY = 10;

    public Object[] objects;
    private int elements = 0;

    public CustomArray(int initialCapacity) {
        if (initialCapacity > 0) {
            objects = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            objects = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public CustomArray() {
        objects = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return objects.length;
    }

    @Override
    public boolean isEmpty() {
        return elements == 0;
    }

    @Override
    public boolean isFull() {
        return elements == objects.length;
    }

    @Override
    public Object getAll() {
        return objects;
    }

    @Override
    public void add(Object o) {
        if (isFull()) {
            grow();
        }

        objects[elements++] = o;
    }

    private void grow() {
        Object[] copy = new Object[objects.length * 2];
        System.arraycopy(objects, 0, copy, 0, objects.length);
        objects = copy;
    }
}
