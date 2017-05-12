package util.swing;
import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Docker {

    public static final String NORTH_DOCKED = "dock north", SOUTH_DOCKED = "dock south", WEST_DOCKED = " dock west", EAST_DOCKED = "dock east";
    public static final String FOLLOW_LAYOUT = "layout follow", STICKY_LAYOUT = "layout sticky", MAGNETIC_LAYOUT = "layout magnetic";
    private HashMap<Component, String> dockees = new HashMap<>();
    private Timer dockeeMoveTimer;
    private ComponentAdapter caDock, caDockee;
    private Component dock;
    private String layout = STICKY_LAYOUT;
    private int MAGNETIC_FIELD_SIZE = 150, movedReactTime = 100;

    public Docker() {
        initTimers();
        initComponentAdapters();
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setComponentMovedReactTime(int milis) {
        movedReactTime = milis;
    }

    private void initComponentAdapters() {
        caDockee = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent ce) {
                super.componentMoved(ce);
                if (layout.equals(MAGNETIC_LAYOUT)) {
                    createDockeeMovedTimer();
                } else {
                    iterateDockables();
                }
            }

            private void createDockeeMovedTimer() {
                if (dockeeMoveTimer.isRunning()) {
                    dockeeMoveTimer.restart();
                } else {
                    dockeeMoveTimer.start();
                }
            }
        };
        caDock = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent ce) {
                super.componentMoved(ce);
                iterateDockables();
            }

            @Override
            public void componentResized(ComponentEvent ce) {
                super.componentResized(ce);
                iterateDockables();
            }
        };
    }

    private void initTimers() {
        dockeeMoveTimer = new Timer(movedReactTime, new AbstractAction() {
			private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent ae) {
                iterateDockables();
            }
        });
        dockeeMoveTimer.setRepeats(false);
    }

    private void iterateDockables() {
        //System.out.println("Dock will call for Dockees to come");
        for (Map.Entry<Component, String> entry : dockees.entrySet()) {
            Component component = entry.getKey();
            String pos = entry.getValue();
            if (!isDocked(component, pos)) {
                dock(component, pos);
            }
        }
    }

    public void registerDock(Component dock) {
        this.dock = dock;
        dock.addComponentListener(caDock);
    }

    public void registerDockee(Component dockee, String pos) {
        dockee.addComponentListener(caDockee);
        dockees.put(dockee, pos);
        caDock.componentResized(new ComponentEvent(dock, 1));//not sure about the int but w dont use it so its fine for now
    }

    public void deregisterDockee(Component dockee) {
        dockee.removeComponentListener(caDockee);
        dockees.remove(dockee);
    }

    public void setMagneticFieldSize(int sizeInPixels) {
        MAGNETIC_FIELD_SIZE = sizeInPixels;
    }

    private boolean isDocked(Component comp, String pos) {
        switch (pos) {
            case EAST_DOCKED:
                int eastDockedX = dock.getX() + dock.getWidth();
                int eastDockedY = dock.getY();
                if (layout.equals(MAGNETIC_LAYOUT)) {
                    if (comp.getLocation().distance(new Point(eastDockedX, eastDockedY)) <= MAGNETIC_FIELD_SIZE) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (comp.getX() == eastDockedX && comp.getY() == eastDockedY) {
                        // System.out.println("is eastly docked");
                        return true;
                    }
                }
                break;
            case SOUTH_DOCKED:
                int southDockedX = dock.getX();
                int southDockedY = dock.getY() + dock.getHeight();
                if (layout.equals(MAGNETIC_LAYOUT)) {
                    if (comp.getLocation().distance(new Point(southDockedX, southDockedY)) <= MAGNETIC_FIELD_SIZE) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (comp.getX() == southDockedX && comp.getY() == southDockedY) {
                        // System.out.println("is southly docked");
                        return true;
                    }
                }
                break;
            case WEST_DOCKED:
                int westDockedX = dock.getX() - comp.getWidth();
                int westDockedY = dock.getY();
                if (layout.equals(MAGNETIC_LAYOUT)) {
                    if (comp.getLocation().distance(new Point(westDockedX + comp.getWidth(), westDockedY)) <= MAGNETIC_FIELD_SIZE) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (comp.getX() == westDockedX && comp.getY() == westDockedY) {
                        // System.out.println("is southly docked");
                        return true;
                    }
                }
                break;
            case NORTH_DOCKED:
                int northDockedX = dock.getX() + comp.getHeight();
                int northDockedY = dock.getY() - comp.getHeight();
                if (layout.equals(MAGNETIC_LAYOUT)) {
                    if (comp.getLocation().distance(new Point(northDockedX - comp.getHeight(), northDockedY + comp.getHeight())) <= MAGNETIC_FIELD_SIZE) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (comp.getX() == northDockedX && comp.getY() == northDockedY) {
                        // System.out.println("is southly docked");
                        return true;
                    }
                }
                break;
        }
        return false;
    }
    private Timer eastTimer = null, southTimer = null, westTimer = null, northTimer = null;

    private void dock(final Component comp, String pos) {
        //System.out.println("Snapping Dockee back to the dock");
        switch (pos) {
            case EAST_DOCKED:
                int eastDockedX = dock.getX() + dock.getWidth();
                int eastDockedY = dock.getY();
                if (eastTimer == null) {
                    eastTimer = getTimer(comp, eastDockedX, eastDockedY);
                    eastTimer.start();
                } else {
                    if (!eastTimer.isRunning()) {
                        eastTimer = getTimer(comp, eastDockedX, eastDockedY);
                        eastTimer.start();
                    }
                }
                break;
            case SOUTH_DOCKED:
                int southDockedX = dock.getX();
                int southDockedY = dock.getY() + dock.getHeight();
                if (southTimer == null) {
                    southTimer = getTimer(comp, southDockedX, southDockedY);
                    southTimer.start();
                } else {
                    if (!southTimer.isRunning()) {
                        southTimer = getTimer(comp, southDockedX, southDockedY);
                        southTimer.start();
                    }
                }
                break;
            case WEST_DOCKED:
                int westDockedX = dock.getX() - comp.getWidth();
                int westDockedY = dock.getY();
                if (westTimer == null) {
                    westTimer = getTimer(comp, westDockedX, westDockedY);
                    westTimer.start();
                } else {
                    if (!westTimer.isRunning()) {
                        westTimer = getTimer(comp, westDockedX, westDockedY);
                        westTimer.start();
                    }
                }
                break;
            case NORTH_DOCKED:
                int northDockedX = dock.getX();
                int northDockedY = dock.getY() - comp.getHeight();
                if (northTimer == null) {
                    northTimer = getTimer(comp, northDockedX, northDockedY);
                    northTimer.start();
                } else {
                    if (!northTimer.isRunning()) {
                        northTimer = getTimer(comp, northDockedX, northDockedY);
                        northTimer.start();
                    }
                }
                break;
        }
    }

    private Timer getTimer(final Component comp, int finalX, int finalY) {
        Timer t = null;
        switch (layout) {
            case STICKY_LAYOUT:
                t = stickyDockableTimer(comp, finalX, finalY);
                break;
            case FOLLOW_LAYOUT:
                t = followDockableTimer(comp, finalX, finalY);
                break;
            case MAGNETIC_LAYOUT:
                t = followDockableTimer(comp, finalX, finalY);
                break;
        }
        return t;
    }

    private Timer followDockableTimer(final Component comp, final int finalX, final int finalY) {
        Timer t = new Timer(1, new AbstractAction() {
			private static final long serialVersionUID = 1L;
			int INCREMENT = 1, DECREMENT = 1;
            int x = comp.getX(), y = comp.getY();

            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println(finalX + "," + finalY);
                if (x < finalX) {
                    x += INCREMENT;
                } else if (x > finalX) {
                    x -= DECREMENT;
                }
                if (y < finalY) {
                    y += INCREMENT;
                } else if (y > finalY) {
                    y -= DECREMENT;
                }
                comp.setLocation(x, y);
                if (x == finalX && y == finalY) {
                    if (comp instanceof Window) {
                        ((Window) comp).toFront();
                    }
                    ((Timer) ae.getSource()).stop();
                }
            }
        });
        return t;
    }

    public JPanel getDockToolbar() {
        JPanel panel = new JPanel();
        for (Map.Entry<Component, String> entry : dockees.entrySet()) {
            final Component component = entry.getKey();
            String pos = entry.getValue();
            Docker.MyButton jtb = new Docker.MyButton("un-dock" + pos.replace("dock", ""), component, pos);
            panel.add(jtb);
            jtb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Docker.MyButton jtb = (Docker.MyButton) ae.getSource();
                    String tmp = jtb.getText();
                    if (tmp.contains("un-dock")) {
                        jtb.setText(tmp.replace("un-dock", "dock"));
                        deregisterDockee(jtb.getComponent());
                    } else {
                        jtb.setText(tmp.replace("dock", "un-dock"));
                        registerDockee(jtb.getComponent(), jtb.getDockablePosition());
                    }
                }
            });
        }
        return panel;
    }

    private Timer stickyDockableTimer(final Component comp, final int finalX, final int finalY) {
        Timer t = new Timer(1, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent ae) {
                comp.setLocation(finalX, finalY);
            }
        });
        t.setRepeats(false);
        return t;
    }

    private class MyButton extends JButton {
		private static final long serialVersionUID = 1L;
		private final Component c;
        private final String pos;

        public MyButton(String text, Component c, String pos) {
            super(text);
            this.c = c;
            this.pos = pos;
        }

        public Component getComponent() {
            return c;
        }

        public String getDockablePosition() {
            return pos;
        }
    }
}