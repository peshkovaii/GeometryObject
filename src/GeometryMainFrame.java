
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;


public class GeometryMainFrame extends JFrame {
    private JTextField areaTextField;
    private JTextField intersectTextField;
    private GeometryPanel geometryPanel;
    private List<GeometryObject> objects;
    private JTextField inputTextField;

    public GeometryMainFrame() {
        setTitle("Библиотека Геометрические объекты");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        objects = new ArrayList<>();
        geometryPanel = new GeometryPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(geometryPanel, BorderLayout.CENTER);

        JPanel infoPanel = createInfoPanel();

        JPanel controlPanel = createControlPanel();

        add(infoPanel, BorderLayout.NORTH);
        add(geometryPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        updateInfoFields();
        setVisible(true);
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout());

        areaTextField = new JTextField(15);
        intersectTextField = new JTextField(20);
        areaTextField.setEditable(false);
        intersectTextField.setEditable(false);

        infoPanel.add(new JLabel("Площадь:"));
        infoPanel.add(areaTextField);
        infoPanel.add(new JLabel("Пересечение:"));
        infoPanel.add(intersectTextField);

        return infoPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(3, 1));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputTextField = new JTextField(30);
        JButton addLineButton = new JButton("Добавить линию");
        JButton addRectButton = new JButton("Добавить прямоугольник");

        inputPanel.add(new JLabel("Координаты:"));
        inputPanel.add(inputTextField);
        inputPanel.add(addLineButton);
        inputPanel.add(addRectButton);

        JPanel movePanel = new JPanel(new GridLayout(1, 4));
        JButton moveLeftButton = new JButton("Влево");
        JButton moveRightButton = new JButton("Вправо");
        JButton moveUpButton = new JButton("Вверх");
        JButton moveDownButton = new JButton("Вниз");

        moveLeftButton.addActionListener(e -> moveAllObjects(-20, 0));
        moveRightButton.addActionListener(e -> moveAllObjects(20, 0));
        moveUpButton.addActionListener(e -> moveAllObjects(0, -20));
        moveDownButton.addActionListener(e -> moveAllObjects(0, 20));

        movePanel.add(moveLeftButton);
        movePanel.add(moveRightButton);
        movePanel.add(moveUpButton);
        movePanel.add(moveDownButton);

        JPanel operationPanel = new JPanel(new FlowLayout());
        JButton checkIntersectButton = new JButton("Проверить пересечение");
        JButton unionButton = new JButton("Объединение");
        JButton clearButton = new JButton("Очистить экран");

        checkIntersectButton.addActionListener(e -> checkAllIntersections());
        unionButton.addActionListener(e -> unionFirstTwoRectangles());
        clearButton.addActionListener(e -> clearAll());

        operationPanel.add(checkIntersectButton);
        operationPanel.add(unionButton);
        operationPanel.add(clearButton);


        addLineButton.addActionListener(e -> addLineFromInput());
        addRectButton.addActionListener(e -> addRectangleFromInput());

        controlPanel.add(inputPanel);
        controlPanel.add(movePanel);
        controlPanel.add(operationPanel);

        return controlPanel;
    }

    private void addLineFromInput() {
        try {
            String input = inputTextField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите координаты в формате: x1,y1,x2,y2");
                return;
            }

            String[] coords = input.split(",");
            if (coords.length != 4) {
                JOptionPane.showMessageDialog(this, "Неверный формат. Нужно: x1,y1,x2,y2");
                return;
            }

            double x1 = Double.parseDouble(coords[0].trim());
            double y1 = Double.parseDouble(coords[1].trim());
            double x2 = Double.parseDouble(coords[2].trim());
            double y2 = Double.parseDouble(coords[3].trim());

            Line line = new Line(new Point(x1, y1), new Point(x2, y2));
            objects.add(line);
            geometryPanel.addObject(line);
            inputTextField.setText("");
            updateInfoFields();

            JOptionPane.showMessageDialog(this, "Линия добавлена: " + line);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка: введите числа в правильном формате");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
        }
    }

    private void addRectangleFromInput() {
        try {
            String input = inputTextField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите координаты в формате: x,y,width,height");
                return;
            }

            String[] coords = input.split(",");
            if (coords.length != 4) {
                JOptionPane.showMessageDialog(this, "Неверный формат. Нужно: x,y,width,height");
                return;
            }

            double x = Double.parseDouble(coords[0].trim());
            double y = Double.parseDouble(coords[1].trim());
            double width = Double.parseDouble(coords[2].trim());
            double height = Double.parseDouble(coords[3].trim());

            if (width <= 0 || height <= 0) {
                JOptionPane.showMessageDialog(this, "Ширина и высота должны быть положительными");
                return;
            }

            Rectangle rect = new Rectangle(new Point(x, y), width, height);
            objects.add(rect);
            geometryPanel.addObject(rect);
            inputTextField.setText("");
            updateInfoFields();

            JOptionPane.showMessageDialog(this, "Прямоугольник добавлен: " + rect);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка: введите числа в правильном формате");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
        }
    }

    private void moveAllObjects(double dx, double dy) {
        for (GeometryObject obj : objects) {
            obj.move(dx, dy);
        }
        geometryPanel.repaint();
        updateInfoFields();
    }

    private void checkAllIntersections() {
        StringBuilder intersectInfo = new StringBuilder();

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GeometryObject obj1 = objects.get(i);
                GeometryObject obj2 = objects.get(j);

                if (obj1.intersects(obj2)) {
                    intersectInfo.append(obj1.getClass().getSimpleName())
                            .append(i).append("-")
                            .append(obj2.getClass().getSimpleName())
                            .append(j).append(" ");
                }
            }
        }

        if (intersectInfo.length() == 0) {
            intersectTextField.setText("No intersections");
        } else {
            intersectTextField.setText(intersectInfo.toString());
        }
    }

    private void unionFirstTwoRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (GeometryObject obj : objects) {
            if (obj instanceof Rectangle) {
                rectangles.add((Rectangle) obj);
            }
        }

        if (rectangles.size() >= 2) {
            Rectangle union = (Rectangle) rectangles.get(0).union(rectangles.get(1));
            if (union != null) {
                objects.add(union);
                geometryPanel.addObject(union);
                updateInfoFields();
                JOptionPane.showMessageDialog(this, "Объединенный прямоугольник создан: " + union);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Нужно как минимум 2 прямоугольника для объединения!");
        }
    }

    private void clearAll() {
        objects.clear();
        geometryPanel.clearObjects();
        updateInfoFields();
        JOptionPane.showMessageDialog(this, "Все фигуры удалены");
    }

    private void updateInfoFields() {
        double totalArea = 0;
        for (GeometryObject obj : objects) {
            totalArea += obj.area();
        }
        areaTextField.setText(String.format("Area: %.1f", totalArea));
        intersectTextField.setText("");

        geometryPanel.repaint();
    }
}