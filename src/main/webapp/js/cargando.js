// JavaScript (script.js)
window.addEventListener('load', function () {
    const loaderWrapper = document.querySelector('.loader-wrapper');
    const content = document.querySelector('.content');

    // Oculta la animación de carga después de 3 segundos (3000 milisegundos)
    setTimeout(function () {
        loaderWrapper.classList.add('hidden'); // Agrega la clase hidden
        content.style.display = 'block'; // Muestra el contenido de la página
    }, 3000); // Cambia este valor para ajustar la duración en milisegundos
});