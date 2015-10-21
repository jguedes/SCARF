package br.com.jguedes.scarf.crud;

import br.com.jguedes.scarf.entidades.usuario.Usuario;

/**
 * Interface para entidades manipuladas no CRUD generico.
 * */
public interface Persistivel {

	public abstract long getId();

	public abstract void setId(long Id);

	public abstract void setCadastrador(Usuario cadastrador);

	public abstract Usuario getCadastrador();

}
