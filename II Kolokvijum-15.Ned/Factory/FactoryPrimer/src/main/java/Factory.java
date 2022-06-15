import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class Factory {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("C:\\Users\\JAGODA\\Desktop\\MihajloJagodic\\Factory.rdf");

        model.read(in, null);
        model.write(System.out);

        Resource masina3 = model.getResource("http://www.singidunum.ac.rs/factory#masina3");
        Resource serviserClass = model.getResource("http://www.singidunum.ac.rs/factory#Serviser");
        Property imaSpecijalizaciju = model.getProperty(model.getNsPrefixURI("factory"), "imaSpecijalizaciju");


        System.out.println("Tip za masina3:");
        StmtIterator iter = model.listStatements(masina3, RDF.type, (RDFNode) null);
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }

        System.out.println("Selektor za sve Servisere:");
        SimpleSelector serviser = new SimpleSelector(null, RDF.type, serviserClass);
        iter = model.listStatements(serviser);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement().toString());
        }

        System.out.println("Selektor za sve specijalizacije:");
        SimpleSelector specijalizacija = new SimpleSelector(null, imaSpecijalizaciju, (RDFNode) null);
        iter = model.listStatements(specijalizacija);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement().toString());
        }
    }
}
