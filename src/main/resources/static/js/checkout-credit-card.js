// Declared in another js file
let cardDetectStatusImg = document.getElementById("card-detect-status");
//let cardDetectBtn = document.getElementById("card-detect-button");


var delayInMilliseconds = 1000; //1 second

function stripeConnect() {
    // Check if any payment reader/terminal is available. Then, connect to reader

    discoverReaderHandler().then(function(discoverResult) {
        if(discoverResult.discoveredReaders.length > 0
        && typeof discoverResult.discoveredReaders != "undefined") {
            return connectReaderHandler(discoveredReaders);
        } else {
            throw new Error('discoverReaderHandler() - No available readers.');
        }
    }).then(function(connectResult){
        if (!connectResult.error) {
            // Show the connected icon
            setTimeout(function() {
            cardDetectStatusImg.src = "/images/payment-status-green-icon.svg";
            }, delayInMilliseconds);
        } else {
            throw new Error('discoverReaderHandler() - Unable to connect reader.');
        }
    }).catch(function(error) {
    console.error("stripeConnect() Error: " + error);
    });
}
//
//async function createAndCapturePayment() {
//
//    let amountInPence = calTotalAmount();
//    collectPayment(amountInPence).then(function(result) {
//        if(result.paymentIntent.length > 0
//        && typeof result.paymentIntent != "undefined") {
//            return capture(result.paymentIntent);
//        } else {
//            throw new Error('collectPayment(amountInPence) - Payment Intent cannot be obtained.');
//        }
//    }).then(function(response){
//        if (!response.error) {
//            console.log("Error: " + response.error);
//        } else {
//            throw new Error('capture(result.paymentIntent) - Unable to capture amount.');
//        }
//    }).catch(function(error) {
//    console.error("createAndCapturePayment() Error: " + error);
//    });



//    collectPayment(amount);
//    capture(paymentIntentId);
//    // After connection, activate the next step2
//    setTimeout(function() {
//    cardDetectBtn.dispatchEvent(new Event('click'));
//    }, delayInMilliseconds);
//}

function calTotalAmount(products) {
    let amountInPence = 0;

    products.forEach(function(product) {
        amountInPence += parseInt(product.price * 100);
        console.log("Total amountInPence: " + amountInPence);
    });
    return amountInPence;
}
