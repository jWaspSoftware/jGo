package test;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.Ansi.Color;

import cloud.jgo.jjdom.dom.nodes.xml.XMLDocument;
import cloud.jgo.jjdom.dom.nodes.xml.XMLElement;
import cloud.jgo.utils.command.LocalCommand;
import cloud.jgo.utils.command.color.ColorLocalCommand;
import cloud.jgo.utils.command.terminal.LocalTerminal;
import cloud.jgo.utils.command.terminal.TerminalColors;
import cloud.jgo.utils.command.terminal.phase.ColorLocalPhaseTerminal;
import cloud.jgo.utils.command.terminal.phase.LocalPhaseTerminal;
import cloud.jgo.utils.command.terminal.phase.Phase;

public class ColorLocalPhaseTerminalTest {
	public static void main(String[] args) {
		

				
//				TerminalColors.PARAMETER_COLOR = Color.YELLOW;
//				TerminalColors.COMMAND_COLOR = Color.CYAN;
//				TerminalColors.PHASE_COLOR = Color.MAGENTA;
				
				// provare il character se funziona

				LocalPhaseTerminal t = new LocalPhaseTerminal("mio terminal");
				
				// unica fase 
				
				Phase phase = t.createPhase(1, "Unica", "Unica fase esistente", null);
				
				t.setName("mio terminale");

				t.useGeneralHelp();

				ColorLocalCommand.setInputHelpExploitable(true);
				ColorLocalCommand.setToStringParamName("c");
				
				// ottengo il comando 
				
				LocalCommand personCmd = LocalCommand.getCommandByObject(Persona.class);
				
				// aggiungo il comando al terminale 
				
				t.addCommand(personCmd);
				
				// avvio il terminale 
				
				t.open();
		
	}
}