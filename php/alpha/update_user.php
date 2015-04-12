<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['birthday']) && isset($_POST['cellphone'])&& isset($_POST['image'])&& isset($_POST['facebook'])) {
 
    $email = $_POST['email'];
    $password = $_POST['password'];
    $image= $_POST['image'];
	$birthday= $_POST['birthday'];
    $facebook = $_POST['facebook'];
    $cellphone = $_POST['cellphone'];
 
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE user SET facebook = '$facebook', password = '$password', birthday = '$birthday', cellphone = '$cellphone' , image = '$image' WHERE email = $email");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>