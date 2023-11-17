<?php
$conexion = mysqli_connect('localhost', 'root', '', 'nightclubb');
?>

<html>
    <head>
        <title>Mostrar Datos y Generar Reporte PDF</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/Reporte.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <?php
        // Verificar si el token está almacenado en la cookie
        if (!isset($_COOKIE['token'])) {
            // Si no hay token, muestra un mensaje de error y no carga los datos ni genera el informe
            echo '<div style="color: red; font-size: 18px; margin: 20px;">Error: Inicia sesión para acceder a esta página.</div>';
        } else {
            // Token presente, mostrar los datos y el botón para generar el informe
            ?>
            <table>
                <thead>
                    <tr>
                        <th>Codigo</th>
                        <th>Dia de la Cita</th>
                        <th>Nombre de la Trabajadora</th>
                        <th>Tipo de Servicio</th>
                        <th>Cliente</th>
                    </tr>
                </thead>
                <?php
                $sql = "SELECT * from cita";
                $result = mysqli_query($conexion, $sql);

                while ($mostrar = mysqli_fetch_array($result)) {
                    ?>
                    <tbody>
                        <tr>
                            <td><?php echo $mostrar['codicita'] ?></td>
                            <td><?php echo $mostrar['diacita'] ?></td>
                            <td><?php echo $mostrar['emplcita'] ?></td>
                            <td><?php echo $mostrar['servcita'] ?></td>
                            <td><?php echo $mostrar['codiUsua'] ?></td>
                        </tr>
                    </tbody>
                    <?php
                }
                ?>
            </table>
            <button type="button" id="generar-reporte" onclick="generarReporte()">GENERAR REPORTE</button>

            <div id="footer">
                Fecha y hora de generación: <span id="fecha-hora"></span>
            </div>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.13/jspdf.plugin.autotable.js"></script>
            <script>
                document.getElementById("generar-reporte").addEventListener("click", function () {
                    generarReporte();
                });

                function generarReporte() {
                    // Verificar si el token está presente antes de generar el informe
                    if (!getCookie('token')) {
                        alert('Error: Inicia sesión para generar el informe.');
                        return;
                    }

                    console.log("Generando reporte PDF...");

                    // Tu código para generar el reporte PDF aquíi
                    var pdf = new jsPDF();
                    pdf.autoTable({html: 'table'});
                    pdf.save("reporte.pdf");
                }

                function getCookie(name) {
                    const nameEQ = name + "=";
                    const cookies = document.cookie.split(';');
                    for (let i = 0; i < cookies.length; i++) {
                        let cookie = cookies[i];
                        while (cookie.charAt(0) === ' ') {
                            cookie = cookie.substring(1, cookie.length);
                        }
                        if (cookie.indexOf(nameEQ) === 0) {
                            return cookie.substring(nameEQ.length, cookie.length);
                        }
                    }
                    return null;
                }
            </script>
            <?php
        }
        ?>
    </body>
</html>

