/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

    function cambiarPagina(select) {
        if (!select) return;
        var url = select.value;
        if (url && url !== "") {
            window.location.href = url;
            // opcional: volver a dejar seleccionado el nombre
            // select.selectedIndex = 0;
        }
    }

    // Imprimir / guardar comprobante en PDF
    function imprimirComprobante() {
        var comp = document.getElementById("comprobante-retiro");
        if (!comp) return;

        var ventana = window.open("", "_blank");
        ventana.document.write("<html><head><title>Comprobante de retiro</title>");
        ventana.document.write("<link rel='stylesheet' href='css/style.css' type='text/css'/>");
        ventana.document.write("</head><body>");
        ventana.document.write(comp.innerHTML);
        ventana.document.write("</body></html>");
        ventana.document.close();
        ventana.focus();
        ventana.print(); // aquí el usuario elige "Guardar como PDF"
    }
