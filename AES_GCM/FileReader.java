package AES_GCM;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public String getFileContent() {
        JFileChooser chooser = new JFileChooser();
        Scanner in = null;
        String text = "";
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            try{
                in = new Scanner(selectedFile);
            }catch (FileNotFoundException e){
                System.out.println(e);
            }
            while (in.hasNextLine()){
                String line = in.nextLine();
                text += line;
            }
        }
        return text;
    }
}
