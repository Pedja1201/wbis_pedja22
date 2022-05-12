/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primer1;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class Primer {
    public static void main(String[] args){
        String nsOsoba = "http://www.singidunum.ac.rs/osoba/";
    String nsNastava = "http://www.singidunum.ac.rs/nastava/";
    String profesorURI = "http://www.singidunum.ac.rs/profesori/marko";
    String asistentURI = "http://www.singidunum.ac.rs/asistenti/petar";
    String ime = "Marko";
    String prezime = "Markovic";
    
    Model model = ModelFactory.createDefaultModel();
    //eksplicitno navodjenje prefiksa. Ako ne navedemo, dodeljuju im se default prefiksi
    model.setNsPrefix("nastava", nsNastava);
    model.setNsPrefix("osoba", nsOsoba);
    model.setNsPrefix("sing","http://www.singidunum.ac.rs/");
    
    Property nsProf = model.createProperty(nsOsoba, "ime"); //osoba:ime
    Property nsAss = model.createProperty(nsNastava, "saradjuje"); //nastava:saradjuje
    
    Resource asistent = model.createResource(asistentURI);
    asistent.addProperty(nsProf, "Petar");
    Resource profesorMarko = model.createResource(profesorURI)
            .addProperty(nsProf, ime)
            .addProperty(nsAss, asistent);
    model.write(System.out);
        
    //prolazak kroz sve iskaze
    StmtIterator iter = model.listStatements();
    while (iter.hasNext()){
       Statement s = iter.nextStatement();
       Resource subject = s.getSubject();
       Property predicate = s.getPredicate();
       RDFNode object = s.getObject(); //rdfnode je superklasa za resource i literal
       
       System.out.print(subject.toString());
       
       System.out.print(" " + predicate.toString() + " ");
       if(object instanceof Resource){
        System.out.print(object.toString());
    }else{
           System.out.print("\"" + object.toString() + "\"");
       }
       
       System.out.println("");
       
    }
}
    
    
    
}
