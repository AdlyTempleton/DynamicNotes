package pixlepix.dynamicnotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pixlepix on 9/6/15.
 */
public class GuiConvert extends JFrame {

    public GuiConvert() {

        initUI();
    }

    JButton convertButton;
    JEditorPane pane;

    public void initUI() {

        setTitle("Dynamic Notes");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        convertButton = new JButton("Convert");

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Main.convert(pane.getText());
            }
        });

        pane = new JEditorPane();

        Container contentPane = getContentPane();
        BorderLayout layout = new BorderLayout(0, 0);
        contentPane.setLayout(layout);

        contentPane.add(pane, BorderLayout.CENTER);
        contentPane.add(convertButton, BorderLayout.SOUTH);

    }
}