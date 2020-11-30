
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ButtonEvent implements MouseListener {
    GameManager mng;
    
    public ButtonEvent(GameManager mng) {
        this.mng=mng;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton btn=(JButton)e.getSource();
        System.out.println(btn.getText());
        mng.getButtonText(btn.getText());
        mng.swapCell();
//        mng.setCount();
//        mng.getFrame().setLbMoveCount(""+mng.getCount());
        if(mng.isWin()){
            mng.isStopGame(mng.isWin());
            JOptionPane.showMessageDialog(mng.getFrame(), "Win");
            
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
