package br.com.jguedes.scarf.crud;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.jguedes.scarf.configuracoes.Configuracao;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.historicodeagendamento.HistoricoDeAgendamento;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public aspect MonitoraCrud {

	public pointcut monitoraSalvarUsuario(Usuario userParaCadastrar,
			Usuario userQueCadastra): call(public static * Crud.salvarUsuario(Usuario,Usuario))&&args(userParaCadastrar,userQueCadastra);

	boolean around(Usuario userParaCadastrar, Usuario userQueCadastra):monitoraSalvarUsuario(userParaCadastrar,userQueCadastra){

		Session s = Configuracao.openNewSession();

		String dadoInformado = null;

		// Nao permitir usuario sem cpf

		if (!userContemDado(userParaCadastrar, "CPF")) {

			System.out.print("\nFalta o CPF!");

			if (!stringContemDado(dadoInformado = providenciarDado("CPF")))
				return false;

			userParaCadastrar.setCpf(dadoInformado);

		}

		// Nao permitir cpf igual

		Usuario aux = (Usuario) s.createCriteria(userParaCadastrar.getClass())
				.add(Restrictions.eq("cpf", userParaCadastrar.getCpf()))
				.uniqueResult();

		String dadoCadastrado = null;

		if (aux != null) {

			dadoCadastrado = aux.getCpf();

			do {

				System.out
						.println("\n"
								+ userParaCadastrar.getClass().getSimpleName()
								+ " ["
								+ userParaCadastrar.getNome().toUpperCase()
								+ "] nao pode ser cadastrado!\nMotivo: JA EXISTE O CPF ["
								+ dadoCadastrado + "] CADASTRADO NO BANCO.\n");

				if (!stringContemDado(dadoInformado = providenciarDado("CPF")))
					return false;

			} while (dadoInformado.equalsIgnoreCase(dadoCadastrado));

			userParaCadastrar.setCpf(dadoInformado);

		}
		// Nao permitir usuario sem login

		dadoInformado = null;

		if (!userContemDado(userParaCadastrar, "login")) {

			System.out.print("\nFalta o LOGIN!");

			if (!stringContemDado(dadoInformado = providenciarDado("LOGIN")))
				return false;

			userParaCadastrar.setLogin(dadoInformado);

		}

		// Nao permitir login igual

		dadoInformado = null;
		dadoCadastrado = null;

		aux = (Usuario) s.createCriteria(userParaCadastrar.getClass())
				.add(Restrictions.eq("login", userParaCadastrar.getLogin()))
				.uniqueResult();

		if (aux != null) {

			dadoCadastrado = aux.getLogin();

			do {

				System.out
						.println("\n"
								+ userParaCadastrar.getClass().getSimpleName()
								+ " ["
								+ userParaCadastrar.getNome()
								+ "] nao pode ser cadastrado!\nMotivo: JA EXISTE O LOGIN ["
								+ userParaCadastrar.getLogin()
								+ "] CADASTRADO NO BANCO.\n");

				if (stringContemDado(dadoInformado = providenciarDado("LOGIN")))
					return false;

			} while (dadoInformado.equalsIgnoreCase(dadoCadastrado));

			userParaCadastrar.setLogin(dadoInformado);

		}

		s.close();

		proceed(userParaCadastrar, userQueCadastra);

		return true;

	}

	private boolean userContemDado(Usuario user, String campo) {

		if (campo.equalsIgnoreCase("cpf")) {

			if (user.getCpf() == null)
				return false;
			if (user.getCpf().length() == 0)
				return false;

		} else if (campo.equalsIgnoreCase("login")) {

			if (user.getLogin() == null)
				return false;
			if (user.getLogin().length() == 0)
				return false;

		}

		return true;
	}

	private boolean stringContemDado(String string) {

		if (string == null)
			return false;

		if (string.length() == 0)
			return false;

		return true;

	}

	private String providenciarDado(String campo) {

		if (MeuScanner.getInstance().nextOpcaoTrueFalse("Digitar " + campo,
				"Abortar")) {

			System.out.print("Digite o " + campo + ": ");

			return MeuScanner.getInstance().next();

		}

		return null;

	}

	public pointcut monitoraSalvarRecurso(Recurso recurso,
			Usuario userQueCadastra): call(public static * Crud.salvarRecurso(Recurso,Usuario))&&args(recurso,userQueCadastra);

	boolean around(Recurso recurso, Usuario userQueCadastra):monitoraSalvarRecurso(recurso,userQueCadastra){

		proceed(recurso, userQueCadastra);

		return true;

	}

	public pointcut monitoraSalvarAgendamento(
			Agendamento agendamentoParaCadastrar, Usuario userQueCadastra):call(public static * Crud.salvarAgendamento(Agendamento,Usuario))&&args(agendamentoParaCadastrar,userQueCadastra);

	boolean around(Agendamento agendamentoParaCadastrar, Usuario userQueCadastra):monitoraSalvarAgendamento(agendamentoParaCadastrar,userQueCadastra){

		HistoricoDeAgendamento historico = gerarHistoricoDeAgendamento(
				agendamentoParaCadastrar, userQueCadastra);

		Session s = Configuracao.openNewSession();

		s.beginTransaction();

		s.save(historico);

		s.getTransaction().commit();

		s.close();

		proceed(agendamentoParaCadastrar, userQueCadastra);

		return true;

	}

	private HistoricoDeAgendamento gerarHistoricoDeAgendamento(
			Agendamento agendamentoParaCadastrar, Usuario userQueCadastra) {

		HistoricoDeAgendamento historico = new HistoricoDeAgendamento();

		historico
				.setAcao(montarAcao(agendamentoParaCadastrar, userQueCadastra));

		historico.setDataEHora(montarDataEHora());

		return historico;

	}

	private String montarAcao(Agendamento agendamento, Usuario userQueCadastra) {

		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm");

		StringBuilder s = new StringBuilder();

		s.append("O ");
		s.append(userQueCadastra.getClass().getSimpleName());
		s.append(" [");
		s.append(userQueCadastra.getNome().toUpperCase());
		s.append("] realizou o agendamento dos seguintes recursos: ");

		for (Object o : agendamento.getRecursos()) {

			s.append(((Recurso) o).getNome());
			s.append(", ");

		}

		s.append("Data de entrega: ");
		s.append(sdf.format(agendamento.getDatain()));
		s.append(" | Data de devolucao : ");
		s.append(sdf.format(agendamento.getDatafin()));

		return s.toString();

	}

	private Date montarDataEHora() {

		return new Date();

	}
}