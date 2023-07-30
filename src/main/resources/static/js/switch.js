document.addEventListener('DOMContentLoaded', () => {
    const switch1 = document.getElementById('switch1');
    switch1.addEventListener('change', switchPage);
});

function switchPage() {

    // If change to checked, signout and switch to checkout page.
    if (switch1.checked) {
        fetch("/usms/signout", {
                method: "POST",
        })
        .then(response => {
            if (response.ok) {
                window.location.href = "/checkout";
            } else {
                alert("Switch failed. Please try again.");
            }
        })
        .catch(error => {
            // Handle fetch error
            console.error("Error occurred during switch:", error);
        });
    }

    // If change to unchecked, switch to login page.
    if (!switch1.checked) {
       window.location.href = "/usms/login";
    }
}