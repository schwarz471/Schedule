package scheduler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@Path(value="/")
public class ReadSchedule {	
	@GET
	public Response read(@QueryParam("format") String format) throws FileNotFoundException, UnsupportedEncodingException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String outform = "RDF/XML";
		if(format != null)
			outform = format;
		
		db.write(out, outform);
		
		return Response.ok().entity(out.toString("UTF-8")).build();
	}
	
	@GET
	@Path(value="/{year}")
	public Response read(@PathParam("year") String year, @QueryParam("format") String format) throws FileNotFoundException, UnsupportedEncodingException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		
		Query query = QueryFactory.create("CONSTRUCT { ?s ?p ?o . } WHERE { ?s ?p ?o . ?s SEvent:startDate ?date FILTER (?date = xsd:dateTime(\"" + year + "\"))");
		QueryExecution qexe = QueryExecutionFactory.create(query, db);
		
		Model dataSet = qexe.execConstruct();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String outform = "RDF/XML";
		if(format != null)
			outform = format;
		
		dataSet.write(out, outform);
		
		return Response.ok().entity(out.toString("UTF-8")).build();
	}
	
	@GET
	@Path(value="/{year}/{month}")
	public Response read(@PathParam("year") String year,@PathParam("month") String month, @QueryParam("format") String format) throws FileNotFoundException, UnsupportedEncodingException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		
		Query query = QueryFactory.create("CONSTRUCT { ?s ?p ?o . } WHERE { ?s ?p ?o . ?s SEvent:startDate ?date FILTER (?date = xsd:dateTime(\"" + year + "-" + month + "\"))");
		QueryExecution qexe = QueryExecutionFactory.create(query, db);
		
		Model dataSet = qexe.execConstruct();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String outform = "RDF/XML";
		if(format != null)
			outform = format;
		
		dataSet.write(out, outform);
		
		return Response.ok().entity(out.toString("UTF-8")).build();
	}
	
	@GET
	@Path(value="/{year}/{month}/{date}")
	public Response read(
			@PathParam("year") String year,@PathParam("month") String month,@PathParam("date") String date, @QueryParam("format") String format)
					throws FileNotFoundException, UnsupportedEncodingException{
		FileInputStream fin = new FileInputStream("/schedules/db.rdf");
		Model db = ModelFactory.createDefaultModel();
		db.read(fin, null);
		
		Query query = QueryFactory.create("CONSTRUCT { ?s ?p ?o . } WHERE { ?s ?p ?o . ?s SEvent:startDate ?date FILTER (?date = xsd:dateTime(\"" + year + "-" + month + "-" + date + "\"))");
		QueryExecution qexe = QueryExecutionFactory.create(query, db);
		
		Model dataSet = qexe.execConstruct();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String outform = "RDF/XML";
		if(format != null)
			outform = format;
		
		dataSet.write(out, outform);
		
		return Response.ok().entity(out.toString("UTF-8")).build();
	}
}
