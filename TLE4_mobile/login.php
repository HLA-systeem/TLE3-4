<?php
    $con = mysqli_connect("localhost", "id1706548_doglas", "Vliegende_Surinamer", "id1706548_tle4"); //link to the db
    
    $username = $_POST["username"]; //recieves the data
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?"); //looks for matches in the db
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $username, $password);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){ //returns the data
        $response["success"] = true;  
        $response["username"] = $username;
        $response["password"] = $password;
    }
    
    echo json_encode($response); //sends JSON response
?>