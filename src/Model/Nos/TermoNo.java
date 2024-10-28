package Model.Nos;

import Model.Token;

public class TermoNo extends No{
    private final No esq;
    private final Token relacao;
    private No dir;

    public TermoNo(No esq, Token relacao, No dir) {
        this.esq = esq;
        this.relacao = relacao;
        this.dir = dir;
    }
}
