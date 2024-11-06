package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception {
        // Inicialitzem el generador per a que ho faci amb RSA
        KeyPairGenerator gene = KeyPairGenerator.getInstance("RSA");
        gene.initialize(2048);
        // Generem el parell de claus
        KeyPair pair = gene.generateKeyPair();
        return pair;
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
        // Declarem un xifrador amb RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // Inicialitzem el cipher en mode xifratge amb la clau publica
        cipher.init(Cipher.ENCRYPT_MODE, clauPublica);

        // Xifrem el missatge
        byte[] missXifr = cipher.doFinal(msg.getBytes());

        return missXifr;
    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada) throws Exception {
        // Declarem un xifrador amb RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // Inicialitzem el cipher en mode desxifratge amb la clau privada
        cipher.init(Cipher.DECRYPT_MODE, ClauPrivada);

        // Desxifrem el missatge
        byte[] missDesxifr = cipher.doFinal(msgXifrat);

        // Convertim a String
        String missFinal = new String(missDesxifr, StandardCharsets.UTF_8);

        return missFinal;

    }
}
