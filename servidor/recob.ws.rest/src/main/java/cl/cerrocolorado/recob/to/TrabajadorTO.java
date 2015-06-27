package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.BaseTO;

public class TrabajadorTO extends BaseTO
{
    private PersonaTO persona;
    private String  cargo;
    private Boolean tieneCursoBloqueo;
    private Boolean vigente;
    private EmpresaTO empresa;

    public PersonaTO getPersona()
    {
        return persona;
    }

    public void setPersona(PersonaTO persona)
    {
        this.persona = persona;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getTieneCursoBloqueo() {
        return tieneCursoBloqueo;
    }

    public void setTieneCursoBloqueo(Boolean tieneCursoBloqueo) {
        this.tieneCursoBloqueo = tieneCursoBloqueo;
    }

    public EmpresaTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaTO empresa) {
        this.empresa = empresa;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }
}
