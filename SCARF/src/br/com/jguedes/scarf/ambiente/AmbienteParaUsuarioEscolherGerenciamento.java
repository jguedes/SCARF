package br.com.jguedes.scarf.ambiente;

import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.recurso.GerenciamentoDePersitivel;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.entidades.usuario.administrador.Administrador;
import br.com.jguedes.scarf.entidades.usuario.master.Master;
import br.com.jguedes.scarf.entidades.usuario.professor.Professor;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

/**
 * Classe para iniciar a interacao do usuario com os servicos de gerenciamento
 * do programa.
 * */
public class AmbienteParaUsuarioEscolherGerenciamento {

	private static final String G_MASTER = "GMA";
	private static final String G_RECURSO = "GRE";
	private static final String G_USUARIO = "GRU";
	private static final String G_PROFESSOR = "GPR";
	private static final String G_ADMNIST = "GAD";
	private static final String G_AGENDAMENTO = "GAG";
	private static final String SAIR = "S";

	/**
	 * Inicia a interacao do usuario com os gerenciamentos do programa.
	 * */
	public AmbienteParaUsuarioEscolherGerenciamento(Usuario user) {

		String sigla;

		mostrarMensagemBemVindo(user);

		do {

			exibirGerenciamentosPermitidos(user.getClass());

			sigla = siglaDeGerenciamento();

			if (sigla.equals(SAIR)) {

				break;

			}

			iniciarGerenciamento(user, sigla);

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Continuar no sistema", "Logoff"));

	}

	private void mostrarMensagemBemVindo(Usuario user) {

		System.out.println("Olá " + user.getClass().getSimpleName() + " "
				+ user.getNome().toUpperCase() + ". Seja BemVindo!");

	}

	/**
	 * Este metodo cria uma String para exibicao na tela dos gerenciamentos e
	 * suas respectivas siglas. Recebe como parametro uma classe do tipo Usuario
	 * para estabelecer permissoes. Somente Usuario Master tem permissao full.
	 * */
	private void exibirGerenciamentosPermitidos(Class<? extends Usuario> classe) {

		StringBuilder s = new StringBuilder();

		String g_ma = "Gerencimento de Master - ".concat(G_MASTER);
		String g_re = "Gerencimento de Recurso - ".concat(G_RECURSO);
		String g_us = "Gerencimento de Usuario - ".concat(G_USUARIO);
		String g_ad = "Gerencimento de Administrador - ".concat(G_ADMNIST);
		String g_pr = "Gerencimento de Professor - ".concat(G_PROFESSOR);
		String g_ag = "Gerencimento de Agendamento - ".concat(G_AGENDAMENTO);
		String sair = "Logoff - ".concat(SAIR);

		/*
		 * Montar a string conforme permissoes.
		 */
		switch (classe.getSimpleName()) {

		case "Master":// permissao full.

			s.append("\t");
			s.append(g_ma);
			s.append("\n\t");
			s.append(g_re);
			s.append("\n\t");
			s.append(g_us);
			s.append("\n\t");
			s.append(g_ad);
			s.append("\n\t");
			s.append(g_pr);
			s.append("\n\t");
			s.append(g_ag);

			break;

		case "Administrador":

			s.append("\t");
			s.append(g_re);
			s.append("\n\t");
			s.append(g_ad);
			s.append("\n\t");
			s.append(g_pr);
			s.append("\n\t");
			s.append(g_ag);

			break;

		case "Professor":

			s.append("\t");
			s.append(g_ag);

			break;

		default:
			break;
		}

		s.append("\n\t");
		s.append(sair);

		exibirNaTela(s);

	}

	private void exibirNaTela(StringBuilder s) {

		StringBuilder exibir = new StringBuilder();

		exibir.append("Servicos oferecidos:\n");
		exibir.append(s);
		exibir.append("\nDigite a sigla do gerencimento desejado: ");

		System.out.print(exibir.toString());

	}

	private String siglaDeGerenciamento() {

		String sigla;

		do {

			sigla = MeuScanner.getInstance().next();

		} while (siglaInvalida(sigla));

		return sigla.toUpperCase();

	}

	private boolean siglaInvalida(String sigla) {

		return !(sigla.equalsIgnoreCase(G_ADMNIST)
				|| sigla.equalsIgnoreCase(G_AGENDAMENTO)
				|| sigla.equalsIgnoreCase(G_MASTER)
				|| sigla.equalsIgnoreCase(G_PROFESSOR)
				|| sigla.equalsIgnoreCase(G_RECURSO)
				|| sigla.equalsIgnoreCase(G_USUARIO) || sigla
					.equalsIgnoreCase(SAIR));

	}

	/**
	 * Chama a inicilizacao do servico de gerenciamento escolhido pelo usuario.
	 * */
	private void iniciarGerenciamento(Usuario user, String sigla) {

		switch (sigla) {

		case G_MASTER:

			new GerenciamentoDePersitivel(Master.class, user);

			break;

		case G_RECURSO:

			new GerenciamentoDePersitivel(Recurso.class, user);

			break;

		case G_USUARIO:

			new GerenciamentoDePersitivel(Usuario.class, user);

			break;

		case G_ADMNIST:

			new GerenciamentoDePersitivel(Administrador.class, user);

			break;

		case G_PROFESSOR:

			new GerenciamentoDePersitivel(Professor.class, user);

			break;

		case G_AGENDAMENTO:

			new GerenciamentoDePersitivel(Agendamento.class, user);

			break;

		default:
			break;
		}
	}
}
