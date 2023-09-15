<?php 
include('conn.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $user_id = $_REQUEST['user_id'];
    $product_id = $_REQUEST['product_id'];
    $count = $_REQUEST['count'];
    $colour = $_REQUEST['colour'];
    $size = $_REQUEST['size'];

    $insertQuery = "insert into cart(user_id, product_id, count, colour, size) VALUES ('$user_id', '$product_id', '$count', '$colour', '$size')";
    $result = pg_query($conn, $insertQuery);

    if ($result) {
        $response['message'] = 'Success';
    } else {
        $response['message'] = 'Fail';
    }
    echo json_encode($response);
}

if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    if (isset($_GET['user_id'])) {

        $user_id = $_GET['user_id'];

        $fetchQuery = "SELECT cart.cart_id, cart.user_id, cart.product_id, products.image, products.name, products.price, cart.count, cart.colour, cart.size FROM cart INNER JOIN products ON cart.product_id = products.product_id WHERE cart.user_id = $user_id";
        $qProductsCart = pg_query($conn,$fetchQuery);
        $response = array();

        if($qProductsCart){
            header("Content-Type: JSON");
            $i = 0;
            while($row = pg_fetch_assoc($qProductsCart)){
                $response[$i]['cart_id'] = $row ['cart_id'];
                $response[$i]['user_id'] = $row ['user_id'];
                $response[$i]['product_id'] = $row ['product_id'];
                $response[$i]['image'] = $row ['image'];
                $response[$i]['name'] = $row ['name'];
                $response[$i]['price'] = $row ['price'];
                $response[$i]['count'] = $row ['count'];
                $response[$i]['colour'] = $row ['colour'];
                $response[$i]['size'] = $row ['size'];
                $i++;
            }
            echo json_encode($response);
        }
    }
    elseif (isset($_GET['cart_id'])) {

        $cart_id = $_GET['cart_id'];

        $deleteQuery = "DELETE FROM cart WHERE cart_id = $cart_id";

        $result = pg_query($conn, $deleteQuery);

        if ($result) {
            $response['message'] = 'Success';
        } else {
            $response['message'] = 'Fail';
        }
        echo json_encode($response);
    }
}
?>