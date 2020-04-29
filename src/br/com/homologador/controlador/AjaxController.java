/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.homologador.model.Feature;
import br.com.homologador.model.Tipo;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.TipoServices;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.TipoServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

@WebServlet(name = "ajax", urlPatterns = { "/ajax" })
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxController() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = null;

		Map<String, Object> result = new TreeMap<String, Object>();
		Gson gson = new Gson();
		String json = null;
				
		final String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		final String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
				
		List<Tipo> tipos = null;
		List<Feature> features = null;
		try
		{

			Integer codigoModulo = 0;
			Integer codigoFeature = 0;
			
			if(StringUtils.isLong(codigoModuloParameter)) {
				
				connection = connectionFactory.getConnectionMySQL();
				FeatureServices featureServices = new FeatureServicesImpl(connection);

				codigoModulo = Integer.valueOf(codigoModuloParameter);
				features = featureServices.getComboFeaturesByModulo(codigoModulo);
				features.sort(Comparator.comparing(f -> f.getDescricaoFeature()));
				result.put(ConstantDataManager.OBJETO_COMBO_FEATURES, features);
			}

			if(StringUtils.isLong(codigoModuloParameter) && StringUtils.isLong(codigoFeatureParameter)) {

				connection = connectionFactory.getConnectionMySQL();
				TipoServices tipoServices = new TipoServicesImpl(connection);

				codigoFeature = Integer.valueOf(codigoFeatureParameter);
				tipos = tipoServices.getComboTiposByFeature(codigoModulo, codigoFeature);
				tipos.sort(Comparator.comparing(t -> t.getDescricaoTipo()));
				result.put(ConstantDataManager.OBJETO_COMBO_TIPOS, tipos);
			}
			
			json = gson.toJson(result);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}

		response.setContentType("appication/json");
		response.getWriter().print(json);
	}
}
