package com.sistemabancario.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContaTest {
    
    //R01
    @Test
    void testSetNumeroValido() {
        final Conta instance = new Conta();
        final String esperado = "12345-6";
        instance.setNumero(esperado);
        final String obtido = instance.getNumero();
        assertEquals(esperado, obtido);
    }
    
    //R01
    @Test
    void testSetNumeroInvalidoNaoArmazena() {
        final Conta instance = new Conta();
        final String invalido = "123";
        assertThrows(IllegalArgumentException.class, () -> instance.setNumero(invalido));
        final String obtido = instance.getNumero();
        assertNotEquals(invalido, obtido);
    }
    
    //R02
    @Test
    void testInstanciaPadraoPoupanca() {
        final Conta instance = new Conta();
        assertFalse(instance.isPoupanca());
    }
    
    //R03
    @Test
    void testSetLimiteContaEspecial() {
        final Conta instance = new Conta();
        instance.setEspecial(true);
        final double esperado = 1000;
        instance.setLimite(esperado);
        final double obtido = instance.getLimite();
        assertEquals(esperado, obtido);
    }
    
    //R03
    @Test
    void testSetLimiteContaNaoEspecial() {
        final Conta instance = new Conta();
        final double limite = 1000;
        assertThrows(IllegalArgumentException.class, () -> instance.setLimite(limite));
    }
    
    //R04
    @Test
    void testHistoricoNotNull() {
        final Conta instance = new Conta();
        assertNotNull(instance.getMovimentacoes());
    }
    
    //R06
    @Test
    void testGetSaldoTotal() {
        final double limite = 500;
        final double esperado = limite;
        final Conta instance = new Conta();
        instance.setEspecial(true);
        instance.setLimite(limite);
        final double obtido = instance.getLimite();
        assertEquals(esperado, obtido);
    }
    
    @Test
    void testDepositoDinheiro() {
        final double limite = 500.4, deposito = 1000.6, esperado = 1501;
        final Conta instance = new Conta();
        instance.setEspecial(true);
        instance.setLimite(limite);
        instance.depositoDinheiro(deposito);
        final double obtido = instance.getSaldoTotal();
        assertEquals(esperado, obtido, 0.001);
    }
    
    @Test
    void testDepositoDinheiroTipoCredito() {
        final Conta instance = new Conta();
        instance.depositoDinheiro(200.0);
        final List <Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao movimentacao = movimentacoes.get(0);
        final char tipoEsperado = 'C';
        final char tipObtido = movimentacao.getTipo() ;
        assertEquals(tipoEsperado, tipObtido);
    }
    
    @Test
    void testDepositoDinheiroConfirmado() {
        final Conta instance = new Conta();
        instance.depositoDinheiro(300);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao movimentacao = movimentacoes.get(0);
        final boolean esperado = true;
        final boolean obtido = movimentacao.isConfirmada();
        assertEquals(esperado, obtido);
    }
    
    @Test
    void testDepositoDinheiroSemValorAtribuido() {
        final double deposito = 0.0;
        final Conta instance = new Conta();
        assertThrows(IllegalArgumentException.class, () -> instance.depositoDinheiro(deposito));
    }
    
    @Test
    void testDepositoDinheiroIncluidoNaLista() {
        //Precisa fazer teste
        final Conta instance = new Conta();
        instance.depositoDinheiro(400);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao movimentacao = movimentacoes.get(movimentacoes.size() - 1);
        final double esperado = 400;
        final double obtido = movimentacao.getValor();
        assertEquals(esperado, obtido);
    }
    
    @Test
    void testAddMovimentacaoCredito() {
        final Conta instance = new Conta();
        final Movimentacao movimentacao = new Movimentacao(instance);
        movimentacao.setConfirmada(true);
        movimentacao.setTipo('C');
        final double esperado = 100.50;
        movimentacao.setValor(esperado);
        instance.addMovimentacao(movimentacao);
        assertEquals(esperado, instance.getSaldoTotal());
    }
    
    @Test
    void testAddMovimentacaoDebito() {
        final Conta instance = new Conta();
        final Movimentacao movimentacao = new Movimentacao(instance);
        movimentacao.setConfirmada(true);
        movimentacao.setTipo('D');
        final double valor = 100.50; 
        final double esperado = -valor;
        movimentacao.setValor(valor);
        instance.addMovimentacao(movimentacao);
        assertEquals(esperado, instance.getSaldoTotal());
    }
}
