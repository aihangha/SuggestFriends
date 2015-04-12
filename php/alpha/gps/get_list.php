<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 // check for required fields
if (isset($_POST['long']) && isset($_POST['lat']) && isset($_POST['radius'])) {
    $long= floatval($_POST['long']);
	$lat = floatval($_POST['lat']);
	$radius = floatval($_POST['radius']);
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM gps  WHERE loc_long-'$long'<'$radius' AND loc_long+'$radius'>'$long'  AND loc_lat-'$lat'<'$radius' AND loc_lat+'$radius'>'$lat' AND last_online > (NOW() - INTERVAL 5 MINUTE") or die(mysql_error());
 
		if (mysql_num_rows($result) > 0) {
			// no products found
			$response["success"] = 1;
			$response["message"] = "Users found";
			$response["users"] = array();
			while ($row = mysql_fetch_array($result)) {
				array_push($response["users"], $row["email"]);
			}
			// echo no users JSON
			echo json_encode($response);
		} else{

			// no products found
			$response["success"] = 0;
			$response["message"] = "No user found";
		 
			// echo no users JSON
			echo json_encode($response);
		}

} else{
// required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>