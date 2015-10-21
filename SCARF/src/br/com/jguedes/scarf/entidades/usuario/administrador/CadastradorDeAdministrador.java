package br.com.jguedes.scarf.entidades.usuario.administrador;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class CadastradorDeAdministrador {

	private boolean abortar;

	public CadastradorDeAdministrador(Usuario userQueCadastra) {

		Administrador administradorParaCadastrar;

		do {

			administradorParaCadastrar = administradorGeradoPeloUsuario();

			if (abortar) {

				return;

			}

			Crud.salvarUsuario(administradorParaCadastrar,
					userQueCadastra);

		} while (cadastrarOutroAdministrador());

	}

	private boolean cadastrarOutroAdministrador() {

		int op;

		do {

			System.out
					.print("\n\t1 - Cadastrar outro administrador.\n\t2 - Sair\nOpcao: ");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

	private Administrador administradorGeradoPeloUsuario() {

		Administrador a = new Administrador();

		a.setNome(nomeInformadoPeloUsuario());
		if (!abortar) {
			a.setCpf(cpfInformadoPeloUsuario());
			if (!abortar) {
				a.setLogin(loginInformadoPeloUsuario());
				if (!abortar) {
					a.setSenha(senhaInformadoPeloUsuario());
					if (!abortar) {
						a.setCargo(cargoInformadoPeloUsuario());
					}
				}
			}
		}

		if (abortar)
			return null;

		return a;

	}

	private String cargoInformadoPeloUsuario() {
		// CAMPO nao OBRIGATORIO, PODE SER NULO

		String cargo;

		if (continuar("cargo")) {

			cargo = MeuScanner.getInstance().nextLine();

		} else {

			abortar = true;

			return null;
		}

		return cargo;

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
