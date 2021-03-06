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
package cloud.jgo.utils.command;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import cloud.jgo.j�;
import cloud.jgo.�;
import cloud.jgo.utils.command.annotations.InvalidClassException;
import cloud.jgo.utils.command.annotations.CommandClass;
import cloud.jgo.utils.command.annotations.Configurable;
import cloud.jgo.utils.command.annotations.ParameterField;
import cloud.jgo.utils.command.annotations.ParameterMethod;
import cloud.jgo.utils.command.execution.Execution;
import cloud.jgo.utils.command.execution.SharedExecution;
import cloud.jgo.utils.command.terminal.Terminal;
import cloud.jgo.utils.command.terminal.phase.Phase;

/**
 * 
 * @author Martire91<br>
 *         This class represents a local command from a terminal
 */
public class LocalCommand implements Command, Iterable<Entry<String, Parameter>>, Shareable, Comparable<LocalCommand> {
	private static final long serialVersionUID = 1L;
	private Execution execution = null;
	protected String help = null;
	private String effect = null;
	private LocalCommand.HelpCommand helpCommand = new LocalCommand.HelpCommand();
	protected String command = null;
	private Object sharedObject = null;
	private static String helpValue = "help";
	private String inputValue = null;
	private boolean inputValueExploitable = false;
	private static boolean inputHelpExploitable = false;
	private boolean merged = false;
	private Phase belongsTo = null;
	// version 1.0.9 : da usare con le annotazioni
	// variabile usata internamente
	private static LocalCommand objCommand = null;

	/**
	 * This method is very useful.
	 * Create a command to create a given type of object, 
	 * so the command is the name of the class, appropriately annotated with
	 * {@link CommandClass}, moreover, the configurable interface can also be implemented,
	 * which indicates when the configuration of a certain type of object is completed.
	 * The parameters of the command, will be the fields and methods of the class,
	 * obviously annotating everything with {@link ParameterField} and {@link ParameterMethod}.
	 * The symbol "�" acts as a space for the arguments (String) of a method, so to give as an
	 * argument for example "Hello World", we will do: -methodName hello � world � !!
	 * The final string will be: hello world !!
	 * @see Configurable
	 * @see CommandClass
	 * @see ParameterField
	 * @see ParameterMethod
	 * @param a The class you want to convert into command
	 * @param <A> type
	 * @return the command
	 */
	public static <A> LocalCommand getCommandByType(Class<?> a) {
		// 1 cosa controllo che sia una classe annotata
		CommandClass commandAnnotation = null;
		if (a.isAnnotationPresent(CommandClass.class)) {
			commandAnnotation = a.getDeclaredAnnotation(CommandClass.class);
			if (commandAnnotation.command().equals("default")) {
				objCommand = new LocalCommand(a.getSimpleName().toLowerCase(), commandAnnotation.help());
			} else {
				objCommand = new LocalCommand(commandAnnotation.command(), commandAnnotation.help());
			}
			// parametro new : condivide l'oggetto
			Parameter parameter = objCommand.addParam("new", "This parameter instantiates the object");
			Parameter cancelParameter = objCommand.addParam("cancel","This parameter cancels the object currently being processed, thus making it \"null\"");
			parameter.setExecution(new Execution() {
				@Override
				public Object exec() {
					Object obj = null;
					try {
						obj = a.newInstance();
						objCommand.shareObject(obj);
						return "Instantiated object ( OK )";
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						return e.getMessage();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						return e.getMessage();
					}
				}
			});
			cancelParameter.setExecution(new Execution() {
				
				@Override
				public Object exec() {
					if (objCommand.getSharedObject()!=null) {
						objCommand.shareObject(null);
						return "Cancellation of the object ( OK )";
					}
					else {
						return "No objects have been processed #";
					}
				}
			});
			// qui proseguo con i campi
			Field[] fields = a.getDeclaredFields();
			if (commandAnnotation.involveAllFields()) {
				// qui trasformiamo tutti i fields in parametri
				// tranne le costanti ovviamente
				for (Field field : fields) {
					field.setAccessible(true);
					// verifico che il campo non sia una costante
					if (!Modifier.isFinal(field.getModifiers())) {
						Parameter param = objCommand.addParam(field.getName(), "set " + �.escp(field.getName()) + "");
						param.setInputValueExploitable(true);
						param.setExecution(new Execution() {
							@Override
							public Object exec() {
								if (param.getInputValue() != null) {

									if (objCommand.getSharedObject() != null) {
										boolean setOk = false;
										String fieldValue = param.getInputValue();
										// controllo il tipo del campo

										if (field.getType().isPrimitive()) {
											// is a primitive
											if (field.getType().getSimpleName().equals("int")) {
												int value = Integer.parseInt(fieldValue);
												try {
													field.setInt(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("double")) {
												double value = Double.parseDouble(fieldValue);
												try {
													field.setDouble(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("float")) {
												float value = Float.parseFloat(fieldValue);
												try {
													field.setFloat(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("long")) {
												long value = Long.parseLong(fieldValue);
												try {
													field.setLong(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("short")) {
												short value = Short.parseShort(fieldValue);
												try {
													field.setShort(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("char")) {
												if (fieldValue.length() == 1) {
													try {
														field.setChar(objCommand.getSharedObject(),
																fieldValue.charAt(0));
														setOk = true;
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} else {
													return "The \"" + field.getName()
															+ "\" field requires a single character #";
												}
											} else if (field.getType().getSimpleName().equals("boolean")) {
												short value = Short.parseShort(fieldValue);
												try {
													field.setShort(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										} else {
											// is an object
											if (!field.getType().isArray()) {

												if (field.getType().getSimpleName().equals("String")
														|| field.getType().getSimpleName().equals("StringBuffer")) {
													try {
														field.set(objCommand.getSharedObject(), fieldValue);
														setOk = true;
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} else {
													// qui faccio il controllo del tipo di oggetto
													if (field.getType().getSimpleName().equals("Integer")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Integer.parseInt(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Double")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Double.parseDouble(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Float")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Float.parseFloat(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Long")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Long.parseLong(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Short")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Short.parseShort(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Character")) {
														if (fieldValue.length() == 1) {
															try {
																field.set(objCommand.getSharedObject(), fieldValue);
																setOk = true;
															} catch (NumberFormatException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalArgumentException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalAccessException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
														} else {
															return "The \"" + field.getName()
																	+ "\" field requires a single character #";
														}
													} else if (field.getType().getSimpleName().equals("Boolean")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Boolean.parseBoolean(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else {
														// qui si tratta di un altro tipo di oggetto
														// quindi gestire ....
													}
												}
											}
										}
										if (setOk) {
											// qui sappiamo che il settaggio � avvenuto, per cui posso controllare
											boolean completed = false;
											if (Configurable.class.isAssignableFrom(a)) {
												try {
													Method method = a.getDeclaredMethod("isCompleted", null);
													try {
														completed = (boolean) method.invoke(objCommand.sharedObject,
																new Object[] {});
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (InvocationTargetException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} catch (NoSuchMethodException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (SecurityException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
											if (completed)
												return "The \"" + field.getName() + "\" variable is set ( OK )\n"
														+ "\t\t***************** Object config:completed *****************";
											else
												return "The \"" + field.getName() + "\" variable is set ( OK )";
										} else {
											// da verificare ...
											return null;
										}
									} else {
										// non esiste un oggetto condiviso
										return "No instanced objects - use \"new\" param #";
									}
								}
								return null;
							}
						});
					}
				}
				// qui ... :
				// prendo i metodi della classe : i metodi li filtro con l'annotazione
				Method[] methods = a.getDeclaredMethods();
				for (Method method : methods) {
					method.setAccessible(true);
					// controllo se � annotato questo metodo
					if (method.isAnnotationPresent(ParameterMethod.class)) {
						String help = method.getDeclaredAnnotation(ParameterMethod.class).help();
						// creo il parametro
						Parameter paraMethod = objCommand.addParam(method.getName(), help);
						int paramsCount = method.getParameterCount();
						// a questo punto devo verificare se il metodo ha parametri
						if (paramsCount>0) {
							// bene setto l'input sfruttabile
							paraMethod.setInputValueExploitable(true);
							// mi creo l'esecuzione
							paraMethod.setExecution(new Execution() {
								@Override
								public Object exec() {
									Object result = null ;
										if (objCommand.getSharedObject()!=null) {
											if (paraMethod.getInputValue()!=null) {
												// quindi nell'input value ci devono essere
												// paramsCount elementi
												String[]split = paraMethod.getInputValue().split(" ");
												if (split.length==paramsCount) {
													// bene i parametri sono stati forniti correttamente
													// adesso devo capire i tipi dei parametri
													Class<?>[]paramTypes=method.getParameterTypes();
													Object[]values = new Object[paramTypes.length];
													for (int i = 0; i < paramTypes.length; i++) {
														Class<?>type = paramTypes[i];
														Object currentValue = null ;
														if(type.isPrimitive()) {
															if(type.getSimpleName().equals("int"))currentValue = Integer.parseInt(split[i]);
															else if(type.getSimpleName().equals("double"))currentValue = Double.parseDouble(split[i]);
															else if(type.getSimpleName().equals("float"))currentValue = Float.parseFloat(split[i]);
															else if(type.getSimpleName().equals("short"))currentValue = Short.parseShort(split[i]);
															else if(type.getSimpleName().equals("long"))currentValue = Long.parseLong(split[i]);
															else if(type.getSimpleName().equals("boolean"))currentValue = Boolean.parseBoolean(split[i]);
															else if(type.getSimpleName().equals("char"))currentValue = split[i].charAt(0);// provvisorio ...
														}
														else if(type.isArray()) {
															// da definire ...
														}
														else {
															// is a object
															if (type.getSimpleName().equals("String"))currentValue = split[i].replaceAll("�"," ");
															else if(type.getSimpleName().equals("StringBuffer"))currentValue = split[i].replaceAll("�"," ");
															else if(type.getSimpleName().equals("Integer"))currentValue = Integer.parseInt(split[i]);
															else if(type.getSimpleName().equals("Double"))currentValue = Double.parseDouble(split[i]);
															else if(type.getSimpleName().equals("Float"))currentValue = Float.parseFloat(split[i]);
															else if(type.getSimpleName().equals("Short"))currentValue = Short.parseShort(split[i]);
															else if(type.getSimpleName().equals("Long"))currentValue = Long.parseLong(split[i]);
															else if(type.getSimpleName().equals("Boolean"))currentValue = Boolean.parseBoolean(split[i]);
															else if(type.getSimpleName().equals("Character"))currentValue = split[i].charAt(0);// provvisorio ...
														}
														if (currentValue!=null) {
															values[i] = currentValue;
														}
													}
													// okok qui abbiamo finito l'elaborazione, adesso possiamo eseguire il metodo
													try {
														result =  method.invoke(objCommand.getSharedObject(),values);
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (InvocationTargetException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												else {
													// qui invece i parametri non si trovano
													// quindi o sn di pi� o di meno
													result = "Wrong number of parameters #";
												}
											}
										}
										else {
											result = "non-existent object #";
										}
										return result ;
								}
							});
						}
						else {
							// non c'� valore da input
							// qui facciamo una esecuzione semplice
							paraMethod.setExecution(new Execution() {
								
								@Override
								public Object exec() {
									// TODO Auto-generated method stub
									Object return_ = null ;
									if (objCommand.getSharedObject()!=null) {
										try {
											return_ = method.invoke(objCommand.getSharedObject(),new Object[] {});
										} catch (IllegalAccessException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IllegalArgumentException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (InvocationTargetException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else {
										return_ = "non-existent object #";
									}
									return return_ ;
								}
							});
						}
					}
				}
			} else {
				// quii invece filtriamo quali field devono essere parametri
				for (Field field : fields) {
					field.setAccessible(true);
					// verifico se il campo � annotato
					if (field.isAnnotationPresent(ParameterField.class)) {
						Parameter param = objCommand.addParam(field.getName(),
								field.getAnnotation(ParameterField.class).help());
						param.setInputValueExploitable(true);
						param.setExecution(new Execution() {
							@Override
							public Object exec() {
								if (param.getInputValue() != null) {

									if (objCommand.getSharedObject() != null) {
										boolean setOk = false;
										String fieldValue = param.getInputValue();
										// controllo il tipo del campo

										if (field.getType().isPrimitive()) {
											// is a primitive
											if (field.getType().getSimpleName().equals("int")) {
												int value = Integer.parseInt(fieldValue);
												try {
													field.setInt(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("double")) {
												double value = Double.parseDouble(fieldValue);
												try {
													field.setDouble(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("float")) {
												float value = Float.parseFloat(fieldValue);
												try {
													field.setFloat(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("long")) {
												long value = Long.parseLong(fieldValue);
												try {
													field.setLong(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("short")) {
												short value = Short.parseShort(fieldValue);
												try {
													field.setShort(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											} else if (field.getType().getSimpleName().equals("char")) {
												if (fieldValue.length() == 1) {
													try {
														field.setChar(objCommand.getSharedObject(),
																fieldValue.charAt(0));
														setOk = true;
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} else {
													return "The \"" + field.getName()
															+ "\" field requires a single character #";
												}
											} else if (field.getType().getSimpleName().equals("boolean")) {
												short value = Short.parseShort(fieldValue);
												try {
													field.setShort(objCommand.getSharedObject(), value);
													setOk = true;
												} catch (IllegalArgumentException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IllegalAccessException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										} else {
											// is an object
											if (!field.getType().isArray()) {

												if (field.getType().getSimpleName().equals("String")
														|| field.getType().getSimpleName().equals("StringBuffer")) {
													try {
														field.set(objCommand.getSharedObject(), fieldValue);
														setOk = true;
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} else {
													// qui faccio il controllo del tipo di oggetto
													if (field.getType().getSimpleName().equals("Integer")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Integer.parseInt(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Double")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Double.parseDouble(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Float")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Float.parseFloat(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Long")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Long.parseLong(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Short")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Short.parseShort(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else if (field.getType().getSimpleName().equals("Character")) {
														if (fieldValue.length() == 1) {
															try {
																field.set(objCommand.getSharedObject(), fieldValue);
																setOk = true;
															} catch (NumberFormatException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalArgumentException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalAccessException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
														} else {
															return "The \"" + field.getName()
																	+ "\" field requires a single character #";
														}
													} else if (field.getType().getSimpleName().equals("Boolean")) {
														try {
															field.set(objCommand.getSharedObject(),
																	Boolean.parseBoolean(fieldValue));
															setOk = true;
														} catch (NumberFormatException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalArgumentException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (IllegalAccessException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													} else {
														// qui si tratta di un altro tipo di oggetto
														// quindi gestire ....
													}
												}
											}
										}
										if (setOk) {
											// qui sappiamo che il settaggio � avvenuto, per cui posso controllare
											boolean completed = false;
											if (Configurable.class.isAssignableFrom(a)) {
												try {
													Method method = a.getDeclaredMethod("isCompleted", null);
													try {
														completed = (boolean) method.invoke(objCommand.sharedObject,
																new Object[] {});
													} catch (IllegalAccessException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (IllegalArgumentException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} catch (InvocationTargetException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												} catch (NoSuchMethodException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (SecurityException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
											if (completed)
												return "The \"" + field.getName() + "\" variable is set ( OK )\n"
														+ "\t\t***************** Object config:completed *****************";
											else
												return "The \"" + field.getName() + "\" variable is set ( OK )";
										} else {
											// da verificare ...
											return null;
										}
									} else {
										// non esiste un oggetto condiviso
										return "No instanced objects - use \"new\" param #";
									}
								}
								return null;
							}
						});
					}
				}
				// qui ... :
				// prendo i metodi della classe : i metodi li filtro con l'annotazione
				Method[] methods = a.getDeclaredMethods();
				for (Method method : methods) {
					method.setAccessible(true);
					// controllo se � annotato questo metodo
					if (method.isAnnotationPresent(ParameterMethod.class)) {
						String help = method.getDeclaredAnnotation(ParameterMethod.class).help();
						int paramsCount = method.getParameterCount();
						// creo il parametro
						Parameter paraMethod = objCommand.addParam(method.getName(), help);
						if (paramsCount>0) {
							// il metodo ha dei parametri, quindi setto l'input value sfruttabile
							paraMethod.setInputValueExploitable(true);
							paraMethod.setExecution(new Execution() {
								@Override
								public Object exec() {
									if (paraMethod.getInputValue()!=null) {
										// quindi nell'input value ci devono essere
										// paramsCount elementi
										String[]split = paraMethod.getInputValue().split(" ");
										if (split.length==paramsCount) {
											// bene i parametri sono stati forniti correttamente
											// adesso devo capire i tipi dei parametri
											Class<?>[]paramTypes=method.getParameterTypes();
											Object[]values = new Object[paramTypes.length];
											for (int i = 0; i < paramTypes.length; i++) {
												Class<?>type = paramTypes[i];
												Object currentValue = null ;
												if(type.isPrimitive()) {
													if(type.getSimpleName().equals("int"))currentValue = Integer.parseInt(split[i]);
													else if(type.getSimpleName().equals("double"))currentValue = Double.parseDouble(split[i]);
													else if(type.getSimpleName().equals("float"))currentValue = Float.parseFloat(split[i]);
													else if(type.getSimpleName().equals("short"))currentValue = Short.parseShort(split[i]);
													else if(type.getSimpleName().equals("long"))currentValue = Long.parseLong(split[i]);
													else if(type.getSimpleName().equals("boolean"))currentValue = Boolean.parseBoolean(split[i]);
													else if(type.getSimpleName().equals("char"))currentValue = split[i].charAt(0);// provvisorio ...
												}
												else if(type.isArray()) {
													// da definire ...
												}
												else {
													// is a object
													if (type.getSimpleName().equals("String"))currentValue = split[i];
													else if(type.getSimpleName().equals("StringBuffer"))currentValue = split[i];
													else if(type.getSimpleName().equals("Integer"))currentValue = Integer.parseInt(split[i]);
													else if(type.getSimpleName().equals("Double"))currentValue = Double.parseDouble(split[i]);
													else if(type.getSimpleName().equals("Float"))currentValue = Float.parseFloat(split[i]);
													else if(type.getSimpleName().equals("Short"))currentValue = Short.parseShort(split[i]);
													else if(type.getSimpleName().equals("Long"))currentValue = Long.parseLong(split[i]);
													else if(type.getSimpleName().equals("Boolean"))currentValue = Boolean.parseBoolean(split[i]);
													else if(type.getSimpleName().equals("Character"))currentValue = split[i].charAt(0);// provvisorio ...
												}
												if (currentValue!=null) {
													values[i] = currentValue;
												}
											}
											// okok qui abbiamo finito l'elaborazione, adesso possiamo eseguire il metodo
											try {
												return method.invoke(objCommand.getSharedObject(),values);
											} catch (IllegalAccessException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IllegalArgumentException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (InvocationTargetException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
										else {
											// qui invece i parametri non si trovano
											// quindi o sn di pi� o di meno
											return "Wrong number of parameters #";
										}
									}
									return null ;
								}
							});
						}
						else {
						paraMethod.setExecution(new Execution() {
							@Override
							public Object exec() {
								if (objCommand.getSharedObject() != null) {
									Object return_ = null;
									try {
										return_ = method.invoke(objCommand.getSharedObject(), new Object[] {});
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									return return_;
								} else {
									return "non-existent object #";
								}
							}
						});
						}
					}
				}
			}
		} else {
			// dare una eccezzione
			try {
				throw new InvalidClassException(a);
			} catch (InvalidClassException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return objCommand;
	}

	// version 1.0.9 : da segnalare ...
	private static String toStringParamName = "to_string";

	// version 1.0.9 :
	/**
	 * This method prints a report of all the fields of the shared object, then
	 * tells us which variables have been set and which are not, all this happens
	 * through reflection. Assuming that the class is noted, this method also checks
	 * if the class implements Configurable. However, this is checked only if the
	 * class is noted, and not some super class.
	 * 
	 * @see Configurable
	 * @param sharedObject
	 *            the shared object
	 * @return the shared object configuration
	 * @throws IllegalArgumentException
	 *             1 exception
	 * @throws IllegalAccessException
	 *             2 exception
	 */
	public static String toString(Object sharedObject) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer string = new StringBuffer();
		if (sharedObject.getClass().isAnnotationPresent(CommandClass.class)) {
			string.append("-----------------------------------------------------------------------------------\n");
			if (((CommandClass) sharedObject.getClass().getDeclaredAnnotation(CommandClass.class)).command()
					.equals("default")) {
				string.append(" " + sharedObject.getClass().getSimpleName()).append(" ~ Configuration\n");
			} else {
				// sharedObject.getClass().getSimpleName()
				string.append(" "
						+ ((CommandClass) sharedObject.getClass().getDeclaredAnnotation(CommandClass.class)).command())
						.append(" ~ Configuration\n");
			}
			string.append("-----------------------------------------------------------------------------------\n");
			Class<?> clazz = sharedObject.getClass();
			Field[] fields = clazz.getDeclaredFields();
			int count = 0;
			// here
			if (((CommandClass) clazz.getAnnotation(CommandClass.class)).involveAllFields()) {
				// qui vengono coinvolti tutti i parametri
				for (Field field : fields) {
					field.setAccessible(true);
					// if (!Modifier.isFinal(field.getModifiers())) {
					String fieldName = field.getName();
					Object fieldValue = field.get(sharedObject);
					if (count == 3) {
						// si va a capo
						count = 0;
						string.append("\n\n");
					}
					string.append("* " + fieldName).append("=").append(fieldValue + "").append("  ");
					// }
					count++;
				}
			} else {
				for (Field field : fields) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(ParameterField.class)) {
						String fieldName = field.getName();
						Object fieldValue = field.get(sharedObject);
						if (count == 3) {
							// si va a capo
							count = 0;
							string.append("\n\n");
						}
						string.append("* " + fieldName).append("=").append(fieldValue + "").append("  ");
					}
					count++;
				}
			}
			// okok qui controllo se l'oggetto � una instanza di configurable
			if (Configurable.class.isInstance(sharedObject)) {
				// qui devo individuare i 3 metodi
				Method[] methods = sharedObject.getClass().getDeclaredMethods();
				for (Method method : methods) {
					method.setAccessible(true);
					if (method.getName().equals("isCompleted")) {
						try {
							boolean result = (boolean) method.invoke(sharedObject, new Object[] {});
							if (!string.toString().endsWith("\n\n"))
								string.append("\n\n").append("* " + method.getName()).append("=").append(result);
							else
								string.append("* " + method.getName()).append("=").append(result);
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// continuare da qui con gli altri due metodi di configurable ...
				}
			}
			return string.toString() + "\n";
		} else {
			Class<? extends Object> superClass = sharedObject.getClass().getSuperclass();
			if (superClass != null) {
				if (superClass.isAnnotationPresent(CommandClass.class)) {
					string.append(
							"-----------------------------------------------------------------------------------\n");
					if (((CommandClass) superClass.getDeclaredAnnotation(CommandClass.class)).command()
							.equals("default")) {
						string.append(" " + superClass.getSimpleName()).append(" ~ Configuration\n");
					} else {
						// sharedObject.getClass().getSimpleName()
						string.append(
								" " + ((CommandClass) superClass.getDeclaredAnnotation(CommandClass.class)).command())
								.append(" ~ Configuration\n");
					}
					string.append(
							"-----------------------------------------------------------------------------------\n");
					Class<?> clazz = superClass;
					Field[] fields = clazz.getDeclaredFields();
					int count = 0;
					// here
					if (((CommandClass) superClass.getAnnotation(CommandClass.class)).involveAllFields()) {
						// qui vengono coinvolti tutti i parametri
						for (Field field : fields) {
							field.setAccessible(true);
							// if (!Modifier.isFinal(field.getModifiers())) {
							String fieldName = field.getName();
							Object fieldValue = field.get(sharedObject);
							if (count == 3) {
								// si va a capo
								count = 0;
								string.append("\n\n");
							}
							string.append("* " + fieldName).append("=").append(fieldValue + "").append("  ");
							// }
							count++;
						}
					} else {
						for (Field field : fields) {
							field.setAccessible(true);
							if (field.isAnnotationPresent(ParameterField.class)) {
								String fieldName = field.getName();
								Object fieldValue = field.get(sharedObject);
								if (count == 3) {
									// si va a capo
									count = 0;
									string.append("\n\n");
								}
								string.append("* " + fieldName).append("=").append(fieldValue + "").append("  ");
							}
							count++;
						}
					}
				} else {
					string = null;
					try {
						throw new InvalidClassException(sharedObject.getClass());
					} catch (InvalidClassException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				string = null;
				try {
					throw new InvalidClassException(sharedObject.getClass());
				} catch (InvalidClassException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (string != null)
			return string.toString() + "\n";
		else
			return null;
	}

	public LocalCommand(String command, String help) {
		// quando inizializzo il costruttore
		this.help = help;
		this.command = command;
		// setto l'help
		this.helpCommand.reload(this);
	}

	/**
	 * This method is used to set the name of the parameter that will take care of
	 * printing the "toString" method of the shared object.
	 * 
	 * @param toStringParamName
	 *            parameter name
	 */
	public static void setToStringParamName(String toStringParamName) {
		LocalCommand.toStringParamName = toStringParamName;
	}

	/**
	 * This method is used to get the name of the parameter that will take care of
	 * printing the "toString" method of the shared object.
	 * 
	 * @return parameter name
	 */
	public static String getToStringParamName() {
		return toStringParamName;
	}

	public LocalCommand(String command, String help, Execution execution) {
		this.command = command;
		this.help = help;
		this.helpCommand.reload(this);
		this.execution = execution;
	}

	public void setBelongsTo(Phase belongsTo) {
		this.belongsTo = belongsTo;
	}

	@Override
	public Phase getBelongsTo() {
		return this.belongsTo;
	}

	// qui ci sar� una struttura dati che regger� il gioco
	protected Map<String, Parameter> structure = new HashMap<String, Parameter>();

	public Map<String, Parameter> getStructure() {
		return structure;
	}

	public boolean useThread = false;
	private When when = When.ALWAYS;

	// version 1.0.9
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return this.command;
	}

	@Override
	public boolean hasAPhase() {
		if (getBelongsTo() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		Command command = (Command) obj;
		int total = 0;
		int count = 0;
		if (this.getCommand().equals(command.getCommand())) {
			if (this.countParameters() == command.countParameters()) {
				equals = true;
			}
		}
		return equals;
	}

	/**
	 * This method reset the structure
	 */
	public void clearStructure() {
		this.structure.clear();
	}

	/**
	 * This method divides the command for the parameters
	 * 
	 * @param inputCommand
	 *            the input command
	 * @return the parameters list
	 */
	public static List<String> splitForParameters(String inputCommand) {

		// in tanto qui � importante prendere il comando ,prima di tutto
		String onlyCommand = null;
		List<String> parameters = new ArrayList<>();

		// splitto per spazio

		String[] splitForSpace = inputCommand.split(" ");

		// controllo che ci siano params

		if (splitForSpace.length > 1) {
			// controllo che la prima riga ,cio� il solo comando non sia vuoto
			if (!splitForSpace[0].isEmpty()) {
				onlyCommand = splitForSpace[0];

				// si continua da qui
				// ora devo prendere dalla lunghezza del comando ,quello che rimane

				String rest = inputCommand.substring(onlyCommand.length()).trim();

				// okok ora splitto il resto per il simbolo del param

				String[] splitForParameters = rest.split("-");

				// qui faccio iterare i params per correggerli
				for (int i = 0; i < splitForParameters.length; i++) {

					// qui devo controllare se il param in questione ha un valore da input
					String param = splitForParameters[i];

					if (!param.isEmpty()) {
						splitForSpace = param.split(" ");
						// quindi se lo split � uno vuol dire che � senza input value
						// invece se � maggiore di 1 vuol dire che ha valore da input
						if (splitForSpace.length > 1) { // c'� il valore da input,quindi prendo il primo elemento
							param = splitForSpace[0];
						}
						// aggiungo il param alla lista
						parameters.add(param);
					}
				}

			}
		}
		return parameters;
	}

	/**
	 * This method divides the command for the parameters
	 * 
	 * @param inputCommand
	 *            the input command
	 * @return the parameters list
	 */
	public static String[] splitForParameters_2(String inputCommand) {

		// in tanto qui � importante prendere il comando ,prima di tutto
		String onlyCommand = null;
		List<String> parameters = new ArrayList<>();
		String[] params = null;
		// splitto per spazio

		String[] splitForSpace = inputCommand.split(" ");

		// controllo che ci siano params

		if (splitForSpace.length > 1) {
			// controllo che la prima riga ,cio� il solo comando non sia vuoto
			if (!splitForSpace[0].isEmpty()) {
				onlyCommand = splitForSpace[0];

				// si continua da qui
				// ora devo prendere dalla lunghezza del comando ,quello che rimane

				String rest = inputCommand.substring(onlyCommand.length()).trim();

				// okok ora splitto il resto per il simbolo del param

				String[] splitForParameters = rest.split("-");

				// qui faccio iterare i params per correggerli
				for (int i = 0; i < splitForParameters.length; i++) {

					// qui devo controllare se il param in questione ha un valore da input
					String param = splitForParameters[i];

					if (!param.isEmpty()) {
						splitForSpace = param.split(" ");
						// quindi se lo split � uno vuol dire che � senza input value
						// invece se � maggiore di 1 vuol dire che ha valore da input
						if (splitForSpace.length > 1) { // c'� il valore da input,quindi prendo il primo elemento
							param = splitForSpace[0];
						}
						// aggiungo il param alla lista
						parameters.add(param);
					}
				}

				// qui si controlla se la lista ha un size accettabile

				if (parameters.size() > 0) {
					params = new String[parameters.size()];
					for (int i = 0; i < parameters.size(); i++) {
						params[i] = parameters.get(i);
					}
				}

			}
		}
		return params;
	}

	/**
	 * This method sets the help command from input
	 * 
	 * @param exploitable
	 *            the flag
	 */
	public static void setInputHelpExploitable(boolean exploitable) {
		inputHelpExploitable = exploitable;
	}

	/**
	 * This method checks if the command has the exploitable help command
	 * 
	 * @return the flag
	 */

	public boolean hasInputHelpExploitable() {
		return inputHelpExploitable;
	}

	/**
	 * 
	 * @author Martire91<br>
	 *         This class is the command help
	 */
	public static class HelpCommand implements Serializable, Comparable<HelpCommand> {
		protected LocalCommand command = null;
		protected StringBuffer buffer = new StringBuffer();
		private final static long serialVersionUID = 1L;

		/**
		 * This method prints the help command
		 */
		public void print() {

			System.out.println(buffer.toString());
		}

		@Override
		public String toString() {
			/*
			 * 
			 * JGO Auto-generated method stub Author : � wasp91 � Date 06 gen 2018
			 * 
			 */
			return this.buffer.toString();
		}

		/**
		 * This command reloads the help command
		 * 
		 * @param command
		 *            the command
		 */
		public void reload(LocalCommand command) {
			this.command = command;
			this.buffer = new StringBuffer();
			buffer.append("===================================================================================\n");
			buffer.append("HELP Of " + "\"" + this.command.command + "\" - Phase :"
					+ ((LocalCommand) this.command).getBelongsTo() + "\n");
			buffer.append("===================================================================================\n");

			// qui devo prendere tutti i parameters
			Collection<Parameter> collection = command.structure.values();
			List<Parameter> orderParameters = command.sortParameters();
			// qui ci sar� la descrizione del comando root
			buffer.append(this.command.getHelp().toUpperCase() + "   / has input value ="
					+ this.command.hasInputValueExploitable() + "\n\n");
			if (orderParameters != null) {
				buffer.append("* Parameters :" + orderParameters + " :\n\n");
				if (this.command.hasParameters()) {
					// ci sono parametri
					// quindi qui devo prendere i params
					Iterator<Parameter> iterator = orderParameters.iterator();
					while (iterator.hasNext()) {
						Parameter param = iterator.next();
						buffer.append(param.getParam() + "=" + param.getParameterHelp() + "  / has input value ="
								+ param.hasInputValueExploitable() + "\n");
					}
				}
			} else {
				buffer.append("* Parameters :" + collection + " :\n\n");
				if (this.command.hasParameters()) {
					// ci sono parametri
					// quindi qui devo prendere i params
					Iterator<Entry<String, Parameter>> iterator = command.iterator();

					while (iterator.hasNext()) {
						Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter> entry = (Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter>) iterator
								.next();
						Parameter param = entry.getValue();
						buffer.append(param.getParam() + "=" + param.getParameterHelp() + "  / has input value ="
								+ param.hasInputValueExploitable() + "\n");
					}
				}
			}
		}

		@Override
		public int compareTo(HelpCommand arg0) {
			return this.command.command.compareTo(arg0.command.command);
		}

	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}

	@Override
	public Object execute() {
		Object execute = null;
		if (hasAnExecution()) {
			if (useThread == true) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (getExecution() instanceof SharedExecution) {
							((SharedExecution) getExecution()).setCurrentSharer(LocalCommand.this);
						}
						switch (when) {
						case ALWAYS:
							getExecution().exec();
							break;
						case IF_ACCESSIBLE:
							// verifico che il comando abbia una fase
							if (getBelongsTo() != null) {
								if (getBelongsTo().isAccessible()) {
									getExecution().exec();
								}
							}
							break;
						case IF_SATISFIED:
							if (getBelongsTo() != null) {
								if (getBelongsTo().isSatisfied()) {
									getExecution().exec();
								}
							}
							break;
						// poi qui aggiornare, quando ci saranno nuovi "quando"
						}
					}
				}).start();
			} else {
				if (getExecution() instanceof SharedExecution) {
					((SharedExecution) getExecution()).setCurrentSharer(this);
				}
				switch (when) {
				case ALWAYS:
					execute = getExecution().exec();
					break;
				case IF_ACCESSIBLE:
					if (getBelongsTo() != null) {
						if (getBelongsTo().isAccessible()) {
							execute = getExecution().exec();
						}
					}
					break;
				case IF_SATISFIED:
					if (getBelongsTo() != null) {
						if (getBelongsTo().isSatisfied()) {
							execute = getExecution().exec();
						}
					}
					break;
				// poi qui aggiornare, quando ci saranno nuovi "quando"
				}
			}
			return execute;
		} else {
			return execute;
		}
	}

	@Override
	public Execution getExecution() {
		return this.execution;
	}

	@Override

	public String getHelp() {
		return this.help;
	}

	@Override

	public String getEffect() {
		return this.effect;
	}

	@Override

	public boolean hasAnExecution() {
		if (this.execution != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Parameter addParam(String param, String help) {
		Parameter param_ = null;
		if (!isParameter(param)) {

			param_ = new DefaultParameter(param, help);

			// qui setto il parent del parametro
			param_.setParent(this);

			// aggiungo il param alla struttura dati

			this.structure.put(param, param_);

			getHelpCommand().reload(this);
		}
		return param_;
	}

	@Override
	public Parameter param(String param) {
		if (this.structure.containsKey(param)) {
			return this.structure.get(param);
		} else {
			return null;
		}
	}

	@Override
	public Parameter param(int index) {
		Iterator<Entry<String, Parameter>> iterator = this.iterator();
		Parameter param = null;
		while (iterator.hasNext()) {

			Entry<String, Parameter> entry = iterator.next();

			if (�.value() == index) {
				param = entry.getValue();

				break;
			}

			�.increment();
		}
		return param;
	}

	/**
	 * This method returns the command help
	 * 
	 * @return the command help
	 */
	public HelpCommand getHelpCommand() {
		return this.helpCommand;
	}

	@Override
	public boolean isParameter(String param) {
		boolean isParameter = false;
		Iterator<Entry<String, Parameter>> iterator = this.iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter> entry = (Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter>) iterator
					.next();
			String key = entry.getKey();
			if (param.equals(key)) {
				isParameter = true;
				break;
			}
		}
		return isParameter;
	}

	@Override
	public Iterator<Entry<String, Parameter>> iterator() {
		return this.structure.entrySet().iterator();
	}

	@Override
	public void removeAllParameters() {

		this.structure = new HashMap<String, Parameter>();

		getHelpCommand().reload(this);

	}

	@Override
	public boolean removeParam(String param) {
		if (countParameters() > 0) {
			Parameter param_ = this.structure.remove(param);

			if (param_ != null) {
				getHelpCommand().reload(this);
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override

	public int countParameters() {

		return this.structure.size();

	}

	@Override
	public boolean removeParam(int index) {
		Parameter param = param(index);
		if (param != null) {
			return removeParam(param.getOnlyParam());
		} else {
			return false;
		}

	}

	@Override
	public boolean replace(String param, Parameter newValue) {
		if (countParameters() > 0) {

			// qui individuo il param
			Parameter param_ = param(param);
			if (param_ != null) {

				boolean flag = this.structure.replace(param, param_, newValue);

				if (flag == true) {
					getHelpCommand().reload(this);
				}
				return flag;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override

	public String getCommand() {
		return this.command;
	}

	@Override
	public Object executeParam(String param) {

		// qui in tanto verifico se il param � un vero param del comando
		if (isParameter(param)) {

			// ottengo il param
			Parameter get = this.param(param);

			return get.execute();
		} else {
			return null;
		}
	}

	@Override
	public Object executeParam(String param, String inputValue) {

		// qui in tanto verifico se il param � un vero param del comando
		if (isParameter(param)) {

			// ottengo il param
			Parameter get = this.param(param);

			get.setInputValue(inputValue);

			return get.execute();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	private static class PrivateEntry {

		private String param;
		private String value;

		public PrivateEntry(String param, String value) {
			this.param = param;
			this.value = value;
		}

		public String getParam() {
			return param;
		}

		public void setParam(String param) {
			this.param = param;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * Questo metodo va spiegato bene : lavora con la stampa di sistema mette a
	 * disposizione una struttura di get and set per le variabili degli oggetti
	 * condivisi su cui si lavora. per settare per esempio la var nome di persona mi
	 * baster� fare :
	 * 
	 * persona -nome Gioacchino
	 * 
	 * Per recuperare il nome poi faccio
	 * 
	 * persona -nome Questo discorso, attenzione � solo per le variabili degli
	 * oggetti condivisi poich� il metodo quando riceve un comando che vuole
	 * accedere a una var,recupera l'oggetto condiviso di quel comando e su di esso
	 * tenta di recuperare quella variabile, il valore della variabile viene
	 * stampato anche nel caso in cui sia null, appunto per avvisare l'utente di
	 * questo valore nullo
	 *
	 */
	// a questo metodo importantissimo ho tolto lo static
	// quindi casomai da problems vedere un attimino o aggiungere lo static al
	// metodo
	// okok qui iniziano i metodi complessi
	// spiegare cosa ritorna questo metodo
	private static Object objectReturn = null;

	/**
	 * This method executes the command and its possible parameters.
	 * 
	 * @param inputCommand
	 *            the input command
	 * @param commands
	 *            the commands list
	 * @return the returned object from execution
	 */
	public static ArrayList<Object> executeInputCommand(String inputCommand, List<? extends Command> commands) {
		final ArrayList<Object> commandReturnList = new ArrayList<>();
		objectReturn = null;
		// prima cosa da fare prendere il comando
		LocalCommand getCommand = null;
		String[] split = inputCommand.split(" ");

		// prendo il comando padre
		String command = null;
		if (!split[0].isEmpty()) {
			command = split[0];
			// controllo se c'� qualcosa dopo il comando
			if (split.length > 1) {
				// c'� qualcosa dopo il comando
				// quindi devo prendere quello che c'� dopo il comando
				int start = command.length();
				String rest = inputCommand.substring(start).trim();
				// okok ora qui devo controllare se il resto � uguale all'helps
				// oppure splittarlo per parameters
				if (rest.equals(LocalCommand.helpValue)) {
					// qui eseguo l'help
					// qui devo prendere il comando
					for (int i = 0; i < commands.size(); i++) {
						if (command.equals(commands.get(i).getCommand())) {
							getCommand = (LocalCommand) commands.get(i);
							break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
						}
					}
					// eseguo l'help solo se questo � sfruttabile
					if (getCommand.hasInputHelpExploitable()) {
						getCommand.getHelpCommand().print();
					}

				} else if (!rest.contains(Parameter.SEPARATOR)) {
					// quindi qui ha validit� solo se � richiesto un valore da input dal comando
					// e gli viene fornito, perch� se non gli venisse fornito non si entrerebbe qui�
					// dentro
					// qui dovrebbe entrare
					// se si da un comando con
					// valore di input, non si possono aggiungere parametri
					// quindi si presume che ci siano pochi comandi con valore da input

					// 1 passo : prendo il comando
					for (int i = 0; i < commands.size(); i++) {
						if (command.equals(commands.get(i).getCommand())) {
							getCommand = (LocalCommand) commands.get(i);
							break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
						}
					}
					if (getCommand != null) {
						// 2 passo : verifico se il comando ha un valore da input
						if (getCommand.hasInputValueExploitable()) {
							// controllo se di fatto c'� un valore da input
							getCommand.setInputValue(rest);
							// eseguo il comando
							objectReturn = getCommand.execute();
							if (objectReturn != null) {
								commandReturnList.add(objectReturn);
								objectReturn = commandReturnList;
							}
						}
					}
				} else {

					// devo qui prendere i parameters
					String[] splitForParameters = rest.split(Parameter.SEPARATOR);
					List<PrivateEntry> entries = new ArrayList<LocalCommand.PrivateEntry>();
					String param, paramValue = null;
					for (int i = 0; i < splitForParameters.length; i++) {
						paramValue = null;
						param = null;
						if (!(splitForParameters[i]).isEmpty()) {

							param = splitForParameters[i].split(" ")[0];

							if (splitForParameters[i].contains(" ")) {

								// c'� il valore

								paramValue = splitForParameters[i].substring(param.length()).trim();
							}
							if (paramValue != null) {
								if (paramValue.isEmpty()) {
									paramValue = null;
								}
							}
							// qui prendo l'entry
							PrivateEntry entry = new PrivateEntry(param, paramValue);
							entries.add(entry);
						}

					}
					// ora qui dobbiamo in tanto far iterare la lista di entries
					if (entries.size() > 0) {
						// qui per prima cosa devo individuare il comando
						for (int i = 0; i < commands.size(); i++) {
							if (command.equals(commands.get(i).getCommand())) {
								getCommand = (LocalCommand) commands.get(i);
								break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
							}
						}

						if (getCommand != null) {
							/***
							 * @author MARTIRE91 : QUI � IMPORTANTE SE UN GIORNO VOGLIAMO ESEGUIRE UN
							 *         COMANDO CHE HA UNA ESECUZIONE E ANCHE DEI PARAMS E QUI CHE DOBBIAMO
							 *         ESEGUIRLO,PERO C'� SOLO UN PROBLEMA POI E' DA TENERE PRESENTE CHE SE
							 *         PER ESEMPIO DIAMO UN COMANDO SIMILE : person -toString, lui
							 *         reinizializza di nuovo la persona e quindi ci stampa valori nulli per
							 *         il momento, l'esecuzione del comando ha valore solo se quest'ultimo
							 *         non ha params,cosa che prima o poi dovremo correggere.
							 */
							// itero la lista di entries
							for (int i = 0; i < entries.size(); i++) {
								PrivateEntry entry = entries.get(i);

								// okok qui per prima cosa verifico che il param corrente dell'entry
								// sia effettivamente un param del comando ottenuto

								if (getCommand.isParameter(entry.param)) {

									// ottengo il param vero e proprio per poi controllare se ha un valore da input
									// sfruttabile
									Parameter getParameter = getCommand.param(entry.param);

									if (getParameter != null) {

										// controllo se questo param ha un valore da input struttabile

										if (getParameter.hasInputValueExploitable()) {

											// qui devo controllare se l'entry ha il valore del parametro
											if (entry.value != null) {
												// setto il valore sfruttabile
												getParameter.setInputValue(entry.value);
												// eseguo il parametro
												objectReturn = getParameter.execute();
												if (objectReturn != null) {
													commandReturnList.add(objectReturn);

													// diciamo che in questo punto del metodo
													// il nostro oggetto di restituzione
													// diventa l'arraylist
													objectReturn = commandReturnList;
												}
											} else {

												// l'entry non ha un valore del parametro
												// eppure ci vuole ,quindi qui � un errore

												// okok ecco la zona di codice che ci interessa
												// qui in tanto � fattibile solo se si sta lavorando
												// con un oggetto condiviso
												// quindi per prima cosa verifico che ci sia
												// un oggetto condiviso

												LocalCommand parent = (LocalCommand) getParameter.getParent();
												Object obj = parent.getSharedObject();
												if (obj != null) {

													/*
													 * Gestire con la RIFLESSIONE :)
													 */

													// prendo il nome del parametro

													String onlyParam = getParameter.getOnlyParam();
													
													
													// qui devo verificare se si tratta di un metodo
													// o di un field
													
													Field[]fields = obj.getClass().getDeclaredFields();
													Method[]methods = obj.getClass().getDeclaredMethods();
													
													boolean isField = false ;
													boolean isMethod = false ;
													
													for (int j = 0; j < fields.length; j++) {
														fields[j].setAccessible(true);
														if (fields[j].getName().equals(onlyParam)) {
															isField = true ;
															break;
														}
													}
													if (isField) {
														// � un field il parametro
														Field field = null;
														try {
															field = obj.getClass().getDeclaredField(onlyParam);
														} catch (NoSuchFieldException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (SecurityException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
															field.setAccessible(true);
															// okok si � trovato
															// questa variabile
															// ora dobbiamo controllare se ha un valore
															Object valueObj = null;
															try {
																valueObj = field.get(obj);
															} catch (IllegalArgumentException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IllegalAccessException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}

															// qua sia che � null oppure che abbia un valore
															// noi stampiamo tale valore

															if (valueObj != null) {
																objectReturn = onlyParam + " = " + valueObj;
																// System.out.println(objectReturn);
																commandReturnList.add(objectReturn);
																objectReturn = commandReturnList;
															} else {
																// qui stampo il valore nullo del valore della var
																// e in pi� la stampa che ricorda che il param necessita
																// di un valore

																objectReturn = onlyParam + " = " + valueObj
																		+ "\nThis parameter requires a value";
																// System.out.println(objectReturn);
																commandReturnList.add(objectReturn);
																objectReturn = commandReturnList;
															}
													}
													else {
														// potrebbe essere un metodo
														Method m = null ;
														for (int j = 0; j < methods.length; j++) {
															methods[j].setAccessible(true);
															if (methods[j].getName().equals(onlyParam)) {
																m = methods[j];isMethod = true ;break;
															}
														}
														if (isMethod)
														{
															m.setAccessible(true);
															// per una questione di coerenza
															// controlliamo se il metodo ha degli argomenti
															if (m.getParameterCount()>0) {
																objectReturn = "This parameter requires a value";
																commandReturnList.add(objectReturn);
																objectReturn = commandReturnList ;
															}
														}
													}
												} else {
													// qui vuol dire che non c'� un oggetto condiviso
													objectReturn = "This parameter requires a value";
													commandReturnList.add(objectReturn);
													objectReturn = commandReturnList;
												}

											}

										} else {

											// qui significa che il parametro non ha un valore da input sfruttabile

											// allora se non ha un valore sfruttabile da input
											// bisogna per prima cosa verificare se l'apposito entry ha il value
											// in tal caso lo diamo come errore,quindi non succede niente
											// perch� l'utente ha sbagliato a scrivere il comando
											if (entry.value != null) {
												objectReturn = "The parameter (" + getParameter
														+ ") has not a exploitable input value #";
												// System.out.println(objectReturn);
												commandReturnList.add(objectReturn);
												objectReturn = commandReturnList;
											} else {
												// eseguo il parametro
												objectReturn = getParameter.execute();
												commandReturnList.add(objectReturn);
												objectReturn = commandReturnList;
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (split.length == 1) {
				// non c'� niente dopo il comando,quindi qui posso eseguirlo tranquillamente
				for (int i = 0; i < commands.size(); i++) {
					if (command.equals(commands.get(i).getCommand())) {
						getCommand = (LocalCommand) commands.get(i);
						break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
					}
				}
				if (getCommand != null) {
					objectReturn = getCommand.execute();
					commandReturnList.add(objectReturn);
					objectReturn = commandReturnList;
				}
			}
		}
		return (ArrayList<Object>) objectReturn;
	}

	private static LocalCommand getCommand = null;

	/**
	 * This method returns the command starting from the input command
	 * 
	 * @param inputCommand
	 *            the input command
	 * @param commands
	 *            the commands list
	 * @return the command
	 */
	public static LocalCommand getCommand(String inputCommand, List<? extends Command> commands) {
		String[] split = inputCommand.split(" ");
		getCommand = null;
		// prendo il comando padre
		String command = null;
		if (!split[0].isEmpty()) {
			command = split[0];
			// controllo se c'� qualcosa dopo il comando
			if (split.length > 1) {
				// c'� qualcosa dopo il comando
				// quindi devo prendere quello che c'� dopo il comando
				int start = command.length();
				String rest = inputCommand.substring(start).trim();
				// okok ora qui devo controllare se il resto � uguale all'helps
				// oppure splittarlo per parameters

				if (rest.equals(LocalCommand.helpValue)) {
					// qui eseguo l'help
					// qui devo prendere il comando
					for (int i = 0; i < commands.size(); i++) {
						if (command.equals(commands.get(i).getCommand())) {
							getCommand = (LocalCommand) commands.get(i);
							break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
						}
					}
					// // eseguo l'help solo se questo � sfruttabile
					// if(getCommand.hasInputHelpExploitable()){
					// getCommand.getHelpCommand().print();
					// }
				} else if (!rest.contains(Parameter.SEPARATOR)) {
					for (int i = 0; i < commands.size(); i++) {
						if (command.equals(commands.get(i).getCommand())) {
							getCommand = (LocalCommand) commands.get(i);
							break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
						}
					}
					if (getCommand != null) {
						// solo se il comando non ha un valore da input
						// setto getCommand a null
						if (!getCommand.hasInputValueExploitable()) {
							getCommand = null;
						}
					}
				} else {

					// devo qui prendere i parameters
					String[] splitForParameters = rest.split(Parameter.SEPARATOR);
					List<PrivateEntry> entries = new ArrayList<LocalCommand.PrivateEntry>();
					String param, paramValue = null;
					for (int i = 0; i < splitForParameters.length; i++) {
						paramValue = null;
						param = null;
						if (!(splitForParameters[i]).isEmpty()) {

							param = splitForParameters[i].split(" ")[0];

							if (splitForParameters[i].contains(" ")) {

								// c'� il valore

								paramValue = splitForParameters[i].substring(param.length()).trim();
							}
							if (paramValue != null) {
								if (paramValue.isEmpty()) {
									paramValue = null;
								}
							}
							// qui prendo l'entry
							PrivateEntry entry = new PrivateEntry(param, paramValue);
							entries.add(entry);
						}

					}
					// ora qui dobbiamo in tanto far iterare la lista di entries
					if (entries.size() > 0) {
						// qui per prima cosa devo individuare il comando
						for (int i = 0; i < commands.size(); i++) {
							if (command.equals(commands.get(i).getCommand())) {
								getCommand = (LocalCommand) commands.get(i);
								break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
							}
						}
						if (getCommand != null) {

							boolean thereIsError = false;
							// itero la lista di entries
							for (int i = 0; i < entries.size(); i++) {
								PrivateEntry entry = entries.get(i);

								// okok qui per prima cosa verifico che il param corrente dell'entry
								// sia effettivamente un param del comando ottenuto

								if (getCommand.isParameter(entry.param)) {

									// ottengo il param vero e proprio per poi controllare se ha un valore da input
									// sfruttabile
									Parameter getParameter = getCommand.param(entry.param);

									if (getParameter != null) {

										// controllo se questo param ha un valore da input struttabile

										if (getParameter.hasInputValueExploitable()) {

											// qui devo controllare se l'entry ha il valore del parametro
											if (entry.value != null) {
												// setto il valore sfruttabile

												getParameter.setInputValue(entry.value);
											}
											// else{
											//
											// if (getCommand.getSharedObject()==null) {
											// // l'entry non ha un valore del parametro
											// // eppure ci vuole ,quindi qui � un errore
											// thereIsError = true ;
											// }
											// }
											//
										}
										// else{
										//
										// // qui significa che il parametro non ha un valore da input sfruttabile
										//
										// // allora se non ha un valore sfruttabile da input
										// // bisogna per prima cosa verificare se l'apposito entry ha il value
										// // in tal caso lo diamo come errore,quindi non succede niente
										// // perch� l'utente ha sbagliato a scrivere il comando
										// if(entry.value!=null){
										//
										// thereIsError = true ;
										// }
										//
										//
										// }
									}

								} else {
									thereIsError = true;
								}

							}
							if (thereIsError) {
								getCommand = null;
							}

						}

					}

				}

			}

			else if (split.length == 1) {
				// non c'� niente dopo il comando,quindi qui posso eseguirlo tranquillamente
				for (int i = 0; i < commands.size(); i++) {
					if (command.equals(commands.get(i).getCommand())) {
						getCommand = (LocalCommand) commands.get(i);
						break; // qui posso uscire perch� il comando � senza params e lo abbiamo trovato
					}
				}

			}
		}
		return getCommand;

	}

	@Override
	public List<Parameter> sortParameters() {
		List<Parameter> params = null;
		if (hasParameters()) { // ordina solo se ci sono params

			params = new ArrayList<>();
			Iterator<Entry<String, Parameter>> itera = this.iterator();
			while (itera.hasNext()) {
				Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter> entry = (Map.Entry<java.lang.String, cloud.jgo.utils.command.Parameter>) itera
						.next();
				Parameter param = entry.getValue();
				params.add(param);
			}

			// qui abbiamo la lista riempita

			Collections.sort(params);

		}
		return params;
	}

	@Override

	public boolean hasParameters() {
		if (this.countParameters() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public <T> T getSharedObject() {
		// TODO Auto-generated method stub
		return (T) sharedObject;
	}

	@Override
	public <T> void shareObject(T sharedObject) {
		// TODO Auto-generated method stub
		this.sharedObject = sharedObject;
		if (this.sharedObject != null) {
			// solo se il param non lo ha gi� lo aggiungo
			if (!isParameter(toStringParamName)) {
				// se toString non � un param lo aggiungo
				final Parameter pToString = this.addParam(toStringParamName,
						"This parameter shows the toString method of the shared object");
				pToString.setExecution(new Execution() {

					@Override
					public Object exec() {

						return sharedObject.toString();

					}
				});
			} else {
				// se invece to_string � un parametro senza eliminarlo
				// mi basta sostituirne l'esecuzione e l'help
				// quindi ottengo il param
				Parameter param_toString = this.param(toStringParamName);
				param_toString.setHelp("This parameter shows the toString method of the shared object");
				param_toString.setExecution(new Execution() {

					@Override
					public Object exec() {

						return sharedObject.toString();
					}
				});
			}
		} else {
			// qui entra se l'oggetto condiviso � null
			// cancello il parametro perch� non vi � pi� l'oggetto condiviso
			if (isParameter(toStringParamName)) {
				removeParam(toStringParamName);
			}
		}
	}

	@Override
	public Parameter[] params() {
		if (structure.values().toArray().length > 0) {
			Object[] objects = structure.values().toArray();
			Parameter[] params = new Parameter[objects.length];
			for (int i = 0; i < objects.length; i++) {
				params[i] = (Parameter) objects[i];
			}
			return params;
		} else {
			return null;
		}
	}

	@Override
	public String getInputValue() {
		// TODO Auto-generated method stub
		return this.inputValue;
	}

	@Override
	public void setInputValue(String inputValue) {
		// TODO Auto-generated method stub
		this.inputValue = inputValue;
	}

	@Override
	public boolean hasInputValueExploitable() {
		// TODO Auto-generated method stub
		return this.inputValueExploitable;
	}

	@Override
	public void setInputValueExploitable(boolean exploitable) {
		// TODO Auto-generated method stub
		this.inputValueExploitable = exploitable;
		getHelpCommand().reload(this);
	}

	@Override
	public Parameter shareParameter(Parameter parameter) {
		Parameter p = addParam(parameter.getOnlyParam(), parameter.getParameterHelp());
		// adesso vado a prendere le info + importanti del parametro che ho ricevuto
		p.setInputValueExploitable(parameter.hasInputValueExploitable());
		p.shared = true;
		return p;
	}

	@Override
	public List<Parameter> getSharedParameters() {
		List<Parameter> params = sortParameters();
		List<Parameter> sharedParams = new ArrayList<>();
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).shared) {
				sharedParams.add(params.get(i));
			}
		}
		return sharedParams;
	}

	/**
	 * This method returns the unshared parameters
	 * 
	 * @return the unshared parameters
	 */
	public List<Parameter> getUnSharedParameters() {
		List<Parameter> params = sortParameters();
		List<Parameter> unSharedParams = new ArrayList<>();
		for (int i = 0; i < params.size(); i++) {
			if (!params.get(i).shared) {
				unSharedParams.add(params.get(i));
			}
		}
		return unSharedParams;
	}

	@Override
	public Type getSharerType() {
		// TODO Auto-generated method stub
		return Type.COMMAND;
	}

	@Override
	public void shareItEntirely(Parameter parameter, SharedExecution execution) {
		Parameter p = addParam(parameter.getOnlyParam(), parameter.getParameterHelp());
		// adesso vado a prendere le info + importanti del parametro che ho ricevuto
		p.setInputValueExploitable(parameter.hasInputValueExploitable());
		p.setExecution(execution);
		p.shared = true;
	}

	@Override
	public void shareItEntirely(Parameter parameter) {
		shareItEntirely(parameter, (SharedExecution) parameter.getExecution());
	}

	@Override
	public int compareTo(LocalCommand o) {
		return getCommand().compareTo(o.getCommand());
	}

	@Override
	public When getHypothesis() {
		// TODO Auto-generated method stub
		return this.when;
	}

	@Override
	public void validExecution(When w) {
		this.when = w;
	}

}