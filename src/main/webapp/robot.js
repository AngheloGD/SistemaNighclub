/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    // Muestra la imagen del perrito y reproduce el audio al cargar la página
    showPuppyAndPlayAudio();

    // Oculta la imagen del perrito después de 4 segundos
    setTimeout(function () {
        hidePuppy();
    }, 4000);
});

function showPuppyAndPlayAudio() {
    var puppyContainer = document.getElementById('puppyContainer');
    var welcomeAudio = document.getElementById('welcomeAudio');

    // Muestra la imagen del perrito
    puppyContainer.style.display = 'block';

    // Reproduce el audio
    welcomeAudio.play();
}

function hidePuppy() {
    var puppyContainer = document.getElementById('puppyContainer');

    // Oculta la imagen del perrito
    puppyContainer.style.display = 'none';
}
