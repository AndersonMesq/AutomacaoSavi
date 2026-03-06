package AutomacaoSavi.AutoData;

public enum TipoAtoData {
    A_ESP("A.ESP", "13"),
    ANEST("A.ESP", "6"),
    ASS_RN_BER("ASS.RN.BER", "10"),
    ASS_RN_PARTO("ASS.RN.PARTO", "9"),
    AUX_ANEST("AUX.ANEST", "7"),
    CIR_OBST("CIR/OBST", "1"),
    CONS_HON("CONS/HON", "8"),
    D_AUX("D.AUX", "5"),
    PACOTE("PACOTE", "99"),
    PARECER("PARECER", "12"),
    PERF("PERF", "11"),
    P_AUX("1AUX", "2"),
    S_AUX("2AUX", "3"),
    T_AUX("3AUX", "4");

    private final String codigoExcel;
    private final String valueHtml;

    TipoAtoData(String codigoExcel, String valueHtml) {
        this.codigoExcel = codigoExcel;
        this.valueHtml = valueHtml;
    }

    public String getValueHtml() {
        return valueHtml;
    }

    public static TipoAtoData fromExcel(String codigo) {
        for (TipoAtoData tipo : values()) {
            if (tipo.codigoExcel.equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + codigo);
    }
}
