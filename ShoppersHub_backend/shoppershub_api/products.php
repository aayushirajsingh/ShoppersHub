<?php 

include('conn.php');
$response = array();
if($conn){
    $sql = "select * from products";
    $result = pg_query($conn, $sql);
    if($result){
        header("Content-Type: JSON");
        $i = 0;
        while($row = pg_fetch_assoc($result)){
            $response[$i]['product_id'] = $row ['product_id'];
            $response[$i]['image'] = $row ['image'];
            $response[$i]['name'] = $row ['name'];
            $response[$i]['description'] = $row ['description'];
            $response[$i]['category'] = $row ['category'];
            $response[$i]['size'] = $row ['size'];
            $response[$i]['colour'] = $row ['colour'];
            $response[$i]['price'] = $row ['price'];
            $i++;
        }
        echo json_encode($response, JSON_PRETTY_PRINT);
    }
}
?>