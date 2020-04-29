/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

import br.com.homologador.model.vo.TesteItens;

public class Teste extends Tipo {

	private int codigoTeste;
	private int situacaoCasoTeste;
	private int situacaoRegra;
	private int situacaoComportamento;
	private String conclusao;
	private String observacao;
	
	private List<TesteItens> itens = new ArrayList<TesteItens>();
	
	public final int getCodigoTeste() {		
		return this.codigoTeste;
	}
	
	public final void setCodigoTeste(int codigoTeste) {
		this.codigoTeste = codigoTeste;
	}

	public final int getSituacaoCasoTeste() {
		return situacaoCasoTeste;
	}

	public final void setSituacaoCasoTeste(int situacaoCasoTeste) {
		this.situacaoCasoTeste = situacaoCasoTeste;
	}

	public final int getSituacaoRegra() {
		return situacaoRegra;
	}

	public final void setSituacaoRegra(int situacaoRegra) {
		this.situacaoRegra = situacaoRegra;
	}

	public final int getSituacaoComportamento() {
		return situacaoComportamento;
	}

	public final void setSituacaoComportamento(int situacaoComportamento) {
		this.situacaoComportamento = situacaoComportamento;
	}

	public final String getConclusao() {
		return conclusao;
	}

	public final void setConclusao(String conclusao) {
		this.conclusao = conclusao;
	}

	public final String getObservacao() {
		return observacao;
	}

	public final void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public final List<TesteItens> getItens() {
		return this.itens;
	}
	
	public void add(TesteItens item) {
		this.itens.add(item);
	}
}
