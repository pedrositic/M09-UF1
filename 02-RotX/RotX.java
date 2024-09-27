import java.util.Arrays;
import java.util.Scanner;

/**
 * RotX
 * Programa para cifrar un mensaje sumandole X posiciones del abecedario a cada
 * letra
 * 
 * Fer modul que pugui xifrar i desxifrar (boolean dreta)
 * 
 * Fer modul que recorra el abc buscant la lletra i retorna si l'ha trobat, la posicio, sino -1
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
        System.out.println(numRota);
    }

    public static String rotaX(String text, boolean dreta) {
        StringBuffer sb = new StringBuffer();


        sb.append(ch);

        // Convertim el buffer en String
        sb.toString();
    }

}