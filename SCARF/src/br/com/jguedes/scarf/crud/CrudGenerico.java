package br.com.jguedes.scarf.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import br.com.jguedes.scarf.configuracoes.Configuracao;
import br.com.jguedes.scarf.entidades.usuario.Usuario;

/**
 * Esta classe foi desenvolvida para implementar um CRUD generico. Nela existe
 * apenas metodos que fazem uso de Session. Todas as entidades manipuladas por
 * esta classe devem implementar a interface Persistivel.
 * */
public class CrudGenerico {

	/**
	 * Este metodo cadastra um persistivel sem importa-se com replicas no banco
	 * de dados. Por isso e' bom realizar pesquisa antes para evitar
	 * redundancias.
	 * */
	public static void cadastrar(Persistivel persistivel,
			Usuario userQueCadastra) {

		try {

			Session s = Configuracao.openNewSession();

			s.beginTransaction();

			persistivel.setCadastrador(userQueCadastra);

			s.save(persistivel);

			s.getTransaction().commit();

			s.close();

		} catch (SessionException e) {

			System.out.println("Erro ao cadastrar persistivel. Causa: "
					+ e.getMessage());

		}

	}

	public static boolean atualizar(Persistivel persistivel) {

		// averiguar se o objeto ja existe

		Persistivel aux = buscarPorId(persistivel.getClass(),
				persistivel.getId());

		if (aux == null) {

			System.out.println("\n\nNao foi possivel atualizar ["
					+ persistivel.toString() + "]\nMotivo: Ele nao existe.");

			return false;

		}

		Session s = Configuracao.openNewSession();

		try {

			s.beginTransaction();

			s.update(persistivel);

			s.getTransaction().commit();

			System.out.println(persistivel.getClass().getSimpleName()
					+ " atualizado com sucesso.\n" + persistivel.toString());

			return true;

		} catch (Exception e) {

			System.out.println(e.getMessage()
					+ "\n\nNao foi possivel atualizar ["
					+ persistivel.toString() + "]\nMotivo: Desconhecido.\n");

			return false;

		} finally {

			if (s.getTransaction().isActive()) {

				s.getTransaction().rollback();

			}

			s.close();

		}

	}

	public static boolean deletar(Persistivel persistivel) {

		// averiguar se o objeto ja existe

		Persistivel aux = buscarPorId(persistivel.getClass(),
				persistivel.getId());

		if (aux == null) {

			System.out.println("\n\nNao foi possivel deletar ["
					+ persistivel.toString() + "]\nMotivo: Ele nao existe.\n");

			return false;

		}

		Session s = Configuracao.openNewSession();

		try {

			s.beginTransaction();

			s.delete(persistivel);

			s.getTransaction().commit();

			System.out.println(aux.toString() + "Deletado com sucesso!\n");

			return true;

		} catch (Exception e) {

			System.out.println(e.getMessage()
					+ "\n\nNao foi possivel deletar [" + persistivel.toString()
					+ "]\n\n");

			return false;

		} finally {

			if (s.getTransaction().isActive()) {

				s.getTransaction().rollback();

			}

			s.close();

		}

	}

	@SuppressWarnings("unchecked")
	public static List<? extends Persistivel> listarComRestrincao(
			Class<? extends Persistivel> classe, String sqlRestrincoes) {

		Session s = Configuracao.openNewSession();

		Criteria criterio = s.createCriteria(classe);

		List<? extends Persistivel> lista = null;

		if (sqlRestrincoes != null) {

			lista = criterio.add(Restrictions.sqlRestriction(sqlRestrincoes))
					.list();

		}

		s.close();

		return lista;

	}

	@SuppressWarnings("unchecked")
	public static List<? extends Persistivel> listarComRestrincao(
			Class<? extends Persistivel> classe, LogicalExpression restrincao) {

		Session s = Configuracao.openNewSession();

		Criteria criterio = s.createCriteria(classe);

		List<? extends Persistivel> lista = null;

		if (restrincao != null) {

			lista = criterio.add(restrincao).list();

		}

		s.close();

		return lista;

	}

	/**
	 * Acessa o banco para obter uma lista de qualquer entidade que implemente a
	 * interface Persitivel.
	 * */
	@SuppressWarnings("unchecked")
	public static List<? extends Persistivel> listarPersistiveis(
			Class<? extends Persistivel> classe) {

		Session s = Configuracao.openNewSession();

		List<? extends Persistivel> lista = s.createCriteria(classe).list();

		s.close();

		return lista;

	}

	public static Persistivel buscarPorId(Class<? extends Persistivel> classe,
			Serializable id) {

		Session s = Configuracao.openNewSession();

		Persistivel aux = (Persistivel) s.get(classe, id);

		s.close();

		return aux;

	}

	public static int count(Class<? extends Persistivel> classe) {

		Session s = Configuracao.openNewSession();

		int count = s.createCriteria(classe).list().size();

		s.close();

		return count;
	}

}