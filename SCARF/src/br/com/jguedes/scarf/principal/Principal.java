package br.com.jguedes.scarf.principal;

import br.com.jguedes.scarf.ambiente.AmbienteParaUsuarioEscolherGerenciamento;
import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class Principal {

	private static Usuario user;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		iniciarPrograma();

		finalizarPrograma();

	}

	private static void iniciarPrograma() {

		mensagemDeEntrada();

		interagirComUsuario();

	}

	private static void mensagemDeEntrada() {

		System.out
				.println("*************************************************************************************************");

		System.out.println("\t\tSistema de Controle de Recursos da Fatec-Pe");

		System.out
				.println("*************************************************************************************************");

	}

	private static void interagirComUsuario() {

		criarInstanciaDeUsuario();

		while (MeuScanner.getInstance().nextOpcaoTrueFalse("Entrar",
				"Encerrar sistema")) {

			if (identificarUsuario()) {

				iniciarAmbienteDeUsuario();

			} else {

				System.out.println("\n!!! Login ou senha invalidos !!!\n");

				continue;

			}

		}

	}

	private static void criarInstanciaDeUsuario() {

		user = new Usuario() {

			@Override
			public void setLogin(String login) {

				super.setLogin(login);

			}

			@Override
			public void setSenha(String senha) {

				super.setSenha(senha);

			}

			@Override
			public void setCadastrador(Usuario cadastrador) {

			}

			@Override
			public Usuario getCadastrador() {

				return null;

			}

			@Override
			public String toExibir() {
				// TODO Auto-generated method stub
				return null;
			}

		};

	}

	private static boolean identificarUsuario() {

		Usuario aux = null;

		solicitarLoginESenha();

		aux = Crud.identificarUsuario(user);

		boolean identificado = aux != null;

		if (identificado) {

			user = aux;

		}

		return identificado;

	}

	private static void solicitarLoginESenha() {

		System.out.println("\nPara entrar, idenfique-se.");

		System.out.print("Login: ");
		user.setLogin(MeuScanner.getInstance().next());

		System.out.print("Senha: ");
		user.setSenha(MeuScanner.getInstance().next());

	}

	/**
	 * Chama a inicializacao do ambiente para interacao do usuario com os
	 * servicos do programa.
	 * */
	private static void iniciarAmbienteDeUsuario() {

		new AmbienteParaUsuarioEscolherGerenciamento(user);

	}

	private static void finalizarPrograma() {

		System.out.println("\n\nPrograma Encerrado.\n");

	}
}
