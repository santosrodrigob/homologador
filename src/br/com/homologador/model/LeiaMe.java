/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class LeiaMe extends Feature {
	
	private int codigoLeiaMe;
	private String descricaoLeiaMe;
	private String solicitante;
	private String versao;
	
	List<LeiaMe> leiames = new ArrayList<LeiaMe>();

	public int getCodigoLeiaMe() {
		return this.codigoLeiaMe;
	}
	public void setCodigoLeiaMe(int codigoLeiaMe) {
		this.codigoLeiaMe = codigoLeiaMe;
	}
	public String getDescricaoLeiaMe() {
		return this.descricaoLeiaMe;
	}
	public void setDescricaoLeiaMe(String descricaoLeiaMe) {
		this.descricaoLeiaMe = descricaoLeiaMe;
	}
	public String getVersao() {
		return this.versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}	
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	
	public void add(LeiaMe leiaMe) {
		this.leiames.add(leiaMe);
	}
	
	public final List<LeiaMe> getLeiames() {
		return this.leiames;
	}
}