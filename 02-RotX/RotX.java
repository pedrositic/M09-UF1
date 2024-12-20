import java.util.Scanner;

/**
 * RotX
 * Programa para cifrar un mensaje sumandole X posiciones del abecedario a cada
 * letra
 * 
 * Fer modul que pugui xifrar i desxifrar (boolean dreta)
 * 
 * Fer modul que recorra el abc buscant la lletra i retorna si l'ha trobat, la
 * posicio, sino -1
 * 
 */
public class RotX {

    // Definir las constantes del abecedario en minúsculas y mayúsculas con
    // toCharArray
    public static final char[] ABCMIN = "aàábcçdeèéfghiíïjklmnñoòópqrstuúüvwxyz".toCharArray();
    public static final char[] ABCMAY = "AÀÁBCÇDEÈÉFGHIÍÏJKLMNÑOÒÓPQRSTUÚÜVWXYZ".toCharArray();
    public static final int LENABC = ABCMIN.length;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre de rotacions:\n-> ");
        int numRota = sc.nextInt();
        sc.close();
        System.out.println(xifraRotX("Hola, bona tarda!", numRota));
        System.out.println(desxifraRotX(xifraRotX("Hola, bona tarda!", numRota), numRota));
        forcaBrutaRotx(xifraRotX("Hola, bona tarda!", numRota));

    }

    public static String xifraRotX(String text, int numRota) {
        return rotaX(text, true, numRota);
    }

    public static String desxifraRotX(String text, int numRota) {
        return rotaX(text, false, numRota);
    }

    public static String rotaX(String text, boolean dreta, int numRota) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int letterPos = findLetterPos(ch);
            boolean minu = false;
            // Si no esta en ningun abecedario
            if (letterPos == -1) {
                sb.append(ch);
                continue;
            }
            // Si es minuscula
            if (letterPos >= 100) {
                letterPos -= 100; // Normalizamos el valor
                minu = true;
            }

            // Si estem xifrant
            if (dreta) {
                if ((letterPos + numRota) >= LENABC) {
                    letterPos = (letterPos + numRota) - LENABC;
                } else {
                    letterPos += numRota;
                }
            }
            // Si estem desxifrant
            else {
                if ((letterPos - numRota) < 0) {
                    letterPos = (letterPos - numRota) + LENABC;
                } else {
                    letterPos -= numRota;
                }
            }

            ch = minu ? ABCMIN[letterPos] : ABCMAY[letterPos];

            sb.append(ch);
        }

        // Convertim el buffer en String
        return sb.toString();
    }

    public static int findLetterPos(char ch) {
        for (int i = 0; i < LENABC; i++) {
            if (ch == ABCMAY[i]) {
                return i;
            }
            if (ch == ABCMIN[i]) {
                return i + 100;
            }
        }
        // Si no la troba retorna -1
        return -1;
    }

    public static void forcaBrutaRotx(String text) {
        System.err.println("Se listan " + LENABC + " posibles coincidencias:");
        for (int i = 0; i<LENABC; i++){
            System.out.println(i+1 + " Rot ->\t" + xifraRotX(text, i));
        }
    }

}