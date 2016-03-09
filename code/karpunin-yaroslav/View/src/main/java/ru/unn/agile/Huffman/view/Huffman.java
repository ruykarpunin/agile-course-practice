package ru.unn.agile.Huffman.view;

import ru.unn.agile.Huffman.infrastructure.TxtLogger;
import ru.unn.agile.Huffman.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public final class Huffman {

    private JPanel mainPanel;
    private JButton btnCalc;
    private ViewModel viewModel;

    private JTextField HuffmanString;

    private JTextField txtResult;
    private JLabel lbStatus;
    private JList<String> lstLog;
    private Huffman() {

    }

    private Huffman(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();

        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                Huffman.this.viewModel.calculate();
                backBind();
            }
        });


        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                Huffman.this.viewModel.processKeyInTextField(e.getKeyCode());
                backBind();
            }
        };
        HuffmanString.addKeyListener(keyListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Huffman");
        TxtLogger logger = new TxtLogger("./Huffman.log");
        frame.setContentPane(new Huffman(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setHuffmanString(HuffmanString.getText());
     }

    private void backBind() {
        btnCalc.setEnabled(viewModel.isCalculateButtonEnabled());

        txtResult.setText(viewModel.getResult());
        lbStatus.setText(viewModel.getStatus());

        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        lstLog.setListData(items);
    }
}
