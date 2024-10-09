
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
}