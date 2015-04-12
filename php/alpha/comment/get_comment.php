<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 // check for required fields
if (isset($_GET['feedid'])) {
    $feedid = $_GET['feedid'];
// include db connect class
require_once __DIR__ . '/../db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
	$result = mysql_query("SELECT * FROM (SELECT *FROM comment WHERE feedid=$feedid ) sub ORDER BY createdate ASC");
 
		if ($result && mysql_num_rows($result) > 0) {
			// no products found
			$response["success"] = 1;
			$response["message"] = "Comments found";
			$response["comments"] = array();
			while ($row = mysql_fetch_array($result)) {
				$cmt = array();
				$cmt['id'] = $row["id"];				
				$cmt['feedid'] = $row["feedid"];
				$cmt['email'] = $row["email"];
				$cmt['content'] = $row["content"];
				$cmt['createdate'] = $row["createdate"];
				array_push($response["comments"],$cmt);
			}
			// echo no users JSON
			echo json_encode($response);
		} else{

			// no products found
			$response["success"] = 0;
			$response["message"] = "No comment found";
		 
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