<?php
include('conectar_BDD.php');
    $expositor = $_GET['expositor'];
    $date = $_GET['date'];
    $idStand = $_GET['idStand'];
    $idOwner = $_GET['idOwner'];
      $consulta="INSERT INTO Speeches(Expositor, Date, IdStand, IdOwner) VALUES('$expositor', '$date','idStand','idOwner');";
      $resultado=mysqli_query ($conecta, $consulta);
      $id=mysqli_insert_id($conecta);
      if($resultado){
        $consulta="SELECT * FROM Speeches WHERE IdSpeech='$id'";
        $resultado=mysqli_query($conecta, $consulta);
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
mysqli_close($conecta);
 ?>
