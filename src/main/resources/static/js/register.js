let submitButton = document.getElementById("form-submission");

document.addEventListener('DOMContentLoaded', () => {

    submitButton.addEventListener('click', submitRegisterForm);
});

function submitRegisterForm() {
    event.preventDefault()

    // Check password
    let checkPassword = document.getElementById("password");
    let checkConfirmPassword = document.getElementById("confirmPassword");
    if(checkPassword.value != checkConfirmPassword.value) {
        alert("The passwords do not match.");
        return;
    }

    console.log("submitRegister Form");

    let url = 'http://localhost:8080/usms/register';
    let firstname = document.getElementById('firstname').value;
    let lastname = document.getElementById('lastname').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let role = document.getElementById('role').value;

    let profile = document.getElementById('photoFile')
    if (profile.files.length == 0) {
        alert("Please provide a profile picture.");
        return;
    }
    profile = profile.files[0];

    if (firstname == "" || lastname == ""|| email == ""|| password == ""|| role == "") {
        alert("Please ensure all the information has been filled in before submission.");
        return;
    }

    console.log("submit Form");

    let formData = new FormData();
    formData.append('firstname', firstname);
    formData.append('lastname', lastname);
    formData.append('email', email);
    formData.append('password', password);
    formData.append('role', role);
    formData.append('profile', profile);

    fetch(url, {
    method: 'POST',
//    headers:{'Accept' : 'application/json',},
    body: formData,
    })
    .then(response => {
    if (response.ok) {
       alert("User " + firstname + lastname + " [Username: " + email + "] successfully created!");
       window.location.href = "/usms/users";
    }
    })
    .catch((error) => {
         console.error('Error:', error);
       });
}