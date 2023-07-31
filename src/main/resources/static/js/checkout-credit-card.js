// Declared in another js file
let cardDetectStatusImg = document.getElementById("card-detect-status");
//let cardDetectBtn = document.getElementById("card-detect-button");


function stripeCreditCardScan() {

    var delayInMilliseconds = 5000; //5 second

    setTimeout(function() {
    cardDetectStatusImg.src = "/images/payment-status-green-icon.svg";
    }, delayInMilliseconds);

    setTimeout(function() {
    cardDetectBtn.dispatchEvent(new Event('click'));
    }, delayInMilliseconds);
}