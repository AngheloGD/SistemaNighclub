/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Protocolo;

/**
 *
 * @author ANGHELO
 */
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DHKeyExchange {

    private KeyPair keyPair;
    private PublicKey clientPublicKey;

    public DHKeyExchange() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    public void setClientPublicKey(String base64Key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64Key));
            clientPublicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String computeSharedSecret() {
        try {
            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(keyPair.getPrivate());
            keyAgreement.doPhase(clientPublicKey, true);
            SecretKey sharedSecret = keyAgreement.generateSecret("AES");
            return Base64.getEncoder().encodeToString(sharedSecret.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
