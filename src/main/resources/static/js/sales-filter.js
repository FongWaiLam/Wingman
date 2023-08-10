function filterStore() {
  var input, filter, table, tr, td, i, txtValue;
  option = document.getElementById("storeId");
  filter = option.value.toUpperCase();

  table = document.getElementById("sales_record");
  tr = table.getElementsByTagName("tr");

  if (filter == "") {
    showFullList(tr);
    return;
  }

  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase() == filter) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}

function showFullList(tr) {
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      txtValue = td.textContent || td.innerText;
       tr[i].style.display = "";
    }
  }
}
