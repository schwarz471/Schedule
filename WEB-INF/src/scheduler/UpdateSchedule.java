package scheduler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

@Path("/update")
public class UpdateSchedule {
	@PUT
	@Path(value="/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response update(@FormDataParam("schedule") String schedule, @PathParam("id") String id) throws IOException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		fin.close();
		Model model = ModelFactory.createDefaultModel();
		Model target = ModelFactory.createDefaultModel();
		
		Query query = QueryFactory.create("CONSTRUCT { <" + Event.getURI() + id + "> ?p ?o . } WHERE { <" + Event.getURI() + id + "> ?p ?o . }");
		QueryExecution qexec = QueryExecutionFactory.create(query, db);
		target = qexec.execConstruct();
		model.read(schedule);

		db.remove(target);
		db.add(model);
		
		FileOutputStream fout = new FileOutputStream("/schedules/db.rdf");
		db.write(fout);
		fout.close();
		
		return Response.ok().build();
	}

}
