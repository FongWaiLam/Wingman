function preview(event, id) {
    if(event.target.files.length > 0) {
        let preview = document.getElementById(id);
        preview.replaceChildren();
        for (let i = 0; i < event.target.files.length; i++) {
            var src = URL.createObjectURL(event.target.files[i]);
            let imgElement = document.createElement("img");
            imgElement.src = src;
            imgElement.style.display = "block";
            imgElement.classList.add("thumbnail");
            preview.appendChild(imgElement);
        }
    }
}




