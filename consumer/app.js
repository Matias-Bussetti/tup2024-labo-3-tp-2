const HOST = "http://localhost:8080";

const formCliente = document.querySelector("form#form-cliente");
formCliente.onsubmit = (e) => {
  e.preventDefault();
  const data = {
    nombre: formCliente.nombre.value,
    apellido: formCliente.apellido.value,
    dni: formCliente.dni.value,
    fechaNacimiento: formCliente.fechaNacimiento.value,
    tipoPersona: formCliente.tipoPersona.value,
    banco: formCliente.banco.value,
  };
  fetch(HOST + "/cliente", {
    method: "POST",
    body: JSON.stringify(data),
    headers: new Headers({
      "Content-type": "application/json; charset=UTF-8",
      "Access-Control-Allow-Origin": "*",
    }),
  }).catch((e) => console.log(e));
  console.log(data);
};

const formBuscarCliente = document.querySelector("form#form-buscar-cliente");

formBuscarCliente.onsubmit = async (e) => {
  e.preventDefault();
  obtenerClientePorDNI(formBuscarCliente.dni.value)
    .then((data) => {
      console.log(data);
      const clienteCard = document.querySelector("div#clone-cliente-card");

      const cloneClienteCard = clienteCard.cloneNode(true);
      cloneClienteCard.classList.remove("visually-hidden");

      updateCloneCard(cloneClienteCard, data);

      cloneClienteCard.classlist;

      document
        .querySelector("div#cliente-container")
        .appendChild(cloneClienteCard);
    })
    .catch((e) => alert(e));
};

function updateCloneCard(cardElement, data) {
  cardElement.querySelector("input[type='checkbox']").onclick = (e) => {
    const spinner = cardElement.querySelector("div.spinner-border");
    if (e.target.checked) {
      spinner.classList.remove("visually-hidden");

      const interval = setInterval(() => {
        obtenerClientePorDNI(data.dni)
          .then((dataUpdated) => {
            updateCloneCard(cardElement, dataUpdated);
          })
          .catch((e) => alert(e));
      }, 1000);

      localStorage.setItem(data.dni + "-interval", interval);
    } else {
      clearInterval(parseInt(localStorage.getItem(data.dni + "-interval")));
      spinner.classList.add("visually-hidden");
    }
  };

  function querySelectorCard(className, value) {
    cardElement.querySelector(className).innerText = value;
  }

  querySelectorCard(".cliente-nombre", data.nombre);
  querySelectorCard(".cliente-apellido", data.apellido);
  querySelectorCard(".cliente-dni", data.dni);
  querySelectorCard(".cliente-fecha-Nacimiento", data.fechaNacimiento);
  querySelectorCard(".cliente-tipo-persona", data.tipoPersona);
  querySelectorCard(".cliente-banco", data.banco);
  querySelectorCard(".cliente-fecha-alta", data.fechaAlta);
  querySelectorCard(".cliente-edad", data.edad);

  const cuentasContainer = cardElement.querySelector(".cliente-cuentas");

  const formAgregarCuentaCliente = cardElement.querySelector("form");
  formAgregarCuentaCliente.onsubmit = (e) => {
    e.preventDefault();
    crearCuentaACliente(
      data.dni,
      formAgregarCuentaCliente.tipoCuenta.value,
      formAgregarCuentaCliente.tipoMoneda.value
    ).then((res) => {
      //   obtenerClientePorDNI(data.dni)
      //     .then((dataUpdated) => updateCloneCard(cardElement, dataUpdated))
      //     .catch((e) => alert(e));
    });
  };

  cuentasContainer.innerHTML = "";

  data.cuentas.forEach((cuenta) => {
    const cloneCuentaLI = document
      .querySelector("#clone-cuenta-li")
      .cloneNode(true);
    cloneCuentaLI.classList.remove("visually-hidden");
    function querySelectorCuentaLi(query, value) {
      cloneCuentaLI.querySelector(query).innerText = value;
    }
    querySelectorCuentaLi(".cliente-cuenta-tipo-moneda", cuenta.moneda);
    querySelectorCuentaLi(".cliente-cuenta-tipo-cuenta", cuenta.tipoCuenta);
    querySelectorCuentaLi(".cliente-cuenta-balance", cuenta.balance);
    querySelectorCuentaLi("small", String(cuenta.numeroCuenta));

    cloneCuentaLI.onclick = () => {
      // Copy the text inside the text field
      navigator.clipboard.writeText(cuenta.numeroCuenta);
      cloneCuentaLI.classList.add("border-success");
      cloneCuentaLI.classList.add("bg-success");
      cloneCuentaLI.classList.add("text-white");
      setTimeout(() => {
        cloneCuentaLI.classList.remove("border-success");
        cloneCuentaLI.classList.remove("bg-success");
        cloneCuentaLI.classList.remove("text-white");
      }, 500);
    };

    cuentasContainer.appendChild(cloneCuentaLI);
  });
}
function obtenerClientePorDNI(dni) {
  const url = `http://localhost:8080/cliente?dni=${dni.replace("x", "")}`;

  return new Promise((resolve, reject) => {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          let responseText = xhr.responseText;
          function addQuotesToAttributes(text) {
            let inString = false;
            let escaped = false;
            let result = "";

            for (let i = 0; i < text.length; i++) {
              let char = text[i];

              if (char === '"' && !escaped) {
                inString = !inString;
              } else if (char === "\\" && !escaped) {
                escaped = true;
                result += char;
                continue;
              } else if (char === ":" && !inString) {
                result = result.replace(/([a-zA-Z0-9_]+)$/, '"$1"');
              }

              result += char;
              escaped = false;
            }

            return result;
          }
          try {
            // Modificar cada campo de la respuesta para agregar un carácter extra
            function addExtraCharacterToFields(text) {
              return text.replace(/"(\w+)":\s*(-?\d+(\.\d+)?)/g, '"$1":"$2x"');
            }

            const modifiedResponseText =
              addExtraCharacterToFields(responseText);
            const parsedResponse = JSON.parse(modifiedResponseText);
            resolve(parsedResponse);
          } catch (error) {
            reject(
              new Error("Error al parsear la respuesta JSON: " + error.message)
            );
          }
        } else {
          reject(new Error(`Error en la solicitud: ${xhr.status}`));
        }
      }
    };

    xhr.onerror = function () {
      reject(new Error("Error en la solicitud"));
    };

    xhr.send();
  });
}

async function crearCuentaACliente(dni, tipoCuenta, moneda) {
  const url = `http://localhost:8080/cuenta?dni=${dni.replace("x", "")}`;
  const data = {
    tipoCuenta: tipoCuenta,
    moneda: moneda,
  };

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const result = await response.json();
    return result;
  } catch (error) {
    console.error("Error:", error);
  }
}

const formTransferencia = document.querySelector(
  "form#form-hacer-transferencia"
);

formTransferencia.cuentaOrigenPaste.onclick = async (e) => {
  formTransferencia.cuentaOrigen.value = await navigator.clipboard.readText();
};
formTransferencia.cuentaDestinoPaste.onclick = async (e) => {
  formTransferencia.cuentaDestino.value = await navigator.clipboard.readText();
};

formTransferencia.onsubmit = (e) => {
  e.preventDefault();
  hacerTransferencia(
    formTransferencia.cuentaOrigen.value,
    formTransferencia.cuentaDestino.value,
    formTransferencia.monto.value,
    formTransferencia.tipoMoneda.value
  );
};

async function hacerTransferencia(
  cuentaOrigen,
  cuentaDestino,
  monto,
  tipoMoneda
) {
  const url = `http://localhost:8080/api/transfer`;
  const data = {
    cuentaOrigen: cuentaOrigen,
    cuentaDestino: cuentaDestino,
    monto: parseFloat(monto),
    moneda: tipoMoneda,
  };

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const result = await response.json();
    return result;
  } catch (error) {
    console.error("Error:", error);
  }
}
