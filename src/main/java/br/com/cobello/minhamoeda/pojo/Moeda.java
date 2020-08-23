package br.com.cobello.minhamoeda.pojo;

import br.com.cobello.minhamoeda.util.Flag;
import lombok.Data;

@Data
public class Moeda 
{
	private String moedaOrigem;
	private String moedaDestino;
	private Double cotacao;
	private String observacao;
	
	public Moeda (String moedaOrigem, String moedaDestino, Double cotacao, String observacao)
	{
		this.moedaOrigem = moedaOrigem;
		this.moedaDestino = moedaDestino;
		this.cotacao = cotacao;
		this.observacao = observacao;
	}
	
	public String getFlagOrigem()
	{
		return Flag.valueOf(moedaOrigem).icon();
	}
	
	public String getFlagDestino()
	{
		return Flag.valueOf(moedaDestino).icon();
	}
}
