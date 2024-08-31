package ar.edu.utn.frbb.tup.controller.dto;

public class ClienteDto extends PersonaDto {
    private String tipoPersona;
    private String banco;

    public String getTipoPersona() {
        return tipoPersona;
    }

    public ClienteDto setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
        return this;
    }

    public String getBanco() {
        return banco;
    }

    public ClienteDto setBanco(String banco) {
        this.banco = banco;
        return this;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "ClienteDto => " + tipoPersona + " " + banco;
    }
}
