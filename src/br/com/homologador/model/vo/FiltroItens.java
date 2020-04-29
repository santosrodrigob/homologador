/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

public class FiltroItens {
	
	private int codigoModulo;
	private int codigoFeature;
	private int codigoTipo;
	
	public final int getCodigoModulo() {
		return codigoModulo;
	}
	public final int getCodigoFeature() {
		return codigoFeature;
	}
	public final int getCodigoTipo() {
		return codigoTipo;
	}
	public final void setCodigoModulo(int codigoModulo) {
		this.codigoModulo = codigoModulo;
	}
	public final void setCodigoFeature(int codigoFeature) {
		this.codigoFeature = codigoFeature;
	}
	public final void setCodigoTipo(int codigoTipo) {
		this.codigoTipo = codigoTipo;
	}
}
