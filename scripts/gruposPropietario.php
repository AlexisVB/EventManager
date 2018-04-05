<?php
include('conectar_BDD.php');
$id = $_GET['idUser'];

$consulta="SELECT * FROM Groups WHERE IdOwner='$id';";
$grupos=mysqli_query($conecta, $consulta);
if (!$grupos){
  //Mensaje de error
}
else{
  $nr=mysqli_num_rows($grupos);
  if($nr>=1){
    $json='{"grupos":[';
    for($cont=1;$cont<=$nr;$cont++){
      $row=mysqli_fetch_object($grupos);
      $encode=json_encode($row);
      if(is_string($encode)){
        if($cont<$nr){
          $json=$json.$encode.",\n";
        }
        else{
          $json=$json.$encode."\n";
        }
      }
    }
    $json=$json."]}";
    echo "$json";
  }
  else{
    $error="{}";
    echo $error;
  }
}
mysqli_close($conecta);
?>
