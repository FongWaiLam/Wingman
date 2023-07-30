function signOut() {
    const alert = document.getElementById("alert");
    fetch("/usms/signout", {
        method: "POST",
    })
    .then(response => {
        if (response.ok) {
            window.location.href = "/usms/login?logout=true"; // Send a param to login page to display alert.
        } else {
            alert("Logout failed. Please try again.");
        }
    })
    .catch(error => {
        // Handle fetch error
        console.error("Error occurred during logout:", error);
    });
}