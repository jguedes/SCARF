package br.com.jguedes.scarf.entidades.agendamento;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

/**
 * Recebe como parametro o usuario responsavel pelo cadastro do agendamento.
 * Interage com o usurio para criar um novo agendamento. Persiste o
 * agendamento no banco. Da a possibilidade de o usuario realizar um novo
 * agendamento.
 * */
public class CadastradorDeAgendamento {

	private boolean abortar;

	public CadastradorDeAgendamento(Usuario userQueCadastra) {

		Agendamento agendamentoParaCadastrar;

		do {

			agendamentoParaCadastrar = agendamentoGeradoPeloUsuario();

			if (abortar) {

				return;

			}

			Crud.salvarAgendamento(agendamentoParaCadastrar,
					userQueCadastra);

		} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Cadastrar outro agendamento", "Sair"));

	}

	private Agendamento agendamentoGeradoPeloUsuario() {

		Agendamento a = new Agendamento();

		a.setDatain(datainInformadoPeloUsuario());
		if (!abortar) {
			a.setDatafin(datafinInformadoPeloUsuario());
			if (!abortar) {
				a.setRecursos(recursosInformadoPeloUsuario(a.getDatain(),
						a.getDatafin()));
			}
		}

		if (abortar)
			return null;

		return a;
	}

	private Date datainInformadoPeloUsuario() {

		return dateInfByUser("data de entrada");

	}

	private Date datafinInformadoPeloUsuario() {

		return dateInfByUser("data de devolucao");

	}

	private Date dateInfByUser(String tipoDeData) {

		Date datain = null;

		if (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Informar a " + tipoDeData, "Abortar")) {

			System.out.print("Digite a " + tipoDeData + ": ");

			datain = MeuScanner.getInstance().nextDateWithHour();

		}

		abortar = (datain == null);

		return datain;

	}

	private Set<Recurso> recursosInformadoPeloUsuario(Date datain, Date datafin) {

		Set<Recurso> recursosEscolhidos = null;

		if (MeuScanner.getInstance().nextOpcaoTrueFalse(
				"Escolher recursos de desejados", "Abortar")) {

			recursosEscolhidos = null;

			List<?> listaDeRecursosDisponiveis = Crud
					.listarRecursosDisponiveis(datain, datafin);

			if (listaDeRecursosDisponiveis == null) {

				abortar = true;

				return null;

			}

			Recurso r = null;

			do {

				exibrRecursos(listaDeRecursosDisponiveis);

				int recursoId = MeuScanner.getInstance().nextInt();

				if (recursoId == 0) {

					break;

				}

				if (recursosEscolhidos == null) {

					recursosEscolhidos = new HashSet<Recurso>();

				}

				for (Object o : listaDeRecursosDisponiveis) {

					r = (Recurso) o;

					if (r.getId() == recursoId) {

						recursosEscolhidos.add(r);

						listaDeRecursosDisponiveis.remove(r);

						break;

					}

				}

			} while (MeuScanner.getInstance().nextOpcaoTrueFalse(
					"Escolher outro recurso", "Terminar"));

		}

		abortar = (recursosEscolhidos == null);

		return recursosEscolhidos;

	}

	private void exibrRecursos(List<?> listaDeRecursosDisponiveis) {

		Recurso r = null;

		StringBuilder s = new StringBuilder();

		s.append("Digite o ID do recurso desejado (para cancelar digite 0(zero)):");
		s.append("\n");

		for (Object o : listaDeRecursosDisponiveis) {

			r = (Recurso) o;

			s.append("----------------------------------------------------------");
			s.append("\n");
			s.append("Recurso: ");
			s.append(r.getNome());
			s.append(" | ID: ");
			s.append(r.getId());
			s.append("\n");
			s.append("----------------------------------------------------------");

		}

		s.append("ID: ");

		System.out.print(s.toString());

	}
}
