/**
 * JGO - A pure Java library,
 * its purpose is to make life easier for the programmer.
 *
 * J - Java
 * G - General
 * O - Operations
 *
 * URL Software : https://www.jgo.cloud/
 * URL Documentation : https://www.jgo.cloud/docs/
 *
 * Copyright � 2018 - Marco Martire (www.jgo.cloud)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the MIT License.
 *
 * You may obtain a copy of License at :
 * https://www.jgo.cloud/LICENSE.txt
 *
 * To collaborate on this project, you need to do it from the software site.
 * 
 */
package cloud.jgo.utils.command.execution;

import java.io.Serializable;
/**
 * 
 * @author Martire91<br>
 * This interface makes executable
 */
public interface Executable extends Serializable{
	/**
	 * Thsi method sets the execution
	 * @param execution the execution
	 */
	public abstract void setExecution(Execution execution); // esegue il comando in questione
	/**
	 * This method calls the method exec of the execution
	 * @return the object returned from execution
	 */
	public abstract Object execute(); // questo comando deve eseguire l'esecuzione
    /**
     * This method returns true if there is an execution present
     * @return the flag
     */
	public abstract boolean hasAnExecution();
}
