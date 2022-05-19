import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class Bolnica {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\wbis_pedja22\\PredragRadak_II_Kolokvijum\\BolnicaRDFS.rdf");

        model.read(in, null);
        model.write(System.out);

        Resource hirurg5 = model.getResource("http://www.singidunum.ac.rs/bolnica#hirurg5");
        Resource doktorClass = model.getResource("http://www.singidunum.ac.rs/bolnica#Doktor");
        Property imaDijagnozu = model.getProperty(model.getNsPrefixURI("bolnica"), "imaDijagnozu");




        System.out.println("Tip za hirurg5:");
        StmtIterator iter = model.listStatements(hirurg5, RDF.type, (RDFNode) null);
        while(iter.hasNext()){
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }

        System.out.println("Selektor za sve doktore:");
        SimpleSelector doktor = new SimpleSelector(null, RDF.type, doktorClass);
        iter = model.listStatements(doktor);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

       System.out.println("Selektor za sve dijagnoze pacijenta:");
        SimpleSelector dijagnoza = new SimpleSelector(null, imaDijagnozu, (RDFNode) null);
        iter = model.listStatements(dijagnoza);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }



    }
}
