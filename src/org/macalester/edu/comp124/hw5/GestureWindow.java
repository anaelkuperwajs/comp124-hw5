package org.macalester.edu.comp124.hw5;

import comp124graphics.CanvasWindow;
import comp124graphics.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The window and user interface for drawing gestures and automatically recognizing them
 * Created by bjackson on 10/29/2016.
 */
public class GestureWindow extends CanvasWindow implements ActionListener, KeyListener, MouseMotionListener, MouseListener{

    private Recognizer recognizer;
    private IOManager ioManager;
    private JButton addTemplateButton;
    private JTextField templateNameField;
    private JLabel matchLabel;
    private List<Point> path;


    public GestureWindow(){
        super("Gesture Recognizer", 600, 600);
        recognizer = new Recognizer();
        path = new ArrayList<>();
        ioManager = new IOManager();
        setupUI();
    }

    /**
     * Create the user interface
     */
    private void setupUI(){
        setLayout(new BorderLayout());
        matchLabel = new JLabel("Match: ");
        matchLabel.setFont(new Font("SanSerif", Font.PLAIN, 24));
        add(matchLabel, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        templateNameField = new JTextField(10);
        panel.add(templateNameField);
        addTemplateButton = new JButton("Add Template");
        addTemplateButton.addActionListener(this);
        panel.add(addTemplateButton);
        add(panel, BorderLayout.SOUTH);
        templateNameField.addKeyListener(this);

        //TODO: tell java to start receiving mouse events
        addMouseListener(this);
        addMouseMotionListener(this);

        revalidate();
    }

    /**
     * Handle what happens when the add template button is pressed. This method adds the points stored in path as a template
     * with the name from the templateNameField textbox. If no text has been entered then the template is named with "no name gesture"
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTemplateButton){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "no name gesture";
            }
            recognizer.addTemplate(name, path); // Add the points stored in the path as a template
        }
    }


    //TODO: Add mouse listeners to allow the user to draw and add the points to the path variable.


    /**
     * Key listener used to save and load gestures for debugging and to write tests.
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_L && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "gesture";
            }
            List<Point> points = ioManager.loadGesture(name+".xml");
            if (points != null){
                recognizer.addTemplate(name, points);
                System.out.println("Loaded "+name);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
            String name = templateNameField.getText();
            if (name.isEmpty()){
                name = "gesture";
            }
            ioManager.saveGesture(path, name, name+".xml");
            System.out.println("Saved "+name);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args){
        GestureWindow window = new GestureWindow();
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        removeAll();
        Point point = new Point(e.getX(), e.getY());
        path.add(point);
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = new Point(e.getX(), e.getY());
        Point lastPoint = path.get(path.size() - 1);
        path.add(point);
        Line line = new Line(point.getX(), point.getY(), lastPoint.getX(), lastPoint.getY());
        add(line);

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
