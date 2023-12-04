package ws; // Declaración del paquete ws

import java.io.IOException; // Importación de la clase IOException para manejar excepciones de entrada/salida
import java.math.BigInteger; // Importación de la clase BigInteger para manipulación de números grandes
import java.security.SecureRandom; // Importación de la clase SecureRandom para generación segura de números aleatorios
import java.util.Base64; // Importación de la clase Base64 para codificación y decodificación de datos en formato Base64
import javax.websocket.EncodeException; // Importación de la clase EncodeException para manejar excepciones de codificación
import javax.websocket.OnMessage; // Importación de la anotación OnMessage para indicar que el método maneja mensajes WebSocket
import javax.websocket.Session; // Importación de la clase Session para representar la conexión WebSocket
import javax.websocket.server.ServerEndpoint; // Importación de la anotación ServerEndpoint para especificar la URI del punto de conexión del servidor WebSocket

@ServerEndpoint("/chatroom") // Especificación del punto de conexión del servidor WebSocket
public class ChatEndpoint {

    private static final BigInteger P = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1"
            + "29024E088A67CC74020BBEA63B139B22514A08798E3404DD"
            + "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245"
            + "E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF", 16); // Declaración y asignación de la constante P como un número grande en formato hexadecimal
    private static final BigInteger G = BigInteger.valueOf(2); // Declaración y asignación de la constante G como el valor 2

    private BigInteger privateKey; // Declaración de la variable privateKey para almacenar la clave privada
    private BigInteger publicKey; // Declaración de la variable publicKey para almacenar la clave pública
    private BigInteger sharedSecret; // Declaración de la variable sharedSecret para almacenar el secreto compartido

    @OnMessage // Anotación que indica que el método message manejará mensajes WebSocket
    public void message(String message, Session client) throws IOException, EncodeException {
        if (message.equals("INIT_DH")) { // Verifica si el mensaje es "INIT_DH"
            generateKeys(); // Llama al método para generar claves
            String publicKeyMessage = "PUBLIC_KEY:" + Base64.getEncoder().encodeToString(publicKey.toByteArray()); // Construye el mensaje de clave pública codificado en Base64
            client.getBasicRemote().sendText(publicKeyMessage); // Envía el mensaje de clave pública al cliente
        } else if (message.startsWith("PUBLIC_KEY:")) { // Verifica si el mensaje comienza con "PUBLIC_KEY:"
            String base64Key = message.substring("PUBLIC_KEY:".length()); // Extrae la clave pública codificada en Base64 del mensaje
            BigInteger clientPublicKey = new BigInteger(Base64.getDecoder().decode(base64Key)); // Decodifica la clave pública del cliente
            sharedSecret = clientPublicKey.modPow(privateKey, P); // Calcula el secreto compartido usando la clave privada y la clave pública del cliente
            System.out.println("Shared Secret: " + sharedSecret); // Imprime el secreto compartido
        } else { // Si el mensaje no es "INIT_DH" ni comienza con "PUBLIC_KEY:"
            for (Session openSession : client.getOpenSessions()) { // Itera sobre todas las sesiones abiertas
                openSession.getBasicRemote().sendText(message); // Reenvía el mensaje a todas las sesiones abiertas
            }
        }
    }

    private void generateKeys() { // Método para generar claves
        SecureRandom random = new SecureRandom(); // Creación de un objeto SecureRandom para generación segura de números aleatorios
        privateKey = new BigInteger(2048, random); // Generación de una clave privada de 2048 bits
        publicKey = G.modPow(privateKey, P); // Cálculo de la clave pública usando el generador y la constante P
    }
}
