package br.com.jguedes.scarf.entidades.usuario.professor;

import br.com.jguedes.scarf.entidades.usuario.Usuario;

public class Professor extends Usuario {

	private String disciplina;

	public Professor() {

	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String toExibir() {

		StringBuilder s = new StringBuilder();
		//
		// s.append("\n");
		// s.append(getClass().getSimpleName() + ":");
		// s.append("\n\t");
		// s.append("ID:");
		// s.append(getId());
		// s.append("\n\t");
		// s.append("Nome:");
		// s.append(getNome());
		// s.append("\n\t");
		// s.append("CPF:");
		// s.append(getCpf());
		s.append(super.toExibir());
		s.append("\n\t");
		s.append("Disciplina:");
		s.append(disciplina);
		s.append("\n");

		return s.toString();

	}
}
