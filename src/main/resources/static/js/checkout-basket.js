let port;
let reader;

// Start Multi-read signal
const startReadHexByteString = "bb0027000322ffff4a7e";
const startReadByteArray = startReadHexByteString.match(/.{1,2}/g).map(byte => parseInt(byte, 16));

// End Multi-read signal
const endReadHexByteString = "bb00280000287e";
const endReadByteArray = endReadHexByteString.match(/.{1,2}/g).map(byte => parseInt(byte, 16));

const startCheckout = document.getElementById('start-checkout');
let cardDetectPayBtn = document.getElementById("card-detect-pay-button");
let payExitBtn = document.getElementById("pay-exit-button");

// Once the DOM has all loaded, attach event listeners
    document.addEventListener('DOMContentLoaded', () => {

    // Add Event Listeners to perform 3 steps
    startCheckout.addEventListener('click', step1Performed);
    payExitBtn.addEventListener('click', step2Performed);
    cardDetectPayBtn.addEventListener('click', step3Performed);

    // Check if serial is supported or not
    const serialNotSupported = document.getElementById('serialNotSupported');
    serialNotSupported.classList.toggle('hidden', 'serial' in navigator);
});

const startRow = document.getElementById('start');
const processRow = document.getElementById('process');
const processInstructRow = document.getElementById('process-instruction');
// Step 1 Procedure GUI
function step1Performed() {
    connect();
    startRow.classList.add('hidden');
    processInstructRow.classList.remove('hidden');
    processRow.classList.remove('hidden');
    // Connect to payment terminal
    stripeConnect();
}

const processInstruct1 = document.getElementById('step1-instruction');
const processInstruct2 = document.getElementById('step2-instruction');
const processImg1 = document.getElementById('step1-img');
const processImg2 = document.getElementById('step2-img');
// Step 2 Procedure GUI
function step2Performed() {

    processInstruct1.classList.add('hidden');
    processInstruct2.classList.remove('hidden');
    processImg1.classList.add('hidden');
    processImg2.classList.remove('hidden');
}

const processImg3 = document.getElementById('step3-img');
const alertStep = document.getElementById('instruction');
// Step 3 Procedure GUI
async function step3Performed() {

    // Disconnect rfid reader
    writeToSerialPort(endReadByteArray);

    let amountInPence = calTotalAmount(scannedProducts);
    let paymentIntentId = await collectPayment(amountInPence);
    paymentIntentId = await capture(paymentIntentId);

    // Perform payment creation and capture
//    let paymentIntentId = await createAndCapturePayment();
        // Post Request: Update the payment and sales record
        if(typeof paymentIntentId != "undefined") {
            alertStep.classList.add('hidden');
            processImg2.classList.add('hidden');
            processImg3.classList.remove('hidden');

            updateSalesPaymentRecord(paymentIntentId);
        }

//    createAndCapturePayment().then(function(paymentIntentId) {
//        // Post Request: Update the payment and sales record
//        if(typeof paymentIntentId != "undefined") {
//            alertStep.classList.add('hidden');
//            processImg2.classList.add('hidden');
//            processImg3.classList.remove('hidden');
//
//            updateSalesPaymentRecord(paymentIntentId);
//        }
//    }).catch(function(error) {
//    console.err("step3Performed() Error: " + error)
//    });


    var delayInMilliseconds = 10000; //10 second
    setTimeout(function() {
    window.location.reload();
    }, delayInMilliseconds);

}

async function connect() {
  //  Only work for previously authorised ports
    const ports = await navigator.serial.getPorts();
  if (ports[0] == null) {
    // - Request a port and open a connection.
    port = await navigator.serial.requestPort();
  } else {
    port = ports[0];
  }
  // - Wait for the port to open.
  await port.open({ baudRate: 115200 });
  console.log("port" + port);
  writeToSerialPort(startReadByteArray);
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
                 console.log (" <" + Array.from(epcSet).join(' ') + "> ");
                 if (epcSet.size != previousSetSize) {
                    // To do: send post request to backend to reply product info
                    console.log (" <" + " [Post request: new epc: " + epc + "] ");
                    getProduct(epc);
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

let scannedProducts = [];
let prodIdDisplay = new Set;
let previousDisplaySetSize = 0;
let total = document.getElementById("total");

function getProduct(epc) {

let url = '/checkout/get_product';

// JSON object
let data = { epc: epc };
console.log(epc);

fetch(url, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(data),
})
.then(response => {
                    if(!response.ok) {
                    console.log(data.message);
                    return;
                    }
                    return response.json();
                  })
.then(data => {
    console.log('Product Info:', data);
    scannedProducts.push(data);
    prodIdDisplay.add(data.prodId);
    if (prodIdDisplay.size != (previousDisplaySetSize + 1)) {
        // Update (Add 1 ) the quantity column of the row with this prodId
        updateQuantity(data.prodId);
    } else {
        // Create a new row for this product with quantity 1
        updateBasket(data);
        previousDisplaySetSize++
    }
    // Update total
    let totalAmount = (calTotalAmount(scannedProducts) / (100.0)).toFixed(2);
    console.log("total: " + totalAmount);
    total.innerHTML = "Total(£): " + totalAmount;
})
.catch((error) => {
  console.error('Error:', error);
});
}

function updateBasket(product) {
  let tableBody = document.getElementById('basket');

  // Create a new row and cells
  let row = document.createElement('tr');
  row.id = product.prodId;
//  let rfidCell = document.createElement('td');
  let photoCell = document.createElement('td');
  let nameCell = document.createElement('td');
  let priceCell = document.createElement('td');
  let quantityCell = document.createElement('td');
  quantityCell.id = product.prodId + "-quantity";

  // Set the text content of each cell
//  rfidCell.textContent = product.rfid;
  let img = document.createElement('img');
  img.src = "https://unmannedstore.blob.core.windows.net/wingman/product/" + product.prodId + "/" + product.photo;
  img.classList.add("thumbnail");
  photoCell.appendChild(img);
  nameCell.textContent = product.name;
  nameCell.classList.add("align-middle");
  priceCell.textContent = product.price;
  priceCell.classList.add("align-middle");
  quantityCell.textContent = 1;
  quantityCell.classList.add("align-middle");

  // Append the cells to the row
  row.appendChild(photoCell);
//  row.appendChild(rfidCell);
  row.appendChild(nameCell);
  row.appendChild(priceCell);
  row.appendChild(quantityCell);
  // Append the row to the table body
  tableBody.appendChild(row);
}

function updateQuantity (prodId) {
    const quantityCell = document.getElementById(prodId + "-quantity");
    let quantity = parseInt(quantityCell.textContent,10);
    quantity++;
    quantityCell.textContent = quantity;
}


const currentURL = window.location.href;
const myArray = currentURL.split("/");
const storeId = myArray[myArray.length-1];
const epcList = [];
function updateSalesPaymentRecord(paymentIntentId) {
    getFinalEPCList();
    console.log("Final EPC List: " + epcList);
    let url = '/update_pay_sales_record';
    let amountInPence = calTotalAmount(scannedProducts);
    // JSON object
    let data = {
        epcList: epcList,
        amountInPence: amountInPence,
        paymentIntentId: paymentIntentId,
        isSuccessful: true,
        storeId: storeId,
        };
    console.log("paymentIntentId: " + paymentIntentId);

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
    .then(response => response.json()) // Parse the response as JSON
    .then(message => {
        console.log('Update Sales, Cart and Payment record:', message.message);
    })
    .catch((error) => {
      console.error('Error:', error);
    });
}

function getFinalEPCList() {
    scannedProducts.forEach((product) => epcList.push(product.epc));
}