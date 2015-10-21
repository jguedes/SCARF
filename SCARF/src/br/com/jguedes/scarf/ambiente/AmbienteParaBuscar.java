package br.com.jguedes.scarf.ambiente;

import java.util.List;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;
import br.com.jguedes.scarf.interfaces.Nomeavel;

public class AmbienteParaBuscar {

	private static final String ID = "ID";
	private static final String NOME = "NOME";
	private static final String CPF = "CPF";
	private static final String CADASTRADOR = "CAD";
	private static final String SAIR = "S";
	private Class<? extends Persistivel> classe;
	private Usuario user;

	public AmbienteParaBuscar(Class<? extends Persistivel> classe, Usuario user) {

		this.classe = classe;

		this.user = user;

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tServico de busca de " + classe.getSimpleName());
		System.out
				.println("*************************************************************************************************");

		do {

			// oferecer tipo de busca
			String porId = "\n\tbuscar pelo id - " + ID;
			String porNome = "\n\tbuscar por nome - " + NOME;
			String porCpf = "\n\tbuscar por cpf - " + CPF;
			String porCadastrador = "\n\tbuscar por cadastrador - "
					+ CADASTRADOR;
			String sair = "\n\tSair - " + SAIR;

			StringBuilder s = new StringBuilder();

			s.append("Que tipo de busca voce deseja realizar (Digite a sigla):");
			s.append(porId);

			if (classe.equals(Nomeavel.class)) {

				s.append(porNome);

				if (classe.equals(Usuario.class)) {

					s.append(porCpf);

				}
			} else if (classe.equals(Agendamento.class)) {

				s.append(porCadastrador);

			}

			s.append(sair);

			s.append("\nSigla: ");

			System.out.print(s.toString());

			// capturar sigla

			String sigla;

			boolean siglaValida;

			do {

				sigla = MeuScanner.getInstance().next();

				// averiguar sigla capturada

				siglaValida = esiglaValida(sigla);

			} while (!siglaValida);

			// averiguar se o usurio solicitou sair do servico de busca

			if (sigla.equals(sair)) {

				break;

			}

			// se o usuario nao solicitou sair levanta o tipo de busca

			levantarTipoDeBusca(sigla);

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Realizar outros tipos de pesquisa", "Terminar"));

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tServico de busca de " + classe.getSimpleName()
				+ " finalizado.");
		System.out
				.println("*************************************************************************************************");

	}

	private void levantarTipoDeBusca(String sigla) {

		switch (sigla.toUpperCase()) {
		case ID:

			buscarPorId();

			break;

		case NOME:

			buscarPorNome();

			break;

		case CPF:

			buscarPorCpf();

			break;

		case CADASTRADOR:

			buscarPorCadastradorDeAgendamento();

			break;

		default:
			break;
		}

	}

	private void buscarPorCadastradorDeAgendamento() {

		Usuario cadastrador;

		List<Agendamento> listaDeAgendamentosDoCadastrador;

		String nomeDeCadastrador;

		do {

			cadastrador = null;

			listaDeAgendamentosDoCadastrador = null;

			nomeDeCadastrador = null;

			System.out
					.println("\n\n********** Pesquisar Cadastrador de Agendamento **********");

			System.out.println("Digite o nome do cadastrador");

			nomeDeCadastrador = MeuScanner.getInstance().nextLine();

			cadastrador = ((Usuario) Crud.buscarPorNomeEspecifico(
					cadastrador.getClass(), nomeDeCadastrador));

			tituloResultadoDaBusca();

			if (cadastrador != null) {

				listaDeAgendamentosDoCadastrador = Crud
						.listarAgendamentosPorCadastrador(cadastrador);

				System.out.println("O nome " + nomeDeCadastrador
						+ " foi encontrado.");

				if (listaDeAgendamentosDoCadastrador != null) {

					System.out.println("Existe "
							+ listaDeAgendamentosDoCadastrador.size()
							+ " agendamentos realizados por ele: ");

					for (Agendamento a : listaDeAgendamentosDoCadastrador) {

						System.out.println(a.toString());

					}

				} else {

					System.out.println("Nao realizou agendamentos.");

				}

			} else {

				System.out.println("O nome de cadastrador ["
						+ nomeDeCadastrador + "] nao foi encotrado!");

			}

			tituloFimDeResultadoDeBusca();

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse("Nova pesquisa",
				"Sair"));
	}

	private void buscarPorCpf() {

		String cpf;

		Usuario encontrado;

		do {

			System.out.println("\n\n********** Pesquisar CPF **********");

			System.out.print("Digite o CPF para pesquisar: ");

			cpf = MeuScanner.getInstance().next();

			encontrado = Crud.buscarUsuarioPorCpf(cpf);

			tituloResultadoDaBusca();

			if (encontrado != null) {

				System.out.println("Cpf [" + cpf + "] encotrado:");

				System.out.println(encontrado.toExibir());

			} else {

				System.out.println("Nenhum Usuario relacionado ao Cpf [" + cpf
						+ "]");

			}

			tituloFimDeResultadoDeBusca();

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse("Nova busca",
				"Sair"));

	}

	private void buscarPorNome() {

		boolean buscarPorParteDoNome = MeuScanner.getInstance()
				.nextOpcaoTrueFalse("Buscar por Parte de nome",
						"Buscar por nome Especifico");

		if (buscarPorParteDoNome) {

			buscarPorParteDeNome();

		} else {

			buscarPorNomeEspecifico();

		}

	}

	private void buscarPorNomeEspecifico() {

		Nomeavel encontrado;

		String nome;

		do {

			System.out
					.println("\n\n********** Pesquisar nome Especifico **********");

			System.out.println("Digite o nome:");

			nome = MeuScanner.getInstance().nextLine();

			encontrado = Crud.buscarPorNomeEspecifico(classe, nome);

			tituloResultadoDaBusca();

			if (encontrado != null) {

				System.out.println("Nome de " + classe.getSimpleName()
						+ " encontrado:");

				System.out.println(encontrado.toString());

			} else {

				System.out
						.println("\n A pesquisa nao encotrou nome relacionados a: "
								+ nome);

			}

			tituloFimDeResultadoDeBusca();

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse("Nova busca",
				"Sair"));
	}

	private void buscarPorParteDeNome() {

		List<Nomeavel> lista;

		String nome;

		do {

			lista = null;

			System.out
					.println("\n\n********** Pesquisar Parte de nome **********");

			System.out
					.println("Digite o fragmento de nome que deseja pesquisar: ");

			nome = MeuScanner.getInstance().nextLine();

			lista = Crud.buscarPorParteDoNome(classe, nome);

			tituloResultadoDaBusca();

			if (lista != null) {

				System.out.println("\n" + lista.size() + " encotrados:");

				for (Nomeavel n : lista) {

					System.out.println(n.toString());

				}

			} else {

				System.out
						.println("\n A pesquisa nao encotrou nomes relacionados a: "
								+ nome);

			}

			tituloFimDeResultadoDeBusca();

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse("Nova busca",
				"Sair"));

	}

	private void buscarPorId() {

		Persistivel encontrado;

		long id;

		do {

			System.out.print("\nDigite o ID: ");

			id = MeuScanner.getInstance().nextInt();

			encontrado = Crud.buscarPorId(classe, id);

			tituloResultadoDaBusca();

			if (encontrado != null) {

				System.out.println(classe.getSimpleName() + " encotrado:");

				System.out.println(encontrado.toString());

			} else {

				System.out.println(classe.getSimpleName() + " com id = " + id
						+ " nao encotrado!!!");

			}

			tituloFimDeResultadoDeBusca();

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Buscar por outro ID", "sair"));

	}

	private void tituloFimDeResultadoDeBusca() {
		System.out.println("\n****************************************");
		System.out.println("******* Fim de resultado da busca ******");
		System.out.println("****************************************\n\n");
	}

	private void tituloResultadoDaBusca() {
		System.out.println("\n\n****************************************");
		System.out.println("********** Resultado da busca **********");
		System.out.println("****************************************");
	}

	private boolean esiglaValida(String sigla) {

		return (sigla.equalsIgnoreCase(ID) || sigla.equalsIgnoreCase(NOME)
				|| sigla.equalsIgnoreCase(CPF)
				|| sigla.equalsIgnoreCase(CADASTRADOR) || sigla
					.equalsIgnoreCase(SAIR));
	}
}
