package Knjizara;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;

public class Knjizara {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\wbis_pedja22\\\\Vezbe7_RDF\\KnjizaraRDF.rdf");

        model.read(in, null);
        model.write(System.out);
    }
}
