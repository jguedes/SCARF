package br.com.jguedes.scarf.principal;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.jguedes.scarf.configuracoes.Configuracao;
import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.administrador.Administrador;
import br.com.jguedes.scarf.entidades.usuario.master.Master;
import br.com.jguedes.scarf.entidades.usuario.professor.Professor;

public aspect MonitoraPrincipal {

	pointcut monitoraConstruirSessionFactory(): call(private static void Principal.iniciarPrograma());

	before():monitoraConstruirSessionFactory(){

		System.out.println("INICIALIZANDO CONFIGURACOES DE PERSISTENCIA...");

		Configuracao.builderSessionFactory(false);

		popularBanco();

		System.out.println("CONFIGURACOES DE PERSISTENCIA INICIALIZADAS.");

	}

	private void popularBanco() {

		Session s = Configuracao.openNewSession();

		s.beginTransaction();

		// Popular com Usuario Master

		// pesquisar se ele ja ta no banco

		Master master1 = new Master();

		if (s.createCriteria(Master.class)
				.add(Restrictions.eq("nome", "Master1")).uniqueResult() == null) {

			master1.setNome("Master1");
			master1.setLogin("master1");
			master1.setSenha("master1");
			master1.setCpf("78945612365");

			s.save(master1);

			s.getTransaction().commit();

		}

		// Popular com Recursos
		Recurso r = new Recurso();

		if (s.createCriteria(Recurso.class)
				.add(Restrictions.like("nome", "DATA SHOW%")).list().size() == 0) {

			r.setNome("DATA SHOW 1");
			r.setDatadecadastro(new Date());

			Crud.salvarRecurso(r, master1);

			r.setId(0);
			r.setNome("DATA SHOW 2");
			r.setDatadecadastro(new Date());

			Crud.salvarRecurso(r, master1);

			r.setId(0);
			r.setNome("DATA SHOW 3");
			r.setDatadecadastro(new Date());

			Crud.salvarRecurso(r, master1);

			r.setId(0);
			r.setNome("DATA SHOW 4");
			r.setDatadecadastro(new Date());

			Crud.salvarRecurso(r, master1);

		}

		// Popular com professores
		Professor p1 = new Professor(), p2 = new Professor(), p3 = new Professor();

		if (s.createCriteria(Professor.class)
				.add(Restrictions.like("nome", "PROF%")).list().size() == 0) {

			p1.setNome("PROFESSOR 1");
			p1.setLogin("prof1");
			p1.setSenha("prof1");
			p1.setCpf("123");
			p1.setDisciplina("disciplina do prof. 1");

			s.getTransaction().begin();

			s.save(p1);

			s.getTransaction().commit();

			p2.setNome("PROFESSOR 2");
			p2.setLogin("prof2");
			p2.setSenha("prof2");
			p2.setCpf("234");
			p2.setDisciplina("disciplina do prof. 2");

			s.getTransaction().begin();

			s.save(p2);

			s.getTransaction().commit();

			p3.setNome("PROFESSOR 3");
			p3.setLogin("prof3");
			p3.setSenha("prof3");
			p3.setCpf("345");
			p3.setDisciplina("disciplina do prof. 3");

			s.getTransaction().begin();

			s.save(p3);

			s.getTransaction().commit();

		}

		// Popular com administradores
		Administrador a = null;

		if (s.createCriteria(Administrador.class)
				.add(Restrictions.like("nome", "ADM%")).list().size() == 0) {

			a = new Administrador();
			a.setNome("ADMINISTRADOR 1");
			a.setLogin("adm1");
			a.setSenha("adm1");
			a.setCpf("456");
			a.setCargo("Cargo 1");

			s.getTransaction().begin();

			s.save(a);

			s.getTransaction().commit();

			a = new Administrador();
			a.setNome("ADMINISTRADOR 2");
			a.setLogin("adm2");
			a.setSenha("adm2");
			a.setCpf("567");
			a.setCargo("Cargo 2");

			s.getTransaction().begin();

			s.save(a);

			s.getTransaction().commit();

			a = new Administrador();
			a.setNome("ADMINISTRADOR 3");
			a.setLogin("adm3");
			a.setSenha("adm3");
			a.setCpf("678");
			a.setCargo("Cargo 3");

			s.getTransaction().begin();

			s.save(a);

			s.getTransaction().commit();

		}

		s.close();

	}

	pointcut monitoraFecharSessionFactory(): call(private static void Principal.finalizarPrograma());

	after():monitoraFecharSessionFactory(){

		System.out.println("FINALIZANDO CONFIGURACOES DE PERSISTENCIA...");

		Configuracao.closeSessionFactory();

		System.out.println("CONFIGURACOES DE PERSISTENCIA FINALIZADAS.");

	}

}