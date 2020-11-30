
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class GameManager {

    private int matrix[];
    private int position;
    private int size;
    private int currentText;
    private JButton[] list;
    private int count;
    private NPG frame;
    private boolean flag;
    private Thread threadElapsed;

    public GameManager(NPG frame) {
        this.frame = frame;
    }

    public void defaultMatrix(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < size * size; i++) {
            list.add(i);
        }

        Random rd = new Random();
        position = rd.nextInt(size * size);
        matrix[position] = 0;
        System.out.println(position);
        int i = 0;
        while (list.size() > 0) {
            int index = rd.nextInt(list.size());
            if (i != position) {
                matrix[i] = list.get(index);
                list.remove(index);
            }
            i++;
        }

        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i * size + j] + "  ");
            }
            System.out.println();
        }
    }

    public boolean checkCanWin(int size) {
        int n = 0;
        for (int i = 0; i < size * size; i++) {
            for (int j = i + 1; j < size * size; j++) {
                if (matrix[i] > matrix[j] && matrix[j] != 0) {
                    n++;
                }
            }
        }
        if (size == 3 && n % 2 == 0) {
            return true;
        }
        if (size == 4) {
            if (n % 2 == 0 && (position / size + 1) % 2 == 0) {
                return true;
            }
            if (n % 2 == 1 && (position / size + 1) % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    public void addButton(int size, JPanel pDisplay) {
        count = 0;
        this.size = size;
        matrix = new int[size * size];
        do {
            defaultMatrix(size);
        } while (!checkCanWin(size));
        list = new JButton[size * size];
        pDisplay.removeAll();
        for (int i = 0; i < size * size; i++) {
            if (matrix[i] == 0) {
                list[i] = new JButton("");
            } else {
                list[i] = new JButton("" + matrix[i]);
            }
            list[i].setFont(new Font("Tahoma", Font.PLAIN, 24));
            list[i].addMouseListener(new ButtonEvent(this));
            pDisplay.add(list[i]);
        }
        pDisplay.setPreferredSize(new Dimension(240, 240));
        pDisplay.setLayout(new GridLayout(size, size, 2, 2));
        pDisplay.updateUI();
        //elapsed();
        frame.setLbMoveCount(""+count);
        frame.setLbEclasped("0");
    }

    public void getButtonText(String text) {
        try {
            this.currentText = Integer.parseInt(text);
        } catch (Exception e) {
            this.currentText=0;
        }

    }

    public int findPosition() {
        for (int i = 0; i < size * size; i++) {
            if (matrix[i] == currentText) {
                return i;
            }
        }
        return -1;
    }

    public void swapCell() {
        int index = findPosition();
        System.out.println("selected:" + index);
        if (index != -1) {
            if (isBottom(index) || isRight(index) || isTop(index) || isleft(index)) {
                list[index].setText("");
                list[position].setText("" + currentText);
                matrix[index] = 0;
                matrix[position] = currentText;
                position = index;
                System.out.println("currentPos:" + position);
                count++;
                frame.setLbMoveCount(""+count);
            }
        }

    }

    public boolean isTop(int positionSelected) {
        int x = position / size;
        int y = position % size;
        return y - 1 == positionSelected % size && x == positionSelected / size;
    }

    public boolean isRight(int positionSelected) {
        int x = position / size;
        int y = position % size;
        return x + 1 == positionSelected / size && y == positionSelected % size;
    }

    public boolean isBottom(int positionSelected) {
        int x = position / size;
        int y = position % size;
        return y + 1 == positionSelected % size && x == positionSelected / size;
    }

    public boolean isleft(int positionSelected) {
        int x = position / size;
        int y = position % size;
        return x - 1 == positionSelected / size && y == positionSelected % size;
    }

    public boolean isWin() {

        for (int i = 0; i < size * size; i++) {
            if (i != position) {
                if (matrix[i] != i + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void isStopGame(boolean flag){
        this.flag=flag;
    }
    
    public Thread elapsed() {
        threadElapsed = new Thread() {

            @Override
            public void run() {
                while (true) {
                    try {
                            threadElapsed.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                    if(!flag){
                        String text=frame.getLbEclasped().getText();
                        String[] str=text.split(" ");
                        int time=Integer.parseInt(str[1]);
                        frame.setLbEclasped(++time+"");                 
                    }
                }
            }
        };
        return threadElapsed;
    }

    public int getCount() {
        return count;
    }

    public void setCount() {
        this.count++;
    }

    public NPG getFrame() {
        return frame;
    }

    public void setFrame(NPG frame) {
        this.frame = frame;
    }

}
// chinh cai neu nhan vao nut rong
