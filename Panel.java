import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel {
    private Timer timer;
    private Base b = new Base();
    private boolean gameOver = true;
    private boolean first = true;
    private boolean held = false;
    private int count = 0;
    // private int pulseSpeed = 40;
    private ArrayList<ArrayList<Invader>> invaders = new ArrayList<ArrayList<Invader>>();
    private ArrayList<Invader> temp = new ArrayList<Invader>();
    // private ArrayList<Missile> invaderMissiles = new ArrayList<>();
    private ArrayList<Mystery> m = new ArrayList<>();
    private int invadersCount = 0;
    private boolean checkM = true;
    private int check = 4;

    private void initializeInvaders() {
        invaders = new ArrayList<>();
        for (int i1 = 0; i1 < 5; i1++) {
            temp = new ArrayList<Invader>();
            switch (i1) {
                case 0:
                    for (int i = 0; i < 10; i++) {
                        temp.add(new InvaderTop(100 + i * 35, 80, b));
                        invadersCount++;
                    }
                    invaders.add(temp);
                    break;
                case 1:
                    for (int i = 0; i < 10; i++) {
                        temp.add(new InvaderMiddle(100 + i * 35, i1 * 25 + 80,
                                b));
                        invadersCount++;
                    }
                    invaders.add(temp);
                    break;
                case 2:
                    for (int i = 0; i < 10; i++) {
                        temp.add(new InvaderMiddle(100 + i * 35, i1 * 25 + 80,
                                b));
                        invadersCount++;
                    }
                    invaders.add(temp);
                    break;
                case 3:
                    for (int i = 0; i < 10; i++) {
                        temp.add(new InvaderBottom(100 + i * 35, i1 * 25 + 80,
                                b));
                        invadersCount++;
                    }
                    invaders.add(temp);
                    break;
                case 4:
                    for (int i = 0; i < 10; i++) {
                        temp.add(new InvaderBottom(100 + i * 35, i1 * 25 + 80,
                                b));
                        invadersCount++;
                    }
                    invaders.add(temp);
                    break;
            }
        }
    }

    public Panel() {
        m.add(new Mystery(100, 100, b, "mystery", false));

        setLayout(new BorderLayout());
        JMenuBar main = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About...");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(Panel.this,
                        "_______________\n" + "SpaceInvaders\nby Jared Koenig"
                                + "\n_______________");
            }
        });
        help.add(about);
        JMenuItem newGame = new JMenuItem("New Game");
        game.add(newGame);
        game.addSeparator();
        JMenuItem pause = new JMenuItem("Pause");
        pause.setEnabled(false);
        game.add(pause);
        JMenuItem resume = new JMenuItem("Resume");
        resume.setEnabled(false);
        game.add(resume);
        game.addSeparator();
        JMenuItem quit = new JMenuItem("Quit");
        game.add(quit);
        main.add(game);
        main.add(help);
        add(main, BorderLayout.NORTH);
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    timer.stop();
                    int result = JOptionPane.showConfirmDialog(Panel.this,
                            "Start a new game?");
                    if (result == JOptionPane.YES_OPTION) {
                        b.addScore(-b.getScore());
                        b.setAlive();
                        if (b.getMissile() != null) {
                            b.getMissile().setValid(false);
                        }
                        invadersCount = 0;
                        invaders = new ArrayList<>();
                        initializeInvaders();
                        invadersCount = 50;
                        gameOver = false;
                        timer = new Timer(10, new Listener());
                        pause.setEnabled(true);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    timer.start();
                }
            }

        });

        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                timer.stop();
                pause.setEnabled(false);
                resume.setEnabled(true);
            }

        });

        resume.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                timer.start();
                pause.setEnabled(true);
                resume.setEnabled(false);
            }

        });

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(Panel.this,
                        "Dare to Quit?");
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        b.move(Base.Direction.LEFT, true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        b.move(Base.Direction.RIGHT, true);
                        break;
                    case KeyEvent.VK_SPACE:
                        held = true;
                        b.shoot();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        b.move(Base.Direction.LEFT, false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        b.move(Base.Direction.RIGHT, false);
                        break;
                    case KeyEvent.VK_SPACE:
                        held = false;
                        break;
                }
            }
        });
        setBackground(Color.BLACK);
        setFocusable(true);
        timer = new Timer(10, new Listener());
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!gameOver) {
                    b.update();
                    if (held) {
                        b.shoot();
                    }
                    if (!m.isEmpty() && checkM) {
                        m.get(0).update();
                        m.get(0).hitByMissile();
                        if (m.get(0).isDead()) {
                            m.remove(0);
                            checkM = false;
                        }
                    }
                    for (ArrayList<Invader> l : invaders) {
                        for (Invader all : l) {
                            all.hitByMissile();
                            if (all.getMissile() != null) {
                                Missile tempM = all.getMissile();
                                if (count % 2 == 0) {
                                    tempM.update();
                                }
                                if (tempM.getX() + 1 >= b.getX()
                                        && tempM.getMissile().getX()
                                                + 1 <= b.getX() + 30
                                        && tempM.getMissile().getY() + 10 >= b
                                                .getY()
                                        && tempM.getMissile().getY()
                                                + 10 <= b.getY() + 30) {
                                    tempM.setValid(false);
                                    b.hitByMissile();
                                    b.hitSound();
                                    b.hitImage();
                                    gameOver = true;
                                }
                                if (all.getY() + 24 >= b.getY()) {
                                    gameOver = true;
                                }
                            }
                        }
                    }
                    // System.out.println(invaders.get(0).get(0).getY());
                    count++;
                    if (invadersCount > 0) {
                        if (count == 40 || first) {
                            for (int i1 = 0; i1 < invaders.size(); i1++) {
                                temp = invaders.get(i1);
                                for (int i = 0; i < temp.size(); i++) {
                                    if (temp.size() > 0) {
                                        temp.get(i).update();
                                        temp.get(i).changeImage();
                                        if (temp.get(i).getChange()) {
                                            for (ArrayList<Invader> l : invaders) {
                                                for (Invader all : l) {
                                                    all.changeDirection();
                                                    // pulseSpeed *= 0.8;
                                                }
                                            }
                                        }
                                        if (temp.get(i).isDead()) {
                                            if (temp.get(i).deadCount() >= 0) {
                                                temp.remove(i);
                                                invadersCount--;
                                            }
                                            continue;
                                        }
                                    }
                                }
                            }
                            try {
                                temp = invaders.get(check);
                                int missileChance = (int) (Math.random() * 10);
                                if (missileChance <= 7 && check >= 0) {
                                    int invaderShooting = (int) (Math.random()
                                            * temp.size());
                                    while (temp.get(invaderShooting) == null) {
                                        invaderShooting = (int) (Math.random()
                                                * temp.size());
                                    }
                                    temp.get(invaderShooting).shoot();
                                }
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                                if(check >= 0)
                                {
                                    check--;
                                }
                                else
                                {
                                    check = 4;
                                }
                            }
                            count = 0;
                            first = false;
                        }
                    }
                    else {
                        initializeInvaders();
                        invadersCount = 50;
                    }
                    repaint();
                }
                else {
                    System.out.println("Dead");
                    timer.stop();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        b.draw(g2);
        for (int i = 0; i < invaders.size(); i++) {
            for (int i1 = 0; i1 < invaders.get(i).size(); i1++) {
                invaders.get(i).get(i1).draw(g2);
            }
        }
        if (checkM) {
            m.get(0).draw(g2);
        }
        if (gameOver) {
            g2.setColor(Color.GREEN);
            g2.setFont(new Font(Font.SANS_SERIF, 15, 50));
            g2.drawString("Game Over", 120, 200);
        }
    }

    public void stopTimer() {
        timer.stop();
    }

}
