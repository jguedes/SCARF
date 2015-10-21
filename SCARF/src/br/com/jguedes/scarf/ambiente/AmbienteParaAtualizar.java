package br.com.jguedes.scarf.ambiente;

import java.util.List;
import java.util.Set;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.entidades.usuario.administrador.Administrador;
import br.com.jguedes.scarf.entidades.usuario.master.Master;
import br.com.jguedes.scarf.entidades.usuario.professor.Professor;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class AmbienteParaAtualizar {

	private Usuario user;

	public AmbienteParaAtualizar(Class<? extends Persistivel> classe,
			Usuario user) {

		this.user = user;

		Persistivel persistivelParaAtualizar = null;

		long id;

		new StringBuilder();

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tAtualizacao de " + classe.getSimpleName()
				+ " iniciado por " + user.getNome().toUpperCase());
		System.out
				.println("*************************************************************************************************");

		boolean op = MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Digitar ID",
				"Visualizar lista de " + classe.getSimpleName()
						+ " para escolher ID");

		if (!op) {

			new AmbienteParaListar(classe, user);

		}

		System.out.print("Digite o ID do " + classe.getSimpleName() + ": ");

		id = MeuScanner.getInstance().nextInt();

		persistivelParaAtualizar = Crud.buscarPorId(classe, id);

		if (persistivelParaAtualizar.getClass().equals(Recurso.class)) {

			persistivelParaAtualizar = atualizarRecurso(((Recurso) persistivelParaAtualizar));

		} else if (persistivelParaAtualizar.getClass().equals(Usuario.class)) {

			persistivelParaAtualizar = atualizarUsuario(((Usuario) persistivelParaAtualizar));

		} else if (persistivelParaAtualizar.getClass()
				.equals(Agendamento.class)) {

			persistivelParaAtualizar = atualizarAgendamento(((Agendamento) persistivelParaAtualizar));

		}

		Crud.atulizar(persistivelParaAtualizar);

		// Field[] campos = persistivelParaAtualizar.getClass()
		// .getDeclaredFields();
		//
		// s.append("\n\nEscolha o campo que deseja alterar (digite o numero a esquerda:\n");
		//
		// for (int i = 0; i < campos.length; i++) {
		//
		// s.append("\n\t");
		// s.append(i + 1);
		// s.append(" - ");
		// s.append(campos[i].getName().toUpperCase());
		//
		// }
		//
		// s.append("\n");
		// s.append("Campo desejado: ");
		//
		// System.out.print(s.toString());
		//
		// do {
		//
		// campoParaAlterar = MeuScanner.getInstance().nextInt();
		//
		// } while (campoParaAlterar >= campos.length);
		//
		// System.out.println("O Campo escolhido foi: "
		// + campos[campoParaAlterar].getName().toUpperCase());
		//
		// System.out.print("Digite o novo valor do campo:");
		//
		// dadoAlterado = MeuScanner.getInstance().nextLine();
		//
		// System.out.print("O novo valor do campo "
		// + campos[campoParaAlterar].getName().toUpperCase() + " e': "
		// + dadoAlterado);

	}

	private Persistivel atualizarRecurso(Recurso recurso) {

		int campo;

		do {

			System.out.println("\n\nEscolha o campo que deseja alterar: ");
			System.out.println("\t1 - nome\n");
			System.out.println("\t2 - descricao\n");
			System.out.println("\t3 - capacidade\n");

			campo = MeuScanner.getInstance().nextInt();

			if (campo == 1) {

				System.out.print("Digite o novo nome: ");
				recurso.setNome(MeuScanner.getInstance().nextLine());

			} else if (campo == 2) {

				System.out.print("Digite a nova descricao: ");
				recurso.setDescricao(novaDescricao());

			} else if (campo == 3) {

				System.out.print("Digite a nova capacidade: ");
				recurso.setCapacidade(MeuScanner.getInstance().nextInt());

			}

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Alterar outro campo", "Terminar"));
		return recurso;
	}

	private Persistivel atualizarUsuario(Usuario usuario) {
		int campo;
		do {
			System.out.println("\n\nEscolha o campo que deseja alterar: ");
			System.out.println("\t1 - nome\n");
			System.out.println("\t2 - cpf\n");
			System.out.println("\t3 - rg\n");
			System.out.println("\t4 - email\n");
			if (usuario.getClass().equals(Professor.class)) {

				System.out.println("\t5 - disciplina\n");

			} else if (usuario.getClass().equals(Administrador.class)) {

				System.out.println("\t5 - cargo\n");

			} else if (usuario.getClass().equals(Master.class)) {

				System.out.println("\t5 - sobre o master\n");

			}

			campo = MeuScanner.getInstance().nextInt();

			if (campo == 1) {

				System.out.print("Digite o novo nome: ");
				usuario.setNome(MeuScanner.getInstance().nextLine());

			} else if (campo == 2) {

				System.out.print("Digite o novo cpf: ");
				usuario.setCpf(MeuScanner.getInstance().nextLine());

			} else if (campo == 3) {

				System.out.print("Digite o novo rg: ");
				usuario.setRg(MeuScanner.getInstance().nextLine());

			} else if (campo == 4) {

				System.out.print("Digite o novo email: ");
				usuario.setEmail(MeuScanner.getInstance().nextLine());

			} else if (campo == 4) {

				if (usuario.getClass().equals(Professor.class)) {

					System.out.print("Digite a nova disiciplina: ");
					((Professor) usuario).setDisciplina(MeuScanner
							.getInstance().nextLine());

				} else if (usuario.getClass().equals(Administrador.class)) {

					System.out.print("Digite o novo cargo: ");
					((Administrador) usuario).setCargo(MeuScanner.getInstance()
							.nextLine());

				} else if (usuario.getClass().equals(Master.class)) {

					System.out.print("Digite o novo sobre o master: ");
					((Master) usuario).setSobreOMaster(MeuScanner.getInstance()
							.nextLine());

				}
			}

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Alterar outro campo", "Terminar"));
		return null;
	}

	private Persistivel atualizarAgendamento(Agendamento agendamento) {
		int campo;
		do {
			System.out.println("\n\nEscolha o campo que deseja alterar: ");
			System.out.println("\t1 - descricao\n");
			System.out.println("\t2 - data de entrada\n");
			System.out.println("\t3 - data de devolucao\n");
			System.out.println("\t4 - recursos locados\n");
			System.out.println("\t5 - status\n");
			campo = MeuScanner.getInstance().nextInt();
			if (campo == 1) {

				agendamento.setDescricao(novaDescricao());

			} else if (campo == 2) {

				agendamento.setDatain(MeuScanner.getInstance()
						.nextDateWithHour());

			} else if (campo == 3) {

				agendamento.setDatafin(MeuScanner.getInstance()
						.nextDateWithHour());

			} else if (campo == 4) {

				agendamento.setRecursos(novaListaDeRecursos(agendamento
						.getRecursos()));

			} else if (campo == 5) {

				agendamento.setStatus(novoStatus());

			}

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Alterar outro campo", "Terminar"));
		return null;
	}

	private char novoStatus() {
		boolean op;

		op = MeuScanner.getInstance().nextOpcaoTrueFalse("status ENTREGUE",
				"status DEVOLVIDO");

		return (op ? 'e' : 'd');
	}

	private Set<Recurso> novaListaDeRecursos(Set<Recurso> set) {

		boolean op;

		long id;

		System.out.println("Atuais recursos locados para este agendamento");

		new AmbienteParaListar(List.class.cast(set), user);

		op = MeuScanner.getInstance().nextOpcaoTrueFalse("Inserir Recurso",
				"Remover Recurso");

		if (op) {

			do {

				System.out.println("Inserir mais recursos");

				System.out.println("Digite o id para inserir");

				id = MeuScanner.getInstance().nextInt();

				set.add((Recurso) Crud.buscarPorId(Recurso.class, id));

			} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
					"Inserir outro recurso", "Terminar"));
		} else {

			do {

				System.out.println("Remover recursos");

				System.out.println("Digite o id para remover");

				id = MeuScanner.getInstance().nextInt();

				set.remove((Recurso) Crud.buscarPorId(Recurso.class, id));

			} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
					"Remover outro recurso", "Terminar"));

		}

		return set;

	}

	private String novaDescricao() {

		return MeuScanner.getInstance().nextLine();

	}
}
