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
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int pos = -1;
            boolean mayus = false;
            // Si es majuscula posem a true i la pasem a minuscula
            if (Character.isUpperCase(ch)) {
                mayus = true;
                ch = Character.toLowerCase(ch);
            }
            
            pos = getPosLletra(original, ch, pos);

            // Si no trobem la lletra
            if (pos == -1) {
                sb.append(ch);
                continue;
            }
            // Xifrem la lletra
            ch = permutat[pos];

            // Si era majuscula la convertim a majuscula
            if (mayus) {
                ch = Character.toUpperCase(ch);
            }

            sb.append(ch);
        }

        return sb.toString();
    }

    private static int getPosLletra(char[] original, char ch, int pos) {
        // Obtenim la posicio de la lletra dins del abecedari
        for (int j = 0; j < LENABC; j++) {
            if (original[j] == ch) {
                pos = j;
                break;
            }
        }
        return pos;
    }

}
