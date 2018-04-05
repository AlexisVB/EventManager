<?php
  $local="localhost";
  $usuario="EventManagerUser";
  $contra="123eventmanager";
  $bd="EventManagerBD";
  $conecta= new mysqli($local, $usuario, $contra, $bd);
  if ($conecta->connect_errno){
    die('Error de Conexión (' . $mysqli->connect_errno . ') '
            . $mysqli->connect_error);
  }
  return $conecta;
?>
