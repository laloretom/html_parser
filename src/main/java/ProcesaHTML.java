import javax.swing.text.html.HTMLEditorKit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class ProcesaHTML {

    public static final Logger LOG = Logger.getLogger( ProcesaHTML.class.getName() );

    public static void main(String args[]) {

        if( args.length == 0 ) {
            LOG.severe("Falto incluir documento HTML");
            System.exit(1);
        }

        FileReader fileReader = null;

        try {
            fileReader = new FileReader( args[0] );
        } catch (FileNotFoundException e) {
            LOG.severe("No se puede abrr documento HTML");
            System.exit(2);
        }

        HTMLParser p  = new HTMLParser();

        HTMLEditorKit.Parser procesador = p.getParser();

        try {
            // procesador.parse( fileReader, new ManejadorEtiquetas(), true);
            procesador.parse( fileReader, new ProcesaParrafo(), true);
        } catch (IOException e) {
            LOG.severe("No se puede leer documento HTML");
            System.exit(2);
        }
    }
}
