<?php
include('conectar_BDD.php');
    $usuario = $_GET['usuario'];
    $password = $_GET['password'];
    $nombre = $_GET['nombre'];
    $app = $_GET['apellido_pat'];
    $apm = $_GET['apellido_mat'];
    $correo = $_GET['correo'];
    $dia = $_GET['dia'];
    $mes = $_GET['mes'];
    $anio = $_GET['anio'];
    $phone = $_GET['phone'];
      $consulta="INSERT INTO Users(User, Pass, Name, Last1, Last2, Day, Month, Year, Mail, Phone) VALUES('$usuario', '$password', '$nombre', '$app', '$apm', $dia, $mes, $anio, '$correo', '$phone');";
      $resultado=mysqli_query ($conecta, $consulta);
      $id=mysqli_insert_id($conecta);
      if($resultado){
        $consulta="SELECT * FROM Users WHERE IdUser='$id'";
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
