<?php
include('conectar_BDD.php');
$usuario = $_GET['usuario'];
$password = $_GET['password'];
$consulta="SELECT * FROM Users WHERE User='$usuario' AND Pass='$password';";
$validar=mysqli_query($conecta, $consulta);
if (!$validar){
  echo "error";
}
else{
  $nr=mysqli_num_rows($validar);
  if($nr>=1){
    $arreglo = mysqli_fetch_object($validar);
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
