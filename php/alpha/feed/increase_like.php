<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['fid'])) {
 
    $feedid = $_POST['fid'];
 
 
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE feed SET NoLike=NoLike+1 WHERE feedID=$feedid");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Increase like successfully";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
		$response["success"] = 0;
		$response["message"] = "Increase like fail";
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "missing require input";
 
    // echoing JSON response
    echo json_encode($response);
}
?>