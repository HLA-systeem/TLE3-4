<?php
    require_once 'config.php';
    $con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE); //link to the db
    
    if($con != true){
        $response = "Connection to database failed.";
    }
?>