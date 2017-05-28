<?php
    require_once 'connection.php';
    
    if($con){
        $username = $_POST["username"]; //recieves the data
        $password = $_POST["password"];
        
        $query = "SELECT * FROM Travelers WHERE username like '$username' AND password like '$password'"; //looks for matches in the db
        $result = mysqli_query($con, $query);

        if($result){
            if(mysqli_num_rows($result) == 1){

                $response = array();  
                
                while(mysqli_stmt_fetch($result)){ //returns the data
                    $response["success"] = true;  
                    $response["username"] = $username;
                    $response["password"] = $password;
                }
                
            }
            else{
                $response = $query;
                //$response = "Wrong username or password";
            }
        }
    }
    
    echo json_encode($response); //sends JSON response
?>