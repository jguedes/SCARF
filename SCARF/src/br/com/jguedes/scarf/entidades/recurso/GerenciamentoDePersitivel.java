package br.com.jguedes.scarf.entidades.recurso;

import br.com.jguedes.scarf.ambiente.AmbienteParaAtualizar;
import br.com.jguedes.scarf.ambiente.AmbienteParaBuscar;
import br.com.jguedes.scarf.ambiente.AmbienteParaCadastro;
import br.com.jguedes.scarf.ambiente.AmbienteParaDeletar;
import br.com.jguedes.scarf.ambiente.AmbienteParaListar;
import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

/**
 * Cria um ambiente para o usuario escolher qual dos servicos oferecidos pelo
 * gerenciamento de um persistivel.
 * */
public class GerenciamentoDePersitivel {

	private static final String CRUD_CADASTRAR = "CAD";
	private static final String CRUD_LISTAR = "LIS";
	private static final String CRUD_BUSCAR = "BUS";
	private static final String CRUD_DELETAR = "DEL";
	private static final String CRUD_ATUALIZAR = "ATU";
	private static final String SAIR = "S";
	private Usuario user;
	private Class<? extends Persistivel> classe;

	/**
	 * Inicializa ambiente para o usuario escolher qual dos servicos oferecidos
	 * pelo gerenciamento de um persistivel.
	 * */
	public GerenciamentoDePersitivel(Class<? extends Persistivel> classe,
			Usuario user) {

		this.classe = classe;

		this.user = user;

		iniciarAmbiente();

		finalizarAmbiente();

	}

	private void iniciarAmbiente() {

		String opcaoDeCrudEscolhida;

		do {
			apresentarTituloDoServico();

			int quantidadeDeCadastrados = exibirQuantidadeDeCadastrados();

			oferecerOpcoesDeCrud(quantidadeDeCadastrados);

			opcaoDeCrudEscolhida = capturarOpcaoDeCrud();

			acionar(opcaoDeCrudEscolhida);

		} while (!opcaoDeCrudEscolhida.equalsIgnoreCase(SAIR));

	}

	private void apresentarTituloDoServico() {

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tGerenciamento de " + classe.getSimpleName()
				+ " iniciado por " + user.getNome().toUpperCase());
		System.out
				.println("*************************************************************************************************");

	}

	private int exibirQuantidadeDeCadastrados() {

		int quantidade = Crud.quantidadeDeCadastrados(classe);

		System.out.println("\nQuantidade de "
				+ classe.getSimpleName()
				+ " cadastrados atualmente: "
				+ (quantidade > 0 ? quantidade
						: "nao existe cadastrados atualmente!".toUpperCase()));

		return quantidade;

	}

	private void oferecerOpcoesDeCrud(int quantidadeDeCadastrados) {

		String crudCadastrar = "Cadastrar " + classe.getSimpleName() + " - "
				+ CRUD_CADASTRAR;
		String crudListar = "Listar " + classe.getSimpleName() + " - "
				+ CRUD_LISTAR;
		String crudBuscar = "Buscar " + classe.getSimpleName() + " - "
				+ CRUD_BUSCAR;
		String crudAtualizar = "Atualizar " + classe.getSimpleName() + " - "
				+ CRUD_ATUALIZAR;
		String crudDeletar = "Deletar " + classe.getSimpleName() + " - "
				+ CRUD_DELETAR;
		String sair = "Sair " + " - " + SAIR;

		StringBuilder s = new StringBuilder();

		s.append("\n");
		s.append("Escolha uma das opcoes abaixo:");
		s.append("\n\t");
		s.append(crudCadastrar);
		s.append("\n\t");

		if (quantidadeDeCadastrados > 0) {

			s.append(crudListar);
			s.append("\n\t");
			s.append(crudBuscar);
			s.append("\n\t");
			s.append(crudAtualizar);
			s.append("\n\t");
			s.append(crudDeletar);
			s.append("\n\t");

		}

		s.append(sair);
		s.append("\n");
		s.append("Opcao: ");

		exibir(s.toString());

	}

	private void exibir(String s) {

		System.out.println(s);

	}

	private String capturarOpcaoDeCrud() {

		String opcao;

		do {

			opcao = MeuScanner.getInstance().next();

		} while (opcaoInvalida(opcao));

		return opcao.toUpperCase();

	}

	private boolean opcaoInvalida(String s) {

		return !(s.equalsIgnoreCase(CRUD_ATUALIZAR)
				|| s.equalsIgnoreCase(CRUD_BUSCAR)
				|| s.equalsIgnoreCase(CRUD_CADASTRAR)
				|| s.equalsIgnoreCase(CRUD_DELETAR)
				|| s.equalsIgnoreCase(CRUD_LISTAR) || s.equalsIgnoreCase(SAIR));

	}

	private void acionar(String opcaoDeCrudEscolhida) {

		switch (opcaoDeCrudEscolhida) {

		case CRUD_CADASTRAR:

			new AmbienteParaCadastro(classe, user);

			break;

		case CRUD_LISTAR:

			new AmbienteParaListar(classe, user);

			break;

		case CRUD_BUSCAR:

			new AmbienteParaBuscar(classe, user);

			break;

		case CRUD_ATUALIZAR:

			new AmbienteParaAtualizar(classe, user);

			break;

		case CRUD_DELETAR:

			new AmbienteParaDeletar(classe, user);

			break;

		case SAIR:

			break;

		default:
			break;
		}

	}

	private void finalizarAmbiente() {

		System.out
				.println("\n*************************************************************************************************");
		System.out.println("\t\tGerenciamento de " + classe.getSimpleName()
				+ " iniciado por " + user.getNome().toUpperCase()
				+ " foi encerrado.");
		System.out
				.println("*************************************************************************************************");

	}

}
