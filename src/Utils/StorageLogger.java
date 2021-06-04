package Utils;

import java.util.logging.Logger;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public class StorageLogger {
    public static final Logger logger = Logger.getLogger(Storage.IO.class.getName());

    public static void warning(StackTraceElement[] stackTrace, String cause) {
        logger.warning("[" + stackTrace[0].getClassName() + "] " + cause);
    }
}
