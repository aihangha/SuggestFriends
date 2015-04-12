<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 // check for required fields
if (isset($_GET['email'])) {
	$receiver = $_GET['email'];
// include db connect class
require_once __DIR__ . '/../db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
	$result = mysql_query("SELECT * FROM (SELECT *FROM message  WHERE (receiver=$receiver AND msgstate='uploaded')) sub ORDER BY timestamp ASC");
	
		if ($result && mysql_num_rows($result) > 0) {
		
			$response["success"] = 1;
			$response["message"] = "Message found";
			$response["entry"] = array();
			while ($row = mysql_fetch_array($result)) {
				$message = array();
				$message['id'] = $row["id"];
				$message['sender'] = $row["sender"];
				$message['content'] = $row["content"];
				array_push($response["entry"],$message);
			}
			// echo no users JSON
			echo json_encode($response);
		} else{

			// no products found
			$response["success"] = 0;
			$response["message"] = "No feed found";
		 
			// echo no users JSON
			echo json_encode($response);
		}

} else{
// required field is missing
 //   $response["success"] = 0;
 //   $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>