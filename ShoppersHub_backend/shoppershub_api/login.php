<?php
include('conn.php');
$username = $_REQUEST['username'];
$password = $_REQUEST['password'];

	
$qUserAssign  = pg_query($conn,"select * from users where username = '$username' and password = '$password'");
$array["data"] = array();

if(pg_num_rows($qUserAssign)>0)
{
	$rUserAssign = pg_fetch_array($qUserAssign);
	//echo '<pre>'; print_r($rUserAssign);exit;
	if(!empty($rUserAssign)) {
		$R['user_id'] = $rUserAssign['user_id'];
		$R['username'] = $rUserAssign['username'];
				
		array_push($array["data"],$R);
	}
}
if(!empty($array['data'])) {
	$array['message'] = 'Success';
}
else {
	$array['message'] = 'Fail';
}
echo json_encode($array);
?>