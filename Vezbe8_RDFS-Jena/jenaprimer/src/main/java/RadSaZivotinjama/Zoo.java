package RadSaZivotinjama;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class Zoo {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\Vezbe7_RDF\\radSaZivotinjamaRDF.rdf");

        model.read(in, null);
        model.write(System.out);
        Resource zmija = model.getResource("http://www.singidunum.ac.rs/zoo#zmija");
        Resource sisariClass = model.getResource("http://www.singidunum.ac.rs/zoo#sisari");
        Resource tipIshraneClass = model.getResource("http://www.singidunum.ac.rs/zoo#tipIshrane");


        System.out.println("Tip za zmije:");
        StmtIterator iter = model.listStatements(zmija, RDF.type, (RDFNode) null);
        while(iter.hasNext()){
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }


        System.out.println("Selektor za sve sisare:");
        SimpleSelector sisari = new SimpleSelector(null, RDF.type, sisariClass);
        iter = model.listStatements(sisari);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }


        System.out.println("Selektor za sve mesojede:");
        SimpleSelector tipIshrane = new SimpleSelector(null, RDF.type, tipIshraneClass);
        iter = model.listStatements(tipIshrane);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }


    }
}
