/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DadosCriacao {

	private int codigoUsuarioCriacao;
	private int codigoUsuarioAlteracao;
	private String usuarioCriacao;
	private String usuarioAlteracao;
	private LocalDate dataCriacao;
	private LocalDate dataAlteracao;
	private String dataCriacaoFormatada;
	private String dataAlteracaoFormatada;
	
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public final int getCodigoUsuarioCriacao() {
		return codigoUsuarioCriacao;
	}
	public final void setCodigoUsuarioCriacao(int codigoUsuarioCriacao) {
		this.codigoUsuarioCriacao = codigoUsuarioCriacao;
	}
	public final int getCodigoUsuarioAlteracao() {
		return codigoUsuarioAlteracao;
	}
	public final void setCodigoUsuarioAlteracao(int codigoUsuarioAlteracao) {
		this.codigoUsuarioAlteracao = codigoUsuarioAlteracao;
	}
	public final String getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public final void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public final String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public final void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public String getDataCriacaoFormatada() {
		if(this.dataCriacao != null) {
			this.dataCriacaoFormatada = formatador.format(this.dataCriacao);
		}
		return this.dataCriacaoFormatada;
	}
	public void setDataCriacaoFormatada(String dataCriacaoFormatada) {
		this.dataCriacaoFormatada = dataCriacaoFormatada;
	}
	public String getDataAlteracaoFormatada() {
		if(this.dataAlteracao != null) {
			this.dataAlteracaoFormatada = formatador.format(this.dataAlteracao);
		}
		return this.dataAlteracaoFormatada;
	}
	public void setDataAlteracaoFormatada(String dataAlteracaoFormatada) {
		this.dataAlteracaoFormatada = dataAlteracaoFormatada;
	}
}
