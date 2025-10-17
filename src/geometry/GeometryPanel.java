package geometry;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class GeometryPanel extends JPanel {
    private List<GeometryObject> objects;

    public GeometryPanel() {
        this.objects = new ArrayList<>();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 400));
    }

    public void addObject(GeometryObject obj) {
        objects.add(obj);
        repaint();
    }

    public void clearObjects() {
        objects.clear();
        repaint();
    }

    public List<GeometryObject> getObjects() {
        return new ArrayList<>(objects);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN};

        for (int i = 0; i < objects.size(); i++) {
            GeometryObject obj = objects.get(i);
            g2d.setColor(colors[i % colors.length]);

            if (obj instanceof Line) {
                drawLine(g2d, (Line) obj, i);
            } else if (obj instanceof Rectangle) {
                drawRectangle(g2d, (Rectangle) obj, i);
            }
        }
    }

    private void drawLine(Graphics2D g2d, Line line, int index) {
        Point start = line.getStart();
        Point end = line.getEnd();

        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(
                (int) start.getX(), (int) start.getY(),
                (int) end.getX(), (int) end.getY()
        );

        g2d.setColor(Color.BLACK);
        g2d.drawString("L" + index, (int) start.getX() + 5, (int) start.getY() - 5);
    }

    private void drawRectangle(Graphics2D g2d, Rectangle rect, int index) {
        Point topLeft = rect.getTopLeft();
        int x = (int) topLeft.getX();
        int y = (int) topLeft.getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();


        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        g2d.drawString("R" + index, x + width / 2 - 10, y + height / 2);
    }
}