<?php
include('conectar_BDD.php');
    $name = $_GET['name'];
    $idOwner = $_GET['idOwner'];
      $consulta="INSERT INTO Groups(IdOwner, Name) VALUES('$idOwner', '$name');";
      $resultado=mysqli_query ($conecta, $consulta);

      $id=mysqli_insert_id($conecta);
      if($resultado){
        $consulta="SELECT * FROM Groups WHERE IdGroup='$id'";
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
