document.addEventListener('DOMContentLoaded', () => {

    // Reference to Google Chart Visualization API
     // Load the Visualization API and the piechart package.
          google.charts.load('current', {'packages':['corechart']});

          // Set a callback to run when the Google Visualization API is loaded.
          google.charts.setOnLoadCallback(drawChart);

          // Callback that creates and populates a data table,
          // instantiates the pie chart, passes in the data and
          // draws it.

          function drawChart() {

            // Get Sales Record
            $.get("/usms/sales/line-chart", function(sales, status){
                if (status == 'success') {
                console.log(sales);
                console.log(typeof sales);
                    chartSetUp(sales);
                }
              });
          }

           function chartSetUp(sales) {
                // Create the data table.
                var data = new google.visualization.DataTable();

                let rows = []
                data.addColumn('string', 'Year-Month');
                data.addColumn('number', 'Total Sales (£)');

                sales.forEach((sale) => {
                    let eachData = [];
                    eachData .push(sale.year+ '-' + sale.month, sale.totalValue);
                    rows.push(eachData );
                })
                data.addRows(rows);

                // Set chart options
                var options = {
                  title: 'Sales Trend of All Stores This Year',
                  chartArea: {width: '80%'},
                  colors: ['#2e41d1'],
                  hAxis: {
                    title: 'Year-Month'
                  },
                  vAxis: {
                    title: 'Total Sales (£)',
                    minValue: 0
                  }
                  };

                // Instantiate and draw our chart, passing in some options.
//                var chart = new google.visualization.LineChart(document.getElementById('line-chart'));
                   var chart = new google.visualization.LineChart(document.getElementById('line-chart'));

                chart.draw(data, options);
           }



//          function getProductsOfCategory(category) {
//              $.get("/usms/inventory/" + category,
//                function(data, status){
//                if (status == 'success') {
//                    products = data;
//                    replaceExistingTableData()
//                }
//              });
//          }
//
//          function replaceExistingTableData() {
//            $("#tableBody").empty();
//                products.forEach((product) => {
//                  // Create a new row and cells
//                    let row = document.createElement('tr');
//                    let categoryCell = document.createElement('td');
//                    let prodIdCell = document.createElement('td');
//                    let prodNameCell = document.createElement('td');
//                    let quantityCell = document.createElement('td');
//                    let valueCell = document.createElement('td');
//                    let storeIdCell = document.createElement('td');
//                    let storeNameCell = document.createElement('td');
////                    let deleteButton = document.createElement('BUTTON');
//
//                    // Set the text content and other attributes of each element
//                    row.id = product.prodId + "-" + product.storeId;
//                    categoryCell.textContent = product.category;
//                    prodIdCell.textContent = product.prodId;
//                    prodNameCell.textContent = product.prodName;
//                    quantityCell.textContent = product.totalAmount;
//                    valueCell.textContent = product.totalValue;
//                    storeIdCell.textContent = product.storeId;
//                    storeNameCell.textContent = product.storeName;
////                      deleteButton.textContent = 'Delete';
////                      deleteButton.classList = 'btn btn-danger btn-sm';
////                      deleteButton.type = 'button';
////                      deleteButton.setAttribute("onclick","removeStock('"+ epc + "');");
//
//                    // Append the cells to the row
//                    row.appendChild(categoryCell);
//                    row.appendChild(prodIdCell);
//                    row.appendChild(prodNameCell);
//                    row.appendChild(quantityCell);
//                    row.appendChild(valueCell);
//                    row.appendChild(storeIdCell);
//                    row.appendChild(storeNameCell);
////                      row.appendChild(deleteButton);
//                    // Append the row to the table body
//                    $("#tableBody").append(row);
//                })
//
//          }


});

