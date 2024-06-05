package Model.Nos;

public class PreCondNo extends No{
    private final No condicao;
    private final No bloco;

    public PreCondNo(No condicao, No bloco) {
        this.condicao = condicao;
        this.bloco = bloco;
    }
}
