<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['semail']) && isset($_POST['scontent'])&& isset($_POST['swhocansee'])) {
/// if (isset($_POST['email']) ) {
    $email = $_POST['semail'];
    $content= $_POST['scontent'];
	$whocansee = $_POST['swhocansee'];
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO feed(feedowner, feedcontent,whocansee) VALUES('$email', '$content','$whocansee')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Feed successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
 //    echoing JSON response
    echo json_encode($response);
}
?>