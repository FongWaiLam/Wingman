const submitButton = document.getElementById('form-submission');

document.addEventListener('DOMContentLoaded', () => {

// If logged out, show an alert.
    const urlParams = new URLSearchParams(window.location.search);
    const logoutParam = urlParams.get('logout');

    if (logoutParam === 'true') {
        const alert = document.getElementById("alert");
        alert.classList.remove("hidden");
        console.log("hidden class removed");

        const myTimeout = setTimeout(function() {
            alert.classList.add("hidden");
        }, 5000);
    }


// Add event listener to submit button
    submitButton.addEventListener('click', submitForm);
    });

    function submitForm(event) {
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let data = {
            email: email,
            password: password,
        };

    fetch("/usms/signin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json", // Set the Content-Type header to application/json
        },
        body: JSON.stringify(data),
    })
    .then(response => {
        if (response.ok) {
            // Handle successful response (e.g., redirect to a new page)
            window.location.href = "/usms"; // Replace "/success" with the desired URL
        } else {
            // Handle error response (e.g., display an error message)
            alert("Login failed. Please try again.");
        }
    })
    .catch(error => {
        // Handle fetch error
        console.error("Error occurred during login:", error);
    });
};
