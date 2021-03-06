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
package cloud.jgo.io.jon;

/**
 * 
 * @author Martire91<br>
 *         This exception occurs when the class is not annotated
 *
 */
public class JONMarshallingException extends Exception {
	private static final long serialVersionUID = 1L;
	private Class<?> clazz = null;

	public Class<?> getClazz() {
		return clazz;
	}

	public JONMarshallingException(Class<?> clazz) {
		// TODO Auto-generated constructor stub
		super(" - Object is not valid for JON marshalling #");
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Exception in " + getStackTrace()[0].getClassName() + "" + getMessage() + " :\n" + "Class "
				+ clazz.getName() + " is not noted " + getClass().getName();
	}
	// getMessage()+" : "+"read from = null "+getClass().getName();

}
