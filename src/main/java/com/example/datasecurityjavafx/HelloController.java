package com.example.datasecurityjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    private TextArea textArea;

    @FXML
    protected void browseFiles() {

    }
    @FXML
    protected void encryptTextArea(){
        String txt = textArea.getText();
        System.out.println(txt);
    }
}