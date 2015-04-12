<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_GET['user1'])) {
    $user1 = $_GET['user1'];
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("SELECT * FROM follow WHERE user1='$user1' ");
 
    // check if row inserted or not
    if ($result&& mysql_num_rows($result) > 0) {
        // successfully inserted into database
        $response["success"] = 1;
			$response["message"] = "Follow found";
			$response["follow"] = array();
			while ($row = mysql_fetch_array($result)) {
				$follow = array();
				$follow['id'] = $row["id"];				
				$follow['user1'] = $row["user1"];
				$follow['user2'] = $row["user2"];	
				array_push($response["follow"],$follow);
			}
			// echo no users JSON
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