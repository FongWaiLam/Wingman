let port;
let reader;

// Start Multi-read signal
const hexByteString = "bb0027000322ffff4a7e";
const byteArray = hexByteString.match(/.{1,2}/g).map(byte => parseInt(byte, 16));

const scanConnect = document.getElementById('scan');
const rfidLog = document.getElementById('rfidLog');

// Check the serial port once the DOM has all loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log(scanConnect);
    scanConnect.addEventListener('click', connect);

    console.log("Testing RFID Web Serial API");
    console.log("byteArray: " , byteArray);
    const serialNotSupported = document.getElementById('serialNotSupported');
    serialNotSupported.classList.toggle('hidden', 'serial' in navigator);

});

async function connect() {
  // Add code to request & open port here.
  // - Request a port and open a connection.
  port = await navigator.serial.requestPort();
  // - Wait for the port to open.
  await port.open({ baudRate: 115200 });
  writeToSerialPort(byteArray);
  readLoop();
}

// Function to write a byte array to the serial port
async function writeToSerialPort(byteArray) {
  const uint8Array = new Uint8Array(byteArray);
//  outputStream.write(uint8Array);

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
                 rfidLog.textContent += " <" + Array.from(epcSet).join(' ') + "> ";
                 if (epcSet.size != previousSetSize) {
                    // To do: send post request to backend to reply product info
                    rfidLog.textContent += " [Post request: new epc: " + epc + "] ";
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



function getProduct(epc) {

let url = '/checkout/getProduct';
let scannedProducts = [];

// JSON object for RFID tag
let data = { rfid: epc };

fetch(url, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(data),
})
.then(response => response.json()) // Parse the response as JSON
.then(data => {
    console.log('Product Info:', data);
    scannedProducts.push(data);
    updateBasket(data);
})
.catch((error) => {
  console.error('Error:', error);
});
}

function updateBasket(product) {
  let tableBody = document.getElementById('basket');

  // Create a new row and cells
  let row = document.createElement('tr');
  let rfidCell = document.createElement('td');
  let nameCell = document.createElement('td');
  let photoCell = document.createElement('td');
  let priceCell = document.createElement('td');
  let quantityCell = document.createElement('td');

  // Set the text content of each cell
  rfidCell.textContent = product.rfid;
  nameCell.textContent = product.name;

  let img = document.createElement('img');
  img.src = product.photoUrl;
  img.classList.add("thumbnail");
  photoCell.appendChild(img);

  priceCell.textContent = product.price;
  quantityCell.textContent = 1;

  // Append the cells to the row
  row.appendChild(rfidCell);
  row.appendChild(nameCell);
  row.appendChild(photoCell);
  row.appendChild(priceCell);
  row.appendChild(quantityCell);
  // Append the row to the table body
  tableBody.appendChild(row);

}