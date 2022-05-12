import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class CdApp {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\Vezbe11_PripremaCDProdavnica\\CdProdvnicaRDFS.rdf");

        model.read(in, null);
        model.write(System.out);

        Resource svetlana = model.getResource("http://www.singidunum.ac.rs/shop#svetlana");
        Resource osobljeClass = model.getResource("http://www.singidunum.ac.rs/shop#Osoblje");
        Resource proizvodClass = model.getResource("http://www.singidunum.ac.rs/shop#Proizvod");
        Resource tehnickoOsobljeClass = model.getResource("http://www.singidunum.ac.rs/shop#TehnickaPodrska");
        Property imaCenu = model.getProperty(model.getNsPrefixURI("shop"), "imaCenu");

        System.out.println("Tip za svetlana:");
        StmtIterator iter = model.listStatements(svetlana, RDF.type, (RDFNode) null);
        while(iter.hasNext()){
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }

        System.out.println("Selektor za sve proizvode:");
        SimpleSelector proizvod = new SimpleSelector(null, RDF.type, proizvodClass);
        iter = model.listStatements(proizvod);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

        System.out.println("Selektor za svo tehnicko osoblje:");
        SimpleSelector tehhnicari = new SimpleSelector(null, RDF.type, tehnickoOsobljeClass);
        iter = model.listStatements(tehhnicari);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

        System.out.println("Selektor za skuplje od 60:");
        SimpleSelector skuplja = new SimpleSelector(null, imaCenu, (RDFNode) null)
        {
            public boolean selects(Statement s){
                return s.getInt() > 60;
            }
        };
        iter = model.listStatements(skuplja);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }

    }
}
