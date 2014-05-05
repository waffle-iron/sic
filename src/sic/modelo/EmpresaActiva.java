package sic.modelo;

public class EmpresaActiva {

    private static EmpresaActiva INSTANCE = new EmpresaActiva();
    private Empresa empresa;

    private EmpresaActiva() {
    }

    public static EmpresaActiva getInstance() {
        return INSTANCE;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
