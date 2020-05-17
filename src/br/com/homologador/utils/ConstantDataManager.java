/**
 * @author RodrigoBorges.
 * @date 01/03/2020
 */
package br.com.homologador.utils;

public final class ConstantDataManager
{
	public static final String BLANK = "";
	public static final String ZERO_STRING = "0";
	public static final String VIRGULA_STRING = ",";
	public static final String PONTO_VIRGULA = ";";
	public static final String SIMPLE_ASPAS = "'";
	public static final String ON = "on";

	//Parameters de Tela
	public static final String PARAMETER_LOGIN = "login";
	public static final String PARAMETER_SENHA = "senha";
	public static final String PARAMETER_CODIGO_MODULO = "codigoModulo";
	public static final String PARAMETER_CODIGO_FEATURE = "codigoFeature";
	public static final String PARAMETER_CODIGO_TIPO = "codigoTipo";
	public static final String PARAMETER_CODIGO_CASO_TESTE = "codigoCasoTeste";
	public static final String PARAMETER_CODIGO_REGRA_NEGOCIO = "codigoRegraNegocio";
	public static final String PARAMETER_CODIGO_COMPORTAMENTO = "codigoComportamento";
	public static final String PARAMETER_CODIGO_BUG = "codigoBug";
	public static final String PARAMETER_DESCRICAO_MODULO = "descricaoModulo";
	public static final String PARAMETER_DESCRICAO_FEATURE = "descricaoFeature";
	public static final String PARAMETER_DESCRICAO_TIPO = "descricaoTipo";
	public static final String PARAMETER_DESCRICAO_BUG = "bug";
	public static final String PARAMETER_DESCRICAO_REGRA_NEGOCIO = "descricaoRegra";
	public static final String PARAMETER_DESCRICAO_CASO_TESTE = "descricaoCasoTeste";
	public static final String PARAMETER_DESCRICAO_COMPORTAMENTO = "descricaoComportamento";
	public static final String PARAMETER_CODIGO_LEIA_ME = "codigoLeiaMe";
	public static final String PARAMETER_DESCRICAO_LEIA_ME = "descricaoLeiaMe";
	public static final String PARAMETER_VERSAO = "versao";
	public static final String PARAMETER_INATIVO = "inativo";
	public static final String PARAMETER_CODIGO_TESTE = "codigoTeste";
	public static final String PARAMETER_OBSERVACAO = "observacao";
	public static final String PARAMETER_CONCLUSAO = "conclusao";
	public static final String PARAMETER_CODIGO = "codigo";
	public static final String PARAMETER_SITUACAO = "situacao";
	public static final String PARAMETER_SITUACAO_REGRA = "situacaoRegra";
	public static final String PARAMETER_SITUACAO_CASO_TESTE = "situacaoCaso";
	public static final String PARAMETER_SITUACAO_COMPORTAMENTO = "situacaoComportamento";
	public static final String PARAMETER_TESTE_FEATURE = "testeFeature";
	public static final String PARAMETER_TIPO_BUG = "tipo";
	public static final String PARAMETER_FILTRAR = "filtrar";
	public static final String PARAMETER_RELATORIO = "relatorio";
	public static final String PARAMETER_SOLICITANTE = "solicitante";

	public static final String PARAMETER_COMBO_CODIGO_MODULO = "modulo";
	public static final String PARAMETER_COMBO_CODIGO_FEATURE = "feature";
	public static final String PARAMETER_COMBO_CODIGO_TIPO = "tipo";

	//Objetos
	public static final String CADASTRO_TESTE_OBJETO_TESTE = "teste";
	public static final String CADASTRO_TESTE_ATRIBUTOS_OBJETO_CASO_TESTE = "testeCasoTeste";
	public static final String CADASTRO_TESTE_ATRIBUTOS_OBJETO_REGRA_NEGOCIO = "testeRegra";
	public static final String CADASTRO_TESTE_ATRIBUTOS_OBJETO_COMPORTAMENTO = "testeComportamento";
	public static final String CADASTRO_MODULO_OBJETO_MODULO = "modulo";
	public static final String CADASTRO_FEATURE_OBJETO_FEATURE = "feature";
	public static final String CADASTRO_TIPO_OBJETO_TIPO = "tipo";

	public static final String OBJETO_REGRA_NEGOCIO = "regraNegocio";
	public static final String OBJETO_CASO_TESTE = "casoTeste";
	public static final String OBJETO_COMPORTAMENTO = "comportamento";

	public static final String CADASTRO_BUG_OBJETO_CASO_TESTE = "bugsCasoTeste";
	public static final String CADASTRO_BUG_OBJETO_REGRA = "bugsRegra";
	public static final String CADASTRO_BUG_OBJETO_COMPORTAMENTO = "bugsComportamento";

	public static final String OBJETO_SESSAO_USUARIO_LOGADO = "usuarioLogado";

	public static final String OBJETO_COMBO_MODULOS = "modulos";
	public static final String OBJETO_COMBO_FEATURES = "features";
	public static final String OBJETO_COMBO_TIPOS = "tipos";
	public static final String OBJETO_LEIA_ME = "leiaMe";
	public static final String OBJETO_GET_ULTIMO_FILTRO = "filtro";

	public static final String OBJETO_LISTA_BUGS = "bugs";
	public static final String OBJETO_LISTA_CASOS_TESTE = "listaCasos";
	public static final String OBJETO_LISTA_REGRA_NEGOCIO = "listaRegras";
	public static final String OBJETO_LISTA_COMPORTAMENTOS = "listaComportamentos";
	public static final String OBJETO_LISTA_MODULOS = "modulos";
	public static final String OBJETO_LISTA_FEATURES = "features";
	public static final String OBJETO_LISTA_TIPOS = "tipos";
	public static final String OBJETO_LISTA_TESTES = "testes";
	public static final String OBJETO_LISTA_LEIA_ME = "leiames";
	
	//Mensagens 
	public static final String MESSAGE = "message";
	public static final String MESSAGE_SESSAO = "messageSession";
	//Erro
	public static final String MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO = "Dados obrigatarios nao informado!";
	public static final String MESSAGE_PROBLEMAS_SALVAR_ALTERAR = "Nu00E3o foi possivel Salvar os Dados!";
	public static final String MESSAGE_PROBLEMAS_INATIVAR = "Nu00E3o foi possivel Inativar!";
	public static final String MESSAGE_PROBLEMAS_LOGIN = "Problemas ao Logar com o Usuario Informado!";
	public static final String MESSAGE_PROCESSO_FINALIZADO_ERRO = "Processo finalizado com Erro";
	//Sucesso
	public static final String MESSAGE_DADOS_ALTERADOS_SUCESSO = "Dados Alterados Com Sucesso!";
	public static final String MESSAGE_INATIVAR_SUCESSO = "Inativado Com Sucesso!";
	public static final String MESSAGE_USUARIO_LOGADO_SUCESSO = "Usuario Logado com Sucesso!";
	public static final String MESSAGE_PROCESSO_FINALIZADO = "Processo Finalizado com Sucesso.";
}