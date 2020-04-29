/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

import br.com.homologador.model.RegraNegocio;

public class TesteRegra extends RegraNegocio {

	private int situacao;
	private String conclusao;
	private String observacao;
	private String bug;
	private String dataHoraInicial;
	private String dataHoraFinal;

	public final int getSituacao() {
		return situacao;
	}

	public final void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public final String getConclusao() {
		return conclusao;
	}

	public final void setConclusao(String conclusao) {
		this.conclusao = conclusao;
	}

	public final String getDataHoraInicial() {
		return dataHoraInicial;
	}

	public final void setDataHoraInicial(String dataHoraInicial) {
		this.dataHoraInicial = dataHoraInicial;
	}

	public final String getDataHoraFinal() {
		return dataHoraFinal;
	}

	public final void setDataHoraFinal(String dataHoraFinal) {
		this.dataHoraFinal = dataHoraFinal;
	}

	public final String getObservacao() {
		return observacao;
	}

	public final void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public final String getBug() {
		return bug;
	}

	public final void setBug(String bug) {
		this.bug = bug;
	}
}
