<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['semail']) && isset($_POST['scontent'])&& isset($_FILES['file'])&&isset($_POST['swhocansee'])) {
    $email = $_POST['semail'];
    $content= $_POST['scontent'];
	$whocansee = $_POST['swhocansee'];
	 //echo "A file was uploaded\n".
    // include db connect class
    require_once __DIR__ . '/../db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT(); 
	$result = mysql_query("INSERT INTO feed(feedowner, feedcontent,whocansee) VALUES('$email', '$content','$whocansee')"); 	 
	 
	$id = mysql_insert_id();
	//echo $id;
	$imagename = $id.'.jpg';
	echo $imagename;
	$result = mysql_query("UPDATE feed SET imagename='$imagename' WHERE feedID=$id");
	 //$fileName = $_FILES['file']['name'];
	 $imageData = fread(fopen($_FILES['file']['tmp_name'],"r"), $_FILES['file']['size']); 
	 file_put_contents('../images/'.$id.'.jpg', $imageData);
	 
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