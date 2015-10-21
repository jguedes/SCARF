package br.com.jguedes.scarf.ambiente;

import java.util.List;

import br.com.jguedes.scarf.crud.Crud;
import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.entidades.usuario.Usuario;

public class AmbienteParaListar {

	public AmbienteParaListar() {

	}

	public AmbienteParaListar(Class<? extends Persistivel> classe, Usuario user) {

		List<? extends Persistivel> lista = Crud.listarPersistiveis(classe,
				user);

		imprimirNaTela(lista);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AmbienteParaListar(List list, Usuario user) {
		List<? extends Persistivel> lista = list;

		imprimirNaTela(lista);
	}

	private void imprimirNaTela(List<? extends Persistivel> lista) {

		System.out
				.println("*************************************************************************************************");
		System.out.println("\t\tLista de "
				+ lista.get(0).getClass().getSimpleName());
		System.out
				.println("*************************************************************************************************");

		for (Persistivel p : lista) {

			System.out.println(p.toString());

		}

		System.out
				.println("*************************************************************************************************");
		System.out.println("\t\tFim da Lista");
		System.out
				.println("*************************************************************************************************");

	}
}
