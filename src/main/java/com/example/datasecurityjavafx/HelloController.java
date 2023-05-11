package com.example.datasecurityjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import src.AES_GCM.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloController {
    @FXML
    private TextArea textArea;
    @FXML
    private Label result;

    @FXML
    protected void browseFiles() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        Scanner input = new Scanner(selectedFile);
        StringBuilder plaintext = new StringBuilder();
        while (input.hasNextLine()){
            plaintext.append(input.nextLine());
        }
        System.out.println(plaintext);
        run(String.valueOf(plaintext), true);
    }
    @FXML
    protected void encryptTextArea(){
        String txt = textArea.getText();
        System.out.println(txt);
        run(txt, true);
    }

    @FXML
    protected void decryptTextArea(){
        String txt = textArea.getText();
        System.out.println(txt);
        run(txt, false);
    }
    protected void run(String plaintext, boolean isEncryption){
        System.out.println("------------------------------");
        Main m = new Main();
        String res = m.getText(plaintext, isEncryption);
        result.setText(res);
    }
}