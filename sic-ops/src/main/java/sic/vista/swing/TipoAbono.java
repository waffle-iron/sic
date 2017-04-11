package sic.vista.swing;

public enum TipoAbono {
    
    PAGO("Pago "),
    
    GASTO("Gasto");

    private final String text;

    private TipoAbono(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
