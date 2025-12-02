// ===== Variables globales para poder usarlas desde el onCompraBingoAjax =====
let cartonesDisponibles = 0;
let lblCartones = null;

// Lee el hidden de JSF (id termina en 'cartonesDisponibles')
function syncCartonesDesdeHidden() {
    const hidden = document.querySelector("input[id$='cartonesDisponibles']");
    cartonesDisponibles = parseInt(hidden?.value || "0");
    if (isNaN(cartonesDisponibles)) cartonesDisponibles = 0;
    if (lblCartones) {
        lblCartones.textContent = cartonesDisponibles;
    }
}

// Función que JSF llama cuando termina el AJAX de compra
function onCompraBingoAjax(e) {
    if (e.status === 'success') {
        // Vuelve a leer el hidden y actualiza el label
        syncCartonesDesdeHidden();
        // Reinicia el tablero para una nueva partida
        iniciarNuevaPartida(false); // false => no consumir cartón aquí aún
        estadoJuego.textContent = "Compra realizada. Usa 'Sacar número' para jugar.";
    }
}

// ===== LÓGICA DEL BINGO =====
let cardDiv;
let btnSacar;
let btnNuevo;
let ultimoNumSpan;
let listaNumerosDiv;
let estadoJuego;

let celdas = [];
let numerosMarcados = new Set();
let numerosExtraidos = [];
const LIMITE_BOLAS = 30;

let partidaActiva = false;   // Hay una partida en curso usando un cartón ya descontado
let partidaTerminada = false;

document.addEventListener("DOMContentLoaded", function () {
    cardDiv = document.getElementById("card");
    btnSacar = document.getElementById("btnSacar");
    btnNuevo = document.getElementById("btnNuevo");
    ultimoNumSpan = document.getElementById("ultimoNum");
    listaNumerosDiv = document.getElementById("listaNumeros");
    estadoJuego = document.getElementById("estadoJuego");
    lblCartones = document.getElementById("lblCartones");

    // Inicializar cantidad de cartones desde el hidden
    syncCartonesDesdeHidden();

    // Crear un cartón vacío al inicio (sin consumir cartón)
    iniciarNuevaPartida(false);

    btnSacar.addEventListener("click", sacarNumero);
    btnNuevo.addEventListener("click", function () {
        // Reinicia el tablero pero NO consume cartón aún.
        partidaActiva = false;
        partidaTerminada = false;
        iniciarNuevaPartida(false);
        estadoJuego.textContent = "Partida reiniciada. Presiona 'Sacar número' para comenzar.";
    });
});

// Genera el cartón de bingo (solo UI)
function generarCarton() {
    // Limpia celdas anteriores (dejando los headers B I N G O)
    while (cardDiv.children.length > 5) {
        cardDiv.removeChild(cardDiv.lastChild);
    }

    celdas = [];
    numerosMarcados.clear();

    // Rangos por columna (típico bingo 75)
    const rangos = [
        [1, 15],   // B
        [16, 30],  // I
        [31, 45],  // N
        [46, 60],  // G
        [61, 75]   // O
    ];

    for (let col = 0; col < 5; col++) {
        const usados = new Set();
        for (let fila = 0; fila < 5; fila++) {
            const cell = document.createElement("div");
            cell.classList.add("bingo-cell");

            let numero = 0;

            // Centro = FREE
            if (fila === 2 && col === 2) {
                cell.textContent = "FREE";
                cell.classList.add("free", "marked");
                cell.dataset.num = "0"; // opcional, para no usarlo en sorteo
            } else {
                const [min, max] = rangos[col];
                do {
                    numero = Math.floor(Math.random() * (max - min + 1)) + min;
                } while (usados.has(numero));
                usados.add(numero);

                cell.textContent = numero;
                cell.dataset.num = String(numero);
            }

            cardDiv.appendChild(cell);
            celdas.push(cell);
        }
    }
}

// Reinicia los datos de la partida
// consumirCarton = true => se descuenta 1 cartón al iniciar la partida
function iniciarNuevaPartida(consumirCarton) {
    numerosMarcados.clear();
    numerosExtraidos = [];
    ultimoNumSpan.textContent = "—";
    listaNumerosDiv.textContent = "";
    estadoJuego.textContent = "";
    partidaTerminada = false;

    if (consumirCarton) {
        if (cartonesDisponibles <= 0) {
            estadoJuego.textContent = "No tienes cartones disponibles. Compra primero.";
            partidaActiva = false;
            return;
        }
        cartonesDisponibles--;
        if (lblCartones) lblCartones.textContent = cartonesDisponibles;
        partidaActiva = true;
    }

    generarCarton();
}

// Saca un número aleatorio y lo marca en el cartón
function sacarNumero() {
    if (partidaTerminada) {
        estadoJuego.textContent = "La partida terminó. Reinicia para usar otro cartón.";
        return;
    }

    // Si aún no hay partida activa, intentamos consumir 1 cartón
    if (!partidaActiva) {
        if (cartonesDisponibles <= 0) {
            estadoJuego.textContent = "No tienes cartones disponibles. Compra primero.";
            return;
        }
        // Nuevo juego: descuenta cartón y reinicia tablero
        iniciarNuevaPartida(true);
        if (!partidaActiva) {
            // por seguridad, si no pudo activarse, salimos
            return;
        }
    }

    // Límite de bolas por partida
    if (numerosExtraidos.length >= LIMITE_BOLAS) {
        partidaTerminada = true;
        estadoJuego.textContent = "Se acabaron las 30 bolas. No hiciste bingo.";
        return;
    }

    // Sacar número no repetido
    let num;
    if (numerosExtraidos.length >= 75) {
        estadoJuego.textContent = "Ya se sortearon todos los números posibles.";
        partidaTerminada = true;
        return;
    }

    do {
        num = Math.floor(Math.random() * 75) + 1;
    } while (numerosExtraidos.includes(num));

    numerosExtraidos.push(num);
    ultimoNumSpan.textContent = num;

    // Mostrar lista de números con guiones: 10 - 2 - 4 - 22
    listaNumerosDiv.textContent = numerosExtraidos.join(" - ");

    // Marcar en cartón si corresponde
    celdas.forEach(cell => {
        const valor = parseInt(cell.dataset.num);
        if (valor === num) {
            cell.classList.add("marked");
            numerosMarcados.add(valor);
        }
    });

    // Verificar bingo
    if (verificarBingo()) {
        partidaTerminada = true;
        estadoJuego.textContent = "¡BINGO! Ganaste esta partida.";
    }
}

// Verifica si hay bingo (filas, columnas o diagonales)
function verificarBingo() {
    // Creamos una matriz 5x5 de booleans (marcado o no)
    const marcado = [];
    for (let i = 0; i < 5; i++) {
        marcado[i] = [];
        for (let j = 0; j < 5; j++) {
            const idx = i * 5 + j;
            const cell = celdas[idx];
            // FREE está marcada por clase 'marked'
            marcado[i][j] = cell.classList.contains("marked");
        }
    }

    // Filas
    for (let i = 0; i < 5; i++) {
        if (marcado[i][0] && marcado[i][1] && marcado[i][2] && marcado[i][3] && marcado[i][4]) {
            return true;
        }
    }

    // Columnas
    for (let j = 0; j < 5; j++) {
        if (marcado[0][j] && marcado[1][j] && marcado[2][j] && marcado[3][j] && marcado[4][j]) {
            return true;
        }
    }

    // Diagonal principal
    if (marcado[0][0] && marcado[1][1] && marcado[2][2] && marcado[3][3] && marcado[4][4]) {
        return true;
    }

    // Diagonal secundaria
    if (marcado[0][4] && marcado[1][3] && marcado[2][2] && marcado[3][1] && marcado[4][0]) {
        return true;
    }

    return false;
}

// numerosSalidos debe ser tu array global donde vas guardando los números extraídos

function enviarJugada(resultado) {
    if (!window.numerosSalidos || numerosSalidos.length === 0) {
        return;
    }

    const numeros = numerosSalidos.join("-");  // ej: "3-12-24-5"

    const inputNums = document.getElementById("formJugada:numeros");
    const inputRes  = document.getElementById("formJugada:resultado");
    const btn       = document.getElementById("formJugada:btnGuardarJugada");

    if (inputNums && inputRes && btn) {
        inputNums.value = numeros;
        inputRes.value  = resultado; // "GANO" o "PERDIO"
        btn.click();                 // envía al bean y guarda en BD
    }
}
