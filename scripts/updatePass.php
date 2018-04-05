<?php
  include('conectar_BDD.php');
  $id = $_GET['id'];
  $password = $_GET['password'];
  $consulta="UPDATE Users SET Pass='$password' WHERE idUser=$id;";
  $resultado=mysqli_query($conecta, $consulta);
  if($resultado){
    $consulta="SELECT Pass FROM Users WHERE idUser=$id;";
    $resultado=mysqli_query($conecta, $consulta);
    if($resultado){
      $nr=mysqli_num_rows($resultado);
      if($nr>=1){
        $arreglo = mysqli_fetch_object($resultado);
        $encode = json_encode($arreglo);
        if(is_string($encode)){
          $json=$encode."\n";
        }
        echo "$json";
      }else{
        //Mensaje de error
      }
    }
  }
mysqli_close($conecta);

 ?>
