package test;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cloud.jgo.io.jon.JONClass;
import cloud.jgo.io.jon.JONField;
import cloud.jgo.net.tcp.http.jor.JOR;
import cloud.jgo.net.tcp.http.jor.ResponseType;
// /persone/nome-cognome-et�
@JOR(
	 field_id = "nome+cognome+et�", url_Pattern = "/persone",
	 responseType=ResponseType.XML_TYPE, SaveFiles=true
	 )
@XmlRootElement
@JONClass
public class Persona {
	@JONField
	private String nome ;
	@JONField
	private String cognome ;
	@JONField
	private int et�;
	public Persona(String nome, String cognome,int et�) {
		this.nome = nome;
		this.cognome = cognome;
		this.et� = et�;
	}
	public Persona() {
		// TODO Auto-generated constructor stub
	}
	@XmlElement
	public int getEt�() {
		return et�;
	}
	public void setEt�(int et�) {
		this.et� = et�;
	}
	@XmlElement
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@XmlElement
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nome+" "+this.cognome+" "+this.et� ;
	}
	
}
