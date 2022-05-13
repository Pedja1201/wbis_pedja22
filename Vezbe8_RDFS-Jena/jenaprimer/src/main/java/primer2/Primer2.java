/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primer2;

import java.io.InputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.rdf.model.Statement;

public class Primer2 {
    public static void main(String[] args){
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\wbis_pedja22\\Vezbe7_RDF\\primer1.rdf");
        
        model.read(in, null);
        model.write(System.out);
        
        //kretanje kroz model
        Resource profesor = model.getResource("http://www.singidunum.ac.rs/osoblje/profesori/marko"); 
        Property propAsistent = model.getProperty("http://www.singidunum.ac.rs/nastava#", "asistent");
        String kontaktNS = model.getNsPrefixURI("kontakt");
        Property kontaktIme = model.getProperty(kontaktNS, "ime");
        
        //getObject nam može vratiti ili literal ili resurs, zato moramo eksplicitno da castujemo
        Resource asistent = (Resource) profesor.getProperty(propAsistent).getObject();
        System.out.println(asistent.toString());
        
        //getResource možemo da koristimo ako već znamo da je resurs
        asistent = profesor.getProperty(propAsistent).getResource();
        System.out.println(asistent.toString());
        
        //getString možemo da koristimo ako znamo da je literal
        String ime = profesor.getProperty(kontaktIme).getString();
        System.out.println(ime);
        //možemo da imamo više statement-a sa istim predikatima
        profesor.addProperty(kontaktIme, "Nemanja");
        profesor.addProperty(kontaktIme, "Milan");
        //dobavljamo sve kontaktIme predikate
        StmtIterator iter = profesor.listProperties(kontaktIme);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().getObject().toString());
        }
        
        //jednostavni upiti
        SimpleSelector svi = new SimpleSelector(null, null,(RDFNode) null);
        SimpleSelector svi2 = new SimpleSelector(null, null,(String) null);
        SimpleSelector sviSaImenom = new SimpleSelector(null, kontaktIme, (String)null);
        SimpleSelector sviSaImenomMarko = new SimpleSelector(null, kontaktIme, "Marko");
        System.out.println("selektori za sve:");
        
        iter = model.listStatements(svi);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }
        System.out.println("selektori za sve sa imenom Marko:");
        iter = model.listStatements(sviSaImenomMarko);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }
        profesor.hasProperty(kontaktIme);
        
        System.out.println("Custom selektor:");
        //možemo definisati naše selektore
        SimpleSelector customSelector = new SimpleSelector(null, kontaktIme, (RDFNode) null)
        {
            public boolean selects(Statement s){
                if(s.getObject().toString().equals("Petar")){
                    return true;
                }
                return false;
            }
        };
        
        iter = model.listStatements(customSelector);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

    }
}
