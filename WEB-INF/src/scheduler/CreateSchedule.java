package scheduler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nameSpaces.Event;

import org.apache.commons.lang3.RandomStringUtils;

import security.Encrypter;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.sun.jersey.multipart.FormDataParam;

@Path("/")
public class CreateSchedule {
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response create(@FormDataParam("schedule") String schedule) throws IOException{
		
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		db.setNsPrefix("SEvent", Event.getURI());
		fin.close();
		
		Model sch = ModelFactory.createDefaultModel();
		Model sch2 = ModelFactory.createDefaultModel();
		sch.read(schedule);
		StmtIterator iter = sch.listStatements();
		StmtIterator iter2 = iter;
		Statement stmt;
		String randomString = null;
		String identifier = null;
		Query query = null;
		QueryExecution qexec = null;
		while(iter.hasNext()){
			stmt = iter.next();
			Resource s = stmt.getSubject();
			String defaultId = null;
			defaultId = s.getURI().toString().split("#")[1];
			query = QueryFactory.create("ASK { <" + Event.getURI() + defaultId + "> ?p ?o . }");
			qexec = QueryExecutionFactory.create(query, db);
			if(qexec.execAsk()){
				while(qexec.execAsk()){
					randomString = RandomStringUtils.randomAlphanumeric(16);
					identifier = Encrypter.getHash(randomString, Encrypter.ALG_SHA256);
					query = QueryFactory.create("ASK { <" + Event.getURI() + identifier + "> ?p ?o . }");
					qexec = QueryExecutionFactory.create(query, db);
				}
				Resource r = sch2.createResource(Event.getURI() + identifier);
				while(iter2.hasNext()){
					Property p = stmt.getPredicate();
					RDFNode o = stmt.getObject();
					sch2.add(r, p, o);
				}
				sch.removeAll();
				sch.add(sch2);
				break;
			}
		}
		db.add(sch);
		
		FileOutputStream fout = new FileOutputStream("/schedules/db.rdf");
		db.write(fout);
		fout.close();
		
		return Response.ok().entity("create success").build();
	}

}
