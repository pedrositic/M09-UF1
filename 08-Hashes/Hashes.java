import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    public int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {

        // Obtenim una instancia amb l'algorisme SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());

        // Obtenim el hash
        byte[] hash = md.digest(pw.getBytes(StandardCharsets.UTF_8));

        // Creem el HexFormat i formatejem la String
        HexFormat hex = HexFormat.of();
        String hashString = hex.formatHex(hash);

        return hashString;
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        // Creem el HexFormat i formatejem la String
        HexFormat hex = HexFormat.of();
        String hashString = hex.formatHex(hash);

        return hashString;

    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        npass = 0;

        char[] charset = "abcdefABCDEF1234567890!".toCharArray();

        // Cada bucle representa una posició a la contrassenya
        // Cada bucle genera totes les posibles contrassenyes per la seva longitud

        for (char one : charset) { // a
            char[] chars = new char[6];
            chars[0] = one;

            // Es crea un nou array de chars que nomes conté els caracters
            String result = testPw(chars, 0, alg, hash, salt);
            if (result != null)
                return result;

            for (char two : charset) { // aa ab
                chars[1] = two;
                result = testPw(chars, 1, alg, hash, salt);
                if (result != null)
                    return result;

                for (char three : charset) { // aaa aba
                    chars[2] = three;
                    result = testPw(chars, 2, alg, hash, salt);
                    if (result != null)
                        return result;

                    for (char four : charset) { // aaaa abaa
                        chars[3] = four;
                        result = testPw(chars, 3, alg, hash, salt);
                        if (result != null)
                            return result;

                        for (char five : charset) { // aaaaa abaaa
                            chars[4] = five;
                            result = testPw(chars, 4, alg, hash, salt);
                            if (result != null)
                                return result;

                            for (char six : charset) { // aaaaaa abaaaa
                                chars[5] = six;
                                result = testPw(chars, 5, alg, hash, salt);
                                if (result != null)
                                    return result;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private String testPw(char[] pw, int len, String alg, String hash, String salt) throws Exception {
        npass++;

        String pwStr = stringer(pw, len);
        System.out.println(pwStr);
        String generatedHash = alg.equals("SHA-512") ? getSHA512AmbSalt(pwStr, salt) : getPBKDF2AmbSalt(pwStr, salt);

        if (generatedHash.equals(hash)) {
            return pwStr;
        }
        return null;
    }

    private String stringer(char[] pw, int len) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < len; i++) {
            result.append(pw[i]);
        }

        return result.toString();
    }

    public String getInterval(long t1, long t2) {
        long interval = t2 - t1;

        long days = interval / 86400000; // 1 día = 86400000 milisegundos
        interval %= 86400000;
        long hours = interval / 3600000; // 1 hora = 3600000 milisegundos
        interval %= 3600000;
        long minutes = interval / 60000; // 1 minuto = 60000 milisegundos
        interval %= 60000;
        long seconds = interval / 1000; // 1 segundo = 1000 milisegundos
        long milliseconds = interval % 1000; // Milisegundos restantes

        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                days, hours, minutes, seconds, milliseconds);
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }
}
