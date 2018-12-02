package cloud.jgo.jjdom.dom.nodes.xml;
import java.util.HashMap;
import java.util.Map;
import cloud.jgo.jjdom.JjDom;
import cloud.jgo.jjdom.dom.Recursion;
import cloud.jgo.jjdom.dom.nodes.Element;
import cloud.jgo.jjdom.dom.nodes.Elements;
import cloud.jgo.jjdom.dom.nodes.Node;
import cloud.jgo.jjdom.dom.nodes.NodeList;
import cloud.jgo.jjdom.dom.nodes.html.HTMLComment;
import cloud.jgo.jjdom.dom.nodes.html.HTMLDefaultDocument;
import cloud.jgo.jjdom.dom.nodes.html.HTMLDefaultElement;
import cloud.jgo.jjdom.dom.nodes.html.HTMLDocument;
import cloud.jgo.jjdom.dom.nodes.html.HTMLElement;
import cloud.jgo.jjdom.dom.nodes.html.HTMLElement.HTMLElementType;

public class XMLElement implements Element{
	private String nodeName;
	private NodeList childNodes = null ;
	private XMLDocument document = null ;
	private String elementName = null ;
	private String startTag,endTag = null ;
	private String originalStartTag,originalEndTag = null ;
	private String textContent = null;
	private Map<String, String> attributes=null;
	private Node parentNode=null;
	private StringBuffer xmlCode = new StringBuffer();
	public XMLElement(String elementName,XMLDocument Document) {
		// TODO Auto-generated constructor stub
		this.startTag = "<"+elementName+">";
		this.originalStartTag = this.startTag;
		this.endTag = "</"+elementName+">";
		this.originalEndTag = this.endTag ;
		this.childNodes = new NodeList();
		this.attributes = new HashMap<>();
		this.document = document ;
	}
	
	public String getStartTag() {
		return this.startTag;
	}
	
	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}
	
	public String getEndTag() {
		return this.endTag;
	}
		protected XMLElement() {}
		
		/**
		 * This method sets the parent node
		 * @param parentNode the parent node
		 */
		public void setParentNode(Node parentNode){
			this.parentNode =  parentNode ;
		}

	@Override
	public Node appendChild(Node node) {
		if (this.childNodes.contains(node)) {
			this.childNodes.remove(node);
		}
		// aggiungo il nodo
		
		boolean result = this.childNodes.addNode(node);
		if (result == true) {
			if (node instanceof Element) {
				((XMLElement)node).setParentNode(this);
			}
			else if(node instanceof HTMLComment){
				((XMLComment)node).setParentNode(this);
			}
			return  node ;
		}
		else {
			return null ;
		}
	}

	@Override
	public Node appendChilds(Node... childs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMarkup() {
		Recursion.examines_xml(this,xmlCode,null); // provvisorio, poi gli dobbiamo passare il document
		String result = xmlCode.toString();
		// pulisco il buffer code html
		xmlCode = new StringBuffer();
		return result ;	
	}

	@Override
	public Node printMarkup() {
		// TODO Auto-generated method stub
		System.out.println(getMarkup());
		return this;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getChildNodes() {
		// TODO Auto-generated method stub
		return this.childNodes ;
	}

	@Override
	public boolean hasThisChild(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node child(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node previous() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Node getFirstChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getLastChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getBrothers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFirstChild(Node node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return this.nodeName ;
	}

	@Override
	public boolean contains(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNodeValue() {
		// TODO Auto-generated method stub
		return this.textContent ;
	}

	@Override
	public String getTextContent() {
		// TODO Auto-generated method stub
		return this.textContent ;
	}

	@Override
	public Node setNodeValue(String nodeValue) {
		// TODO Auto-generated method stub
		this.textContent = nodeValue ;
		return this ;
	}

	@Override
	public Node setTextContent(String textContent) {
		// TODO Auto-generated method stub
		this.textContent = textContent ;
		return this ;
	}

	@Override
	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLDocument getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeType getNodeType() {
		// TODO Auto-generated method stub
		return NodeType.ELEMENT;
	}

	@Override
	public boolean hasChildNodes() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node insertBefore(Node newNode, Node refChild) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node insertAfter(Node newNode, Node refChild) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqualNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node removeNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node replaceChild(Node newNode, Node oldNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getNodeByPath(String nodePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String nodeName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JjDom home() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getElementById(String elementId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByClassName(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByAttributeValue(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByAttributeValue(String attr, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsByDifferentAttributeValue(String attr, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsThatStartWithAttributevalue(String attr,
			String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsThatEndWithAttributeValue(String attr, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getElementsThatContainTheAttributeValue(String attr,
			String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getDirectChildrenByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getAdiacentSiblingsByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements getGeneralSiblingsByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBrothers() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLElement setAttribute(String attr, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLElement replaceAttributeValue(String attr, String newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLElement removeAttribute(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribute(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttributeValue(String attr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getPreviousSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPresent(String attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAttributes() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCompletePath() {
		// TODO Auto-generated method stub
		return null;
	}

}
