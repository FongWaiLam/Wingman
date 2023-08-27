// Codes referenced to Stripe Documents - Accept in-person payments

var terminal = StripeTerminal.create({
  onFetchConnectionToken: fetchConnectionToken,
  onUnexpectedReaderDisconnect: unexpectedDisconnect,
});

var discoveredReaders;


function unexpectedDisconnect() {
  console.log("Disconnected from reader")
  // Payment reader/ terminal cannot be connected
  var delayInMilliseconds = 10000; //10 second
  setTimeout(function() {
  window.location.reload();
  }, delayInMilliseconds);
}

function fetchConnectionToken() {
  return fetch('/connection_token', { method: "POST" })
    .then(function(response) {
      return response.json();
    })
    .then(function(data) {
      return data.secret;
    });
}

// Handler for a "Discover readers" button
async function discoverReaderHandler() {
    console.log("discoverReaderHandler() entered");
  var config = {simulated: true};
      return terminal.discoverReaders(config).then(function(discoverResult) {
          if (discoverResult.error) {
              console.log('Failed to discover: ', discoverResult.error);
              return discoverResult;
          } else if (discoverResult.discoveredReaders.length === 0) {
              console.log('No available readers.');
              return discoverResult;
          } else {
              discoveredReaders = discoverResult.discoveredReaders;
              console.log('terminal.discoverReaders', discoveredReaders);
              return discoverResult;
          }
      });

//  terminal.discoverReaders(config).then(function(discoverResult) {
//    if (discoverResult.error) {
//      console.log('Failed to discover: ', discoverResult.error);
//    } else if (discoverResult.discoveredReaders.length === 0) {
//        console.log('No available readers.');
//    } else {
//        discoveredReaders = discoverResult.discoveredReaders;
//        console.log('terminal.discoverReaders', discoveredReaders);
//    }
//    console.log('discoverResult', discoverResult);
//    return discoverResult;
//  });
}


async function connectReaderHandler(discoveredReaders) {
  // Just select the first reader here.
  var selectedReader = discoveredReaders[0];
      return terminal.connectReader(selectedReader).then(function(connectResult) {
          if (connectResult.error) {
              console.log('Failed to connect: ', connectResult.error);
              return connectResult;
          } else {
              console.log('Connected to reader: ', connectResult.reader.label);
              console.log('terminal.connectReader', connectResult);
              return connectResult;
          }
      });

//  terminal.connectReader(selectedReader).then(function(connectResult) {
//    if (connectResult.error) {
//      console.log('Failed to connect: ', connectResult.error);
//    } else {
//        console.log('Connected to reader: ', connectResult.reader.label);
//        console.log('terminal.connectReader', connectResult)
//    }
//    return connectResult;
//  });
}

async function fetchPaymentIntentClientSecret(amount) {
  const bodyContent = JSON.stringify({ amount: amount });
  return fetch('/create_payment_intent', {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: bodyContent
  })
  .then(function(response) {
    return response.json();
  })
  .then(function(data) {
    return data.client_secret;
  });
}

async function collectPayment(amount) {
  if (amount == 0) {
    console.log("Amount is zero. The user only wants to exit the shop.")
    throw new Error("Amount is zero");
  }
  try {
    const client_secret = await fetchPaymentIntentClientSecret(amount);
    terminal.setSimulatorConfiguration({testCardNumber: '4242424242424242'});
    const collectResult = await terminal.collectPaymentMethod(client_secret);

    if (collectResult.error) {
      console.log("Unable to create payment for amount: " + amount);
      return; // or throw an error here if this is an exceptional condition
    }

    console.log('terminal.collectPaymentMethod', collectResult.paymentIntent);
    const processResult = await terminal.processPayment(collectResult.paymentIntent);

    if (processResult.error) {
      console.log(processResult.error);
      return processResult;
    } else if (processResult.paymentIntent) {
      console.log('terminal.processPayment', processResult.paymentIntent);
      return processResult.paymentIntent.id;
    }
  } catch (error) {
    console.log("Error in collectPayment: ", error);
    throw error;
  }
}

//async function collectPayment(amount) {
//let paymentIntentId;
//  fetchPaymentIntentClientSecret(amount).then(function(client_secret) {
//      terminal.setSimulatorConfiguration({testCardNumber: '4242424242424242'});
//      terminal.collectPaymentMethod(client_secret).then(function(result) {
//      if (result.error) {
//        // Placeholder for handling result.error
//        console.log("Unable to create payment for amount: " + amount);
//      } else {
//          console.log('terminal.collectPaymentMethod', result.paymentIntent);
//          terminal.processPayment(result.paymentIntent).then(function(result) {
//          if (result.error) {
//            console.log(result.error)
//            return result;
//          } else if (result.paymentIntent) {
//              paymentIntentId = result.paymentIntent.id;
//              console.log('terminal.processPayment', result.paymentIntent);
////              return result;
//              return paymentIntentId;
//          }
//        });
//      }
//    });
//  });
//}

async function capture(paymentIntentId) {
  console.log("Entered capture(paymentIntentId): paymentIntentId " + paymentIntentId);
  try {
    const response = await fetch('/capture_payment_intent', {
      method: "POST",
      headers: {
          'Content-Type': 'application/json'
      },
        body: JSON.stringify({"paymentIntentId": paymentIntentId})
    });

    // Check if the response is ok (status in the range 200-299)
    if (!response.ok) {
      console.error("Server responded with status", response.status);
      // Here you could throw an error or return an error object,
      // depending on how you want to handle this situation.
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    console.log("capture(paymentIntentId): ", data);
    console.log('server.capture', data);
    return paymentIntentId;
  } catch (error) {
    console.log("Error in capture: ", error);
    throw error;
  }
}



//async function capture(paymentIntentId) {
//console.log("Entered capture(paymentIntentId): paymentIntentId" + paymentIntentId);
//  return fetch('/capture_payment_intent', {
//    method: "POST",
//    headers: {
//        'Content-Type': 'application/json'
//    },
//      body: JSON.stringify({"payment_intent_id": paymentIntentId})
//  })
//  .then(function(response) {
//  console.log("capture(paymentIntentId): return " + response.json())
//    return response.json();
//  })
//  .then(function(data) {
//    console.log('server.capture', data);
//    return paymentIntentId;
//  });
//}


//const discoverButton = document.getElementById('discover-button');
//discoverButton.addEventListener('click', async (event) => {
//  discoverReaderHandler();
//});
//
//const connectButton = document.getElementById('connect-button');
//connectButton.addEventListener('click', async (event) => {
//  connectReaderHandler(discoveredReaders);
//});
//
//const collectButton = document.getElementById('collect-button');
//collectButton.addEventListener('click', async (event) => {
//  amount = document.getElementById("amount-input").value
//  collectPayment(amount);
//});
//
//const captureButton = document.getElementById('capture-button');
//captureButton.addEventListener('click', async (event) => {
//  capture(paymentIntentId);
//});

//function log(method, message){
//  var logs = document.getElementById("logs");
//  var title = document.createElement("div");
//  var log = document.createElement("div");
//  var lineCol = document.createElement("div");
//  var logCol = document.createElement("div");
//  title.classList.add('row');
//  title.classList.add('log-title');
//  title.textContent = method;
//  log.classList.add('row');
//  log.classList.add('log');
//  var hr = document.createElement("hr");
//  var pre = document.createElement("pre");
//  var code = document.createElement("code");
//  code.textContent = formatJson(JSON.stringify(message, undefined, 2));
//  pre.append(code);
//  log.append(pre);
//  logs.prepend(hr);
//  logs.prepend(log);
//  logs.prepend(title);
//}

//function stringLengthOfInt(number) {
//  return number.toString().length;
//}
//
//function padSpaces(lineNumber, fixedWidth) {
//  // Always indent by 2 and then maybe more, based on the width of the line
//  // number.
//  return " ".repeat(2 + fixedWidth - stringLengthOfInt(lineNumber));
//}
//
//function formatJson(message){
//  var lines = message.split('\n');
//  var json = "";
//  var lineNumberFixedWidth = stringLengthOfInt(lines.length);
//  for(var i = 1; i <= lines.length; i += 1){
//    line = i + padSpaces(i, lineNumberFixedWidth) + lines[i-1];
//    json = json + line + '\n';
//  }
//  return json
//}