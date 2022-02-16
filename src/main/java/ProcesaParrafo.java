import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class ProcesaParrafo extends HTMLEditorKit.ParserCallback {
    private int contador;
    private boolean inParagraph;

    public ProcesaParrafo() {
        contador = 0;
        inParagraph = false;
    }


    @Override
    public void handleText(char[] data, int pos) {
        if (inParagraph) {
            String texto = new String(data);
            HashMap<String, Integer> mapa = new HashMap<>();

            texto = texto.replaceAll("[\\—\\.\\,\\(\\)]", "");
            String[] palabras = texto.split(" ");

            int contadorTotal = 0;

            for (String palabra : palabras) {
                if (!palabra.matches("\\d*")) {
                    contadorTotal++;
                    if (mapa.containsKey(palabra)) {
                        int cuenta = mapa.get(palabra);
                        mapa.put(palabra, cuenta + 1);
                    }
                    else {
                        mapa.put(palabra, 1);
                    }
                }
            }
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
