public class Arvore {
    No raiz;
    int qtdNos;

    void inserirNovo(No novoNo){
        No aux = raiz;
        //se quiser esq:
        //aux = raiz.esq;

        //se quiser dir:
        //aux = raiz.dir;

        if(aux != null){
            while(!estaVazioEsq(aux) || !estaVazioDir(aux)) aux = aux.esq;
        }
        //while(!estaVazioEsq(aux) || !estaVazioDir(aux)) aux = aux.esq;
        //while(!estaVazioEsq(aux) || !estaVazioDir(aux)) aux = aux.dir;
        //while(!estaVazioEsq(aux)) aux = aux.esq;
        //while(!estaVazioDir(aux)) aux = aux.dir;
    }
    boolean estaVazioEsq(No pai){
        return pai.esq == null;
    }
    boolean estaVazioDir(No pai){
        return pai.dir == null;
    }
}
