package br.com.jguedes.scarf.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.com.jguedes.scarf.configuracoes.Configuracao;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.interfaces.Nomeavel;

/**
 * Esta classe implementa metodos que acessam o banco de dados e manipulam os
 * dados para gerar resultados personalizados.
 * */
public class Crud {

	// private static Session s = null;

	/**
	 * Este metodo retorna o usuario referenciado pelo seu login e senha.
	 * */
	public static Usuario identificarUsuario(Usuario user) {

		try {

			return buscarPorLoginESenha(user);

		} catch (Exception e) {

			System.out.println("Erro ao identificar Usuario!\nMotivo: "
					+ e.getMessage());

			return null;

		}
	}

	/**
	 * Este metodo cadastra um usuario no banco de dados. Mas, antes de
	 * cadastrar, pesquisa se existe usuario com mesmo login. Existindo, nao
	 * sera permitido o cadastro. Esta pesquisa e' relizada via aspecto.
	 * */
	public static boolean salvarUsuario(Usuario userParaCadastrar,
			Usuario userQueCadastra) {

		CrudGenerico.cadastrar(userParaCadastrar, userQueCadastra);

		return true;

	}

	public static int quantidadeDeCadastrados(
			Class<? extends Persistivel> classe) {

		return CrudGenerico.count(classe);

	}

	public static boolean salvarRecurso(Recurso recurso, Usuario user) {

		CrudGenerico.cadastrar(recurso, user);

		return true;

	}

	public static boolean salvarAgendamento(
			Agendamento agendamentoParaCadastrar, Usuario userQueCadastra) {

		CrudGenerico.cadastrar(agendamentoParaCadastrar, userQueCadastra);

		return true;

	}

	/**
	 * Este metodo gera uma lista de recursos que nao estao locados em
	 * agendamentos para as datas e horarios especificados nos parametros.
	 * */
	public static List<?> listarRecursosDisponiveis(Date datain, Date datafin) {

		// primeiro:
		// listar todos os recursos cadastrados
		@SuppressWarnings("unchecked")
		List<Recurso> recursos = (List<Recurso>) CrudGenerico
				.listarPersistiveis(Recurso.class);

		// segundo:
		// saber quais agendamentos coincidem com as datas especificas.
		List<Agendamento> agendamentos = consultAgendamentoPorData(datain,
				datafin);

		// terceiro:
		// saber quais recursos fazem parte da locacao dos
		// agendamentos com datas coincidentes.
		List<Recurso> recursosLocados = listarRecursosEmAgendamentos(agendamentos);

		// quarto:
		// remover da lista de todos os recursos os recursos locados
		recursos.removeAll(recursosLocados);// para o removeAll funcionar
											// deve-se sobreescrever o metodo
											// equals(Object) na classe tipo da
											// lista. Para este caso, classe
											// Recurso.

		// quinto:
		// retornar os recursos nao locados.
		return recursos;

	}

	private static List<Recurso> listarRecursosEmAgendamentos(
			List<Agendamento> agendamentos) {

		List<Recurso> recursos = new ArrayList<Recurso>();

		for (Agendamento agendamento : agendamentos) {

			recursos.addAll(agendamento.getRecursos());

		}

		return recursos;

	}

	public static List<? extends Persistivel> listarPersistiveis(
			Class<? extends Persistivel> classe, Usuario user) {

		return CrudGenerico.listarPersistiveis(classe);

	}

	public static Persistivel buscarPorId(Class<? extends Persistivel> classe,
			long id) {

		return CrudGenerico.buscarPorId(classe, id);

	}

	@SuppressWarnings("unchecked")
	public static List<Nomeavel> buscarPorParteDoNome(
			Class<? extends Persistivel> classeNomeavel, String nome) {

		if (classeNomeavel.equals(Nomeavel.class)) {

			return (List<Nomeavel>) CrudGenerico.listarComRestrincao(
					classeNomeavel, "nome like %" + nome + "%");

		} else {

			System.out
					.println(("\n\nErro. Passe para o parametro apenas classe persitiveis nomeaveis!!!!\n\n"));

		}

		return null;

	}

	public static Nomeavel buscarPorNomeEspecifico(
			Class<? extends Persistivel> classeNomeavel, String nome) {

		if (classeNomeavel.equals(Nomeavel.class)) {

			return (Nomeavel) buscarPorStringIgual(classeNomeavel, "nome", nome);

		} else {

			System.out
					.println(("\n\nErro. Passe para o parametro apenas classe persitiveis nomeaveis!!!!\n\n"));

		}

		return null;

	}

	public static Usuario buscarUsuarioPorCpf(String cpf) {

		return (Usuario) buscarPorStringIgual(Usuario.class, "cpf", cpf);

	}

	private static Persistivel buscarPorStringIgual(
			Class<? extends Persistivel> classe, String campo, String string) {

		List<? extends Persistivel> lista;

		lista = CrudGenerico
				.listarComRestrincao(classe, campo + " = " + string);

		if (lista != null) {

			return lista.get(0);

		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public static List<Agendamento> listarAgendamentosPorCadastrador(
			Usuario cadastrador) {

		List<Agendamento> lista = null;

		lista = (List<Agendamento>) CrudGenerico.listarComRestrincao(
				Agendamento.class, "agendador = " + cadastrador.getId());

		return lista;
	}

	/**
	 * Este metodo gera uma lista de agendamentos que tem suas datas de entrada
	 * e devolucao coindentes com a as datas do parametro agendamento.
	 * */
	public static List<?> buscarPorDatasCoincidentes(Agendamento a) {

		return consultAgendamentoPorData(a.getDatain(), a.getDatafin());

	}

	/**
	 * Este metodo pesquisa no bd um usuario contendo login e senha igual a do
	 * usuario do paramentro. E por referencia retorna no parametro o usuario
	 * encontrado.
	 * 
	 * @param s
	 * */
	public static Usuario buscarPorLoginESenha(Usuario user) {

		Session s = Configuracao.openNewSession();

		user = (Usuario) s.createCriteria(Usuario.class)
				.add(Restrictions.like("login", user.getLogin()))
				.add(Restrictions.like("senha", user.getSenha()))
				.uniqueResult();

		s.close();

		return user;

	}

	/**
	 * Este metodo pesquisa se existe no bd um usuario contendo login igual a do
	 * usuario do paramentro.
	 * 
	 * @param s
	 * */
	public static boolean loginDeUsuarioExiste(Usuario user) {

		Session s = Configuracao.openNewSession();

		boolean existe = s.createCriteria(Usuario.class)
				.add(Restrictions.like("login", user.getLogin()))
				.uniqueResult() != null;

		s.close();

		return existe;

	}

	/**
	 * Este metodo gera uma lista de agendamentos que tem suas datas de entrada
	 * e devolucao coindentes com a as datas dos parametros. Para que uma data
	 * seja coincidente deve estar nas seguintes circunstancias: as datas de
	 * entrada dos agendamentos devem ser maior ou igual a data de entrada do
	 * paramentro e menores que a data de devolucao do parametro. Ou, as datas
	 * de devolucao dos agendamentos devem ser maior que a data de entrada do
	 * paramentro e menores ou iguais a data de devolucao do parametro........
	 * [(x >= datain) and (x < datafin)] or [(y > datain) and (y <= datafin)] ,
	 * onde x equivale as datas de entrada dos agendamentos e, y as datas de
	 * devolucao dos agendamentos.
	 * */
	@SuppressWarnings("unchecked")
	public static List<Agendamento> consultAgendamentoPorData(Date datain,
			Date datafin) {

		Session s = Configuracao.openNewSession();

		/*
		 * Restrincao r1: buscar palos registros com data de entrada maior ou
		 * igual a data de entrada desejada.
		 */
		SimpleExpression r1 = Restrictions.ge("datain", datain);

		/*
		 * Restrincao r2: buscar palos registros com data de entrada menor que a
		 * data de devolucao desejada.
		 */
		SimpleExpression r2 = Restrictions.lt("datain", datafin);

		/*
		 * Restrincao r3: buscar palos registros com data de devolucao maior que
		 * a data de entrada desejada.
		 */
		SimpleExpression r3 = Restrictions.gt("datafin", datain);

		/*
		 * Restrincao r4: buscar palos registros com data de devolucao menor ou
		 * igual a data de devolucao desejada.
		 */
		SimpleExpression r4 = Restrictions.le("datafin", datafin);

		/*
		 * Restrincao r1_and_r2: buscar palos registros com restricao r1 e r2
		 */
		LogicalExpression r1_and_r2 = Restrictions.and(r1, r2);

		/*
		 * Restrincao r3_and_r4: buscar palos registros com restricao r3 e r4
		 */
		LogicalExpression r3_and_r4 = Restrictions.and(r3, r4);

		/*
		 * Restrincao r1_and_r2_OR_r3_and_r4: buscar palos registros com
		 * restricao r1_and_r2 ou r3_and_r4
		 */
		LogicalExpression r1_and_r2_OR_r3_and_r4 = Restrictions.or(r1_and_r2,
				r3_and_r4);

		// buscar
		@SuppressWarnings("rawtypes")
		List lista = s.createCriteria(Agendamento.class)
				.add(r1_and_r2_OR_r3_and_r4).list();

		s.close();

		return lista;

	}

	public static void atulizar(Persistivel persistivelParaAtualizar) {

		CrudGenerico.atualizar(persistivelParaAtualizar);

	}

	public static void deletar(Class<? extends Persistivel> classe, int id) {

		Persistivel p = buscarPorId(classe, id);

		CrudGenerico.deletar(p);

	}

	public static List<Agendamento> listarAgendamentosPorRecurso(Recurso recurso) {

		String sqlRestrincoes = "from agendamento a, agendamento_recurso ar where ((a.status = e) or (status = a)) and ar.id_recurso = "
				+ recurso.getId();

		CrudGenerico.listarComRestrincao(Agendamento.class, sqlRestrincoes);

		return null;
	}

	public static void deletar(Persistivel p) {

		CrudGenerico.deletar(p);

	}

}
