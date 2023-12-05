// Variables globales
var username;  // Almacena el nombre de usuario actual
var chatRoomField = document.getElementById("chatRoomField");  // Referencia al elemento del campo de chat en el DOM
var sendField = document.getElementById("sendField");  // Referencia al elemento del campo de envío en el DOM
var sendButton = document.getElementById("sendButton");  // Referencia al botón de envío en el DOM
var websocket = new WebSocket("ws://192.168.1.43:8080/SistemaNightclub/chatroom");  // Creación de un objeto WebSocket para la comunicación
var aesKey;  // Almacena la clave AES para cifrar y descifrar mensajes

// Función para generar una clave AES (solo con fines demostrativos)
function generateAESKey() {
    return "clavegenerada";
}

// Función para cifrar un mensaje utilizando AES
function encryptMessage(username, message, key) {
    var combinedMessage = username + ": " + message;  // Combina el nombre de usuario y el mensaje
    var encrypted = CryptoJS.AES.encrypt(combinedMessage, key);  // Cifra el mensaje combinado con la clave AES
    return encrypted.toString();  // Convierte el mensaje cifrado a una cadena para su envío
}

// Función para descifrar un mensaje cifrado con AES
function decryptMessage(encryptedMessage, key) {
    var decrypted = CryptoJS.AES.decrypt(encryptedMessage, key);  // Descifra el mensaje cifrado con la clave AES
    return decrypted.toString(CryptoJS.enc.Utf8);  // Convierte el mensaje descifrado a texto plano
}

// Agrega un evento para el botón de envío que detecta la tecla "Enter" y llama a la función send_message
sendField.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        send_message();
    }
});

// Evento que se dispara cuando se recibe un mensaje a través del WebSocket
websocket.onmessage = function (evt) {
    console.log("Mensaje cifrado recibido:", evt.data);

    // Intenta descifrar el mensaje
    var decryptedMessage = decryptMessage(evt.data, aesKey);

    if (decryptedMessage.startsWith("AES_KEY:")) {
        // Este mensaje contiene la clave AES
        var base64Key = decryptedMessage.substring("AES_KEY:".length);
        aesKey = atob(base64Key);  // Decodificar la clave desde base64
        console.log("Clave AES recibida: " + aesKey);
    } else {
        // El mensaje no es una clave AES, procesar el contenido del mensaje
        if (decryptedMessage.trim() !== "") {
            // Procesa y muestra el mensaje en la interfaz de usuario
            processAndDisplayMessage(decryptedMessage);
        }
    }
};

// Función para procesar y mostrar un mensaje en la interfaz de usuario
function processAndDisplayMessage(decryptedMessage) {
    // Verifica si el mensaje proviene del usuario actual y actualiza la interfaz de usuario
    var currentContent = chatRoomField.innerHTML;
    var messageArray = decryptedMessage.split(":");
    var senderUsername = messageArray[0];
    var messageContent = messageArray.slice(1).join(":"); // Reunir el resto del array como contenido del mensaje
    var messageClass = username === senderUsername ? "me" : "other";

    // Verifica si el mensaje proviene del usuario actual
    if (messageClass === "me") {
        // Si es el usuario actual, solo muestra el contenido del mensaje
        chatRoomField.innerHTML = currentContent + '<div class="chat-message ' + messageClass + '"><div class="chat-message-content">' + messageContent + '</div></div>';
    } else {
        // Si es otro usuario, muestra el nombre de usuario y el contenido del mensaje
        chatRoomField.innerHTML = currentContent + '<div class="chat-message ' + messageClass + '"><div class="chat-message-content">' + senderUsername + ': ' + messageContent + '</div></div>';
    }

    chatRoomField.scrollTop = chatRoomField.scrollHeight;
}

// Función que se llama al unirse al chat
function join() {
    // Muestra la animación de carga
    $(".loading-image").show();

    // Después de 2 segundos, oculta la animación y muestra los elementos del chat con la animación de desvanecer
    setTimeout(function () {
        $(".loading-image").hide();
        $("#chatRoomField, #sendField, #sendButton").addClass("fade-in").removeClass("hidden");
    }, 3000);

    // Configuración inicial al unirse al chat, incluyendo el envío de la clave AES
    username = document.getElementById("newUserField").value;
    document.getElementById("newUserField").disabled = true;
    document.getElementById("newUserButton").disabled = true;
    chatRoomField.disabled = false;
    sendField.disabled = false;
    sendButton.disabled = false;

    // Genera una clave AES y la envía al servidor a través del WebSocket
    aesKey = generateAESKey();
    websocket.send("AES_KEY:" + btoa(aesKey));

    // Muestra un mensaje indicando que el usuario se ha unido al chat
    var joinMessage = "* " + username + " se ha unido!!";
    chatRoomField.innerHTML += '<div class="chat-message"><div class="chat-message-content">' + joinMessage + '</div></div>';

    // Envía un mensaje al servidor indicando que el usuario se ha unido
    websocket.send(username + " se ha unido!!");
}

// Función para enviar un mensaje al chat
function send_message() {
    // Obtiene el contenido del mensaje del campo de envío, lo cifra y lo envía mediante WebSocket
    var messageContent = sendField.value;
    var encryptedMessage = encryptMessage(username, messageContent, aesKey);
    websocket.send(encryptedMessage);

    // Limpia el campo de envío después de enviar el mensaje
    sendField.value = "";
}

// Función para redirigir a principal.html
function goToPrincipalPage() {
    window.location.href = 'Principal.html';
}

// Evento que se dispara cuando el contenido del DOM ha sido cargado
document.addEventListener("DOMContentLoaded", function () {
    var storedUsername = localStorage.getItem('username');

    if (storedUsername) {
        // Inserta el nombre de usuario almacenado en el campo de entrada si existe
        document.getElementById("newUserField").value = storedUsername;
    }
});