  <?php
  include('conectar_BDD.php');
      $texto_nota = $_GET['Contain'];
      $hora_nota = $_GET['Date'];
      $id_conferencia = $_GET['IdSpeech'];
      $id_usuario = $_GET['IdUser'];
        $consulta="INSERT INTO Notes(idNote, Contain, Date, IdSpeech, IdUser) VALUES('NULL', '$texto_nota', '$hora_nota', '$id_conferencia', '$id_usuario');";
        $resultado=mysqli_query ($conecta, $consulta);
        $id=mysqli_insert_id($conecta);
        if($resultado){
          $consulta="SELECT * FROM Notes WHERE idNote='$id'";
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
