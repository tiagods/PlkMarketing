package br.com.tiagods.view;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT;
import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

/*
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*   - Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*
*   - Redistributions in binary form must reproduce the above copyright
*     notice, this list of conditions and the following disclaimer in the
*     documentation and/or other materials provided with the distribution.
*
*   - Neither the name of Oracle or the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
* IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
* PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

public class LoadingView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5988038104895514107L;

	private Color cor;
	final WaitLayerUI layerUI = new WaitLayerUI();
	
	private static LoadingView instance;
	private boolean mIsRunning;
	private boolean mIsFadingOut;
	
	public static LoadingView getInstance(){
		return instance;
	}
	public void fechar(){
		layerUI.stop();
		this.dispose();
	}
	
	public static void inicializar(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);
		//If shaped windows aren't supported, exit.
		if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
			System.err.println("Shaped windows are not supported");
		}
		if (!isTranslucencySupported) {
			System.out.println("Translucency is not supported, creating an opaque window");
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				instance = new LoadingView();
				
				// Set the window to 70% translucency, if supported.
				if (isTranslucencySupported) {
					instance.setOpacity(0.25f);
				}
				instance.setVisible(true);
				instance.openMenu();
			}
		});
	}
	
	public static void main(String[] args) {
		inicializar();
	}
	
	public void openMenu(){
		OpenProgramView run = new OpenProgramView();
		Thread thread = new Thread(run);
		thread.start();
	}
	public class OpenProgramView implements Runnable{
		@Override
		public void run() {
			MenuView.getInstance();
		}
		
	}
	
	public LoadingView(){
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new Ellipse2D.Double(20,20,getWidth()-40,getHeight()-40));

			}
		});
		setUndecorated(true);
		setSize(300,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		receiveColor();
		createUI();
	}
	private void receiveColor(){
		LocalTime local = LocalTime.now();
		
		if(local.getHour()>0 && local.getHour()<10){
			cor = Color.CYAN;
		}
		else if(local.getHour()>=10 && local.getHour()<12){
			cor = Color.WHITE;
		}
		else if(local.getHour()>=12 && local.getHour()<14){
			cor = Color.BLUE;
		}
		else if(local.getHour()>=14 && local.getHour()<16){
			cor = Color.MAGENTA;
		}
		else
			cor = Color.RED;
	}
	public void createUI() {
		
		JPanel panel = createPanel();
		JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
		final Timer stopper = new Timer(20000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				layerUI.stop();
				dispose();
			}
		});
		stopper.setRepeats(false);
		add (jlayer);

		layerUI.start();
		if (!stopper.isRunning()) {
			stopper.start();
		}
	}

	private JPanel createPanel() {
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setBackground(cor);
		return p;
	}
	
	public class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
		
		private Timer mTimer;

		private int mAngle;
		private int mFadeCount;
		private int mFadeLimit = 15;

		@Override
		public void paint (Graphics g, JComponent c) {
			int w = c.getWidth();
			int h = c.getHeight();

			// Paint the view.
			super.paint (g, c);

			if (!mIsRunning) {
				return;
			}

			Graphics2D g2 = (Graphics2D)g.create();

			float fade = (float)mFadeCount / (float)mFadeLimit;
			// Gray it out.
			Composite urComposite = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, .5f * fade));
			g2.fillRect(0, 0, w, h);
			g2.setComposite(urComposite);

			// Paint the wait indicator.
			int s = Math.min(w, h) / 5;
			int cx = w / 2;
			int cy = h / 2;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(
					new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setPaint(Color.white);
			g2.rotate(Math.PI * mAngle / 180, cx, cy);
			for (int i = 0; i < 12; i++) {
				float scale = (11.0f - (float)i) / 11.0f;
				g2.drawLine(cx + s, cy, cx + s * 2, cy);
				g2.rotate(-Math.PI / 6, cx, cy);
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, scale * fade));
			}
			g2.dispose();
		}

		public void actionPerformed(ActionEvent e) {
			if (mIsRunning) {
				firePropertyChange("tick", 0, 1);
				mAngle += 3;
				if (mAngle >= 360) {
					mAngle = 0;
				}
				if (mIsFadingOut) {
					if (--mFadeCount == 0) {
						mIsRunning = false;
						mTimer.stop();
					}
				}
				else if (mFadeCount < mFadeLimit) {
					mFadeCount++;
				}
			}
		}

		public void start() {
			if (mIsRunning) {
				return;
			}

			// Run a thread for animation.
			mIsRunning = true;
			mIsFadingOut = false;
			mFadeCount = 0;
			int fps = 24;
			int tick = 1000 / fps;
			mTimer = new Timer(tick, this);
			mTimer.start();
		}

		public void stop() {
			mIsFadingOut = true;
		}

		@Override
		public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
			if ("tick".equals(pce.getPropertyName())) {
				l.repaint();
			}
		}
	}

}
