
/**
 * Polialfabetic
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Polialfabetic {

    public static final char[] ABCMIN = "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz".toCharArray();
    public static final int LENABC = ABCMIN.length;
    public static Random rand;
    public static char[] abcPermutat;

    public static void main(String[] args) {
        int clauSecreta = 220105;
        String msgs[] = { "Test 01 àrbritre, coixí, Perímetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats[] = new String[msgs.length];
        System.out.println("Xifratge: \n --------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("Desxifratge: \n -----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

    public static void initRandom(int clau) {
        rand = new Random(clau);
    }

    public static void permutaAlfabet() {
        List<Character> abcList = new ArrayList<>();
        for (char ch : ABCMIN) {
            abcList.add(ch);
        }

        Collections.shuffle(abcList, rand);

        char[] abc = new char[LENABC];

        for (int i = 0; i < LENABC; i++) {
            abc[i] = abcList.get(i);
        }
        abcPermutat = abc;
    }

    public static String xifraPoliAlfa(String text) {
        return xifraText(text, true);
    }

    public static String desxifraPoliAlfa(String text) {
        return xifraText(text, false);
    }

    private static String xifraText(String text, boolean xifra) {
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