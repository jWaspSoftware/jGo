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
package cloud.jgo.jjdom;

import static cloud.jgo.jjdom.JjDom.$;
import static cloud.jgo.jjdom.JjDom.jquery;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import cloud.jgo.�;
import cloud.jgo.io.File;
import cloud.jgo.jjdom.css.CSSSelection;
import cloud.jgo.jjdom.css.CSSSelector;
import cloud.jgo.jjdom.css.NoSelectorSetException;
import cloud.jgo.jjdom.css.concrete.CSSSimpleSelector;
import cloud.jgo.jjdom.dom.Manipulable;
import cloud.jgo.jjdom.dom.Recursion;
import cloud.jgo.jjdom.dom.nodes.Document;
import cloud.jgo.jjdom.dom.nodes.Element;
import cloud.jgo.jjdom.dom.nodes.Elements;
import cloud.jgo.jjdom.dom.nodes.Node;
import cloud.jgo.jjdom.dom.nodes.NodeList;
import cloud.jgo.jjdom.dom.nodes.html.HTMLDefaultDocument;
import cloud.jgo.jjdom.dom.nodes.html.HTMLDocument;
import cloud.jgo.jjdom.dom.nodes.html.HTMLElement;
import cloud.jgo.jjdom.dom.nodes.html.HTMLElement.HTMLElementType;
import cloud.jgo.jjdom.dom.nodes.html.color.HTMLColorDocument;
import cloud.jgo.jjdom.jquery.Event;
import cloud.jgo.jjdom.jquery.jQueryNotInitializedException;
import cloud.jgo.jjdom.jquery.jQuerySelector;
import cloud.jgo.jjdom.jquery.jQuerySupport;
import cloud.jgo.jjdom.jquery.jQueryfunction;
import cloud.jgo.net.tcp.http.headers.Header;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import cloud.jgo.jjdom.jquery.Event.EventType;

/**
 * @author Martire91
 * @see jQuerySupport
 * @see JjDom#$(String)
 * @see JjDom#jquery(String)
 * @version 1.0.5 <!--<link rel='styleSheet' href=
 *          'https://www.jgo.cloud/docStyle.css'>--> <!--Author : *** Marco
 *          Martire *** -->
 *          <h1 style='color: #282828;'>jGo<strong style='color:
 *          green;'>.cloud</strong>/<a
 *          id='link'href='https://www.jgo.cloud/jjdom'>JjDom</a></h1> <img
 *          id='logo'src='https://www.jgo.cloud/jgo2/' alt='logo jgo'
 *          width='50px' height='50px'><br>
 *          <strong>Description :</strong><br>
 *          This technology basically allows you to create or work on a single
 *          html document.<br>
 *          Moreover you have the possibility to work with jquery and css,<br>
 *          to migrate the site and update it. You can see your site
 *          dynamically<br>
 *          , at any moment, both the code and the page (front end).
 *          {@link JjDom} is<br>
 *          a union between java, jquery and javascript, and the <a href=
 *          'https://it.wikipedia.org/wiki/Document_Object_Model'>dom</a>
 *          structure.<br>
 *          here are the simple steps to follow to use jjdom :<br>
 *          <br>
 *          <ol>
 *          <li>You create a document - {@link JjDom#newDocument()} |
 *          {@link JjDom#newDocument(String)}</li>
 *          <li>You configure jquery by calling the method
 *          {@link JjDom#jqueryInit()}</li>
 *          <li>You add the main tags to the web page</li>
 *          <li>Call the {@link JjDom#printDocumentMarkup()} method to show the
 *          current html markup</li>
 *          <li>Let's move on to selection with the method
 *          {@link JjDom#$(String)} or {@link JjDom#jquery(String)}</li>
 *          <li>Call some <a href='https://jquery.com/'>jQuery</a> method</li>
 *          <li>See the page preview by calling the method
 *          {@link JjDom#preview()}</li>
 *          </ol>
 *          <br>
 *          <br>
 *          Here is the url for general documentation :<br>
 *          <a href=
 *          'https://www.jgo.cloud/jjdom'>https://www.jgo.cloud/jjdom</a><br>
 *          <br>
 *          <br>
 *          <em><u>(Write a little and get a lot)</u></em><br>
 *          <br>
 *          <strong>J</strong> - <em>Java</em><br>
 *          <strong>j</strong> - <em>jQuery</em><br>
 *          <strong>Dom</strong> - <em>Dom</em><br>
 *          <br>
 *          Let's see how to use JjDom :
 *          <ul>
 *          <li><em>We do a nice static import of JjDom, so as to have more
 *          comfort:</em><br>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | </code><code class=
 *          "cm_b_n_n_7F0055">import static</code><code class=
 *          "cm_n_n_n_0">&nbsp;cloud.jgo.jjdom.JjDom.*;</code> </div><br>
 *          <em>or directly import the necessary method :</em><br>
 *          <br>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | </code><code class=
 *          "cm_b_n_n_7F0055">import</code><code class=
 *          "cm_n_n_n_0">&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">static</code><code class=
 *          "cm_n_n_n_0">&nbsp;cloud.jgo.jjdom.JjDom.jquery;</code> </div> <br>
 *          <em>or same thing :</em><br>
 *          <br>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | </code><code class=
 *          "cm_b_n_n_7F0055">import</code><code class=
 *          "cm_n_n_n_0">&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">static</code><code class=
 *          "cm_n_n_n_0">&nbsp;cloud.jgo.jjdom.JjDom.$;</code> </div> <br>
 *          </li>
 *          <li><em>You create the document with the minimal tags already set,
 *          and finally set the docType of the document:</em><br>
 *          <div class="cm_source"> <code class=
 *          "cm_n_n_n_0">1 | newDocument().setMinimalTags().useDoctype(</code><code class
 *          ="cm_b_n_n_7F0055">true</code><code class="cm_n_n_n_0">);</code>
 *          </div></li>
 *          <li><em>We set up jquery :</em> <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | jqueryInit();</code> </div></li>
 *          <li><em>Now we need to create the elements({@link HTMLElement}) that
 *          we want to add to the html document.</em></li>
 *          <li><em>So now we can have fun with
 *          <a href='https://www.jquery.com/'>jquery</a> in java:</em>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | $(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;p&quot;</code><code class=
 *          "cm_n_n_n_0">).hide(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;slow&quot;</code><code class=
 *          "cm_n_n_n_0">).show(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;slow&quot;</code><code class=
 *          "cm_n_n_n_0">);</code> </div></li>
 *          <li><em>Another example</em> <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 | jquery(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;h1,p&quot;</code><code class=
 *          "cm_n_n_n_0">).css(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;background&quot;</code><code class=
 *          "cm_n_n_n_0">,</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;black&quot;</code><code class=
 *          "cm_n_n_n_0">);</code> </div></li>
 *          <li><em>We can even call some jquery function :</em>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 &nbsp;|&nbsp;&nbsp;$(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;document&quot;</code><code class=
 *          "cm_n_n_n_0">).ready(</code><code class=
 *          "cm_b_n_n_7F0055">new</code><code class=
 *          "cm_n_n_n_0">&nbsp;jQueryfunction()&nbsp;{<br>
2 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
3 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Override<br>
4 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">public</code><code class=
 *          "cm_n_n_n_0">&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">void</code><code class=
 *          "cm_n_n_n_0">&nbsp;function(Event&nbsp;event)&nbsp;{<br>
5 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
6 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
7 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$(</code><code class
 *          ="cm_n_n_n_2A00FF">&quot;p&quot;</code><code class=
 *          "cm_n_n_n_0">).fadeOut(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;slow&quot;</code><code class=
 *          "cm_n_n_n_0">).fadeIn(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;slow&quot;</code><code class=
 *          "cm_n_n_n_0">);<br>
8 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
9 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
10 |&nbsp;&nbsp;&nbsp;&nbsp;});</code> </div></li>
 *          <li><em>If instead we want to get the selected elements, we just
 *          have to do:</em><br>
 *          <div class="cm_source"> <code class=
 *          "cm_n_n_n_0">1 | Elements&nbsp;elements&nbsp;=&nbsp;$(</code><code class
 *          ="cm_n_n_n_2A00FF">&quot;p&quot;</code><code class=
 *          "cm_n_n_n_0">).elements();<br>
2 |&nbsp;&nbsp;&nbsp;&nbsp;<br>
3 |&nbsp;&nbsp;&nbsp;&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">for</code><code class=
 *          "cm_n_n_n_0">&nbsp;(HTMLElement&nbsp;htmlElement&nbsp;:&nbsp;elements)&nbsp;{<br>
4 |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;elementMarkup&nbsp;=&nbsp;htmlElement.getMarkup();<br>
5 |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(</code><code class
 *          ="cm_n_n_n_2A00FF">&quot;\n&quot;</code><code class=
 *          "cm_n_n_n_0">+elementMarkup+</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;\n&quot;</code><code class=
 *          "cm_n_n_n_0">);<br>
6 |&nbsp;&nbsp;&nbsp;&nbsp;}</code> </div></li>
 *          <li><em>If instead we do not want to save any reference we can use
 *          the "each ()" method, which however works only at the dom level, and
 *          therefore not with the jquery results :</em><br>
 *          <div class="cm_source">
 *          <code class="cm_n_n_n_0">1 &nbsp;|&nbsp;&nbsp;$(</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;p&quot;</code><code class=
 *          "cm_n_n_n_0">).each(</code><code class=
 *          "cm_b_n_n_7F0055">new</code><code class=
 *          "cm_n_n_n_0">&nbsp;function()&nbsp;{<br>
2 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
3 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Override<br>
4 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">public</code><code class=
 *          "cm_n_n_n_0">&nbsp;Object&nbsp;function(Object&nbsp;e)&nbsp;{<br>
5 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
6 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code><code class
 *          ="cm_b_n_n_7F0055">int</code><code class=
 *          "cm_n_n_n_0">&nbsp;index&nbsp;=&nbsp;(</code><code class=
 *          "cm_b_n_n_7F0055">int</code><code class=
 *          "cm_n_n_n_0">)&nbsp;e&nbsp;;<br>
7 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;elementMarkup&nbsp;=&nbsp;JjDom.element(index).getMarkup();<br>
8 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(</code><code class
 *          ="cm_n_n_n_2A00FF">&quot;\n&quot;</code><code class=
 *          "cm_n_n_n_0">+elementMarkup+</code><code class=
 *          "cm_n_n_n_2A00FF">&quot;\n&quot;</code><code class=
 *          "cm_n_n_n_0">);<br>
9 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code><code class
 *          ="cm_b_n_n_7F0055">return</code><code class=
 *          "cm_n_n_n_0">&nbsp;</code><code class=
 *          "cm_b_n_n_7F0055">true</code><code class="cm_n_n_n_0">&nbsp;;<br>
10 |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
11 |&nbsp;&nbsp;&nbsp;&nbsp;});</code> </div></li>
 *          </ul>
 *          <em>Visit the general documentation <a href=
 *          'https://www.jgo.cloud/jjdom/'>https://www.jgo.cloud/jjdom/</a></em>
 */
public final class JjDom implements jQuerySupport, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * jQuery Version
	 */
	public transient final static String JQUERY_VERSION = "3.3.1"; // da aggiornare
	/**
	 * jQuery URL Snippet
	 */
	public transient final static String JQUERY_URL_SNIPPET = "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js";
	/**
	 * Document Selection
	 */
	public final static String DOCUMENT = "document";
	private static String documentName = null; // questa var si inizializza quando ci connettiamo ad una risorsa, oppure
												// quando facciamo la migrazione della pagina web
	private static boolean selectedDocument = false;
	private static String selection = null;
	public static String documentURL = null;
	private static Logger logger = Logger.getLogger("cloud.jgo.jjdom"); // questo verr� usato solo in alcuni metodi
	/**
	 * The current selection
	 */
	public static String $these = null; // non � altro che la selezione corrente
	// version 1.0.9 : segnalare
	public static Class<? extends HTMLDocument> documentTypeUsed = null; // default value
	/**
	 * It is the "this" jquery, and therefore works only with the jquery results
	 */
	public final static String _this_ = "this"; // questa invece lavora solo con i risultati jquery
	private static Event currentEvent = null;
	/**
	 * Format used for serialization
	 */
	public final static String SERIALIZATION_FORMAT = ".dat";
	/**
	 * Javascript variable
	 */
	public final static JavaScriptSupport window = new JjDom.JavaScriptSupport();
	private static boolean thereIsSelection = false; // inizialmente non c'� alcuna selezione
	private static boolean called = false;
	private static CSSSelector selector = null;
	/**
	 * Default css selector
	 */
	public final static CSSSimpleSelector DEFAULT_SELECTOR = new CSSSimpleSelector(); // default value
	static {
		JjDom.selector = DEFAULT_SELECTOR;
		/* default value */}
	private static CSSSelection currentSelection = null;
	/**
	 * The Jjdom Document
	 */
	public static HTMLDocument document = null;
	private static JjDom instance = new JjDom(); // unica istanza esistente
	private static Elements elements = null;
	public static int length = 0; // questa variabile va modificata alla selezione
	public static final String start_ready = "$('document').ready(function(){\n";
	public static final String end_ready = "});//end_ready\n";
	public final static String PROPERTY_TAGNAME = "tagname";
	public final static String PROPERTY_NODENAME = "nodename";
	public final static String PROPERTY_NODETYPE = "nodetype";
	public final static String PROPERTY_CHECKED = "checked";
	public final static String PROPERTY_DEFAULT_CHECKED = "defaultChecked";
	public final static String PROPERTY_DEFAULT_SELECTED = "defaultselected";
	public final static String PROPERTY_SELECTED_INDEX = "selectedIndex";
	public final static String PROPERTY_NODEVALUE = "nodevalue";
	/**
	 * config ftp - version 1.0.1
	 */
	private static FTPClient ftp_client = new FTPClient();
	private static String ftpHost, ftpUser, ftpPassw = null;
	private static String urlFileName = null;
	private static String urlDirName = null;
	private static String urlOnlyFileName = null;
	/**
	 * version 1.0.7
	 */
	public static List<jQuerySelector> availableSelectors = new ArrayList<jQuerySelector>();
	// version 1.0.9
	private static boolean migrated = false;
	private static boolean downloaded = false;
	static {
		// init-list
		availableSelectors.add(jQuerySelector.VISIBLE);
		availableSelectors.add(jQuerySelector.HIDDEN);
	}

	private JjDom() {
	}

	/**
	 * 
	 * @author Martire91<br>
	 *         The Concrete Event
	 */
	private class DefaultEvent implements Event {
		private EventType type;

		public DefaultEvent(EventType type) {
			// TODO Auto-generated constructor stub
			this.type = type;
		}

		@Override
		public void preventdefault() {
			final String jsCode = "event.preventDefault();";
			executeMethod(jsCode);
		}

		@Override
		public void stopPropagation() {
			final String jsCode = "event.stopPropagation();";
			executeMethod(jsCode);
		}

		@Override
		public void stopImmediatePropagation() {
			final String jsCode = "event.stopImmediatePropagation();";
			executeMethod(jsCode);
		}

		@Override
		public EventType getEventType() {
			// TODO Auto-generated method stub
			return this.type;
		}
	}

	/**
	 * This method allows us to move from one document to another
	 * 
	 * @param document
	 *            the new document
	 * @return the JjDom instance
	 */
	public static JjDom swicth(HTMLDocument document) {
		JjDom.document = document;
		clearSelection();
		called = false;
		setSelector(DEFAULT_SELECTOR);
		return instance;
	}

	// version 1.0.7
	// si pu� specificare sia un file html/xml
	// oppure sia un host a cui connettersi
	/**
	 * This method connects to the server, using an ftp:
	 * <a href='http://www.sauronsoftware.it/projects/ftp4j/?lang=it'>ftp4j</a>
	 * client.
	 * 
	 * @param ftpHost
	 *            the ftp host - example: ftp.website.com
	 * @param ftpUser
	 *            the ftp username
	 * @param ftpPassw
	 *            the ftp password
	 * @return the JjDom instance
	 */
	public static JjDom connect(String ftpHost, String ftpUser, String ftpPassw) {
		// per prima cosa mi connetto
		JjDom inst = null;
		try {
			String[] response = ftp_client.connect(ftpHost);
			for (String reply : response) {
				System.out.println(reply);
			}
			System.out.println("Wait for...");
			// controllo se sn connesso
			if (ftp_client.isConnected()) {
				ftp_client.login(ftpUser, ftpPassw);
				if (ftp_client.isAuthenticated()) {
					// okok siamo connessi
					inst = instance;
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inst;
	}

	// version 1.0.9
	/**
	 * This method connects to the server, using an ftp:
	 * <a href='http://www.sauronsoftware.it/projects/ftp4j/?lang=it'>ftp4j</a>
	 * client.
	 * 
	 * @param ftpHost
	 *            the ftp host - example: ftp.website.com
	 * @param ftpPort
	 *            the ftp port - example: 21
	 * @param ftpUser
	 *            the ftp username
	 * @param ftpPassw
	 *            the ftp password
	 * @return the JjDom instance
	 */
	public static JjDom connect(String ftpHost, int ftpPort, String ftpUser, String ftpPassw) {
		// per prima cosa mi connetto
		JjDom inst = null;
		try {
			String[] response = ftp_client.connect(ftpHost, ftpPort);
			for (String reply : response) {
				System.out.println(reply);
			}
			System.out.println("Wait for...");
			// controllo se sn connesso
			if (ftp_client.isConnected()) {
				ftp_client.login(ftpUser, ftpPassw);
				if (ftp_client.isAuthenticated()) {
					// okok siamo connessi
					inst = instance;
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inst;
	}

	// agisce sul documento di JjDom
	/**
	 * This method migrates the JjDom html document, associating a correct URL in
	 * the server.
	 * 
	 * @param urlResource
	 *            document URL
	 * @return the JjDom instance
	 */
	public static JjDom migrate(String urlResource) {
		return migrate(urlResource, JjDom.document, true);
	}

	/**
	 * This method migrates the document passed as a parameter, associating it with
	 * a correct URL in the server.
	 * 
	 * @param urlResource
	 *            document URL
	 * @param document
	 *            the document
	 * @param log
	 *            notify if the upload has occurred
	 * @return the JjDom instance
	 */
	public static JjDom migrate(String urlResource, Document document, boolean log) {
		JjDom inst = null;
		String dirUrl = null;
		if (isConnected() && isAuthenticated()) {
			String onlyFileName = null;
			onlyFileName = urlResource.split("/")[urlResource.split("/").length - 1].trim();
			String fileNameWithoutFormat = null;
			int lastIndex = onlyFileName.lastIndexOf(".");
			fileNameWithoutFormat = onlyFileName.substring(0, lastIndex).trim();
			save(onlyFileName, document);
			serializes(fileNameWithoutFormat + JjDom.SERIALIZATION_FORMAT, document);
			File docFile, docFileSer = null;
			docFile = new File(onlyFileName);
			docFileSer = new File(fileNameWithoutFormat + JjDom.SERIALIZATION_FORMAT);
			if (urlResource.split("/").length > 1) {
				dirUrl = urlResource.replace(onlyFileName, "");
			}
			if (dirUrl != null) {
				try {
					ftp_client.changeDirectory(dirUrl);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FTPIllegalReplyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FTPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				ftp_client.upload(docFile);
				ftp_client.upload(docFileSer);
				if (log) {
					System.out.println("Uploads completed successfully !!");
				}
				migrated = true;
				downloaded = false; // diciamo che se il documento � stato uplodato, non pu� risultare gi� scaricato
				JjDom.documentURL = urlResource;
				inst = instance;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPDataTransferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean deleted, deleted2;
			deleted = docFile.delete();
			if (!deleted) {
				docFile.deleteOnExit();
			}
			deleted2 = docFileSer.delete();
			if (!deleted2) {
				docFileSer.deleteOnExit();
			}
		}
		return inst;
	}

	/**
	 * This method downloads the document, provided that the connect ()<br>
	 * method has been called first and a correct path<br>
	 * has been specified for the resource.
	 * 
	 * @param urlResource
	 *            document URL
	 * @return the downloaded document
	 */
	public static Document download(String urlResource) {
		Document inst = null;
		if (isConnected() && isAuthenticated()) {
			if (urlResource.startsWith("/")) {
				int indexFirst = urlResource.indexOf("/");
				urlResource = urlResource.substring(indexFirst).trim();
			}
			String fileName = urlResource.split("/")[urlResource.split("/").length - 1];
			int lastIndex = fileName.lastIndexOf(".");
			String onlyFileName = fileName.substring(0, lastIndex);
			String serFileName = onlyFileName + JjDom.SERIALIZATION_FORMAT;
			JjDom.documentURL = urlResource;
			urlResource = urlResource.replace(fileName, serFileName);
			try {
				java.io.File serFile = new java.io.File("tmp.dat");
				ftp_client.download(urlResource, serFile);
				inst = JjDom.deserializes(serFile.getPath());
				downloaded = true;
				// quando il documento viene scaricato : impostiamo il migrated a false
				migrated = false;
				if (inst instanceof HTMLDocument) {
					JjDom.document = (HTMLDocument) inst;
				}
				// elimino il file
				boolean deleted = serFile.delete();
				if (!deleted) {
					serFile.deleteOnExit();
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPDataTransferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public static boolean isAuthenticated() {
		return ftp_client.isAuthenticated();
	}

	public static JjDom closeConnection() {
		if (ftp_client.isConnected()) {
			try {
				ftp_client.disconnect(true);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	public static boolean isConnected() {
		return ftp_client.isConnected();
	}

	// version 1.0.9
	public static boolean isMigrated() {
		return migrated;
	}

	public static boolean isDownloaded() {
		return downloaded;
	}

	/**
	 * This method checks if the document is selected
	 * 
	 * @return true if the document is selected
	 */
	public static boolean isSelectedDocument() {
		return selectedDocument;
	}

	/**
	 * This method gets the selected item to the "index" position
	 * 
	 * @see JjDom#get(int)
	 * @param index
	 *            the element index
	 * @return the element at position "index"
	 */
	public static HTMLElement element(int index) {
		cleanUp();
		return $().get(index);
	}

	/**
	 * This method sets the CSSselector
	 * 
	 * @param selector
	 *            the new selector
	 */
	public static void setSelector(CSSSelector selector) {
		if (selector != null) {
			JjDom.selector = selector;
		} else {
			clearSelection();
		}
	}

	/**
	 * This method returns the current CSSselector
	 * 
	 * @return current CSSselector
	 */
	public static CSSSelector getSelector() {
		return selector;
	}

	/**
	 * This method executes a document preview
	 * 
	 * @return the Jjdom instance
	 */
	public static JjDom preview() {
		if (document != null) {
			File htmlPreview = new File("preview.html");
			�.fl(htmlPreview, �.FILE_MODE_WRITE, document.getMarkup(), null, null);
			// imposto il file che si deve eliminare alla fine del programma
			try {
				Desktop.getDesktop().browse(htmlPreview.toURI());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// diamo il tempo al browser di aprire il file ...
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// htmlPreview.delete(); da controllare ...
			htmlPreview.deleteOnExit();
		}
		return instance;
	}

	/**
	 * This method saves the document in the file
	 * 
	 * @param file
	 *            the file destination
	 * @param document
	 *            the document
	 * @return the Jjdom instance
	 */
	public static JjDom save(File file, Document document) {
		�.writeFile(file, false, new String[] { document.getMarkup() });
		return instance;
	}

	/**
	 * This method saves the document in the file
	 * 
	 * @param fileName
	 *            the file name
	 * @return the Jjdom instance
	 */
	public static JjDom save(String fileName) {
		return save(new File(fileName), JjDom.document);
	}

	/**
	 * This method saves the document in the file
	 * 
	 * @param fileName
	 *            the file name
	 * @param document
	 *            the document
	 * @return the Jjdom instance
	 */
	public static JjDom save(String fileName, Document document) {
		return save(new File(fileName), document);
	}
	// questo metodo continua il metodo connect
	// ad una risorsa tipo una cartella, e qui diamo
	// il nome del documento che vogliamo attribuire

	private static String getUrlWithoutProto(String urlResource) {
		if (urlResource.contains("//")) {
			int index = urlResource.indexOf("//") + 2;
			urlResource = urlResource.substring(index);
		}
		return urlResource;
	}

	/**
	 * This method prints the document
	 * 
	 * @see HTMLDocument#printMarkup()
	 * @return the Jjdom instance
	 */
	public static JjDom printDocumentMarkup() {
		document.printMarkup();
		return instance;
	}

	// default : CHARSET_UTF_8
	/**
	 * This method creates a new html document. Takes into account the type of
	 * document used.
	 * 
	 * @see #newDocument(String)
	 * @see #newVoidDocument(String)
	 * @return the html document
	 */
	public static HTMLDocument newDocument() {
		if (documentTypeUsed == null) {
			document = new HTMLDefaultDocument(HTMLDocument.CHARSET_UTF_8, null, instance);
			migrated = false;
			downloaded = false;
		} else {
			if (documentTypeUsed.getSimpleName().equals("HTMLDefaultDocument")) {
				document = new HTMLDefaultDocument(HTMLDocument.CHARSET_UTF_8, null, instance);
				migrated = false;
				downloaded = false;
			} else if (documentTypeUsed.getSimpleName().equals("HTMLColorDocument")) {
				document = new HTMLColorDocument(HTMLDocument.CHARSET_UTF_8, instance);
				migrated = false;
				downloaded = false;
			} else {
				// qui dare una eccezzione ...
			}
		}
		return document;
	}

	/**
	 * This method creates a new html document. Takes into account the type of
	 * document used.
	 * 
	 * @see #newDocument()
	 * @see #newVoidDocument(String)
	 * @param charsetName
	 *            the charset name
	 * @return the html document
	 */
	public static HTMLDocument newDocument(String charsetName) {
		if (documentTypeUsed == null) {
			document = new HTMLDefaultDocument(charsetName, null, instance);
			migrated = false;
			downloaded = false;
		} else {
			if (documentTypeUsed.getSimpleName().equals("HTMLDefaultDocument")) {
				document = new HTMLDefaultDocument(charsetName, null, instance);
				migrated = false;
				downloaded = false;
			} else if (documentTypeUsed.getSimpleName().equals("HTMLColorDocument")) {
				document = new HTMLColorDocument(HTMLDocument.CHARSET_UTF_8, instance);
				migrated = false;
				downloaded = false;
			} else {
				// qui dare una eccezzione ...
				try {
					throw new InvalidDocumentTypeException(documentTypeUsed);
				} catch (InvalidDocumentTypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return document;
	}

	/**
	 * This method creates a new void document. Does not take into account the type
	 * of document used.
	 * 
	 * @see #newDocument()
	 * @see #newDocument(String)
	 * @param charsetName
	 *            the charset name
	 * @return the html document
	 */
	public static HTMLDocument newVoidDocument(String charsetName) {
		document = HTMLDefaultDocument.newVoidDocument(charsetName, instance);
		migrated = false;
		downloaded = false;
		return document;
	}

	/**
	 * This method deserializes the object
	 * 
	 * @param fileName
	 *            the file name
	 * @return the html document
	 */
	public static Document deserializes(String fileName) {
		File file = �.fl(fileName);
		if (file.exists()) {
			return �.deserializes(file);
		} else {
			return null;
		}
	}

	/**
	 * This method deserializes the object/html document
	 * 
	 * @param serFile
	 *            the file
	 * @return the html document
	 */
	public static HTMLDocument deserializes(File serFile) {
		if (serFile.exists()) {
			JjDom.document = �.deserializes(serFile);
			return JjDom.document;
		} else {
			return null;
		}
	}

	/**
	 * This method serializes the object/html document
	 * 
	 * @param fileName
	 *            the file name
	 * @return the JjDom instance
	 */
	public static JjDom serializes(String fileName) {

		File serFile = �.serializes(document, fileName);

		if (serFile != null) {
			return instance;
		} else {
			return null;
		}
	}

	// version 1.0.7
	/**
	 * This method serializes the object/html document
	 * 
	 * @param fileName
	 *            the file name
	 * @param document
	 *            the document
	 * @return the JjDom instance
	 */
	public static JjDom serializes(String fileName, Document document) {
		File serFile = �.serializes(document, fileName);
		if (serFile != null) {
			return instance;
		} else {
			return null;
		}
	}

	/**
	 * This method serializes the object/html document
	 * 
	 * @param file
	 *            the file
	 * @return the JjDom instance
	 */
	public static JjDom serializes(File file) {
		�.serializes(document, file);
		return instance;
	}

	/**
	 * This method checks if jquery is set in the document
	 * 
	 * @return true if jquery is set
	 */
	public static boolean jqueryIsSet() {
		boolean set = false;
		Elements scripts = JjDom.document.getElementsByTag("script");
		for (Element htmlElement : scripts) {
			if (htmlElement.hasAttributes()) {
				if (htmlElement.isPresent("src")) {
					String jquerySource = htmlElement.getAttributeValue("src");
					if (jquerySource.equals(document.jqueryPath())) {
						set = true;
						break;
					}
				}
			}
		}
		return set;
	}

	// questo metodo inizializzi il nodo script source ma non lo assegna a nessun
	// nodo
	// insomma non lo aggiunge nel documento, e questo avviene solo se � una
	// configurazione interna
	// nel momento in cui, chiamiamo il primo metodo java script oppure jquery, ecco
	// che questo nodo
	// viene aggiunto, questo avviene anche con l'aiuto della variabile called
	/**
	 * This method sets jquery in the document
	 * 
	 * @return the JjDom instance
	 */
	public static JjDom jqueryInit() {
		HTMLElement scriptTag = null;
		scriptTag = (HTMLElement) document.createElement(HTMLElementType.SCRIPT);
		scriptTag.setAttribute("src", document.jqueryPath());
		document.setJsSourceTag((HTMLElement) document.createElement(HTMLElementType.SCRIPT));
		// aggiungo per prima cosa l'inclusione del sorgente jquery
		if (document != null) {
			if (document.getRootElement() != null) {
				if (document.getHead() != null) {
					// qui inserisco
					// come primo elemento dell'head
					// il tag script
					if (document.getMetaTag() != null) {
						// se esiste il meta tag charset, lo inserisco dopo di questo il tag script
						document.getHead().insertAfter(scriptTag, document.getMetaTag());
					} else {
						// se non esiste il meta tag
						// lo inserisco provvisori...
						// come primo elemento dell'head
						document.getHead().addFirstChild(scriptTag);
					}
				} else {
					// creo la testa del sito

					HTMLElement head = (HTMLElement) JjDom.document.createElement(HTMLElementType.HEAD);

					// inserisco nella testa il tag script,poich� siamo sicura che l'unic tag che ci
					// va

					head.appendChild(scriptTag);

					// inserisco questo tag nel root html
					document.getRootElement().appendChild(head);
				}
			}
		}
		return instance;
	}

	/**
	 * This method clears the selection,
	 * 
	 * @return the JjDom instance
	 */
	public static JjDom clearSelection() {
		thereIsSelection = false; // non c'� nessuna selezione
		currentSelection = null; // dico che non c'� nessuna selezione variabile
		selectedDocument = false;
		elements = null; // riporto gli elementi a null
		length = 0; // riporto la lunghezza degli elementi a 0
		selection = null;
		return instance;
	}

	/**
	 * @return The JjDom instance
	 */
	public static JjDom $() {
		return instance;
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#jquery(String, int)
	 * @param selection
	 *            the css selection
	 * @param from
	 *            from which index of the list you want to start
	 * @return the elements list
	 */
	public static Elements $(String selection, int from) {
		Elements elements = $(selection).elements;
		Elements elements2 = new Elements();
		for (int i = from; i < elements.size(); i++) {
			elements2.add(elements.get(i));
		}
		return elements2;
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#$(String, int)
	 * @param selection
	 *            the css selection
	 * @param from
	 *            from which index of the list you want to start
	 * @return the elements list
	 */
	public static Elements jquery(String selection, int from) {
		Elements elements = $(selection).elements;
		Elements elements2 = new Elements();
		for (int i = from; i < elements.size(); i++) {
			elements2.add(elements.get(i));
		}
		return elements2;
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#$(String, String, String)
	 * @param el
	 *            elements selection 1
	 * @param combiner
	 *            the css combiner
	 * @param el2
	 *            elements selection 2
	 * @return the JjDom instance
	 */
	public static JjDom jquery(String el, String combiner, String el2) {
		return $(el, combiner, el2);
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#jquery(String, String, String)
	 * @param el
	 *            elements selection 1
	 * @param combiner
	 *            the css combiner
	 * @param el2
	 *            elements selection 2
	 * @return the JjDom instance
	 */
	public static JjDom $(String el, String combiner, String el2) {
		// qui controllo se c'� un metodo ready
		JjDom.selection = el + combiner + el2;
		$these = JjDom.selection;
		if (document.isReady()) {
			document.setJsSource(
					new StringBuffer(document.jsSource().toString().replace(start_ready, "").replace(end_ready, "")));
			document.jsSource().append("$('" + selection + "')");
			StringBuffer buffer = new StringBuffer();
			buffer.append(start_ready).append(document.jsSource().toString()).append(end_ready);
			document.setJsSource(buffer);
		} else {
			// non c'� un metodo ready, quindi posso appendere tranquillamente
			document.jsSource().append("$('" + selection + "')");
		}
		if (selection.equals("document")) {
			// in questo caso la lista di elementi selezionati non centra
			// qui praticamente settiamo
			// il boolean a true, per indicare, laddove necessario
			// che sotto selezione c'� il documento
			selectedDocument = true;
			// svuoto gli elementi
			currentSelection = null;
			elements = null;
			length = 1; // solo il documento � selezionato
			thereIsSelection = true;
		} else {
			// nel momento in cui si seleziona un altra cosa
			// riportiamo a false selectedDocument per questioni di sicurezza
			selectedDocument = false;
			// qui subentra il selettore
			if (selector != null) {
				currentSelection = selector.select(el, combiner, el2);
				elements = currentSelection.getSelectedItems();
				length = elements.size();
				thereIsSelection = true;
				return instance;
			} else {
				clearSelection(); // cancello la selezione e do eccezione
				try {
					throw new NoSelectorSetException();
				} catch (NoSelectorSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return instance;
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#$(String)
	 * @param selection
	 *            the css selection
	 * @return the JjDom instance
	 */
	public static JjDom jquery(String selection) {
		return $(selection);
	}

	/**
	 * This method selects the document elements using a default CSS Selector.
	 * 
	 * @see JjDom#jquery(String)
	 * @param selection
	 *            the css selection
	 * @return the JjDom instance
	 */
	public static JjDom $(String selection) {
		// qui controllo se c'� un metodo ready
		JjDom.selection = selection;
		JjDom.$these = JjDom.selection;
		if (document.isReady()) {
			document.setJsSource(
					new StringBuffer(document.jsSource().toString().replace(start_ready, "").replace(end_ready, "")));
			if (selection.equals("this")) {
				document.jsSource().append("$(" + selection + ")");
			} else {
				document.jsSource().append("$('" + selection + "')");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(start_ready).append(document.jsSource().toString()).append(end_ready);
			document.setJsSource(buffer);
		} else {
			// non c'� un metodo ready, quindi posso appendere tranquillamente
			if (selection.equals("this")) {
				document.jsSource().append("$(" + selection + ")");
			} else {
				document.jsSource().append("$('" + selection + "')");
			}
		}
		if (selection.equals("document")) {
			// in questo caso la lista di elementi selezionati non centra
			// qui praticamente settiamo
			// il boolean a true, per indicare, laddove necessario
			// che sotto selezione c'� il documento
			selectedDocument = true;
			// svuoto gli elementi
			currentSelection = null;
			elements = null;
			length = 1; // solo il documento � selezionato
			thereIsSelection = true;
		} else {
			// nel momento in cui si seleziona un altra cosa
			// riportiamo a false selectedDocument per questioni di sicurezza
			selectedDocument = false;
			// qui subentra il selettore
			/*
			 * da qui a @ - for version 1.0.8
			 */

			// qui controlliamo che non si tratti di una selettore jquery

			/*
			 * @
			 */
			if (selector != null) {
				currentSelection = selector.select(selection);
				elements = currentSelection.getSelectedItems();
				length = elements.size();
				thereIsSelection = true;
				return instance;
			} else {
				clearSelection(); // cancello la selezione e do eccezione
				try {
					throw new NoSelectorSetException();
				} catch (NoSelectorSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return instance;
	}

	@Override
	public JjDom addClass(String className) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".addClass('" + className + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom removeClass(String className) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".removeClass('" + className + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggleClass(String className) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggleClass('" + className + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// questo metodo se non viene selezionato il documento
	// non restituisce niente, e anche se viene richiamato + volte
	// non restituisce niente
	@Override
	public void ready(jQueryfunction handler) {
		// verifico che il metodo ready non sia gi� presente nel documento
		if (!document.isReady()) {
			// 1 passo : controllo se jquery � stata impostata
			if (jqueryIsSet()) {

				// 2 passo : controllo se il documento � selezionato
				if (isSelectedDocument()) {
					// 3 passo : controllo della locazione jquery
					// 5 passo : controllo se non vi � un tag script source impostato nel documento
					if (!called) {
						called = true;

						// aggiungo il tag al documento

						JjDom.document.getBody().appendChild(document.getJsSourceTag());
					}
					// 1) passo : sostituisco : "$('document')"

					document.setJsSource(
							new StringBuffer(document.jsSource().toString().replace("$('document')", "").trim()));

					String currentValue = document.jsSource().toString();

					document.setJsSource(new StringBuffer());

					// a questo punto scriviamo il codice relativo a ready
					document.jsSource().append(start_ready);
					if (!currentValue.isEmpty() && !currentValue.equals(" ")) {
						document.jsSource().append(currentValue + "\n");
					}
					document.jsSource().append(end_ready);
					// okok aggiorno
					document.getJsSourceTag().setTextContent("\n" + document.jsSource());

					handler.function(null);
				}
			} else {
				// jquery non impostata : aggiornare qui ...
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public JjDom show(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".show('" + jqEffect + "');";
				executeMethod(jsCode);
				return inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom show(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".show(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public JjDom show(final jQueryEffect effect) {
		return show(effect.name().toLowerCase());
	}

	@Override
	public JjDom show(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".show('" + jqEffect + "',function(){\n";
				executeMethodWithFunction(callBack, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom show(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".show(" + (millisec) + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom hide(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".hide('" + jqEffect + "');";
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom hide(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".hide(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public JjDom hide(final jQueryEffect effect) {
		return hide(effect.name().toLowerCase());
	}

	@Override
	public JjDom hide(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".hide('" + jqEffect + "',function(){\n";
				executeMethodWithFunction(callBack, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom hide(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".hide(" + (millisec) + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideUp(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideUp('" + jqEffect + "');";
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideUp(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideUp(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideUp(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideUp('" + jqEffect + "',function(){\n";
				executeMethodWithFunction(callBack, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideUp(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideUp(" + (millisec) + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public JjDom slideUp(final jQueryEffect effect) {
		return slideUp(effect.name().toLowerCase());
	}

	@Override
	public JjDom slideDown(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideDown('" + jqEffect + "');";
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideDown(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideDown(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideDown(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideDown('" + jqEffect + "',function(){\n";
				executeMethodWithFunction(callBack, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideDown(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideDown(" + (millisec) + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public JjDom slideDown(final jQueryEffect effect) {
		return slideDown(effect.name().toLowerCase());
	}

	@Override
	public JjDom slideToggle(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideToggle('" + jqEffect + "');";
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideToggle(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideToggle(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slidetoggle(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (jqEffect.equals(jQueryEffect.SLOW.name().toLowerCase())
					|| jqEffect.equals(jQueryEffect.FAST.name().toLowerCase())) {
				final String jsCode = ".slideToggle('" + jqEffect + "',function(){\n";
				executeMethodWithFunction(callBack, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom slideToggle(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".slideToggle(" + (millisec) + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	public JjDom slideToggle(final jQueryEffect effect) {
		return slideToggle(effect.name().toLowerCase());
	}

	@Override
	public JjDom css(String cssProp, String cssValue) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String cssMethodCode = ".css('" + cssProp + "','" + cssValue + "');";
			executeMethod(cssMethodCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// c'� anche un piccolo trucco : se selezioniamo il documento, ci restituisce
	// tutto il codice all'interno del tag root html

	@Override
	public String html() {
		String htmlString = null;
		if (jqueryIsSet()) {
			if (currentSelection != null || isSelectedDocument()) {
				if (isSelectedDocument()) {
					String markup = document.getMarkup().trim();
					if (markup.startsWith("<html") && markup.endsWith("</html>")) {
						int first = markup.indexOf("<");
						String subString = markup.substring(first, markup.indexOf("\n"));
						markup = markup.replace(subString, "");
						String subString2 = "</html>";
						int lastIndex = markup.lastIndexOf(subString2);
						markup = markup.substring(0, lastIndex);
						htmlString = markup.trim();
					}
				} else {
					HTMLElement el = (HTMLElement) elements.element();
					if (!el.hasChildNodes()) {
						htmlString = el.getTextContent();
					} else {
						String markup = el.getMarkup().trim();
						if (markup.startsWith("<" + el.getNodeName())
								&& markup.endsWith("</" + el.getNodeName() + ">")) {
							int first = markup.indexOf("<");
							String subString = markup.substring(first, markup.indexOf("\n"));
							markup = markup.replace(subString, "");
							String subString2 = "</" + el.getNodeName() + ">";
							int lastIndex = markup.lastIndexOf(subString2);
							markup = markup.substring(0, lastIndex);
							htmlString = markup.trim();
						}
					}
				}
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return htmlString;
	}

	@Override
	public JjDom html(String htmlString) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".html('" + htmlString + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom next() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".next();";
				executeMethod(jsCode);
				Elements listNodes = new Elements();
				for (Element htmlElement : elements) {
					Node next = htmlElement.next();
					listNodes.add((HTMLElement) next);
				}
				elements = listNodes;
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom prev() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".prev();";
				executeMethod(jsCode);
				Elements listNodes = new Elements();
				for (Element htmlElement : elements) {
					Node prev = htmlElement.previous();
					listNodes.add((HTMLElement) prev);
				}
				elements = listNodes;
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// metodi per la restituzione dei nodi della selezione jquery

	// questo metodo va segnalato poich� lavora sul dom
	@Override
	public HTMLElement get(int index) {
		if (jqueryIsSet()) {
			cleanUp();
			return (HTMLElement) elements.get(index);
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	// segnalare cosa fa questo metodo
	/**
	 * this method cleans the javascript code from incomplete selections
	 * 
	 * @see JjDom#selectionIsNotCompleted()
	 * @see JjDom#deleteTheSelectionNotCompleted();
	 * @return the jjdom instance
	 */
	private static JjDom cleanUp() {
		if (selectionIsNotCompleted()) {
			deleteTheSelectionNotCompleted();
		}
		return instance;
	}

	// questi due metodi : segnalare : lavorano a livello si javascript source
	// qui diamo per scontato che la conf jquery c'� stata
	private static boolean selectionIsNotCompleted() {
		// 1 passo : verifico se c'� il metodo ready
		boolean flag = false;
		String jsSource = null;
		if (document.isReady()) {
			// sostituisco l'ultimo pezzo del metodo ready con una string a vuota
			jsSource = document.jsSource().toString().replace(end_ready, "").replace(start_ready, "");
		} else {
			jsSource = document.jsSource().toString();
		}
		// 2 passo : faccio il controllo di prima
		if (jsSource.equals("$('" + selection + "')") || jsSource.endsWith("$('" + selection + "')")) {
			// selezione non completata
			flag = true;
		}
		return flag;
	}

	// questo metodo va chiamato dopo aver verificato che
	// c'� la selezione non completata, quindi lo diamo per scontato
	// insomma questo metodo reimposta il buffer
	private static void deleteTheSelectionNotCompleted() {
		String jssource = document.jsSource().toString();
		boolean ready = false;
		if (document.isReady()) {
			ready = true;
			jssource = document.jsSource().toString().replace(start_ready, "").replace(end_ready, "");
		}
		if (jssource.equals("$('" + selection + "')")) {

			// quindi qui rimpiazzo con un buffer nuovo vuoto
			document.setJsSource(new StringBuffer());
		} else {
			// qui vuol dire che finisce con : $('"+selection+"')
			int lastIndex = jssource.lastIndexOf("$('" + selection + "')");
			document.setJsSource(new StringBuffer(jssource.substring(0, lastIndex)));
		}
		// ultimo controllo: se c'� il ready method
		if (ready) {
			StringBuffer newBuffer = new StringBuffer();
			newBuffer.append(start_ready).append(document.jsSource()).append(end_ready);
			document.setJsSource(newBuffer);
		}
	}

	/**
	 * This method returns the selected elements list
	 * 
	 * @return the elements list
	 */
	public static Elements elements() {
		if (jqueryIsSet()) {
			cleanUp();
			return elements;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	// i metodi della classe JavaScriptSupport
	// pure che non trovano jquery init
	// danno l'eccezzione ma kmq restituiscono
	// l'oggetto window
	/**
	 * 
	 * @author Martire91<br>
	 *         This class contains some javascript methods
	 */
	public static class JavaScriptSupport implements Home {

		/**
		 * This method returns the JjDom document
		 * 
		 * @return the JjDom document
		 */
		public static HTMLDocument document() {
			return JjDom.document;
		}

		public static void executeJsMethod(String jsCode) {
			if (jqueryIsSet()) {
				// controllo che non ci sia selezione
				// controllo il tipo di locazione javascript
				if (!called) {
					called = true;

					// aggiungo il tag al documento

					JjDom.document.getBody().appendChild(document.getJsSourceTag());
				}

				// da qui a @
				// verifico la presenza del metodo ready
				boolean readyIsPresent = false;
				String start, end = null;
				if (document.isReady()) {

					// okok abbiamo il metodo ready
					readyIsPresent = true;

					// quindi sostituisco le parti peculiari
					String source = document.jsSource().toString();
					source = source.replace(start_ready, "").replace(end_ready, "");
					document.setJsSource(new StringBuffer(source));
				}
				// @
				// a questo punto raccolgo il codice :

				document.jsSource().append(jsCode + "\n");

				StringBuffer newSource = new StringBuffer();

				if (readyIsPresent) {
					newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
					document.setJsSource(newSource);
				}

				document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());

			} else {
				// jquery non impostata: non inizializzata
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		private static void executeMethodWithFunction(final jQueryfunction func, final String jsCode,
				final String param) {
			final String end_method = "},'" + param + "');\n";
			if (jqueryIsSet()) {
				// controllo della locazione
				if (!called) {
					called = true;

					// aggiungo il tag al documento

					JjDom.document.getBody().appendChild(document.getJsSourceTag());
				}
				boolean readyIsPresent = false;
				String start, end = null;
				if (document.isReady()) {
					// okok abbiamo il metodo ready
					readyIsPresent = true;
					// quindi sostituisco le parti peculiari
					String source = document.jsSource().toString();
					source = source.replace(start_ready, "").replace(end_ready, "");
					document.setJsSource(new StringBuffer(source));
				}
				// scrivo il codice nel buffer
				document.jsSource().append(jsCode);

				// eseguo il metodo

				func.function(null); // for moment parameter = null

				document.jsSource().append(end_method); // si chiude il codice della funzione

				StringBuffer newSource = new StringBuffer();

				if (readyIsPresent) {
					newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
					document.setJsSource(newSource);
				}
				document.getJsSourceTag().setTextContent("\n" + document.jsSource());
			} else {
				// eccezzione ...
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private static void executeJsMethodWithFunction(final jQueryfunction func, final String jsCode,
				final int param) {
			final String end_method = "}," + param + ");\n";
			if (jqueryIsSet()) {
				// controllo della locazione
				if (!called) {
					called = true;

					// aggiungo il tag al documento

					JjDom.document.getBody().appendChild(document.getJsSourceTag());
				}
				boolean readyIsPresent = false;
				String start, end = null;
				if (document.isReady()) {

					// okok abbiamo il metodo ready
					readyIsPresent = true;

					// quindi sostituisco le parti peculiari
					String source = document.jsSource().toString();
					source = source.replace(start_ready, "").replace(end_ready, "");
					document.setJsSource(new StringBuffer(source));
				}
				// scrivo il codice nel buffer
				document.jsSource().append(jsCode);

				// eseguo il metodo

				func.function(null); // for moment parameter = null

				document.jsSource().append(end_method); // si chiude il codice della funzione

				StringBuffer newSource = new StringBuffer();

				if (readyIsPresent) {
					newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
					document.setJsSource(newSource);
				}
				document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());

			} else {
				// eccezzione ...
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// chiude la finestra del browser
		/**
		 * This method closes the window browser
		 * 
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport close() {
			final String jsCode = "window.close();";
			executeJsMethod(jsCode);
			return window;
		}

		/**
		 * This method sets an interval
		 * 
		 * @param function
		 *            the function that is performed at each interval
		 * @param millisec
		 *            the milliseconds
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport setInterval(final jQueryfunction function, final int millisec) {
			// se jquery � impostata
			final String jsCode = "setInterval(function(){\n";
			executeJsMethodWithFunction(function, jsCode, millisec);
			return window;
		}

		/**
		 * This method sets a timeout
		 * 
		 * @param function
		 *            the function to be performed
		 * @param millisec
		 *            the milliseconds
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport setTimeout(final jQueryfunction function, final int millisec) {
			// se jquery � impostata
			final String jsCode = "setTimeout(function(){\n";
			executeJsMethodWithFunction(function, jsCode, millisec);
			return window;
		}

		/**
		 * This method executes the javascript alert method
		 * 
		 * @param text
		 *            the text to show
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport alert(String text) {
			// se jquery � impostata
			final String jsCode = "alert('" + text + "');";
			executeJsMethod(jsCode);
			return window;
		}

		private static int callsPrompt = 0;

		/**
		 * This method executes the prompt javascript method
		 * 
		 * @param text
		 *            the text to show
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport prompt(String text) {
			// se jquery � impostata
			String jsCode = "var inputValue = prompt('" + text + "');\n" + "console.log(inputValue);";
			if (jqueryIsSet()) {
				// controllo che non ci sia selezione
				// controllo il tipo di locazione javascript
				callsPrompt = callsPrompt + 1;
				if (!called) {
					called = true;

					// aggiungo il tag al documento

					JjDom.document.getBody().appendChild(document.getJsSourceTag());
				}

				// da qui a @
				// verifico la presenza del metodo ready
				boolean readyIsPresent = false;
				String start, end = null;
				if (document.isReady()) {

					// okok abbiamo il metodo ready
					readyIsPresent = true;

					// quindi sostituisco le parti peculiari
					String source = document.jsSource().toString();
					source = source.replace(start_ready, "").replace(end_ready, "");
					document.setJsSource(new StringBuffer(source));
				}
				// @

				// a questo punto raccolgo il codice :
				if (callsPrompt > 1) {
					jsCode = jsCode.replace("inputValue", "inputValue" + (callsPrompt));
				}
				document.jsSource().append(jsCode + "\n");

				StringBuffer newSource = new StringBuffer();

				if (readyIsPresent) {
					newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
					document.setJsSource(newSource);
				}

				document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
			} else {
				// jquery non impostata: non inizializzata
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return window;
		}

		private static int callsConfirm = 0;

		/**
		 * This method executes the confirm javascript method
		 * 
		 * @param text
		 *            the text to show
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport confirm(String text) {
			// se jquery � impostata
			String jsCode = "var result = confirm('" + text + "');\n" + "console.log(result);";
			if (jqueryIsSet()) {
				// controllo che non ci sia selezione
				// controllo il tipo di locazione javascript
				callsConfirm = callsConfirm + 1;
				if (!called) {
					called = true;

					// aggiungo il tag al documento

					JjDom.document.getBody().appendChild(document.getJsSourceTag());
				}

				// da qui a @
				// verifico la presenza del metodo ready
				boolean readyIsPresent = false;
				String start, end = null;
				if (document.isReady()) {

					// okok abbiamo il metodo ready
					readyIsPresent = true;

					// quindi sostituisco le parti peculiari
					String source = document.jsSource().toString();
					source = source.replace(start_ready, "").replace(end_ready, "");
					document.setJsSource(new StringBuffer(source));
				}
				// @
				// a questo punto raccolgo il codice :
				if (callsConfirm > 1) {
					jsCode = jsCode.replace("result", "result" + (callsConfirm));
				}
				document.jsSource().append(jsCode + "\n");

				StringBuffer newSource = new StringBuffer();

				if (readyIsPresent) {
					newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
					document.setJsSource(newSource);
				}
				document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
			} else {
				// jquery non impostata: non inizializzata
				try {
					throw new jQueryNotInitializedException();
				} catch (jQueryNotInitializedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return window;
		}

		/**
		 * This method executes the "console.log" javascript method
		 * 
		 * @param log
		 *            the javascript log
		 * @return the JavaScriptSupport instance
		 */
		public static JavaScriptSupport console_log(String log) {
			final String jsCode = "console.log('" + log + "');";
			executeJsMethod(jsCode);
			return window;
		}

		@Override
		public JjDom home() {
			// TODO Auto-generated method stub
			return instance;
		}
	}

	@Override
	public JjDom click(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			String clickCode = ".click(function(event){\n";
			currentEvent = new DefaultEvent(EventType.CLICK);
			executeMethodWithFunction(handler, clickCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// da segnalare : la divergenza rispetto a gli altri metodi
	@Override
	public String attr(String attributeName) {
		String attributeValue = null;
		if (jqueryIsSet()) {
			final String jsCode = ".attr('" + attributeName + "');";
			executeMethod(jsCode);
			if (currentSelection != null) {
				// ottengo gli elementi
				HTMLElement firstElement = (HTMLElement) currentSelection.getSelectedItems().element();
				attributeValue = firstElement.getAttributeValue(attributeName);
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return attributeValue;
	}

	@Override
	public JjDom attr(String attrName, String attrValue) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".attr('" + attrName + "','" + attrValue + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeOut(String jqEfect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeOut('" + jqEfect + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeOut(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeOut(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeOut(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeOut('" + jqEffect + "',function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeOut(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeOut(" + millisec + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeIn(String jqEfect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeIn('" + jqEfect + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeIn(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeIn(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeIn(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeIn('" + jqEffect + "',function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeIn(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeIn(" + millisec + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeToggle(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeToggle('" + jqEffect + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeToggle(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeToggle(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeToggle(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeToggle('" + jqEffect + "',function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom fadeToggle(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".fadeToggle(" + millisec + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// metodi di supporto : in questi metodi se qualcosa non va mi deve dare
	// eccezzione
	// quindi ultimare ...

	// tipo : click
	private static void executeMethodWithFunction(final jQueryfunction func, final String jsCode) {
		final String end_method = "});\n";
		if (jqueryIsSet()) {
			// controllo della locazione
			if (!called) {
				called = true;

				// aggiungo il tag al documento

				JjDom.document.getBody().appendChild(document.getJsSourceTag());
			}
			boolean readyIsPresent = false;
			String start, end = null;
			if (document.isReady()) {

				// okok abbiamo il metodo ready
				readyIsPresent = true;

				// quindi sostituisco le parti peculiari
				String source = document.jsSource().toString();
				source = source.replace(start_ready, "").replace(end_ready, "");
				document.setJsSource(new StringBuffer(source));

			}
			// @
			if (document.jsSource().toString().endsWith(";\n")) {
				StringBuffer newBuffer = document.jsSource()
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1)
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1);
				document.setJsSource(newBuffer);
			}

			// scrivo il codice nel buffer
			document.jsSource().append(jsCode);

			// eseguo il metodo
			func.function(currentEvent); // for moment parameter = null

			document.jsSource().append(end_method); // si chiude il codice della funzione

			StringBuffer newSource = new StringBuffer();

			if (readyIsPresent) {
				newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
				document.setJsSource(newSource);
			}
			document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
		} else {
			// eccezzione ...
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// per i metodi come : hover()
	private static void executeMethodWithFunction(final jQueryfunction func, final String jsCode,
			final jQueryfunction func2) {
		final String end_method = "});\n";
		final String end_method2 = "}";
		if (jqueryIsSet()) {
			// controllo della locazione
			if (!called) {
				called = true;

				// aggiungo il tag al documento

				JjDom.document.getBody().appendChild(document.getJsSourceTag());
			}
			boolean readyIsPresent = false;
			String start, end = null;
			if (document.isReady()) {

				// okok abbiamo il metodo ready
				readyIsPresent = true;

				// quindi sostituisco le parti peculiari
				String source = document.jsSource().toString();
				source = source.replace(start_ready, "").replace(end_ready, "");
				document.setJsSource(new StringBuffer(source));
			}
			// @
			if (document.jsSource().toString().endsWith(";\n")) {
				StringBuffer newBuffer = document.jsSource()
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1)
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1);
				document.setJsSource(newBuffer);
			}

			// scrivo il codice nel buffer
			document.jsSource().append(jsCode);

			// eseguo il metodo

			func.function(null); // for moment parameter = null

			document.jsSource().append(end_method2 + ",function(){\n");

			func2.function(null);

			document.jsSource().append(end_method);

			StringBuffer newSource = new StringBuffer();

			if (readyIsPresent) {
				newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
				document.setJsSource(newSource);
			}
			document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
		} else {
			// eccezzione ...
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// questo invece � : per quei metodi che finiscono
	// con un parametro dopo la funzione, tipo setInterval, metodo javascript
	private static void executeMethodWithFunction(final jQueryfunction func, final String jsCode, final String param) {
		final String end_method = "},'" + param + "');\n";
		if (jqueryIsSet()) {
			// controllo della locazione
			if (!called) {
				called = true;

				// aggiungo il tag al documento

				JjDom.document.getBody().appendChild(document.getJsSourceTag());
			}
			boolean readyIsPresent = false;
			String start, end = null;
			if (document.isReady()) {
				// okok abbiamo il metodo ready
				readyIsPresent = true;

				// quindi sostituisco le parti peculiari
				String source = document.jsSource().toString();
				source = source.replace(start_ready, "").replace(end_ready, "");
				document.setJsSource(new StringBuffer(source));
			}
			// @
			if (document.jsSource().toString().endsWith(";\n")) {
				StringBuffer newBuffer = document.jsSource()
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1)
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1);
				document.setJsSource(newBuffer);
			}

			// scrivo il codice nel buffer
			document.jsSource().append(jsCode);

			// eseguo il metodo

			func.function(null); // for moment parameter = null

			document.jsSource().append(end_method); // si chiude il codice della funzione

			StringBuffer newSource = new StringBuffer();

			if (readyIsPresent) {
				newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
				document.setJsSource(newSource);
			}
			document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
		} else {
			// eccezzione ...
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// questo invece � : per quei metodi che finiscono
	// con un parametro dopo la funzione, tipo setInterval, metodo javascript
	private static void executeMethodWithFunction(final jQueryfunction func, final String jsCode, final int param) {
		final String end_method = "}," + param + ");\n";
		if (jqueryIsSet()) {
			// controllo della locazione
			if (!called) {
				called = true;

				// aggiungo il tag al documento

				JjDom.document.getBody().appendChild(document.getJsSourceTag());
			}
			boolean readyIsPresent = false;
			String start, end = null;
			if (document.isReady()) {

				// okok abbiamo il metodo ready
				readyIsPresent = true;

				// quindi sostituisco le parti peculiari
				String source = document.jsSource().toString();
				source = source.replace(start_ready, "").replace(end_ready, "");
				document.setJsSource(new StringBuffer(source));
			}
			// @
			if (document.jsSource().toString().endsWith(";\n")) {
				StringBuffer newBuffer = document.jsSource()
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1)
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1);
				document.setJsSource(newBuffer);
			}

			// scrivo il codice nel buffer
			document.jsSource().append(jsCode);

			// eseguo il metodo

			func.function(null); // for moment parameter = null

			document.jsSource().append(end_method); // si chiude il codice della funzione

			StringBuffer newSource = new StringBuffer();

			if (readyIsPresent) {
				newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
				document.setJsSource(newSource);
			}
			document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
		} else {
			// eccezzione ...
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void executeMethod(String jsCode) {
		if (jqueryIsSet()) {
			// controllo che non ci sia selezione
			// controllo il tipo di locazione javascript
			if (!called) {
				called = true;

				// aggiungo il tag al documento

				JjDom.document.getBody().appendChild(document.getJsSourceTag());
			}

			// da qui a @
			// verifico la presenza del metodo ready
			boolean readyIsPresent = false;
			String start, end = null;
			if (document.isReady()) {

				// okok abbiamo il metodo ready
				readyIsPresent = true;

				// quindi sostituisco le parti peculiari
				String source = document.jsSource().toString();
				source = source.replace(start_ready, "").replace(end_ready, "");
				document.setJsSource(new StringBuffer(source));
			}
			// @
			if (document.jsSource().toString().endsWith(";\n")) {
				StringBuffer newBuffer = document.jsSource()
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1)
						.deleteCharAt(document.jsSource().toString().toCharArray().length - 1);
				document.setJsSource(newBuffer);
			}
			document.jsSource().append(jsCode + "\n");

			StringBuffer newSource = new StringBuffer();

			if (readyIsPresent) {
				newSource.append(start_ready).append(document.jsSource().toString()).append(end_ready);
				document.setJsSource(newSource);
			}

			document.getJsSourceTag().setTextContent("\n" + document.jsSource().toString());
		} else {
			// jquery non impostata: non inizializzata
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// esegue solo il codice jquery
	@Override
	public JjDom append(String content) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".append('" + content + "');";
			executeMethod(jsCode);
			inst = instance;
		} else
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return inst;
	}

	@Override
	public JjDom val(String value) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".val('" + value + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom val() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".val();";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// personalizzato, segnalare differenza con quello jquery
	@Override
	public String text() {
		String texts = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".text();";
				executeMethod(jsCode);
				texts = Recursion.getTexts(elements);
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return texts;
	}

	@Override
	public JjDom text(String text) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".text('" + text + "');";
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	/**
	 * Questo metodo non memorizza il tipo di evento
	 */
	@Deprecated
	@Override
	public JjDom bind(String eventType, jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".bind('" + eventType + "',function(event){\n";
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom bind(EventType eventType, jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			String eventTypeName = getEventTypeName(eventType);
			if (eventTypeName != null) {
				final String jsCode = ".bind('" + eventTypeName + "',function(event){\n";
				currentEvent = new DefaultEvent(eventType);
				executeMethodWithFunction(handler, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// metodi di supporto per gli eventType fino a #

	private static String getEventTypeName(EventType eventType) {
		String eventType_ = null;
		switch (eventType) {
		case CLICK:

			eventType_ = "click";

			break;

		case DBL_CLICK:

			eventType_ = "dblclick";

			break;

		case SUBMIT:

			eventType_ = "submit";

			break;

		case MOUSE_OVER:

			eventType_ = "mouseover";

			break;

		case MOUSE_OUT:

			eventType_ = "mouseout";

			break;

		case MOUSE_ENTER:

			eventType_ = "mouseenter";

			break;

		case MOUSE_LEAVE:

			eventType_ = "mouseleave";

			break;

		default:
			break;
		}
		return eventType_;
	}

	/**
	 * Questo metodo non memorizza il tipo di evento
	 */
	@Deprecated
	@Override
	public JjDom on(String eventType, jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".on('" + eventType + "',function(event){\n";
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom on(EventType eventType, jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String eventType_ = getEventTypeName(eventType);
			if (eventType_ != null) {
				final String jsCode = ".on('" + eventType_ + "',function(event){\n";
				currentEvent = new DefaultEvent(eventType);
				executeMethodWithFunction(handler, jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Deprecated
	@Override
	public JjDom off() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".off();";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Deprecated
	@Override
	public JjDom off(String eventType) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".off('" + eventType + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom off(EventType eventType) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String eventType_ = getEventTypeName(eventType);
			if (eventType_ != null) {
				final String jsCode = ".off('" + eventType_ + "');";
				currentEvent = null;
				executeMethod(jsCode);
				inst = instance;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom hover(jQueryfunction handlerIn, jQueryfunction handlerOut) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".hover(function(event){\n";
			executeMethodWithFunction(handlerIn, jsCode, handlerOut);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom mouseenter(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".mouseenter(function(event){\n";
			currentEvent = new DefaultEvent(EventType.MOUSE_ENTER);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom mouseleave(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".mouseleave(function(event){\n";
			currentEvent = new DefaultEvent(EventType.MOUSE_LEAVE);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom mouseover(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".mouseover(function(event){\n";
			currentEvent = new DefaultEvent(EventType.MOUSE_OVER);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom mouseout(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".mouseout(function(event){\n";
			currentEvent = new DefaultEvent(EventType.MOUSE_OUT);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom submit() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".submit();";
			currentEvent = new DefaultEvent(EventType.SUBMIT);
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom submit(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".submit(function(event){\n";
			currentEvent = new DefaultEvent(EventType.SUBMIT);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom dblclick(jQueryfunction handler) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".dblclick(function(event){\n";
			currentEvent = new DefaultEvent(EventType.DBL_CLICK);
			executeMethodWithFunction(handler, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggle(String jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle('" + jqEffect + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggle(jQueryEffect jqEffect) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle('" + jqEffect.name().toLowerCase() + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return inst;
	}

	@Override
	public JjDom toggle(String jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle('" + jqEffect + "',function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggle(jQueryEffect jqEffect, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle('" + jqEffect.name().toLowerCase() + "',function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggle(int millisec) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle(" + millisec + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom toggle(int millisec, jQueryfunction callBack) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".toggle(" + millisec + ",function(){\n";
			executeMethodWithFunction(callBack, jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	/**
	 * This method is equivalent to the metod : css("background",color);
	 * 
	 * @param color
	 *            the background color
	 * @return the JjDom instance
	 */
	public JjDom background(String color) {
		return css("background", color);
	}

	/**
	 * This method is equivalent to the metod : css("color",color);
	 * 
	 * @param color
	 *            the foreground color
	 * @return the JjDom instance
	 */
	public JjDom foreground(String color) {
		return css("color", color);
	}

	// da segnalare , � personalizzato
	@Override
	public String prop(String propertyName) {
		String prop = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				HTMLElement el = (HTMLElement) elements.element();
				final String jsCode = ".prop('" + propertyName + "');";
				executeMethod(jsCode);
				switch (propertyName) {

				case PROPERTY_TAGNAME:

					prop = el.getNodeName();

					break;

				case PROPERTY_NODENAME:

					prop = el.getNodeName();

					break;

				case PROPERTY_NODETYPE:

					prop = el.getNodeType().name();

					break;

				case PROPERTY_SELECTED_INDEX:

					prop = String.valueOf(el.getIndex());

					break;

				case PROPERTY_NODEVALUE:

					prop = el.getNodeValue();

					break;
				}
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return prop;
	}

	@Override
	public JjDom prop(String propertyName, String value) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".prop('" + propertyName + "','" + value + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom prepend(String content) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".prepend('" + content + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom removeAttr(String attributeName) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".removeAttr('" + attributeName + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom delay(int duration) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".delay(" + duration + ");";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public HTMLElement first() {
		HTMLElement element = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".first();";
				executeMethod(jsCode);
				element = (HTMLElement) elements.element();
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return element;
	}

	@Override
	public HTMLElement last() {
		HTMLElement element = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".last();";
				executeMethod(jsCode);
				element = (HTMLElement) elements.getLast();
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return element;
	}

	@Override
	public JjDom after(String content) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".after('" + content + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom before(String content) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".before('" + content + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public NodeList children() {
		NodeList list = null;
		if (jqueryIsSet()) {
			if (currentSelection != null) {
				final String jsCode = ".children();";
				executeMethod(jsCode);
				list = new NodeList();
				for (Element el : elements) {
					NodeList listNodes = el.getChildNodes();
					for (int i = 0; i < listNodes.getLength(); i++) {
						list.addNode(listNodes.item(i));
					}
				}
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	// segnalare questo metodo sia perch� lavora con il dom
	// e sia perch� deve restituire true, per eseguire il prossimo elemento
	public JjDom each(function function) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			boolean notCompleted = selectionIsNotCompleted();
			if (notCompleted == true) {
				deleteTheSelectionNotCompleted();
			}
			for (int i = 0; i < elements.size(); i++) {
				Boolean flag = (Boolean) function.function(new Integer(i));
				boolean result = flag.booleanValue();
				if (result == false) {
					break;
				}
			}
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public int length() {
		if (jqueryIsSet()) {
			cleanUp();
			return JjDom.length;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}

	}

	/**
	 * I risultati jquery e dom possono non compaciare
	 */
	@SuppressWarnings("static-access")
	@Override
	public JjDom find(String selection) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			Elements listElements = new Elements();
			for (Element htmlElement : JjDom.elements) {
				// qui cambio contesto
				getSelector().setRootContext(htmlElement);
				// a questo punto seleziono normalmente con jquery
				Elements newElements = jquery(selection).elements();
				for (Element htmlElement2 : newElements) {
					listElements.add(htmlElement2);
				}
			}
			// riporto il root context a document
			JjDom.elements = listElements;
			getSelector().setRootContext(JjDom.document);
			final String jsCode = ".find('" + selection + "');";
			executeMethod(jsCode);
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom empty() {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".empty();";
			executeMethod(jsCode);
			// ora agisco a livello dom
			// si da per scontato che ci siamo
			// elementi selezionati
			for (Element element : elements) {
				// in tanto cancello il testo
				if (element.getTextContent() != null) {
					element.setTextContent(null);
				}
				// mi serve un metodo che dato un nodo, ne cancella i figli
				if (element.hasChildNodes()) {
					element.removeChildren();
				}
			}
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	@Override
	public JjDom add(String selection) {
		JjDom inst = null;
		if (jqueryIsSet()) {
			final String jsCode = ".add('" + selection + "');";
			executeMethod(jsCode);
			// semplicemente aggiungo gli elementi di questa selezione
			// a quelli precedenti
			CSSSelection selection_ = selector.select(selection);
			elements.addAll(selection_.getSelectedItems());
			inst = instance;
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inst;
	}

	// segnalare che non lavora con i risultati jquery
	@Override
	public boolean is(jQuerySelector jquerySelector) {
		// TODO Auto-generated method stub
		boolean is = false;
		if (jqueryIsSet()) {
			switch (jquerySelector) {
			case VISIBLE:
				for (Element element : elements) {
					HTMLElement cast = (HTMLElement) element;
					if (cast.hasCssProp("display")) {
						String value = cast.getCssPropValue("display");
						if (value.equals("none")) {
							is = false;
						} else {
							is = true;
							break;
						}
					} else {
						is = true;
						break;
					}
				}
				break;
			case HIDDEN:
				for (Element element : elements) {
					HTMLElement cast = (HTMLElement) element;
					if (cast.hasCssProp("display")) {
						String value = cast.getCssPropValue("display");
						if (value.equals("none")) {
							is = true;
							break;
						} else {
							is = false;
						}
					} else {
						is = false;
					}
				}
				break;
			}
		} else {
			try {
				throw new jQueryNotInitializedException();
			} catch (jQueryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return is;
	}

	// segnalare che non lavora con i risultati jquery
	@Override
	public boolean is(String selector) {
		jQuerySelector selector_ = null;
		if (selector.startsWith(":")) {
			selector = selector.replace(":", "");
			for (jQuerySelector jQuerySelector : availableSelectors) {
				if (selector.equals(jQuerySelector.name().toLowerCase())) {
					selector_ = jQuerySelector;
					break;
				}
			}
			if (selector_ != null)
				return is(selector_);
			else
				return false;
		} else {
			return false;
		}
	}

	/**
	 * This method updates the document,<br>
	 * useful when you make changes to<br>
	 * an online document, and you want<br>
	 * to save these changes.
	 * 
	 * @param document
	 *            the document
	 * @return the JjDom instance
	 */
	public static JjDom update(Document document) {
		return migrate(JjDom.documentURL, document, false);
	}

	// agisce sul documento di JjDom
	/**
	 * This method updates the JjDom document,<br>
	 * useful when you make changes to<br>
	 * an online document, and you want<br>
	 * to save these changes.
	 * 
	 * @return the JjDom instance
	 */
	public static JjDom update() {
		return migrate(JjDom.documentURL);
	}
}
