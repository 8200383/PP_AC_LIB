package Core.Enums;

import edu.ma02.core.enumerations.Parameter;
import edu.ma02.core.enumerations.Unit;

/*
 * Nome: Micael André Cunha Dias
 * Número: 8200383
 * Turma: LEI1T4
 *
 * Nome: Hugo Henrique Almeida Carvalho
 * Número: 8200590
 * Turma: LEI1T3
 */
public enum ParametersUnits {
    NO2(Unit.UG_M3),
    O3(Unit.UG_M3),
    PM2_5(Unit.UG_M3),
    PM10(Unit.UG_M3),
    SO2(Unit.UG_M3),
    C6H6(Unit.UG_M3),
    CO(Unit.MG_M3),
    LAEQ(Unit.DB),
    PA(Unit.MBAR),
    TEMP(Unit.DEGREE_CELSIUS),
    RU(Unit.UV),
    VD(Unit.DEGREE),
    VI(Unit.KM_H),
    HM(Unit.PERCENTAGE),
    PC(Unit.MM),
    RG(Unit.W_M2);

    private final Unit unit;

    ParametersUnits(Unit unit) {
        this.unit = unit;
    }

    private Unit getUnit() {
        return unit;
    }

    public static Unit getUnitByParameter(Parameter parameter) {
        return switch (parameter) {
            case NO2 -> NO2.getUnit();
            case O3 -> O3.getUnit();
            case PM2_5 -> PM2_5.getUnit();
            case PM10 -> PM10.getUnit();
            case SO2 -> SO2.getUnit();
            case C6H6 -> C6H6.getUnit();
            case CO -> CO.getUnit();
            case LAEQ -> LAEQ.getUnit();
            case PA -> PA.getUnit();
            case TEMP -> TEMP.getUnit();
            case RU -> RU.getUnit();
            case VD -> VD.getUnit();
            case VI -> VI.getUnit();
            case HM -> HM.getUnit();
            case PC -> PC.getUnit();
            case RG -> RG.getUnit();
        };
    }
}
