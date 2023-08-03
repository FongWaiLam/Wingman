// Declared in another js file
let cardDetectStatusImg = document.getElementById("card-detect-status");
//let cardDetectBtn = document.getElementById("card-detect-button");


function stripeConnect() {
    // Check if any payment reader/terminal is available. Then, connect to reader
    discoverReaderHandler();
    connectReaderHandler(discoveredReaders);

    var delayInMilliseconds = 1000; //1 second
    // Show the connected icon
    setTimeout(function() {
    cardDetectStatusImg.src = "/images/payment-status-green-icon.svg";
    }, delayInMilliseconds);
}

function createAndCapturePayment() {
    let amount = calTotalAmount();
    collectPayment(amount);
    capture(paymentIntentId);
    // After connection, activate the next step2
    setTimeout(function() {
    cardDetectBtn.dispatchEvent(new Event('click'));
    }, delayInMilliseconds);
}


function calTotalAmount() {
    let amount =0;
    scannedProducts.forEach(function(product) {
        amount += price;
    })
    return amount;
}
