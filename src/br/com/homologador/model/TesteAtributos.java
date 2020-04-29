/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

import br.com.homologador.model.vo.BugCasoTeste;
import br.com.homologador.model.vo.BugComportamento;
import br.com.homologador.model.vo.BugRegra;
import br.com.homologador.model.vo.TesteCasoTeste;
import br.com.homologador.model.vo.TesteComportamento;
import br.com.homologador.model.vo.TesteRegra;

public class TesteAtributos extends Tipo {

	private int codigoTeste;

	List<TesteCasoTeste> casos = new ArrayList<TesteCasoTeste>();
	List<TesteRegra> regras = new ArrayList<TesteRegra>();
	List<TesteComportamento> comportamentos = new ArrayList<TesteComportamento>();
	List<BugCasoTeste> bugCasos = new ArrayList<BugCasoTeste>();
	List<BugRegra> bugRegras = new ArrayList<BugRegra>();
	List<BugComportamento> bugComportamentos = new ArrayList<BugComportamento>();
	
	public final int getCodigoTeste() {		
		return this.codigoTeste;
	}
	
	public final void setCodigoTeste(int codigoTeste) {
		this.codigoTeste = codigoTeste;
	}

	public final List<TesteRegra> getRegras() {
		return this.regras;
	}
	
	public final List<TesteCasoTeste> getCasos() {
		return this.casos;
	}
	
	public final List<TesteComportamento> getComportamentos() {
		return this.comportamentos;
	}
	
	public final List<BugCasoTeste> getBugCasos() {
		return this.bugCasos;
	}

	public final List<BugRegra> getBugRegras() {
		return this.bugRegras;
	}

	public final List<BugComportamento> getBugComportamentos() {
		return this.bugComportamentos;
	}
	
	public void addBugCaso(BugCasoTeste bugCaso) {
		this.bugCasos.add(bugCaso);
	}
	
	public void addBugRagra(BugRegra bugRegra) {
		this.bugRegras.add(bugRegra);
	}
	
	public void addBugComportamento(BugComportamento bugComportamento) {
		this.bugComportamentos.add(bugComportamento);
	}

	public void addRegra(TesteRegra regra) {
		this.regras.add(regra);
	}
	
	public void addCaso(TesteCasoTeste caso) {
		this.casos.add(caso);
	}
	
	public void addComportamento(TesteComportamento comportamento) {
		this.comportamentos.add(comportamento);
	}
}
