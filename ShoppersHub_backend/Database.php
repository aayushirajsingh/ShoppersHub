<?php
    include('connection.php');
    $sql = "select * from products order by product_id asc ";
	$select_query = pg_query($dbconn3,$sql);
	$product_data = pg_fetch_all($select_query);
    // echo "<pre>" ;
    // print_r($product_data);
?> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopper's Hub Database</title>
    <style>
        body {
            font-family: "Poppins", Arial, sans-serif;
            font-size: 16px;
            line-height: 1.8;
            font-weight: normal;
            background: #F9F7F2;
            text-align: left;
            margin: 5%;
            color: rgb(0, 0, 0); 
        }
        img {
            vertical-align: middle;
            border-style: none; 
        }
        table {
            border-collapse: collapse; 
        }
        h1, h2, h3, h4, h5,
        .h1, .h2, .h3, .h4, .h5 {
            line-height: 1.5;
            font-weight: 400;
            font-family: "Poppins", Arial, sans-serif;
            color: #44000A; 
        }
        .img {
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center center;
        }
        .table-wrap {
            overflow-x: scroll; 
        }
        .table {
            min-width: 1000px !important;
            width: 100%;
            background: #fff;
            -webkit-box-shadow: 0px 5px 12px -12px rgba(0, 0, 0, 0.29);
            -moz-box-shadow: 0px 5px 12px -12px rgba(0, 0, 0, 0.29);
            box-shadow: 0px 5px 12px -12px rgba(0, 0, 0, 0.29); 
        }
        .table thead.thead-primary {
            background: #FCCA6F; 
        }
        .table thead th {
            border: none;
            padding: 30px;
            font-size: 13px;
            font-weight: 500;
            color: white; 
        }
        .table tbody tr {
            margin-bottom: 10px; 
        }
        .table tbody th, .table tbody td {
            border: none;
            padding: 30px;
            font-size: 14px;
            background: #fff;
            border-bottom: 4px solid #f8f9fd;
            vertical-align: middle; 
        }
        .table tbody td.quantity {
            width: 10%; 
        }
        .table tbody td .img {
            width: 100px;
            height: 80px; 
        }
        .table tbody td .close span {
            font-size: 12px;
            color: #dc3545; 
        }
        .bold-text {
            font-weight: bold;
        }
    </style>
</head>

<body>
    <section>
        
        <div class="container">
            <div>
                <h2 style="text-align: center;">
                    Shooper's HUB Database
                </h2>
            </div>
            
            <div class="row">
                <div class="table-wrap">                    
                    <table class="table" id ="row_id">
                        <thead class="thead-primary">
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Name & Description</th>
                                <th>Category</th>
                                <th>Size</th>
                                <th>Colour</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        <?php foreach($product_data as $row) { ?>
                        <tr>
                            <td>
                                <?php echo $row['product_id']; ?>
                            </td>
            
                            <td>
                                <div class="img" style="background-image: url(images/<?php echo $row['image']; ?>);">
                                </div>
                            </td>

                            <td>
                                <div>
                                    <div class="bold-text" >
                                        <?php echo $row['name']; ?>
                                    </div>
                                    <div>
                                        <?php echo $row['description']; ?>
                                    </div>
                                </div>
                            </td>

                            <td>
                                <div>
                                    <?php echo $row['category']; ?>
                                </div>
                            </td>

                            <td>
                                <div>
                                    <?php echo $row['size']; ?>
                                </div>
                            </td>

                            <td>
                                <?php echo $row['colour']; ?>
                            </td>
                            
                            <td>
                                <?php echo $row['price']; ?>
                            </td>
                        </tr>
                        <?php } ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</body>
</html>