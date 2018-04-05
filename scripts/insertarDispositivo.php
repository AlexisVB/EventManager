<?php
include('conectar_BDD.php');
    $mac = $_GET['mac'];
    $idUser = $_GET['idUser'];
    $idGroup = $_GET['idgrupo'];
      $consulta="INSERT INTO Devices(Mac, Iduser) VALUES('$mac', '$idUser');";
      $resultado=mysqli_query ($conecta, $consulta);
      $idDevice=mysqli_insert_id($conecta);
        if($resultado){
          $consultaDos="INSERT INTO RelDeviceGroup(IdDevice, IdGroup) VALUES('$idDevice', '$idGroup');";
          $resultadoDos=mysqli_query ($conecta, $consultaDos);
          $idRel=mysqli_insert_id($conecta);
          if($resultadoDos){
            $consultaTres="SELECT * FROM RelDeviceGroup WHERE IdRel='$idRel'";
            $resultadoTres=mysqli_query($conecta, $consultaTres);
            $nr=mysqli_num_rows($resultadoTres);
            if($nr>=1){
              $arreglo = mysqli_fetch_object($resultadoTres);
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
