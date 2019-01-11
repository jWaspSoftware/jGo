package test;


import javax.xml.bind.annotation.XmlRootElement;

import org.fusesource.jansi.Ansi.Color;

import cloud.jgo.utils.command.LocalCommand;
import cloud.jgo.utils.command.annotations.CommandClass;
import cloud.jgo.utils.command.annotations.Configurable;
import cloud.jgo.utils.command.color.ColorLocalCommand;
@CommandClass(help = "Creates a Person", involveAll=true)
public class Persona implements Configurable{
	private String nome ;
	private String cognome ;
	private int eta ;
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
	public Persona() {
		super();
		this.nome = null ;
		this.cognome = null ;
		this.eta = 0 ;
		this.stipendio = new Double(0);
	}
	public Persona(String nome, String cognome, int et�) {
		this.nome = nome;
		this.cognome = cognome;
		this.eta = et�;
		this.stipendio = new Double(0);
	}
	public int getEt�() {
		return eta;
	}
	public void setEt�(int et�) {
		this.eta = et�;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = null ;
		try {
			result =  ColorLocalCommand.toString(this, Color.DEFAULT, Color.GREEN);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;
	}
	@Override
	public String getTarget() {
		// TODO Auto-generated method stub
		return "persona";
	}
	@Override
	public Class<? extends Configurable> getTargetType() {
		// TODO Auto-generated method stub
		return getClass();
	}
	@Override
	public boolean isCompleted() {
		if (this.nome!=null&&this.cognome!=null&&this.eta>0) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
}
