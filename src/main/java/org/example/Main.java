package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Gierson");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //ustawia rozmiar window do rozmiaru komonentow (game panel)

        window.setLocationRelativeTo(null);
        window.setVisible(true);


        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}