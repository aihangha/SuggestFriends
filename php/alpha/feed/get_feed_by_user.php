<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 // check for required fields
if (isset($_GET['email'])) {
    $email = $_GET['email'];
// include db connect class
require_once __DIR__ . '/../db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
//$result = mysql_query("SELECT *FROM feed ");
	$result = mysql_query("SELECT * FROM (SELECT *FROM feed WHERE feedowner=$email ORDER BY lastupdate DESC LIMIT 20) sub ORDER BY lastupdate ASC");
 
		if ($result && mysql_num_rows($result) > 0) {
			// no products found
			$response["success"] = 1;
			$response["message"] = "Feed found";
			$response["feeds"] = array();
			while ($row = mysql_fetch_array($result)) {
				$feed = array();
				$feed['feedID'] = $row["feedID"];				
				$feed['feedowner'] = $row["feedowner"];
				$feed['feedcontent'] = $row["feedcontent"];
				$feed['feedtimestamp'] = $row["feedtimestamp"];
				$feed['whocansee'] = $row["whocansee"];
				$feed['imagename'] = $row["imagename"];
				$feed['lastupdate'] = $row["lastupdate"];
				$thisfeedid = $row["feedID"];
				$likeresult = mysql_query("SELECT * FROM `tblike` WHERE (feedID=$thisfeedid AND email=$email)");							
				if($likeresult){
					if(mysql_num_rows($likeresult) > 0){
						$feed['liked'] ='1';
					} else{
						$feed['liked'] ='0';
					}
				} else {
					$feed['liked'] ='0';
				}
				
				$likeNO = mysql_query("SELECT * FROM `tblike` WHERE feedID=$thisfeedid");
				if($likeNO){
					$feed['NoLike'] = mysql_num_rows($likeNO);					
				} else{
					$feed['NoLike'] = 0;
				}
				
				
				
				$commentNO = mysql_query("SELECT * FROM `comment` WHERE feedID=$thisfeedid");
				if($commentNO){
					$feed['NoComment'] = mysql_num_rows($commentNO);					
				} else{
					$feed['NoComment'] = 0;
				}
				
				array_push($response["feeds"],$feed);
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