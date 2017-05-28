<?php
    require_once 'connection.php';
    
    if($con){
        $username = $_POST["username"];
        $password = $_POST["password"];
     
        $query ="INSERT INTO Travelers (username, password) VALUES ('$username', '$password')";
        if($con->query($query) === TRUE){
            $response = "succes";
        }
        else{
            $response = "Error: " . $con->error;
        }
    }
    
    echo json_encode($response); //echo gives tags
?>