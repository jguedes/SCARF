package br.com.jguedes.scarf.entidades.usuario.administrador;

import br.com.jguedes.scarf.entidades.usuario.Usuario;

public class Administrador extends Usuario {

	private String cargo;

	public Administrador() {

	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String toExibir() {

		StringBuilder s = new StringBuilder();

		s.append(super.toExibir());
		s.append("\n\t");
		s.append("Cargo:");
		s.append(cargo);
		s.append("\n");

		return s.toString();

	}

}
