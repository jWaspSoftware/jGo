package test;

import cloud.jgo.net.tcp.http.jor.JOR;
// /persone/nome-cognome-et�
@JOR(field_id = "nome", url_Pattern = "/persone")
public class Persona {
	private String nome ;
	private String cognome ;
	private int et�;
	public Persona(String nome, String cognome,int et�) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.et� = et�;
	}
	public int getEt�() {
		return et�;
	}
	public void setEt�(int et�) {
		this.et� = et�;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nome+" "+this.cognome ;
	}
	
}
