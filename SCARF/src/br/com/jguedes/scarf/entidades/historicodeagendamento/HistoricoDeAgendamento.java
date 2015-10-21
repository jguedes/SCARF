package br.com.jguedes.scarf.entidades.historicodeagendamento;

import java.util.Date;

import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.usuario.Usuario;

public class HistoricoDeAgendamento implements Persistivel {

	private long Id;
	private String acao;
	private Date dataEHora;

	public HistoricoDeAgendamento() {

	}

	@Override
	public long getId() {

		return Id;

	}

	@Override
	public void setId(long Id) {

		this.Id = Id;

	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Date getDataEHora() {
		return dataEHora;
	}

	public void setDataEHora(Date dataEHora) {
		this.dataEHora = dataEHora;
	}

	@Override
	public void setCadastrador(Usuario cadastrador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario getCadastrador() {
		// TODO Auto-generated method stub
		return null;
	}

}
