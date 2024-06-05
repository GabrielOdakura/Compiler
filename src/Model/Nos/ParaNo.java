package Model.Nos;

import Model.Token;

public class ParaNo extends No{
    private final Token id;
    private final No inicial;
    private final No condicao;
    private final No update;
    private final No Bloco;

    public ParaNo(Token id, No inicial, No condicao, No update, No bloco) {
        this.id = id;
        this.inicial = inicial;
        this.condicao = condicao;
        this.update = update;
        Bloco = bloco;
    }
}
