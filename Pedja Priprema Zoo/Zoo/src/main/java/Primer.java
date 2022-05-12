import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class Primer {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\wbis_pedja22\\Pedja Priprema Zoo\\ZooRDFS.rdf");

        model.read(in, null);
        model.write(System.out);

        Resource zmija = model.getResource("http://www.singidunum.ac.rs/zoo#zmija");
        Resource sisarClass = model.getResource("http://www.singidunum.ac.rs/zoo#Sisar");
        Property tipIshrane = model.getProperty(model.getNsPrefixURI("zoo"), "tipIshrane");


        System.out.println("Tip za zmije:");
        StmtIterator iter = model.listStatements(zmija, RDF.type, (RDFNode) null);
        while(iter.hasNext()){
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }

        System.out.println("Selektor za sve sisare:");
        SimpleSelector sisari = new SimpleSelector(null, RDF.type, sisarClass);
        iter = model.listStatements(sisari);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

        //FIXME:Ispisuje sve tipove ishrane
        System.out.println("Selektor za sve mesojede:");
        SimpleSelector mesojedi = new SimpleSelector(null, tipIshrane, (RDFNode) null);
        iter = model.listStatements(mesojedi);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }


    }
}
