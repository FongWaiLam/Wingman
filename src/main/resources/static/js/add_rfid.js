let port;
let reader;

// Start Multi-read signal
const multiReadHexByteString = "bb0027000322ffff4a7e";
const multiReadByteArray = multiReadHexByteString.match(/.{1,2}/g).map(byte => parseInt(byte, 16));
// End Multi-read signal
const endReadHexByteString = "bb00280000287e";
const endReadByteArray = multiReadHexByteString.match(/.{1,2}/g).map(byte => parseInt(byte, 16));

const scanConnect = document.getElementById('scan');
const submitForm = document.getElementById('form-submission');

// Check the serial port once the DOM has all loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log(scanConnect);
    scanConnect.addEventListener('click', connect);
    submitForm.addEventListener('click', submitRFIDForm);

    console.log("Testing RFID Web Serial API");
    console.log("multiReadByteArray: " , multiReadByteArray);
    const serialNotSupported = document.getElementById('serialNotSupported');
    serialNotSupported.classList.toggle('hidden', 'serial' in navigator);

});


async function connect() {
  // Add code to request & open port here.
  // - Request a port and open a connection.
  port = await navigator.serial.requestPort();

  // Add listener to auto connect again if permission is granted once.
  navigator.serial.addEventListener("connect", async (event) => {
    port = event.target;
    await port.open({ baudRate: 115200 });
  });

  // - Wait for the port to open.
  await port.open({ baudRate: 115200 });
  writeToSerialPort(multiReadByteArray);
  readLoop();
}

// Function to write a byte array to the serial port
async function writeToSerialPort(byteArray) {
  const uint8Array = new Uint8Array(byteArray);
//  outputStream.write(uint8Array);

    if (port == null) {
        await connect();
    }
  const writer = port.writable.getWriter();
  await writer.write(uint8Array);


  // Allow the serial port to be closed later.
  writer.releaseLock();

}


// Set to store rfid epc card no.
var epcSet = new Set();
let previousSetSize = 0;
let rfid = "";

async function readLoop() {
  const reader = port.readable.getReader();
  // Read loop
  while (true) {
    const { value, done } = await reader.read();
    if (value) {
      hexArray = uint8ArrayToHexArray(value);
      hexArray.forEach(function(hexString) {
           rfid += hexString;
           if (hexString == '7e') {
            if (rfid != "bb01ff000115167e" && rfid.length == 48) {
                epc = rfid.substring(16, 40);
                 epcSet.add(epc);
                 if (epcSet.size != previousSetSize) {
                    updateStock(epc);
                    previousSetSize++;
                 }
             }
             rfid = "";
           }
      });
    }
    if (done) {
      console.log('[readLoop] DONE', done);
      reader.releaseLock();
      break;
    }
}
}

//function uint8ArrayToHexString(uint8Array) {
//  const hexString = Array.from(uint8Array, byte => byte.toString(16).padStart(2, '0')).join('');
//  return hexString;
//}

function uint8ArrayToHexArray(uint8Array) {
  const hexArray = Array.from(uint8Array, byte => byte.toString(16).padStart(2, '0'));
  return hexArray;
}



function updateStock(epc) {
  let tableBody = document.getElementById('stock');

  // Create a new row and cells
  let row = document.createElement('tr');
  let rfidCell = document.createElement('td');
  let deleteButton = document.createElement('BUTTON');

  // Set the text content and other attributes of each element
    row.id = epc;
  rfidCell.textContent = epc;
    deleteButton.textContent = 'Delete';
    deleteButton.classList = 'btn btn-danger btn-sm';
    deleteButton.type = 'button';
    deleteButton.setAttribute("onclick","removeStock('"+ epc + "');");

  // Append the cells to the row
  row.appendChild(rfidCell);
    row.appendChild(deleteButton);
  // Append the row to the table body
  tableBody.appendChild(row);

}

function removeStock(epc) {
  let row = document.getElementById(epc);
    row.remove();
    epcSet.delete(epc);
    previousSetSize--;
}


async function submitRFIDForm() {
    console.log("submitRFID Form");
    let url = 'http://localhost:8080/usms/save_rfid_list';
    let prodId = document.getElementById('prodId').value;
    let name = document.getElementById('name').value;
    let storeId = document.getElementById('storeId').value;

    console.log("storeId: " + storeId);
    console.log("epcSet: " + Array.from(epcSet).join(' '));
    if (epcSet.size == 0 || storeId == "") {
        alert("Please select the store and scan at least one RFID label.");
        console.log("epcSet or storeId is null.");
        return;
    }

    console.log("submitRFID Form");
    writeToSerialPort(endReadByteArray);

    let data = {
        epcSet: epcSet,
        prodId: prodId,
        name: name,
        storeId: storeId,
    };

    fetch(url, { method: 'POST', headers:{'Content-Type' : 'application/json',},
    body: JSON.stringify(data),
    })
    .then(response => {
    if (response.ok) {
                    // Redirect to product page
                    window.location.href = "/usms/products";
                }
    })
    .catch((error) => {
         console.error('Error:', error);
       });
}
