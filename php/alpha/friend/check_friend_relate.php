<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_GET['user1']) && isset($_GET['user2'])) {
    $user1 = $_GET['user1'];
    $user2= $_GET['user2'];
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("SELECT * FROM friend WHERE ((user1='$user1' AND user2='$user2')OR(user1='$user2' AND user2='$user1'))");
 
    // check if row inserted or not
    if ($result&& mysql_num_rows($result) > 0) {
        // successfully inserted into database
        $response["success"] = 1;
			$response["message"] = "Friend found";
			$response["friend"] = array();
			while ($row = mysql_fetch_array($result)) {
				$friend = array();
				$friend['id'] = $row["id"];				
				$friend['user1'] = $row["user1"];
				$friend['user2'] = $row["user2"];	
				$friend['isaccept'] = $row["isaccept"];	
				$friend['isreaded'] = $row["isreaded"];	
				array_push($response["friend"],$friend);
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