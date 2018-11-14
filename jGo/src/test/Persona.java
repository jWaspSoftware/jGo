package test;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cloud.jgo.io.jon.JONClass;
import cloud.jgo.io.jon.JONField;
import cloud.jgo.net.tcp.http.jor.JOR;
import cloud.jgo.net.tcp.http.jor.ResponseType;
// /persone/nome-cognome-et�
@JOR(field_id = "nome+cognome+eta", url_Pattern = "/persone",responseType=ResponseType.JSON)
public class Persona {
	private String nome ;
	private String cognome ;
	private int eta;
	public Persona(String nome, String cognome,int et�) {
		this.nome = nome;
		this.cognome = cognome;
		this.eta = et�;
	}
	public Persona() {
		// TODO Auto-generated constructor stub
	}
	public int getEt�() {
		return eta;
	}
	public void setEt�(int et�) {
		this.eta = et�;
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
		return this.nome+" "+this.cognome+" "+this.eta ;
	}
}
