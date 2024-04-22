import java.util.ArrayList;

public class TreeUtils {

    public TreeType checkTree(ArrayList<Token> lToken){
        int i = 0;
        TreeType tipoArvore = null;
        while(i <= lToken.size()) {
            switch (lToken.get(i).tipo) {
                case INT_LIT -> {


                }

                case BOOL_LIT -> {}

                case abreParenteses -> {}

                case fechaParenteses -> {}

                case MAIS, MULT, MENOS -> {
                    tipoArvore = TreeType.OPAR;
                }

                default -> {
                }
            }
            i++;
        }
        return tipoArvore;
    }
    public Arvore construirArvore(ArrayList<Token> listaTokens,TreeType tipoArvore){
        Arvore novaArvore = new Arvore();
        switch(tipoArvore){
            case OPAR ->{

            }
        }
    }
}
