package br.com.jguedes.scarf.ambiente;

import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.agendamento.CadastradorDeAgendamento;
import br.com.jguedes.scarf.entidades.recurso.CadastradorDeRecurso;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.entidades.usuario.administrador.Administrador;
import br.com.jguedes.scarf.entidades.usuario.administrador.CadastradorDeAdministrador;
import br.com.jguedes.scarf.entidades.usuario.master.CadastradorDeMaster;
import br.com.jguedes.scarf.entidades.usuario.master.Master;
import br.com.jguedes.scarf.entidades.usuario.professor.CadastradorDeProfessor;
import br.com.jguedes.scarf.entidades.usuario.professor.Professor;

public class AmbienteParaCadastro {

	private Class<? extends Persistivel> classe;
	private Usuario user;

	/**
	 * Inicializa o ambiente para o usuario criar e cadastrar um persitivel
	 * */
	public AmbienteParaCadastro(Class<? extends Persistivel> classe,
			Usuario user) {

		this.classe = classe;

		this.user = user;

		mostrarTitulo();

		levantarCadastrador();

	}

	private void mostrarTitulo() {

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tAmbiente para cadastrar"
				+ classe.getSimpleName() + " por: "
				+ user.getNome().toUpperCase());
		System.out
				.println("*************************************************************************************************");

	}

	private void levantarCadastrador() {

		if (classe.equals(Recurso.class)) {
			new CadastradorDeRecurso(user);
		} else if (classe.equals(Master.class)) {
			new CadastradorDeMaster(user);
		} else if (classe.equals(Administrador.class)) {
			new CadastradorDeAdministrador(user);
		} else if (classe.equals(Professor.class)) {
			new CadastradorDeProfessor(user);
		} else if (classe.equals(Agendamento.class)) {
			new CadastradorDeAgendamento(user);
		}
	}

}
