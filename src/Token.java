public class Token {
    public TokenType tipo;
    public char[] cadeia;

    public Token (TokenType tipo, char[] cadeiaRecebida){
        this.tipo = tipo;
        this.cadeia = new char[cadeiaRecebida.length];
        for(int i = 0; i < cadeiaRecebida.length; i++){
            cadeia[i] = cadeiaRecebida[i];
        }
    }

    @Override
    public String toString() {
        String teste = "";
        for(int i = 0; i < this.cadeia.length; i++) teste = teste + cadeia[i];
        return "Tipo: " + this.tipo + "\nCadeia: " + teste;
    }
}
