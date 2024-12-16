// helper class for user input
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    protected boolean up, left, down, right, sprintOn, sprintOff, pressed, iUp, iDown, use, useAlt;
    protected boolean upTyped, leftTyped, downTyped, rightTyped, sprintOnTyped, sprintOffTyped, pressedTyped, iUpTyped, iDownTyped, useTyped, useAltTyped;

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

        // WASD controls
        if (code == KeyEvent.VK_W) {
         upTyped = true;
        }

        if (code == KeyEvent.VK_A) {
         leftTyped = true;
        }

        if (code == KeyEvent.VK_S) {    
         downTyped = true;
        }

        if (code == KeyEvent.VK_D) {
         rightTyped = true;
        }

        if (code == KeyEvent.VK_SHIFT) {        
         sprintOnTyped = true;
        }

        if (code == KeyEvent.VK_CONTROL) {
         sprintOffTyped = true;
        }

        if (code == KeyEvent.VK_UP) {
         iUpTyped = true;
        }   

        if (code == KeyEvent.VK_DOWN) {
         iDownTyped = true;
        }

        if (code == KeyEvent.VK_J) {
         useTyped = true;
        }
        if (code == KeyEvent.VK_K) {
         useAltTyped = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       int code = e.getKeyCode();
       pressed = true;
       // WASD controls
       if (code == KeyEvent.VK_W) {
        up = true;
       }

       if (code == KeyEvent.VK_A) {
        left = true;
       }

       if (code == KeyEvent.VK_S) {
        down = true;
       }

       if (code == KeyEvent.VK_D) {
        right = true;
       }

       if (code == KeyEvent.VK_SHIFT) {
        sprintOn = true;
       }

       if (code == KeyEvent.VK_CONTROL) {
        sprintOff = true;
       }

       if (code == KeyEvent.VK_UP) {
        iUp = true;
       }

       if (code == KeyEvent.VK_DOWN) {
        iDown = true;
       }

       if (code == KeyEvent.VK_J) {
        use = true;
       }
       if (code == KeyEvent.VK_K) {
        useAlt = true;
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        pressed = false;
       // WASD controls
       if (code == KeyEvent.VK_W) {
        up = false;
       }

       if (code == KeyEvent.VK_A) {
        left = false;
       }

       if (code == KeyEvent.VK_S) {
        down = false;
       }

       if (code == KeyEvent.VK_D) {
        right = false;
       }
       
       if (code == KeyEvent.VK_SHIFT) {
        sprintOn = false;
       }

       if (code == KeyEvent.VK_CONTROL) {
        sprintOff = false;
       }

       if (code == KeyEvent.VK_UP) {
        iUp = false;
       }

       if (code == KeyEvent.VK_DOWN) {
        iDown = false;
       }

       if (code == KeyEvent.VK_J) {
        use = false;
       }

       if (code == KeyEvent.VK_K) {
        useAlt = false;
       }


    }

}
