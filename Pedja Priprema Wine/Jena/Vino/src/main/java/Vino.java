import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.InputStream;

public class Vino {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\wbis_pedja22\\Pedja Priprema Wine\\WineRDFS.rdf");

        model.read(in, null);
        model.write(System.out);

        Resource brie = model.getResource("http://www.singidunum.ac.rs/wine#brie");
        Resource osobljeClass = model.getResource("http://www.singidunum.ac.rs/wine#Osoblje");
        Resource vinoClass = model.getResource("http://www.singidunum.ac.rs/wine#Vino");
        Property imaCenu = model.getProperty(model.getNsPrefixURI("wine"), "imaCenu");


        System.out.println("Tip za brie:");
        StmtIterator iter = model.listStatements(brie, RDF.type, (RDFNode) null);
        while(iter.hasNext()){
            Statement s = iter.nextStatement();
            System.out.println(s.toString());
        }


        System.out.println("Selektor za sve zaposlene:");
        SimpleSelector zaposleni = new SimpleSelector(null, RDF.type, osobljeClass);
        iter = model.listStatements(zaposleni);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }


        System.out.println("Selektor za sva vina:");
        SimpleSelector vina = new SimpleSelector(null, RDF.type, vinoClass);
        iter = model.listStatements(vina);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }


        System.out.println("Selektor za skuplje od 41:");
        SimpleSelector skuplja = new SimpleSelector(null, imaCenu, (RDFNode) null)
        {
            public boolean selects(Statement s){
                return s.getInt() > 41;
            }
        };
        iter = model.listStatements(skuplja);
        while(iter.hasNext()){
            System.out.println(iter.nextStatement().toString());
        }
    }
}
