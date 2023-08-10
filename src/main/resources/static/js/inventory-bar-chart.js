    // Reference to Google Chart Visualization API

          function drawBarChart() {
            // Get products
            $.get("/usms/inventory/bar_chart", function(products, status){
                if (status == 'success') {
                console.log("bar chart data: " + products);
                console.log(typeof products);
                    barChartSetUp(products);
                }
              });
          }

           function barChartSetUp(products) {
                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('number', 'Store ID');
                data.addColumn('number', 'Total Amount (Qty)');
                data.addColumn('number', 'Total Value (£)');

                let rows = []
                products.forEach((product) => {
                    let data = [];
                    if (product.storeId == 0) return;
                    console.log(product.storeName + " [ID: " + product.storeId + "]" + " " + product.totalAmount + " " + product.totalValue);
                    data.push(product.storeId, product.totalAmount, product.totalValue);
                    rows.push(data);
                });
                data.addRows(rows);

                // Set chart options
                  var options = {
                    title: 'Store Inventory Amount and Value',
                    chartArea: {width: '50%'},
                    colors: ['#2e41d1', '#7adcff'],
                    hAxis: {
                      title: 'Total',
                      minValue: 0
                    },
                    vAxis: {
                      title: 'Store'
                    }
                  };

                // Instantiate and draw
                var chart = new google.visualization.BarChart(document.getElementById('bar-chart'));

                  function selectHandler() {
                  var selectedItem = chart.getSelection()[0];
                  if (selectedItem) {
                    var storeId = data.getValue(selectedItem.row, 0);
                    getProductsOfStoreId(storeId);
//                    alert('The user selected store ' + storeId);
                  }
                }
                  google.visualization.events.addListener(chart, 'select', selectHandler);
                  setTimeout(function(){ chart.draw(data, options); }, 1000);
           }

            function getProductsOfStoreId(storeId) {
                          $.get("/usms/inventory/store/" + storeId,
                            function(data, status){
                            if (status == 'success') {
                                products = data;
                                replaceExistingTableData(products);
                            }
                          });
                      }
