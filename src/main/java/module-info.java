module com.example.simplechat {
  requires javafx.controls;
  requires javafx.fxml;
  requires lombok;

  opens com.example.simplechat to javafx.fxml;
  exports com.example.simplechat;
}