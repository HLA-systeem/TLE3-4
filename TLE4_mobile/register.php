<?php
    $con = mysqli_connect("localhost", "id1706548_doglas", "Vliegende_Surinamer", "id1706548_tle4");
    
    $username = $_POST["username"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "INSERT INTO user (username, password) VALUES (?, ?)"); //places it into the db
    mysqli_stmt_bind_param($statement, "siss", $username, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  //retruns "sussces" in Json
    
    echo json_encode($response);
?>