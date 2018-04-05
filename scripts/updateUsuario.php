<?php

include('conectar_BDD.php');

$id = $_GET['id'];
$usuario = $_GET['usuario'];
$nombre = $_GET['nombre'];
$app = $_GET['apellido_pat'];
$apm = $_GET['apellido_mat'];
$correo = $_GET['correo'];
$dia = $_GET['dia'];
$mes = $_GET['mes'];
$anio = $_GET['anio'];
$phone = $_GET['phone'];

$consulta="UPDATE Users SET User='$usuario', Name='$nombre', Last1='$app', Last2='$apm', Day='$dia', Month='$mes', Year='$anio', Mail='$correo', Phone='$phone' WHERE idUser='$id';";
$resultado=mysqli_query($conecta, $consulta);
if($resultado){
  $consulta="SELECT * FROM Users WHERE IdUser=$id;";
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
} else {

}
mysqli_close($conecta);
 ?>
