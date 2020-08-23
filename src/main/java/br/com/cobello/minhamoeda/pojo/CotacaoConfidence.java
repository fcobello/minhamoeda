package br.com.cobello.minhamoeda.pojo;

import lombok.Data;

@Data
public class CotacaoConfidence 
{
	private String uuid;
	private Integer cotacaoTimeout;
	private Long dataHoraCriacao;
	private Double cotacao;
	private Double cotacaoDolar;
	private Integer minimoOperavel;
	private Integer maximoOperavel;
	private Integer multiplicidade;
	private Object produto;
}
