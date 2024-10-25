package iticbcn.xifratge;

/**
 * Polialfabetic
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    public static final char[] ABCMIN = "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz".toCharArray();
    public static final int LENABC = ABCMIN.length;
    public Random rand;
    public char[] abcPermutat;

    public void initRandom(int clau) {
        rand = new Random(clau);
    }

    public void permutaAlfabet() {
        List<Character> abcList = new ArrayList<>();
        for (char ch : ABCMIN) {
            abcList.add(ch);
        }

        Collections.shuffle(abcList, rand);

        abcPermutat = new char[LENABC];

        for (int i = 0; i < LENABC; i++) {
            abcPermutat[i] = abcList.get(i);
        }
    }

    public String xifraPoliAlfa(String text) {
        return xifraText(text, true);
    }

    public String desxifraPoliAlfa(String text) {
        return xifraText(text, false);
    }

    private String xifraText(String text, boolean xifra) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            // Permutem l'alfabet
            permutaAlfabet();

            char ch = text.charAt(i);
            boolean mayus = false;
            // Si es majuscula posem a true i la pasem a minuscula
            if (Character.isUpperCase(ch)) {
                mayus = true;
                ch = Character.toLowerCase(ch);
            }

            // Obtenim la posició de la lletra segons l'operacio que estem fent
            int pos = getPosLletra(xifra ? ABCMIN : abcPermutat, ch);

            // Si no trobem la lletra
            if (pos == -1) {
                sb.append(ch);
            } else {
                // Xifrem la lletra i comprovem si ha de ser majúscula
                char xifrat = xifra ? abcPermutat[pos] : ABCMIN[pos];
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
        clauCorrecta(clau);
        return new TextXifrat(xifraPoliAlfa(msg).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        clauCorrecta(clau);
        return desxifraPoliAlfa(xifrat.toString());
    }

    private void clauCorrecta(String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("Clau de Polialfabétic ha de ser un String");
        }
        try {
            Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
    }
}