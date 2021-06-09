package Quickchart;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */

/**
 * Enumeration for Chart Types
 */
public enum ChartType {
    LINE, BAR;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        switch (this) {
            case BAR -> {
                return "bar";
            }
            case LINE -> {
                return "line";
            }
        }

        return null;
    }
}
