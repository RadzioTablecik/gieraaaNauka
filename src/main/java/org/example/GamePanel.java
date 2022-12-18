package org.example;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile (char, npc, mobs)
    final int scale = 3; //skaluje 16x16 do wieklszych rozdzielczosci

    public final int tileSize = originalTileSize * scale; //48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; //tabela dot rozmiaru mapy
    public final int screenWidth = tileSize*maxScreenCol; //768 px
    public final int screenHeight = tileSize * maxScreenRow; //576 px

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow=50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxScreenRow;

    //FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //lepszy render
        this.addKeyListener(keyH);
        this.setFocusable(true); //game panel skupia sie na sluchaniu klawiszy
    }

    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        //korzystam z delta game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread!=null){

            currentTime = System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            timer += (currentTime-lastTime);
            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;

            }

            if(timer>=1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); //odwolujemy sie do oryginalnego paintComponent zawartego w JPanel

        Graphics2D g2 = (Graphics2D)g;
        //TILE
        tileM.draw(g2) ;

        //OBJECT
        for(int i = 0; i< obj.length; i++){
            if(obj[i]!=null){
                obj[i].draw(g2, this);
            }
        }
        //PLAYER
        player.draw(g2);
        g2.dispose();

    }
}
