package nameSpaces;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Event {
	private static Model m_model = ModelFactory.createDefaultModel();
	public static final String NS = "http://dspace.fujii-lab.jp/Event#";
	public static final Resource NAMESPACE = m_model.createResource(NS);
	public static final Resource Event = m_model.createResource(NS + "Event");
	public static String getURI() { return NS; }
	public static final Property title = m_model.createProperty(NS + "title");
	public static final Property startDate = m_model.createProperty(NS + "startDate");
	public static final Property endDate = m_model.createProperty(NS + "endDate");
	public static final Property place = m_model.createProperty(NS + "place");
	public static final Property detail = m_model.createProperty(NS + "detail");

}
