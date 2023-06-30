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

async function readLoop() {
  const reader = port.readable.getReader();
  // Read loop
  while (true) {
    const { value, done } = await reader.read();
    if (value) {
      hexString = uint8ArrayToHexString(value);
      rfidLog.textContent += hexString;
      if ("7e" == hexString) {
      rfidLog.textContent += '\n';
      }

    }
    if (done) {
      console.log('[readLoop] DONE', done);
      reader.releaseLock();
      break;
    }
}
}

function uint8ArrayToHexString(uint8Array) {
  const hexString = Array.from(uint8Array, byte => byte.toString(16).padStart(2, '0')).join('');
  return hexString;
}

