package cloud.jgo.test;
import cloud.jgo.jjdom.JjDom;
import cloud.jgo.jjdom.dom.nodes.Comment;
import cloud.jgo.jjdom.dom.nodes.Document;
import cloud.jgo.jjdom.dom.nodes.Element;
import cloud.jgo.jjdom.dom.nodes.Elements;
import cloud.jgo.jjdom.dom.nodes.xml.XMLDocument;

public class XMLTest {
public static void main(String[] args) {
	
	// BUG da risolvere nel metodo connect, quando lo usiamo per recuperare 
	// il documento, vedere come risolvere
	
	// primo passo : creo il documento XML
	
	Document document = new XMLDocument("wasp.project");
	
	// a questo punto mi creo gli elementi 
	Element projectName,projectVersion,projectUrl;
	Element contacts,contact,email,tel ;
	
	projectName = document.createElement("project.name");
	projectVersion = document.createElement("project.version");
	projectUrl = document.createElement("project.product");
	contacts = document.createElement("contacts");
	contact = document.createElement("contact");
	email = document.createElement("email");
	tel = document.createElement("tel");

	
	// aggiungo i testi 
	
	projectName.setTextContent("My-Project-Test");
	projectVersion.setTextContent("1.0.0-Snapshot");
	projectUrl.setTextContent("https://www.github.com/My-Project-Test");
	email.setTextContent("wasp91dayno@gmail.com");
	tel.setTextContent("39321221");
	// okok annodo gli elementi 
	
	contact.appendChilds(email,tel);
	contacts.appendChild(contact);
	document.getRootElement().appendChilds(projectName,projectVersion,projectUrl,contacts);
	
	// voglio publicare il documento adesso ... 
	
	
}
}
