<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 date_default_timezone_set('Asia/Ho_Chi_Minh');
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['semail'])&& isset($_POST['slong'])&& isset($_POST['slat']) ){
 
    $email = $_POST['semail'];
	$mlong = floatval($_POST['slong']);
	$mlat = floatval($_POST['slat']);
	$nowFormat = date('Y-m-d H:i:s');
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
	$result = mysql_query("SELECT loc_long FROM gps WHERE email = '$email'");
	if(mysql_num_rows($result) == 0) {
		$result = mysql_query("INSERT INTO gps(email, loc_long, loc_lat) VALUES('$email', '$mlong', '$mlat'");
	} else {
		$result = mysql_query("UPDATE gps SET loc_lat='$mlat', loc_long='$mlong', last_online='$nowFormat' WHERE email='$email'");
	}
    
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Create GPS Entry successful.";
 
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
 
    // echoing JSON response
    echo json_encode($response);
}
?>