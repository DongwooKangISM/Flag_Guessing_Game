import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {
    private JPanel mainPanel;
    private JButton submitButton;
    private JTextField inputTextField;
    private JLabel imageLabel;
    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JButton newGameButton;
    private JList<String> countriesList;
    private JScrollPane countriesScrollPane;
    private JFrame frame;

    public MainForm() {
        Model model = new Model();
        model.newQuiz();


        mainPanel.setPreferredSize(new Dimension(600,340));

        ImageIcon icon = new ImageIcon("images/" + model.getNextQuestion().fileName);
        imageLabel.setIcon(icon);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<String> countries = model.getCountries();
        countries.forEach(listModel::addElement); // Don't want to keep shuffle.

        countriesList.setModel(listModel);
        countriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        resultLabel.setText("Right Answer");
        scoreLabel.setText(model.getNumCorrect() + " / " + model.getNumAttempted());

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the selected country
                String selected = countriesList.getSelectedValue();
                if(selected != null) {
                    String correct = model.checkAnswer(countriesList.getSelectedValue());
                    // Pass it to the model
                    resultLabel.setText(correct);
                    scoreLabel.setText(model.getNumCorrect() + " / " + model.getNumAttempted());

                    ImageIcon icon = new ImageIcon("images/" + model.getNextQuestion().fileName);
                    imageLabel.setIcon(icon);

                    inputTextField.setText(" ");
                    inputTextField.requestFocus();
                }


            }
        });



        inputTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countriesList.setModel(updateListModel(countries));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                countriesList.setModel(updateListModel(countries));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        //Create JFrame
        //Add the Panel
        //SetVisible etc
        //Separate thread for runnign UI
        frame = new JFrame("my app");
        frame.setContentPane(mainPanel);
        frame.getRootPane().setDefaultButton(submitButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.newQuiz();
                ImageIcon icon = new ImageIcon("images/" + model.getNextQuestion().fileName);
                imageLabel.setIcon(icon);
                resultLabel.setText("Right Answer");
                scoreLabel.setText(model.getNumCorrect() + " / " + model.getNumAttempted());
            }
        });

        inputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    countriesList.setSelectedIndex(0);
                    countriesList.requestFocus();
                }
            }
        });

        countriesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    inputTextField.requestFocus();
                }
            }
        });



    }

    private DefaultListModel<String> updateListModel(List<String> countries) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        countries.forEach(
                element -> {
                    if(element.toLowerCase().contains(inputTextField.getText().toLowerCase())) {
                        listModel.addElement(element);

                    } else if(inputTextField.getText().equals(" ")) {
                        listModel.addElement(element);
                    }
                }
        );

        return listModel;
    }



    public static void main(String[] args) {


        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {


                new MainForm().frame.setVisible(true);

            }
        });
    }


}
