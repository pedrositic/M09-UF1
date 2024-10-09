/*
 * A partir d'un abecedari permutat automaticament es xifra i es desxifra un missatge
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Monoalfabetic {

    public static final char[] ABCMIN = "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz".toCharArray();
    public static final int LENABC = ABCMIN.length;

    public static void main(String[] args) {
        char[] abcPermutat = permutaAlfabet();
        System.out.println(xifraMonoAlfa("Holaaa, bon dia!", abcPermutat));
        System.out.println(desxifraMonoAlfa(xifraMonoAlfa("Holaaa, bon dia!", abcPermutat), abcPermutat));
    }

    public static char[] permutaAlfabet() {

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
    
    public static String xifraMonoAlfa(String text, char[] abcPermutat) {
        return xifraText(text, ABCMIN, abcPermutat);
    }

    public static String desxifraMonoAlfa(String text, char[] abcPermutat) {
        return xifraText(text, abcPermutat, ABCMIN);
    }

    private static String xifraText(String text, char[] original, char[] permutat) {
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

    private static int getPosLletra(char[] original, char ch) {
        // Obtenim la posicio de la lletra dins del abecedari
        for (int j = 0; j < LENABC; j++) {
            if (original[j] == ch) {
                return j;
            }
        }
        return -1;
    }

}
