let password = document.getElementById("password");
let confirmPassword = document.getElementById("confirmPassword");
let warnDiv = document.getElementById("warn");

document.addEventListener('DOMContentLoaded', () => {

    confirmPassword.addEventListener('change', checkPassword);
});

function checkPassword() {
    let warningElement = document.getElementById("warning");
    if(password.value != confirmPassword.value && warningElement == null) {
        let warningElement = document.createElement("p");
        warningElement.innerHTML= "The passwords are not the same!";
        warningElement.id = "warning";
        warningElement.style.color = "red";
        warnDiv.appendChild(warningElement);
    } else if (password.value == confirmPassword.value && warningElement != null) {
        try{
            warningElement.remove();
        } catch(err) {
        }
    }
}