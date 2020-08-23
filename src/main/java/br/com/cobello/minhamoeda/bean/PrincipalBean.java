package br.com.cobello.minhamoeda.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.fasterxml.jackson.core.JsonParseException;

import br.com.cobello.minhamoeda.pojo.CotacaoConfidence;
import br.com.cobello.minhamoeda.pojo.Moeda;
import br.com.cobello.minhamoeda.rest.RestService;
import br.com.cobello.minhamoeda.util.JSONUtil;
import lombok.Data;

@ManagedBean
@Data
public class PrincipalBean
{
	List<Moeda> moedas;

	StringBuilder sb = new StringBuilder();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@PostConstruct
	public void init()
	{
		try
		{
			moedas = avaliaCotacoes();
		} catch (JsonParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Moeda> cotarConfidence() throws JsonParseException
	{
		CotacaoConfidence cotacao = JSONUtil.jsonToObject(new RestService().send(
				"https://www2.confidencecambio.com.br/api/v1/cotacoes?idProduto=61&idCidade=4854",
				"ecommerce.confidence|ECommerce|null|2760|MCwCFHR9v54Zh3NSStFTH6qZQNcjRZbwAhRBcNjWyU7ufGJw62q/uN8acQL+vg=="),
				CotacaoConfidence.class, false);
		List<Moeda> moeda = new ArrayList<Moeda>();

		moeda.add(new Moeda("BRL", "USD", cotacao.getCotacaoDolar(), "Confidence Câmbio"));
		moeda.add(new Moeda("BRL", "ARS", cotacao.getCotacao(), "Confidence Câmbio"));

		return moeda;
	}

	private List<Moeda> cotarBancoDeLaNacion()
	{
		List<Moeda> moedas = new ArrayList<Moeda>();
		int indexof;
		Double valor;
		String html = new RestService().send("https://www.bna.com.ar/");

		indexof = html.indexOf("U.S.A");
		html = html.substring(indexof);
		indexof = html.indexOf("<td>");
		html = html.substring(indexof);
		valor = Double.valueOf(html.substring(4, 11).replace(",", "."));
		moedas.add(new Moeda("ARS", "USD", valor, "Banco de La Nacion"));
		indexof = html.indexOf("Real");
		html = html.substring(indexof);
		indexof = html.indexOf("<td>");
		html = html.substring(indexof);
		valor = Double.valueOf(html.substring(4, 11).replace(",", ".")) / 100;
		moedas.add(new Moeda("BRL", "ARS", 1 / valor, "Banco de La Nacion"));

		return moedas;
	}

	private Double desconto(Double valor)
	{
		return valor - (valor * 0.01);
	}

	public List<Moeda> avaliaCotacoes() throws JsonParseException, InterruptedException
	{
		List<Moeda> moedas = new ArrayList<>();

		moedas.addAll(cotarConfidence());
		moedas.addAll(cotarBancoDeLaNacion());

		 System.out.println("Trocar Reais por Peso (Brasil): R$5000,00 -> ARS" +
		 5000/moedas.get(1).getCotacao());
		 System.out.println("Trocar Reais por Dolar(Brasil): R$5000,00 -> USD" +
		 5000/moedas.get(0).getCotacao());
		 System.out.println("Trocar Dolar por Peso (Argentina): UDS" +
		 (5000/moedas.get(0).getCotacao()) + " -> ARS" +
		 (5000/moedas.get(0).getCotacao())*moedas.get(2).getCotacao());
		 System.out.println("Trocar Reais por Peso (Argentina): R$5000,00 -> ARS" +
		 5000/moedas.get(3).getCotacao());

		return moedas;
	}

	public static void main(String[] args) throws JsonParseException, InterruptedException
	{
		PrincipalBean pb = new PrincipalBean();

		pb.avaliaCotacoes();
	}
}
