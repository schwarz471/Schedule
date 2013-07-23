package scheduler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import nameSpaces.Event;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@Path("/delete")
public class DeleteSchedule {
	@DELETE
	@Path(value="/{id}")
	public Response delete(@PathParam("id") String id) throws IOException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		fin.close();
		Model target = ModelFactory.createDefaultModel();
		
		Query query = QueryFactory.create("CONSTRUCT { <" + Event.getURI() + id + "> ?p ?o . } WHERE { <" + Event.getURI() + id + "> ?p ?o . }");
		QueryExecution qexec = QueryExecutionFactory.create(query, db);
		target = qexec.execConstruct();

		db.remove(target);
		
		FileOutputStream fout = new FileOutputStream("/schedules/db.rdf");
		db.write(fout);
		fout.close();
		
		return Response.ok().build();
	}

}
