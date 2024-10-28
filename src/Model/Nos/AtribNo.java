package Model.Nos;

import Model.Token;

public class AtribNo extends No{
    private final Token tipo;
    private final Token id;
    private final No valor;

    public AtribNo(Token tipo, Token id, No valor){
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
    }
}
