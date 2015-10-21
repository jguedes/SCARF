package br.com.jguedes.scarf.entidades.usuario;

import br.com.jguedes.scarf.crud.Persistivel;
import br.com.jguedes.scarf.interfaces.Nomeavel;

public abstract class Usuario implements Persistivel, Nomeavel {
	private long id;
	private String login;
	private String senha;
	private String nome;
	private String cpf;
	private String rg;
	private String email;
	private String rua;
	private String numero;
	private String bairro;
	private String municipio;
	private String uf = "PE";
	private String pais = "BR";
	private String cep;
	private Usuario cadastrador;

	public Usuario() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {

		if (email != null) {

			this.email = email.toLowerCase();

		}

	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public Usuario getCadastrador() {

		return cadastrador;

	}

	@Override
	public void setCadastrador(Usuario cadastrador) {

		this.cadastrador = cadastrador;

	}

	public String toExibir() {

		StringBuilder s = new StringBuilder();

		s.append("\n");
		s.append(getClass().getSimpleName());
		s.append(" cadastrado pelo ");
		if (cadastrador != null) {

			s.append(cadastrador.getClass().getSimpleName().toLowerCase());
			s.append(" ");
			s.append(cadastrador.getNome());

		} else {

			s.append("SISTEMA");

		}
		s.append(":");
		s.append("\n\t");
		s.append("ID:");
		s.append(getId());
		s.append("\n\t");
		s.append("Nome:");
		s.append(getNome());
		s.append("\n\t");
		s.append("CPF:");
		s.append(getCpf());

		return s.toString();

	}

	@Override
	public boolean equals(Object obj) {

		return ((Usuario) obj).getCpf().equalsIgnoreCase(cpf);

	}

}
