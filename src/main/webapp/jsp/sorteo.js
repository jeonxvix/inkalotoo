// Navegación del combo de usuario (por si lo usas aquí también)
function cambiarPagina(sel) {
    if (sel && sel.value) {
        window.location.href = sel.value;
    }
}

(function () {
    const precioSelect = document.getElementById("formSorteo:precio");
    const cantidadInput = document.getElementById("cantidad");
    const totalSpan = document.getElementById("totalCompra");
    const autoBtn = document.getElementById("auto");
    const numerosGrid = document.getElementById("numerosGrid");

    // Input de fecha (JSF suele generar id con el id del form delante)
    let fechaInput = document.getElementById("formSorteo:fechaSorteo");
    if (!fechaInput) {
        // fallback por si en algún momento cambia
        fechaInput = document.getElementById("fechaSorteo");
    }

    // ====== TOTAL ======
    function actualizarTotal() {
        if (!precioSelect || !cantidadInput || !totalSpan) return;
        const precio = parseFloat(precioSelect.value || "0");
        const cant = parseInt(cantidadInput.value || "0");
        const total = (precio * cant).toFixed(2);
        totalSpan.textContent = total;
    }

    // ====== GENERAR NÚMEROS ALEATORIOS ======
    function generarNumeros() {
        if (!numerosGrid) return;
        const inputs = numerosGrid.getElementsByTagName("input");
        const usados = new Set();
        for (let i = 0; i < inputs.length; i++) {
            let n;
            do {
                n = Math.floor(Math.random() * 45) + 1;
            } while (usados.has(n));
            usados.add(n);
            inputs[i].value = n;
        }
    }

    // ====== FECHA MÍNIMA (NO FECHAS PASADAS) ======
    if (fechaInput) {
        const hoy = new Date().toISOString().split("T")[0]; // "yyyy-MM-dd"
        fechaInput.min = hoy;
    }

    // Eventos
    if (precioSelect) {
        precioSelect.addEventListener("change", actualizarTotal);
    }
    if (cantidadInput) {
        cantidadInput.addEventListener("input", actualizarTotal);
    }
    if (autoBtn) {
        autoBtn.addEventListener("click", generarNumeros);
    }

    // Calcular total inicial
    actualizarTotal();
})();
