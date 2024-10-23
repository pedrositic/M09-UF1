package iticbcn.xifratge;

/*
 * A partir d'un abecedari permutat automaticament es xifra i es desxifra un missatge
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class XifradorMonoalfabetic implements Xifrador {

    public static final char[] ABCMIN = "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz".toCharArray();
    public static final int LENABC = ABCMIN.length;
    public static char[] abcPermutat;

    public XifradorMonoalfabetic() {
        abcPermutat = permutaAlfabet();
    }

    public char[] permutaAlfabet() {

        List<Character> abcList = new ArrayList<>();
        for (char ch : ABCMIN) {
            abcList.add(ch);
        }

        Collections.shuffle(abcList);

        char[] abc = new char[LENABC];

        for (int i = 0; i < LENABC; i++) {
            abc[i] = abcList.get(i);
        }
        return abc;
    }

    public String xifraMonoAlfa(String text, char[] abcPermutat) {
        return xifraText(text, ABCMIN, abcPermutat);
    }

    public String desxifraMonoAlfa(String text, char[] abcPermutat) {
        return xifraText(text, abcPermutat, ABCMIN);
    }

    private String xifraText(String text, char[] original, char[] permutat) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            boolean mayus = false;
            // Si es majuscula posem a true i la pasem a minuscula
            if (Character.isUpperCase(ch)) {
                mayus = true;
                ch = Character.toLowerCase(ch);
            }

            int pos = getPosLletra(original, ch);

            // Si no trobem la lletra
            if (pos == -1) {
                sb.append(ch);
            } else {
                // Xifrem la lletra i comprovem si ha de ser majúscula
                char xifrat = permutat[pos];
                sb.append(mayus ? Character.toUpperCase(xifrat) : xifrat);
            }
        }
        return sb.toString();
    }

    private int getPosLletra(char[] original, char ch) {
        // Obtenim la posicio de la lletra dins del abecedari
        for (int j = 0; j < LENABC; j++) {
            if (original[j] == ch) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return new TextXifrat(xifraMonoAlfa(msg, abcPermutat).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return desxifraMonoAlfa(xifrat.toString(), abcPermutat);
    }

}
