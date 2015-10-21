package br.com.jguedes.scarf.entidades.recurso;

import java.util.Date;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.entidades.usuario.Usuario;
import br.com.jguedes.scarf.ferramentas.MeuScanner;

public class CadastradorDeRecurso {

	private boolean abortar;

	public CadastradorDeRecurso(Usuario userQueCadastra) {

		Recurso recursoParaCadastrar;

		do {

			recursoParaCadastrar = recursoGeradoPeloUsuario();

			if (abortar) {

				return;

			}

			Crud.salvarRecurso(recursoParaCadastrar, userQueCadastra);

		} while (cadastrarOutroRecurso());

	}

	private boolean cadastrarOutroRecurso() {

		int op;

		do {

			System.out
					.print("\n\t1 - Cadastrar outro recurso.\n\t2 - Sair\nOpcao: ");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

	private Recurso recursoGeradoPeloUsuario() {

		Recurso r = new Recurso();

		r.setNome(nomeInformadoPeloUsuario());
		if (!abortar) {
			r.setCapacidade(capacidadeInformadoPeloUsuario());
			if (!abortar) {
				r.setDescricao(descricaoInformadoPeloUsuario());
			}
		}

		if (abortar)
			return null;

		r.setDatadecadastro(new Date());

		return r;
	}

	private String descricaoInformadoPeloUsuario() {
		// ATENCAO CAMPO NAO OBRIGATORIO POR ISSO PODE SER NULL

		String descricao;

		if (continuar("descricao")) {

			descricao = MeuScanner.getInstance().nextLine();

		} else {

			abortar = true;

			return null;
		}

		return descricao;

	}

	private long capacidadeInformadoPeloUsuario() {
		// ATENCAO CAMPO NAO OBRIGATORIO POR ISSO PODE SER 0(ZERO)

		long capacidade = 0;

		if (continuar("capacidade")) {

			capacidade = MeuScanner.getInstance().nextInt();

		} else {

			abortar = true;

		}

		return capacidade;

	}

	private String nomeInformadoPeloUsuario() {
		// CAMPO OBRIGATORIO NAO PODE SER NULO

		String nome;

		do {

			if (continuar("nome")) {

				nome = MeuScanner.getInstance().nextLine();

			} else {

				abortar = true;

				return null;
			}

		} while (nome == null);

		return nome;

	}

	private boolean continuar(String campo) {

		int op;

		do {

			System.out.println("\n\t1 - Digitar " + campo + "\n\t2 - Abortar");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		if (op == 2) {

			return false;

		}

		System.out.print("Digite o " + campo + ": ");

		return true;

	}

}
