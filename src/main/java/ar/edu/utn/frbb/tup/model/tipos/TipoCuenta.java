package ar.edu.utn.frbb.tup.model.tipos;

public enum TipoCuenta {

    CUENTA_CORRIENTE,
    CAJA_AHORRO,
    CA$, CC$, CAU$S;

    public static boolean isValid(String checkStr) {
        return checkStr.equals("CA$") ||
                checkStr.equals("CC$") ||
                checkStr.equals("CAU$S");
    }

    public String getDescription() {
        if (this.equals(CA$))
            return "CA$";
        if (this.equals(CC$))
            return "CC$";
        if (this.equals(CAU$S))
            return "CAU$S";
        return null;
    }

}
