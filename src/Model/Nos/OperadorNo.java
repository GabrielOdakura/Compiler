package Model.Nos;

import Model.Token;

public class OperadorNo extends No{
    private final No esq;
    private final Token operador;
    private final No dir;

    public OperadorNo(No esq, Token op, No dir){
        this.esq = esq;
        this.operador = op;
        this.dir = dir;
    }
}
