           function drawPieChart() {

            // Get products
            $.get("/usms/inventory/pie_chart", function(products, status){
                if (status == 'success') {
                console.log(products);
                console.log(typeof products);
                    pieChartSetUp(products);
                }
              });
          }

           function pieChartSetUp(products) {
                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Category');
                data.addColumn('number', 'Total Values');

                let rows = []
                products.forEach((product) => {
                    let data = [];
                    data.push(product.category, product.totalValue);
                    rows.push(data);
                })
                data.addRows(rows);

                // Set chart options
                var options = {'title':'Categories vs Total Values'};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('pie-chart'));

                function selectHandler() {
                  var selectedItem = chart.getSelection()[0];
                  if (selectedItem) {
                    var category = data.getValue(selectedItem.row, 0);
                    // Put category product data to products variable
                    getProductsOfCategory(category);
//                    alert('The user selected ' + category);
                  }
                }

                google.visualization.events.addListener(chart, 'select', selectHandler);
                setTimeout(function(){ chart.draw(data, options); }, 1000);
           }



          function getProductsOfCategory(category) {
              $.get("/usms/inventory/" + category,
                function(data, status){
                if (status == 'success') {
                    products = data;
                    replaceExistingTableData(products)
                }
              });
          }

          function replaceExistingTableData(products) {
            $("#tableBody").empty();
                products.forEach((product) => {
                  // Create a new row and cells
                    let row = document.createElement('tr');
                    let categoryCell = document.createElement('td');
                    let prodIdCell = document.createElement('td');
                    let prodNameCell = document.createElement('td');
                    let quantityCell = document.createElement('td');
                    let valueCell = document.createElement('td');
                    let storeIdCell = document.createElement('td');
                    let storeNameCell = document.createElement('td');
//                    let deleteButton = document.createElement('BUTTON');

                    // Set the text content and other attributes of each element
                    row.id = product.prodId + "-" + product.storeId;
                    categoryCell.textContent = product.category;
                    prodIdCell.textContent = product.prodId;
                    prodNameCell.textContent = product.prodName;
                    quantityCell.textContent = product.totalAmount;
                    valueCell.textContent = product.totalValue;
                    storeIdCell.textContent = product.storeId;
                    storeNameCell.textContent = product.storeName;
//                      deleteButton.textContent = 'Delete';
//                      deleteButton.classList = 'btn btn-danger btn-sm';
//                      deleteButton.type = 'button';
//                      deleteButton.setAttribute("onclick","removeStock('"+ epc + "');");

                    // Append the cells to the row
                    row.appendChild(categoryCell);
                    row.appendChild(prodIdCell);
                    row.appendChild(prodNameCell);
                    row.appendChild(quantityCell);
                    row.appendChild(valueCell);
                    row.appendChild(storeIdCell);
                    row.appendChild(storeNameCell);
//                      row.appendChild(deleteButton);
                    // Append the row to the table body
                    $("#tableBody").append(row);
                })

          }
