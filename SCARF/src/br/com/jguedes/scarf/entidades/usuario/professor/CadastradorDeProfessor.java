package br.com.jguedes.scarf.entidades.usuario.professor;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class CadastradorDeProfessor {

	private boolean abortar;

	public CadastradorDeProfessor(Usuario userQueCadastra) {

		Professor professorParaCadastrar;

		do {

			professorParaCadastrar = professorGeradoPeloUsuario();

			if (abortar) {

				return;

			}

			Crud.salvarUsuario(professorParaCadastrar, userQueCadastra);

		} while (cadastrarOutroProfessor());

	}

	private boolean cadastrarOutroProfessor() {

		int op;

		do {

			System.out
					.print("\n\t1 - Cadastrar outro professor.\n\t2 - Sair\nOpcao: ");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

	private Professor professorGeradoPeloUsuario() {

		Professor p = new Professor();

		p.setNome(nomeInformadoPeloUsuario());
		if (!abortar) {
			p.setCpf(cpfInformadoPeloUsuario());
			if (!abortar) {
				p.setLogin(loginInformadoPeloUsuario());
				if (!abortar) {
					p.setSenha(senhaInformadoPeloUsuario());
					if (!abortar) {
						p.setDisciplina(disciplinaInformadoPeloUsuario());
					}
				}
			}
		}

		if (abortar)
			return null;

		return p;

	}

	private String disciplinaInformadoPeloUsuario() {
		// CAMPO Nao OBRIGATORIO, PODE SER NULO

		String disciplina;

		if (continuar("disciplina")) {

			disciplina = MeuScanner.getInstance().nextLine();

		} else {

			abortar = true;

			return null;
		}

		return disciplina;

	}

	private String senhaInformadoPeloUsuario() {
		// CAMPO OBRIGATORIO NAO PODE SER NULO

		String senha;

		do {

			if (continuar("senha")) {

				senha = MeuScanner.getInstance().next();

			} else {

				abortar = true;

				return null;
			}

		} while (senha == null);

		return senha;

	}

	private String loginInformadoPeloUsuario() {
		// CAMPO OBRIGATORIO NAO PODE SER NULO

		String login;

		do {

			if (continuar("login")) {

				login = MeuScanner.getInstance().next();

			} else {

				abortar = true;

				return null;
			}

		} while (login == null);

		return login;

	}

	private String cpfInformadoPeloUsuario() {
		// CAMPO OBRIGATORIO NAO PODE SER NULO

		String cpf;

		do {

			if (continuar("cpf")) {

				cpf = MeuScanner.getInstance().next();

			} else {

				abortar = true;

				return null;
			}

		} while (cpf == null);

		return cpf;

	}

	private String nomeInformadoPeloUsuario() {
		// CAMPO OBRIGATORIO NAO PODE SER NULO

		String nome;

		do {

			if (continuar("nome")) {

				nome = MeuScanner.getInstance().nextLine();

			} else {

				abortar = true;

				return null;
			}

		} while (nome == null);

		return nome;

	}

	private boolean continuar(String campo) {

		int op;

		do {

			System.out.println("\n\t1 - Digitar " + campo + "\n\t2 - Abortar");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		if (op == 2) {

			return false;

		}

		System.out.print("Digite o " + campo + ": ");

		return true;

	}

}
