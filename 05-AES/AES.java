import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";

    public static void main(String[] args) {
        String msgs[] = { "Lorem ipsum dicet",

                "Hola Andrés cómo está tu cuñado",
                "Àgora ïlla Ôtto" };
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);

            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());

            }
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau)
            throws Exception {
        // Obtenir els bytes de l’String
        byte[] bites = msg.getBytes("UTF-8");

        // Genera IvParameterSpec
        // --- Creem el SecureRandom
        SecureRandom sr = new SecureRandom();
        // --- Omplim l'array amb bytes aleatoris
        sr.nextBytes(iv);
        // --- Creem la instancia del IV amb l'array ple
        IvParameterSpec ivParaSpec = new IvParameterSpec(iv);

        // Genera hash
        // --- Creem una instància de MessageDigest amb l'algorisme que utilitzem
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        // --- Obtenim el hash de la clau (en bytes)
        byte[] clauHash = md.digest(clau.getBytes("UTF-8"));
        // --- Guardem el hash de la clau en una SecretKeySpec
        SecretKeySpec sks = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

        // Encrypt.
        // - Inicialitzacio del xifrador AES en mode CBC
        // --- Creem una instancia de Cipher amb el Format de AES adient
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        // --- Inicialitzem el xifrador en mode xifrat pasant-li la SecretKey i el IV
        cipher.init(Cipher.ENCRYPT_MODE, sks, ivParaSpec);
        // - Xifrem el missatge
        // -- Convertim el missatge a bytes i l'encriptem
        byte[] cipherText = cipher.doFinal(bites);

        // Combinar IV i part xifrada.
        // --- Creem l'array on anira la combinació
        byte[] combi = new byte[iv.length + cipherText.length];
        // --- Copiem el iv a l'inici del array
        System.arraycopy(iv, 0, combi, 0, iv.length);
        // --- Copiem el text xifrat després de l'IV
        System.arraycopy(cipherText, 0, combi, iv.length, cipherText.length);

        // return iv+msgxifrat
        return combi;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau)
            throws Exception {
        // Extreure l'IV
        // --- L'IV es troba en els primers 16 bytes de l'array
        byte[] iv = Arrays.copyOfRange(bIvIMsgXifrat, 0, 16);
        // --- Creem una instància d'IvParameterSpec utilitzant l'IV extret
        IvParameterSpec ivParaSpec = new IvParameterSpec(iv);
        
        // Extreure la part xifrada
        // --- El text xifrat es troba després de l'IV, a partir del byte 16
        byte[] ciphertext = Arrays.copyOfRange(bIvIMsgXifrat, 16, bIvIMsgXifrat.length);

        // Fer hash de la clau
        // --- Creem una instància de MessageDigest amb l'algorisme SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // --- Obtenim el hash de la clau en bytes utilitzant SHA-256
        byte[] clauHash = md.digest(clau.getBytes(StandardCharsets.UTF_8));

        // Desxifra el text xifrat
        // - Inicialitza el desxifrador AES en mode CBC
        // --- Creem una instància de Cipher per AES-CBC amb padding PKCS5
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        // --- Inicialitzem el desxifrador en mode desxifrat amb la clau i l'IV
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(clauHash, "AES"), ivParaSpec);
        // --- Desencriptem el text xifrat per obtenir el missatge original
        byte[] plaintext = cipher.doFinal(ciphertext);

        // Retornem el missatge desxifrat convertit a string
        return new String(plaintext, StandardCharsets.UTF_8);
    }

}