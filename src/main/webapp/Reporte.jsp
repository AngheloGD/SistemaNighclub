<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/error.jsp" %>

<html>
<head>
    <title>Mostrar Datos y Generar Reporte PDF</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/Reporte.css">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.13/jspdf.plugin.autotable.js"></script>

    <script>
        // Función para verificar la sesión y redirigir si es necesario
        function verificarSesion() {
            $.ajax({
                url: 'validarSesion',
                type: 'GET',
                dataType: 'json',
                success: function (response) {
                    if (response.resultado !== "ok") {
                        // El usuario no está autenticado, redirigir a index.html de inmediato
                        console.log("Redirigiendo a index.html");
                        window.location.replace("index.html");
                    }
                },
                error: function () {
                    // En caso de error, redirigir a index.html de inmediato
                    console.log("Error en la verificación de sesión. Redirigiendo a index.html");
                    window.location.replace("index.html");
                }
            });
        }

        $(document).ready(function () {
            verificarSesion();
        });

        // Resto de tu código para generar el reporte
        function generarReporte() {
            console.log("Generando reporte PDF...");

            // Inicializar el objeto jsPDF
            var pdf = new jsPDF();

            // Obtener la fecha y hora actual
            var currentDate = new Date();
            var formattedDate = currentDate.toLocaleString();

            // Configurar el encabezado del PDF
            pdf.text("SistemaNighclub", 20, 10);

            // Configurar el contenido de la tabla en el PDF
            pdf.autoTable({html: 'table'});

            // Configurar la posición del pie de página en cada página
            pdf.text("Reporte generado: " + formattedDate, 20, pdf.internal.pageSize.height - 10);

            // Guardar el PDF con un nombre de archivo
            pdf.save("reporte.pdf");
        }
    </script>
</head>
<body>
    <%-- Tu código Java para la conexión y obtención de datos --%>
    <%
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/nightclubb", "root", "");
            statement = conexion.createStatement();

            // Verificar si el token está almacenado en la cookie
            boolean tokenPresente = false;
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        tokenPresente = true;
                        break;
                    }
                }
            }

            // Token presente, mostrar los datos y el botón para generar el informe
            if (tokenPresente) {
                String sql = "SELECT * FROM cita";
                resultSet = statement.executeQuery(sql);
    %>
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
        <%
            while (resultSet.next()) {
        %>
        <tbody>
            <tr>
                <td><%= resultSet.getString("codicita")%></td>
                <td><%= resultSet.getString("diacita")%></td>
                <td><%= resultSet.getString("emplcita")%></td>
                <td><%= resultSet.getString("servcita")%></td>
                <td><%= resultSet.getString("codiUsua")%></td>
            </tr>
        </tbody>
        <%
            }
        %>
    </table>

    <button type="button" id="generar-reporte" onclick="generarReporte()">GENERAR REPORTE</button>

    <div id="footer">
        Fecha y hora de generación: <span id="fecha-hora"></span>
    </div>
    <%
        } else {
    %>
    <div style="color: red; font-size: 18px; margin: 20px;">Error: Inicia sesión para acceder a esta página.</div>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>
</body>
</html>
