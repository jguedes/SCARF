package br.com.jguedes.scarf.entidades.recurso;

import java.util.Date;

import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.interfaces.Nomeavel;

public class Recurso implements Persistivel, Nomeavel {

	private long id;
	private String nome;
	private String descricao;
	private Date datadecadastro;
	private long capacidade;
	private Usuario cadastrador;

	// private Set<Agendamento> agendamentos = null;

	public Recurso() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDatadecadastro() {
		return datadecadastro;
	}

	public void setDatadecadastro(Date datadecadastro) {
		this.datadecadastro = datadecadastro;
	}

	public long getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(long capacidade) {
		this.capacidade = capacidade;
	}

	//
	// public Set<Agendamento> getAgendamentos() {
	// return agendamentos;
	// }
	//
	// public void setAgendamentos(Set<Agendamento> agendamentos) {
	// this.agendamentos = agendamentos;
	// }

	public Usuario getCadastrador() {
		return cadastrador;
	}

	public void setCadastrador(Usuario cadastrador) {
		this.cadastrador = cadastrador;
	}

	@Override
	public String toString() {

		StringBuilder s = new StringBuilder();

		s.append("\n");
		s.append("Recurso:");
		s.append("\n\t");
		s.append("Id = ");
		s.append(id);
		s.append("\n\t");
		s.append("Nome = ");
		s.append(nome);
		s.append("\n\t");
		s.append("Descricao: ");
		s.append(descricao);
		s.append("\n\t");
		s.append("Capacidade = ");
		s.append(capacidade);
		s.append("\n\t");
		s.append("Cadastrado em ");
		s.append(datadecadastro);
		s.append("\n\t");
		s.append("Cadastrado por ");
		s.append(cadastrador.getNome());
		s.append("\n");

		return s.toString();

	}

	@Override
	public boolean equals(Object obj) {

		return ((Recurso) obj).getId() == id;

	}
}
