package AES_GCM;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame implements ActionListener{
    JFrame f;
    JLabel text_label, or_label;
    JButton next_button , choose_file_button;
    JTextField tf;

    JLabel cipher_text;

    public Frame(){
        f = new JFrame("AES GCM");
        String plain_text = "";

        text_label = new JLabel();
        text_label.setText("Type text here: ");
        text_label.setBounds(50, 50, 100, 30);

        tf = new JTextField();
        tf.setBounds(50,80,300,30);

        next_button = new JButton("Continue");
        next_button.setBounds(250,120,100,30);

        or_label = new JLabel();
        or_label.setText("OR ");
        or_label.setBounds(180, 170, 50, 30);

        choose_file_button = new JButton("Choose a file");
        choose_file_button.setBounds(50,220,300,30);

        next_button.addActionListener(this);
        choose_file_button.addActionListener(this);


        f.add(text_label);
        f.add(tf);
        f.add(next_button);
        f.add(or_label);
        f.add(choose_file_button);

        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = "";
        boolean success = false;
        if(e.getSource() == next_button){
            if(tf.getText() != ""){
                text = tf.getText();
                success = true;
            }
        }else if(e.getSource() == choose_file_button){
            text = new FileReader().getFileContent();
            success = true;
        }

        if(success){
            System.out.println(text);

            f.getContentPane().removeAll();
            f.getContentPane().repaint();

            cipher_text = new JLabel();
            cipher_text.setText(text);
            cipher_text.setBounds(50, 50, 100, 30 );
            f.add(cipher_text);

        }
    }
}
