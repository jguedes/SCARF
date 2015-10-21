package br.com.jguedes.scarf.configuracoes;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class Configuracao {

	private static Configuration configuracao = null;

	private static SessionFactory sessionFactory = null;

	private Configuracao(boolean mostrarScrptSql) {

		configuracao = new Configuration();

		configuracao.configure(new File(
				"src/br/com/jguedes/scarf/configuracoes/hibernate.cfg.xml"));

		SchemaExport schemaExport = new SchemaExport(configuracao);

		schemaExport.create(mostrarScrptSql, true);

	}

	/**
	 * Constroi a fabrica de sessao sse ela nao estiver ativa.
	 * */
	@SuppressWarnings("deprecation")
	public static void builderSessionFactory(boolean mostrarScrptSql) {

		if (configuracao == null) {

			new Configuracao(mostrarScrptSql);

		}

		sessionFactory = configuracao.buildSessionFactory();
		//
		// if (sessionFactory == null) {
		//
		// sessionFactory = configuracao.buildSessionFactory();
		//
		// } else if (sessionFactory.isClosed()) {
		//
		// sessionFactory = configuracao.buildSessionFactory();
		//
		// }

	}

	public static boolean closeSessionFactory() {

		if (!sessionFactory.isClosed()) {

			sessionFactory.close();

		}

		return sessionFactory.isClosed();

	}

	/**
	 * Retorna uma nova sessao. A fabrica de sessao deve ser previamente
	 * construida com o metodo Configuracao.openSessionFactory().
	 * */
	public static Session openNewSession() {

		return sessionFactory.openSession();

	}

}
