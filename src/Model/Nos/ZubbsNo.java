package Model.Nos;

import Model.Token;

public class ZubbsNo extends No{
    private final Token tipo;
    private final No bloco;

    public ZubbsNo(Token tipo, No bloco){
        this.tipo = tipo;
        this.bloco = bloco;
    }

}
