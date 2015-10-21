package br.com.jguedes.scarf.entidades.agendamento;

import java.util.Date;
import java.util.Set;

import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;

public class Agendamento implements Persistivel {

	private long id;
	private Date datain;
	private Date datafin;
	private String descricao;
	private char status = 'a';
	private Set<Recurso> recursos = null;
	private Usuario agendador;

	public Agendamento() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatain() {
		return datain;
	}

	public void setDatain(Date datain) {
		this.datain = datain;
	}

	public Date getDatafin() {
		return datafin;
	}

	public void setDatafin(Date datafin) {
		this.datafin = datafin;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {

		if ((status == 'a') || (status == 'e') || (status == 'd')) {

			this.status = status;

		}
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
	}

	@Override
	public void setCadastrador(Usuario agendador) {

		this.agendador = agendador;

	}

	@Override
	public Usuario getCadastrador() {

		return agendador;

	}

	@Override
	public String toString() {

		StringBuilder s = new StringBuilder();

		s.append("Agendamento realizado pelo ");
		s.append(agendador.getClass().getSimpleName().toLowerCase());
		s.append(" ");
		s.append(agendador.getNome());
		s.append("\n\t");
		s.append("ID:");
		s.append(id);
		s.append("\n\t");
		s.append("descricao:");
		s.append(descricao);
		s.append("\n\t");
		s.append("status:");
		s.append(status);
		s.append("\n\t");
		s.append("Data de entrega:");
		s.append(datain);
		s.append("\n\t");
		s.append("Data de devolucao:");
		s.append(datafin);
		s.append("\n\t");
		s.append("Recursos locados:");

		for (Recurso r : recursos) {

			s.append(r.getNome());
			s.append(", ");

		}

		s.delete(s.length() - 2, s.length());

		s.append("\n");

		return s.toString();

	}

}
