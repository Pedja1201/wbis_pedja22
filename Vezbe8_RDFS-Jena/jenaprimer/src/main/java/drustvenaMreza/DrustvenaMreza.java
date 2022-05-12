package drustvenaMreza;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;

public class DrustvenaMreza {
    public static void main(String[] args) {
        //primer učitavanja modela
        Model model = ModelFactory.createDefaultModel();
        InputStream in = RDFDataMgr.open("D:\\Singidunum III\\Letnji semestar 2021_22\\Veb bazirani info sistemi\\Vezbe7_RDF\\drustvenaMrezaRDF.rdf");

        model.read(in, null);
        model.write(System.out);

        //kretanje kroz model
        Resource osobaUMrezi = model.getResource("http://www.singidunum.ac.rs/osobaUMrezi/pedja");
        Property propOsoba= model.getProperty("http://www.singidunum.ac.rs/osoba#", "slika");
        String osobaNS = model.getNsPrefixURI("osoba");
        Property kontaktIme = model.getProperty(osobaNS, "ime");

        //getObject nam može vratiti ili literal ili resurs, zato moramo eksplicitno da castujemo
        Resource slika = (Resource) osobaUMrezi.getProperty(propOsoba).getObject();
        System.out.println(slika.toString());

    }
}
