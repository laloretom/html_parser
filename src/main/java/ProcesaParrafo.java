import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class ProcesaParrafo extends HTMLEditorKit.ParserCallback {
    private int contador;
    private boolean inParagraph;
    private Set<String> stopWords = null;

    public ProcesaParrafo() {
        contador = 0;
        inParagraph = false;
        stopWords = this.stopWord();
    }


    public Set<String> stopWord(){
        TreeSet<String> set = new TreeSet<>();
        try {
            FileReader fl = new FileReader("stop-word-list.txt");
            BufferedReader in = new BufferedReader(fl);
            String linea = null;
            while ((linea = in.readLine()) != null) {
                set.add(linea.trim());
            }
            in.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return set;
    }

    @Override
    public void handleText(char[] data, int pos) {
        if (inParagraph) {
            String texto = new String(data).toLowerCase();
            HashMap<String, Integer> mapa = new HashMap<>();

            texto = texto.replaceAll("[\\—\\.\\,\\(\\)]", "");
            String[] palabras = texto.split(" ");

            int contadorTotal = 0;

            for (String palabra : palabras) {
                if (!palabra.matches("\\d*")) {
                    contadorTotal++;

                    if (!stopWords.contains(palabra)){
                        if (mapa.containsKey(palabra))
                        {
                            int cuenta = mapa.get(palabra.toLowerCase());
                            mapa.put(palabra, cuenta + 1);
                        } // fin de if
                        else
                        {
                            mapa.put(palabra, 1);
                        }
                    }
                }
            }
            //System.out.println(mapa);
            System.out.printf("El parafo %d tiene: %7d palabras, %3d Diferentes%n", contador+1, contadorTotal,mapa.size());
        }
    }

    @Override
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.P) {
            inParagraph = true;
        }
    }

    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        if (t == HTML.Tag.P) {
            inParagraph = false;
            contador++;
        }
        if (t == HTML.Tag.BODY) {
            System.out.printf("Total de parrafos en documento: %d%n", contador);
        }
    }

}
