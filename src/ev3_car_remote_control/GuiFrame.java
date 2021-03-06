package ev3_car_remote_control;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JFrame;


public class GuiFrame extends JFrame {
	
	private HashMap<Integer,Action> keyActions;
	protected Ev3Controller ev3Controller;
	
	public GuiFrame(String ip) {
		setupFrame(ip);
		setupEv3();
		setupHotkeys();
	}

	private void setupEv3() {
		ev3Controller = new Ev3Controller();
	}

	private void setupHotkeys() {
		setupKeyHashMap_tracked_vehicle();

		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				int key = e.getKeyCode();
				if (keyActions.containsKey(key)){
					keyActions.get(key).actionPerformed(new ActionEvent(e.getSource(), e.getID(), null ));
					return true;
				}else if (key == 0){
					return false;
				}else{
					return false;
				}
			}
		});
	}

	private void setupKeyHashMap_front_turn_vehicle() {
//		keyActions = new HashMap<Integer, Action>();
//		
//		keyActions.put(KeyEvent.VK_W, new GogoAction(ev3Controller, true,MotorPort.A,MotorSize.LARGE));
//		keyActions.put(KeyEvent.VK_S, new GogoAction(ev3Controller, false));
//		
//		keyActions.put(KeyEvent.VK_A, new TurnAction(ev3Controller, false));
//		keyActions.put(KeyEvent.VK_D, new TurnAction(ev3Controller, true));
	}
	
	private void setupKeyHashMap_tracked_vehicle() {
		keyActions = new HashMap<Integer, Action>();
		
		MotorInfo ma = new MotorInfo(MotorPort.B, MotorSize.L, 100, 300);
		MotorInfo mb = new MotorInfo(MotorPort.A, MotorSize.L, 100, 300);
		
		keyActions.put(KeyEvent.VK_W, new GogoAction(ev3Controller, false,ma));
		keyActions.put(KeyEvent.VK_E, new GogoAction(ev3Controller, false,mb));
		
		keyActions.put(KeyEvent.VK_S, new GogoAction(ev3Controller, true,ma));
		keyActions.put(KeyEvent.VK_D, new GogoAction(ev3Controller, true,mb));
	}

	private void setupFrame(String ip) {

		setResizable(false);
		this.setSize(232, 210);
		this.setTitle("EV3 Remote Control");
		this.setContentPane(new GuiPanel(ip));
		
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println("closing");
					ev3Controller.closeConnection();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	
}






