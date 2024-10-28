package Model;

public enum TokenType {
    /*Palavras Chave*/ PRINCIPAL, RETORNAR, BOOL, CAR, INT, FACA, ENQUANTO, PARA, SE, SENAO, ENTAO, OU, PF, OBG, EOF,
    /*Identificadores*/ ID,
    /*Operadores*/ MAIS, MENOS, MULT, ABRE_PARENTESES, FECHA_PARENTESES, IGUAL, MAIORIGUAL, MENORIGUAL, IGUALIGUAL,
    /*Literais*/ INT_LIT, CAR_LIT, BOOL_LIT,
    /*Separadores*/ SEMICOLON,
}
