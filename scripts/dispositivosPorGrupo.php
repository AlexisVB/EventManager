<?php

include('conectar_BDD.php');
$grupo = $_GET['idGroup'];
$consulta="SELECT D.IdDevice, D.Mac, U.IdUser, U.User, U.Name, U.Last1, U.Last2, U.Phone FROM RelDeviceGroup R INNER JOIN Devices D ON R.IdDevice = D.IdDEvice INNER JOIN Users U ON D.IdUser = U.IdUser WHERE R.IdGroup =  $grupo;";
$dispositivos=mysqli_query($conecta, $consulta);
if (!$dispositivos){
  echo "error";
}
else{
  $nr=mysqli_num_rows($dispositivos);
  if($nr>=1){
    $json='{"Tracking":[';
    for($cont=1;$cont<=$nr;$cont++){
      $row=mysqli_fetch_object($dispositivos);
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
