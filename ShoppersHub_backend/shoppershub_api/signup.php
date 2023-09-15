<?php

include('conn.php');

$email = $_REQUEST['email'];
$username = $_REQUEST['username'];
$password = $_REQUEST['password'];

$insertQuery = "insert into users (email, username, password) VALUES ('$email', '$username', '$password')";

$result = pg_query($conn, $insertQuery);

if ($result) {
    $response['message'] = 'Success';
} else {
    $response['message'] = 'Fail';
}

echo json_encode($response);

?>