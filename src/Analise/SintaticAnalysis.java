package Analise;

import Exceptions.SintaticalException;
import Model.Nos.*;
import Model.Token;
import Model.TokenType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SintaticAnalysis {
    private final Iterator<Token> tokens;
    private Token tokenAtual;

    public SintaticAnalysis(LinkedList<Token> listaDeTokens){
        this.tokens = listaDeTokens.iterator();
        tokenAtual = tokens.next();
    }
    public No parse(){
        return parseZubbs();
    }

    //passa pro próximo token se ele tem um próximo Token
    private void proximo(){
        if(tokens.hasNext()){
            tokenAtual = tokens.next();
        }
    }

    //verifica se o tipo de Token é igual ao tipo recebido
    private Token tokenEsperado(TokenType tipo){
        if(tokenAtual.getTipo() == tipo){
            Token tokenAux = tokenAtual;
            proximo();
            return tokenAux;
        }else{
            throw new SintaticalException("Token Esperado: " + tipo + " | Token Encontrado: " + tokenAtual.getTipo());
        }
    }

    private No parseZubbs(){
        Token tipoToken = tokenEsperado(TokenType.INT);
        tokenEsperado(TokenType.PRINCIPAL);
        tokenEsperado(TokenType.ABRE_PARENTESES);
        tokenEsperado(TokenType.FECHA_PARENTESES);
        No bloco = parseBloco();
        return new ZubbsNo(tipoToken, bloco);
    }

    private No parseBloco() {
        tokenEsperado(TokenType.PF);
        List<No> cmds = new ArrayList<>();
        while(tokenAtual.getTipo() != TokenType.OBG){
            cmds.add(parseCmd());
        }
        tokenEsperado(TokenType.OBG);
        return new BlocoNo(cmds);
    }

    private No parseCmd() {
        switch (tokenAtual.getTipo()) {
            case INT, ID -> {
                return parseAtrib();
            }
            case MAIS, MENOS, MULT -> {
                return parseOperacao();
            }
            case SE -> {
                return parseSe();
            }
            case PARA, ENQUANTO, FACA -> {
                return parseLoop();
            }
            default -> {
                throw new SintaticalException("Token não esperado: " + tokenAtual);
            }
        }
    }

    private No parseAtrib () {
        Token tipoToken = null;
        if(tokenAtual.getTipo() == TokenType.INT){
            tipoToken = tokenAtual;
            proximo();
        }else if (tokenAtual.getTipo() == TokenType.BOOL){
            tipoToken = tokenAtual;
            proximo();
        }else if (tokenAtual.getTipo() == TokenType.CAR){
            tipoToken = tokenAtual;
            proximo();
        }
        Token idToken = tokenEsperado(TokenType.ID);
        tokenEsperado(TokenType.IGUAL);
        No valor;
        if(tokenAtual.getTipo() == TokenType.INT){
            valor = new NumeroNo(tokenAtual);
            proximo();
            tokenEsperado(TokenType.SEMICOLON);
        }else{
            valor = parseOperacao();
            tokenEsperado(TokenType.SEMICOLON);
            //___________________________________________________________________________________ver se precisa de ver se tem ;
        }
        return new AtribNo(tipoToken, idToken, valor);
    }

    private No parseOperacao () {
        No esq = new NumeroNo(tokenEsperado(TokenType.INT_LIT));
        TokenType tipoAtual = tokenAtual.getTipo();
        while(tipoAtual == TokenType.MAIS || tipoAtual == TokenType.MENOS || tipoAtual == TokenType.MULT){
            Token operador = tokenAtual;
            proximo();
            No dir = new NumeroNo(tokenEsperado(TokenType.INT_LIT));
            esq = new OperadorNo(esq,operador,dir);
        }
        return esq;
    }

    private No parseSe () {
        tokenEsperado(TokenType.SE);
        tokenEsperado(TokenType.ABRE_PARENTESES);
        No condicao = parseTermo();
        tokenEsperado(TokenType.FECHA_PARENTESES);
        tokenEsperado(TokenType.ENTAO);
        No entaoBloco = parseBloco();
        No ouBloco = null;
        if(tokenAtual.getTipo() == TokenType.SENAO){
            proximo();
            ouBloco = parseBloco();
        }
        return new SeNo(condicao,entaoBloco,ouBloco);
    }

    private No parseLoop () {
        switch(tokenAtual.getTipo()){
            case PARA ->{
                return parsePara();
            }
            case ENQUANTO -> {
                return parsePosCond();
            }
            case FACA ->{
                return parsePreCond();
            }
            default -> throw new SintaticalException("Token inesperado " + tokenAtual.getTipo());
        }
    }

    private No parsePara() {
        tokenEsperado(TokenType.PARA);
        tokenEsperado(TokenType.ABRE_PARENTESES);
        Token idToken = tokenEsperado(TokenType.ID);
        tokenEsperado(TokenType.IGUAL);
        No atribId = parseAtrib();
        tokenEsperado(TokenType.SEMICOLON);
        No condicao = parseTermo();
        tokenEsperado(TokenType.SEMICOLON);
        No repeticao = parseAtrib();
        tokenEsperado(TokenType.FECHA_PARENTESES);
        No bloco = parseBloco();
        return new ParaNo(idToken, atribId, condicao,repeticao,bloco);
    }


    private No parsePosCond() {
        tokenEsperado(TokenType.ENQUANTO);
        tokenEsperado(TokenType.ABRE_PARENTESES);
        No condicao = parseTermo();
        tokenEsperado(TokenType.FECHA_PARENTESES);
        tokenEsperado(TokenType.FACA);
        No bloco = parseBloco();
        return new PosCondNo(condicao, bloco);
    }
    private No parsePreCond() {
        tokenEsperado(TokenType.FACA);
        No bloco = parseBloco();
        tokenEsperado(TokenType.ENQUANTO);
        tokenEsperado(TokenType.ABRE_PARENTESES);
        No condicao = parseTermo();
        tokenEsperado(TokenType.FECHA_PARENTESES);
        tokenEsperado(TokenType.SEMICOLON);
        return new PreCondNo(condicao, bloco);
    }

    private No parseTermo(){
        No esq = parseVar();
        Token relacao = tokenAtual;
        switch(relacao.getTipo()){
            case MENORIGUAL, MAIORIGUAL, IGUALIGUAL -> {
                proximo();
            }
            default -> throw new SintaticalException("Operador relacional não encontrado, encontrado: " + relacao.getTipo());
        }
        No dir = parseVar();
        return new TermoNo(esq,relacao,dir);
    }

    private No parseVar(){
        switch (tokenAtual.getTipo()){
            case INT_LIT -> {
                return new NumeroNo(tokenEsperado(TokenType.INT_LIT));
            }
            case CAR_LIT -> {
                return new CarNo(tokenEsperado(TokenType.CAR_LIT));
            }
            case BOOL_LIT -> {
                return new BoolNo(tokenEsperado(TokenType.BOOL_LIT));
            }
            case ID ->{
                return new IdNo(tokenEsperado(TokenType.ID));
            }
            default->{
                throw new SintaticalException("Token inesperado, token atual: " + tokenAtual.getTipo());
            }
        }
    }
}
