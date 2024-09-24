import java.util.Arrays;

/**
 * Rot13
 * Programa para cifrar un mensaje sumandole 13 posiciones del abecedario a cada letra
 */
public class Rot13 {

    public static char[] abcMin = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    public static char[] abcMay = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static void main(String[] args) {
        System.out.println(xifraRot13("Bona tarda, em dic Pau!"));
        System.out.println(desxifraRot13("Oban gneqn, rz qvp Cnh!"));
    }

    public static String xifraRot13(String text) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int posList = 0;
            char newChar = ' ';

            // Saltar espacio y signos de puntuacion
            if (!Character.isAlphabetic(ch)) {
                result += ch;
                continue;
            }

            // Distinguir entre minúsculas y mayúsculas
            if (Character.isUpperCase(ch)) {
                // Obtener posicion de la letra en el array
                posList = getPos(ch, abcMay);
                // Cambiarla por la cifrada
                newChar = changeCh(posList, abcMay, true);
            } else if (Character.isLowerCase(ch)) {
                posList = getPos(ch, abcMin);
                newChar = changeCh(posList, abcMin, true);
            }

            result += newChar;
        }

        return result;
    }

    public static String desxifraRot13(String text) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int posList = 0;
            char newChar = ' ';

            // Saltar espacio y signos de puntuacion
            if (!Character.isAlphabetic(ch)) {
                result += ch;
                continue;
            }

            // Distinguir entre minúsculas y mayúsculas
            if (Character.isUpperCase(ch)) {
                // Obtener posicion de la letra en el array
                posList = getPos(ch, abcMay);
                // Cambiarla por la cifrada
                newChar = changeCh(posList, abcMay, false);
            } else if (Character.isLowerCase(ch)) {
                posList = getPos(ch, abcMin);
                newChar = changeCh(posList, abcMin, false);
            }

            result += newChar;
        }

        return result;
    }

    // Obtener la posición de la letra actual en el array
    public static int getPos(char ch, char[] array) {
        // Recorremos el array hasta encontrar la letra
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ch) {
                return i;
            }
        }
        return -1; // Si no se encuentra el carácter, devuelve -1
    }

    // Cambio de letra
    public static char changeCh(int posList, char[] array, boolean mode) {
        // boolean == true -> xifrar
        if (mode) {
            // Si al sumarle 13 se pasa del abecedario
            // le restamos 26 para obtener la letra equivalente
            if ((posList + 13) >= 26) {
                posList = (posList + 13) - 26;
            } else {
                posList += 13;
            }
        }
        // boolean == false -> desxifrar
        else {
            // Si al restarle 13 se pasa del abecedario
            // le sumamos 26 para obtener la letra equivalente
            if ((posList - 13) < 0) {
                posList = (posList - 13) + 26;
            } else {
                posList -= 13;
            }
        }
        // Obtenemos del array correspondiente la letra adecuada
        return array[posList];
    }
}
