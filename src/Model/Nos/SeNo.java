package Model.Nos;

public class SeNo extends No{
    private final No condicao;
    private final No blocoThen;
    private final No blocoElse;

    public SeNo(No condicao, No blocoThen, No blocoElse){
        this.condicao = condicao;
        this.blocoThen = blocoThen;
        this.blocoElse = blocoElse;
    }
}
