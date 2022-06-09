package tdbprimer;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.arq.querybuilder.UpdateBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class Primer {

	public static void main(String[] args) {
		
		//Kreiramo triplet koji ubacujemo u RDF store
		Node subject = NodeFactory.createURI("http://www.tdbprimer/primer#marko");
		Node predicate = NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		Node object = NodeFactory.createURI("http://www.tdbprimer/primer#Profesor");
		//Da bismo menjali sadržaj store-a koristimo UpdateBuilder
		UpdateBuilder ub = new UpdateBuilder()
				//insert komanda za ubacivanje
				.addInsert(subject, predicate, object);
		
		try {
			//kreiramo request, i šaljemo na remote lokaciju
			UpdateRequest req = ub.buildRequest();
			UpdateProcessor u = UpdateExecutionFactory.createRemote(req, "http://localhost:3030/primer");
			u.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//primer dobavljanja koristeæi string upit
		String query = "SELECT ?subjekat WHERE { ?subjekat  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://www.tdbprimer/primer#Profesor> }";
		
		//koristimo sparqlService za slanje zahteva preko http
		QueryExecution q = QueryExecutionFactory
				.sparqlService("http://localhost:3030/primer", query);
		//kao odgovor dobijamo ResultSet kroz koji iteriramo
		ResultSet results = q.execSelect();
		while (results.hasNext()) {
			//Stavke iz ResultSet su solution, koji sadrže promenljive koje smo
			//zadali u upitu
			QuerySolution solution = results.nextSolution();
			RDFNode subjekat = solution.get("subjekat");
			System.out.println(subjekat);
		}
		
		//primer koristeæi select builder uz upotrebu prefiksa
		SelectBuilder sb = new SelectBuilder()
				.setDistinct(true)
				.addPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
				.addPrefix("prim", "http://www.tdbprimer/primer#")
				.addVar("?subjekat")
				.addWhere("?subjekat", "rdf:type", "prim:Profesor");
		Query q2 = sb.build();
		QueryExecution qe2 = QueryExecutionFactory.sparqlService("http://localhost:3030/primer", q2);
		ResultSet rs = qe2.execSelect();
		while (rs.hasNext()) {
			QuerySolution solution = rs.nextSolution();
			RDFNode subjekat = solution.get("subjekat");
			System.out.println(subjekat);
		}
		

	}

}
