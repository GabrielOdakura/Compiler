package Model.Nos;

public class PosCondNo extends No{
    private final No condicao;
    private final No bloco;

    public PosCondNo(No condicao, No bloco) {
        this.condicao = condicao;
        this.bloco = bloco;
    }
}
