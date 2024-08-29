package org.example;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Языки разметки");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);


        JButton button = new JButton("Выбрать файл");
        button.setBounds(50, 100, 220, 30);
        frame.add(button);

        JButton closeButton = new JButton("Выход из программы");
        closeButton.setBounds(50, 150, 220, 30);
        frame.add(closeButton);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Завершение программы
            }
        });


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Создаем объект для выбора файла
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                // Открываем диалоговое окно для выбора файла
                int result = fileChooser.showOpenDialog(frame);

                // Если файл был выбран
                // Получаем выбранный файл
                File selectedFile = fileChooser.getSelectedFile();

                // Сохраняем путь к файлу в поле
                try {
                    Client client = new Client();
                    HashMap<String, Reactor> reactors = client.readCommonClass(selectedFile.getAbsolutePath());
                    for (Reactor reactor : reactors.values()) {
                    }
                    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
                    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

                    for (Map.Entry<String, Reactor> entry : reactors.entrySet()) {
                        DefaultMutableTreeNode reactorNode = new DefaultMutableTreeNode(entry.getKey());

                        reactorNode.add(new DefaultMutableTreeNode("Сгорание: " + entry.getValue().burnup));
                        reactorNode.add(new DefaultMutableTreeNode("Класс: " + entry.getValue().reactorClass));
                        reactorNode.add(new DefaultMutableTreeNode("Электрическая мощность: " + entry.getValue().electricalCapacity));
                        reactorNode.add(new DefaultMutableTreeNode("Первая загрузка: " + entry.getValue().firstLoad));
                        reactorNode.add(new DefaultMutableTreeNode("КПД: " + entry.getValue().kpd));
                        reactorNode.add(new DefaultMutableTreeNode("Срок службы: " + entry.getValue().lifeTime));
                        reactorNode.add(new DefaultMutableTreeNode("Тепловая мощность: " + entry.getValue().terminalCapacity));
                        reactorNode.add(new DefaultMutableTreeNode("Тип файла: " + entry.getValue().fileType));
                        treeModel.insertNodeInto((MutableTreeNode) reactorNode, (MutableTreeNode) treeModel.getRoot(), treeModel.getChildCount(treeModel.getRoot()));
                    }

                    JTree tree = new JTree(treeModel);

                    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

                    JScrollPane scrollPane = new JScrollPane(tree);

                    JFrame frame = new JFrame("JTree");
                    frame.add(scrollPane);

                    frame.setSize(400, 300);

                    frame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка считывания: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }


            }
        });


        // Отображаем фрейм
        frame.setVisible(true);

    }

}