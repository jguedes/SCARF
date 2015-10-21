package br.com.jguedes.scarf.entidades.usuario.master;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class CadastradorDeMaster {
	private boolean abortar;

	public CadastradorDeMaster(Usuario userQueCadastra) {

		Master masterParaCadastrar;

		do {

			masterParaCadastrar = masterGeradoPeloUsuario();

			if (abortar) {

				return;

			}

			Crud.salvarUsuario(masterParaCadastrar, userQueCadastra);

		} while (cadastrarOutroMaster());

	}

	private boolean cadastrarOutroMaster() {

		int op;

		do {

			System.out
					.print("\n\t1 - Cadastrar outro master.\n\t2 - Sair\nOpcao: ");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

	private Master masterGeradoPeloUsuario() {

		Master m = new Master();

		m.setNome(nomeInformadoPeloUsuario());
		if (!abortar) {
			m.setCpf(cpfInformadoPeloUsuario());
			if (!abortar) {
				m.setLogin(loginInformadoPeloUsuario());
				if (!abortar) {
					m.setSenha(senhaInformadoPeloUsuario());
				}
			}
		}

		if (abortar)
			return null;

		return m;

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
