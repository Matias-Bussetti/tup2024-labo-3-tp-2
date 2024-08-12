package ar.edu.utn.frbb.tup.model.tipos;

public enum TipoMoneda {
    PESOS,
    DOLARES;

    public String getDescription() {
        if (this.equals(DOLARES))
            return "dolares";
        if (this.equals(PESOS))
            return "dolares";
        return null;
    }

}
