package Storage.Exceptions;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class KeyNotFound extends Exception {

    public KeyNotFound() {
        super("Specified Key not found in JsonObject");
    }

    public KeyNotFound(String message) {
        super(message);
    }
}
