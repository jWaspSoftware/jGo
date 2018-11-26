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
package cloud.jgo.net.config;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Logger;
public abstract class Configuration2 extends Hashtable<String,Object> {
	public final static ConfigurationKey TIMER = new ConfigurationKey("jgo.net.timer",Integer.class);
	/**
		 * 
		 * @return all configurations in the form of a string
		 */
		public abstract StringBuffer AllConfigurations(); // stampa tutte le configurazioni
		/**
		 * This method returns the server logger
		 * @return the server logger
		 */
		public abstract Logger getLogger();
		/**
		 * 
		 * @return the settings number
		 */
		public abstract int getSettingsCounter();
		/**
		 * Writes the configuration to an XML file
		 * @param fileName the file name
		 *@return the XML file
		 */
		public abstract cloud.jgo.io.File toXML(String fileName);
		/**
		 * Recovers the configuration from the xml file
		 * @param xmlFile the xml file
		 */
		public abstract boolean fromXML(cloud.jgo.io.File xmlFile);
		/**
		 *	Recovers the configuration from the xml file 
		 * @param fileName the xml file
		 */
		public abstract boolean fromXML(String fileName);
		
		public abstract <V> V put(ConfigurationKey key,Object value);
		
		public abstract <V> V putIfAbsent(ConfigurationKey key,Object value);
		
		public abstract <V> V getConfig(ConfigurationKey key);
		
		public abstract <V> V replace(ConfigurationKey key,Object value);
		
		public abstract boolean replace(ConfigurationKey key,Object oldValue,Object newValue);
		
		// mi creo un altra interfaccia
		public static class ConfigurationKey{
			protected String key;protected Class<?>type;
			protected ConfigurationKey(String key,Class<?>type) {
			this.key = key;
			this.type = type;
			}
			public String getKey() {
				return key;
			}
			public void setKey(String key) {
				this.key = key;
			}
			public Class<?> getType() {
				return type;
			}
			public void setType(Class<?> type) {
				this.type = type;
			}
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return key+"/"+type.getName();
			}
			@Override
			public boolean equals(Object obj) {
				ConfigurationKey cast = (ConfigurationKey) obj;
				if (cast.getKey().equals(this.key)&&cast.getType().equals(this.getType())) {
					return true ;
				}
				else{
					return false ;
				}
			}
		}
}
