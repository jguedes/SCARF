package br.com.jguedes.scarf.ambiente;

import java.util.List;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.agendamento.Agendamento;
import br.com.jguedes.scarf.entidades.recurso.Recurso;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class AmbienteParaDeletar {

	private Usuario user;
	private Class<? extends Persistivel> classe;

	public AmbienteParaDeletar(Class<? extends Persistivel> classe, Usuario user) {

		this.classe = classe;
		this.user = user;

		int id;

		System.out.println("Digite o ID de "
				+ classe.getSimpleName().toUpperCase() + " para deletar.");

		id = MeuScanner.getInstance().nextInt();

		Persistivel p = Crud.buscarPorId(classe, id);

		if (classe.getClass().equals(Recurso.class)) {

			if (verificarSeRecursoEstaPendenteEmAlgumAgendamento(p)) {

				return;

			}

		}

		Crud.deletar(p);

	}

	private boolean verificarSeRecursoEstaPendenteEmAlgumAgendamento(
			Persistivel p) {

		List<Agendamento> listaDeAgendamentosComRecurso = null;

		listaDeAgendamentosComRecurso = Crud
				.listarAgendamentosPorRecurso(((Recurso) p));

		if (listaDeAgendamentosComRecurso != null) {

			System.out.println("Nao pode deletar " + p.toString());

			System.out.println("Existem agendamentos depentes dele:");

			for (Agendamento a : listaDeAgendamentosComRecurso) {

				System.out.println(a.toString());

			}

			return true;
		}

		return false;

	}

}
