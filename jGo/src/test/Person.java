package test;

import cloud.jgo.utils.command.LocalCommand;
import cloud.jgo.utils.command.annotations.Command;
import cloud.jgo.utils.command.annotations.Parameter;

@Command(help = "Creates a Person",involveAll=true)
public class Person {

	private String nome ;
	private String cognome ;
	private int et� ;
	private Double stipendio ;
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
	public Double getStipendio() {
		return stipendio;
	}
	public void setStipendio(Double stipendio) {
		this.stipendio = stipendio;
	}
	public Person() {
		super();
		this.nome = null ;
		this.cognome = null ;
		this.et� = 0 ;
		this.stipendio = new Double(0);
	}
	public Person(String nome, String cognome, int et�) {
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = null ;
		try {
			result =  LocalCommand.toString(this);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
}
