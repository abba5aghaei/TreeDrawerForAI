package ai.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import javax.imageio.ImageIO;
import ai.Main;
import ai.model.Edge;
import ai.model.Graph;
import ai.model.GraphNode;
import ai.model.Tree;
import ai.model.TreeNode;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Controller {
    private Main main;
    private GraphicsContext pen;
    private double size, x, y;
    private int length, increaser;
    private char type;
    private boolean inHand;
    private String temperoryName;
    private Color nodeColor, edgeColor, textColor;
    private ImageCursor cursor;
    private Tree tree;
    private Graph graph;
    private Vector<TreeNode> treeNodes;
    private Vector<GraphNode> graphNodes;
    private Vector<Circle> graphCircles;
    private Vector<Text> graphTexts;
    private Vector<Line> graphLines;
    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ScrollPane scrollDesk;
    @FXML
    private AnchorPane desk;
    @FXML
    private Canvas paper;
    @FXML
    private AnchorPane treeDialog;
    @FXML
    private AnchorPane colorDialog;
    @FXML
    private AnchorPane graphDialog;
    @FXML
    private AnchorPane removeDialog;
    @FXML
    private TextField treeNodeName;
    @FXML
    private TextField graphNodeName;
    @FXML
    private TextField treeNodeCost;
    @FXML
    private TextField graphEdgeCost;
    @FXML
    private ComboBox<String> treeNodeParent;
    @FXML
    private ComboBox<String> graphSourse;
    @FXML
    private ComboBox<String> graphTarget;
    @FXML
    private TextField removeNodeName;
    @FXML
    private Slider slider;
    @FXML
    private TextArea consol;
    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private MenuButton draw;
    @FXML
    private MenuButton edit;
    @FXML
    private MenuButton tools;
    @FXML
    private MenuButton help;
    @FXML
    private Button lightClose;
    @FXML
    private Button lightMinimize;
    @FXML
    private MenuItem addNode;
    @FXML
    private MenuItem removeNode;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem saveAsMenu;
    @FXML
    private MenuItem imageMenu;
    @FXML
    private MenuItem bfs;
    @FXML
    private MenuItem dfs;
    @FXML
    private MenuItem uniform;
    @FXML
    private MenuItem duplicate;
    @FXML
    private ColorPicker textColorPicker;
    @FXML
    private ColorPicker edgeColorPicker;
    @FXML
    private ColorPicker nodeColorPicker;
    @FXML
    private TitledPane graphTitled;

    public void initialize() {
        scroll.setHvalue(0.5);
        pen = paper.getGraphicsContext2D();
        nodeColor = Color.AQUA;
        edgeColor = Color.SILVER;
        textColor = Color.BLACK;
        nodeColorPicker.setValue(nodeColor);
        edgeColorPicker.setValue(edgeColor);
        textColorPicker.setValue(textColor);
        tree = new Tree(null);
        graph = new Graph();
        tree.setConsol(consol);
        treeNodes = new Vector<TreeNode>();
        graphNodes = new Vector<GraphNode>();
        graphCircles = new Vector<Circle>();
        graphTexts = new Vector<Text>();
        graphLines = new Vector<Line>();
        size = 40;
    }

    private void repaint() {
        switch (type) {
            case 1: {
                pen.clearRect(0, 0, paper.getWidth(), paper.getHeight());
                if (tree.getRoot() != null) {
                    increaser = 0;
                    setBounds();
                    Queue<TreeNode> queue = new LinkedList<TreeNode>();
                    queue.add(tree.getRoot());
                    printTreeNode(tree.getRoot());
                    tree.getRoot().setVisited(true);
                    scroll.setHvalue(0.5);
                    while (!queue.isEmpty()) {
                        TreeNode node = queue.remove();
                        TreeNode child = node.getUnvisitedChild();
                        while (child != null) {
                            child.setVisited(true);
                            pen.setStroke(edgeColor);
                            pen.setLineWidth(size / 20);
                            pen.strokeLine(node.getX() + size / 2, node.getY() + size + 3, child.getX() + size / 2,
                                    child.getY());
                            printTreeNode(child);
                            queue.add(child);
                            child = node.getUnvisitedChild();
                        }
                    }
                    tree.clearTreeNodes();
                }
                break;
            }
            case 2: {
                clearGraphShapes();
                for(int i=0;i<graphCircles.size();i++) {
                    graphCircles.get(i).setRadius(size/2);
                    graphCircles.get(i).setFill(edgeColor);
                    if(i<graphLines.size()) {
                        graphLines.get(i).setStrokeWidth(size/10);
                        graphLines.get(i).setStroke(edgeColor);
                    }
                    length = graphTexts.get(i).getText().length();
                    if (length == 1) {
                        x = graphCircles.get(i).getCenterX() - size / 6;
                        y = graphCircles.get(i).getCenterY() + size / 5;
                        graphTexts.get(i).setFont(new Font(size/2+4));
                    } else if (length <= 3) {
                        x = graphCircles.get(i).getCenterX() - size / 4;
                        y = graphCircles.get(i).getCenterY() + size / 6;
                        graphTexts.get(i).setFont(new Font(size/3+4));
                    } else if (length <= 5) {
                        x = graphCircles.get(i).getCenterX() - size / 3;
                        y = graphCircles.get(i).getCenterY() + size / 9;
                        graphTexts.get(i).setFont(new Font(size/5+4));
                    } else {
                        x = graphCircles.get(i).getCenterX() - size / 2 + size/12;;
                        y = graphCircles.get(i).getCenterY() + size / 9;
                        graphTexts.get(i).setFont(new Font(size/7+4));
                        if (length > 6)
                            graphTexts.get(i).setText(graphTexts.get(i).getText().substring(0, 6)+"...");
                    }
                    graphTexts.get(i).setLayoutX(x);
                    graphTexts.get(i).setLayoutY(y);
                    graphTexts.get(i).setFill(textColor);
                    graphCircles.get(i).setFill(nodeColor);
                }
                for(Line element : graphLines)
                    root.getChildren().add(element);
                for(Circle element : graphCircles)
                    root.getChildren().add(element);
                for(Text element : graphTexts)
                    root.getChildren().add(element);
                for(int i=0; i <graphNodes.size(); i++)
                    for(Edge element : graphNodes.get(i).getEdges()) {
                        element.getText().setLayoutX((element.getLine().getStartX()+element.getLine().getEndX())/2);
                        element.getText().setLayoutY((element.getLine().getStartY()+element.getLine().getEndY())/2);
                        root.getChildren().add(element.getText());
                    }
            }
        }
    }

    private void setBounds() {
        switch (type) {
            case 1: {
                Queue<TreeNode> queue = new LinkedList<TreeNode>();
                queue.add(tree.getRoot());
                tree.getRoot().setX(paper.getWidth() / 2);
                tree.getRoot().setY(10);
                tree.getRoot().setVisited(true);
                while (!queue.isEmpty()) {
                    TreeNode node = queue.remove();
                    TreeNode child = node.getUnvisitedChild();
                    while (child != null) {
                        child.setVisited(true);
                        child.setX(getChildX(node.getX(), node.getChildren().size(), node.getChildren().indexOf(child)));
                        child.setY(node.getY() + size * 2);
                        queue.add(child);
                        child = node.getUnvisitedChild();
                    }
                }
                tree.clearTreeNodes();
                break;
            }
            case 2: {

            }
        }
    }

    private void printTreeNode(TreeNode node) {
        pen.setFill(nodeColor);
        pen.fillOval(node.getX(), node.getY(), size, size);
        length = node.getData().length();
        if (length == 1) {
            x = node.getX() + size / 3;
            y = node.getY() + size / 2 + size / 5;
            pen.setFont(Font.font(size / 2 + 4));
        } else if (length <= 3) {
            x = node.getX() + size / 5;
            y = node.getY() + size / 2 + size / 6;
            pen.setFont(Font.font(size / 3 + 4));
        } else if (length <= 5) {
            x = node.getX() + size / 7;
            y = node.getY() + size / 2 + size / 9;
            pen.setFont(Font.font(size / 5 + 4));
        } else {
            x = node.getX() + size / 11;
            y = node.getY() + size / 2 + size / 9;
            pen.setFont(Font.font(size / 7 + 4));
        }
        pen.setFill(textColor);
        pen.fillText(node.getData(), x, y);
        x = node.getX() + size / 3;
        y = node.getY() + size / 4;
        pen.setFont(Font.font(size / 4));
        pen.fillText(node.getCost()+"", x, y);
    }

    public void treeNodeAdder() {
        String name = treeNodeName.getText().trim();
        if (name.length() != 0 && name != null) {
            TreeNode node = new TreeNode(name, Double.parseDouble(treeNodeCost.getText()));
            if (!tree.contains(node)) {
                treeNodes.add(node);
                String parentName = "ندارد";
                if (tree.getRoot() != null) {
                    parentName = treeNodeParent.getSelectionModel().getSelectedItem();
                    for (TreeNode parent : treeNodes) {
                        if (parent.getData().equals(parentName)) {
                            parent.addChildren(node);
                            node.setDepth(parent.getDepth() + 1);
                        }
                    }
                    treeNodeParent.getItems().add(name);
                } else {
                    tree.setRoot(node);
                    treeNodeCost.setText("1");
                    treeNodeParent.getItems().add(name);
                    treeNodeParent.getSelectionModel().select(0);
                }
                println("گره " + name + " به درخت اضافه شد،  والد: " + parentName);
                repaint();
            } else {
                println("نام گره تکراری است!");
                flash(consol);
            }
        } else {
            println("نام گره نباید خالی باشد!");
            flash(consol);
            flash(treeNodeName);
        }
    }

    public void nodeRemover() {
        String name = removeNodeName.getText().trim();
        if (name.length() != 0 && name != null) {
            switch (type) {
                case 1: {
                    if (tree.contains(new TreeNode(name))) {
                        tree.clearTreeNodes();
                        Queue<TreeNode> queue = new LinkedList<TreeNode>();
                        queue.add(tree.getRoot());
                        tree.getRoot().setVisited(true);
                        if (tree.getRoot().getData().equalsIgnoreCase(name)) {
                            treeNodes.clear();
                            tree.getRoot().getChildren().clear();
                            tree.setRoot(null);
                            println("گره " + name + " حذف شد");
                            repaint();
                            treeNodeParent.getItems().remove(name);
                        } else {
                            while (!queue.isEmpty()) {
                                TreeNode node = queue.remove();
                                TreeNode child = node.getUnvisitedChild();
                                while (child != null) {
                                    child.setVisited(true);
                                    if (child.getData().equalsIgnoreCase(name)) {
                                        for (TreeNode element : child.getChildren())
                                            treeNodes.remove(element);
                                        treeNodes.remove(child);
                                        child.getChildren().clear();
                                        node.getChildren().remove(child);
                                        println("گره " + name + " حذف شد");
                                        repaint();
                                        child = null;
                                        treeNodeParent.getItems().remove(name);
                                    } else {
                                        queue.add(child);
                                        child = node.getUnvisitedChild();
                                    }
                                }
                            }
                        }
                        tree.clearTreeNodes();
                    } else {
                        println("پیدا نشد");
                        flash(consol);
                    }
                    break;
                }
                case 2: {
                    for(int i=0 ; i<graphTexts.size() ; i++)
                        if(graphTexts.get(i).getText().equals(name)) {
                            root.getChildren().remove(graphTexts.get(i));
                            root.getChildren().remove(graphCircles.get(i));
                            graphCircles.remove(i);
                            graphTexts.remove(i);
                            for(Edge element : graphNodes.get(i).getEdges()) {
                                graphLines.remove(element.getLine());
                                element.getTarget().getInEdges().remove(element);
                            }
                            graphNodes.remove(i);
                            repaint();
                        }
                    break;
                }
            }
        } else {
            println("نام گره نباید خالی باشد!");
            flash(consol);
            flash(removeNodeName);
        }
    }

    public double getChildX(double parentX, int childCunt, int childIndex) {
        double childX;
        switch (childCunt) {
            case 1: {
                childX = parentX;
                break;
            }
            case 2: {
                switch (childIndex) {
                    case 0: {
                        childX = parentX - size * childCunt;
                        break;
                    }
                    default: {
                        childX = parentX + size * childCunt;
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (childIndex) {
                    case 0: {
                        childX = parentX - size * childCunt;
                        break;
                    }
                    case 1: {
                        childX = parentX;
                        break;
                    }
                    default: {
                        childX = parentX + size * childCunt;
                        break;
                    }
                }
                break;
            }
            case 4: {
                switch (childIndex) {
                    case 0: {
                        childX = parentX - size * childCunt;
                        break;
                    }
                    case 1: {
                        childX = parentX - size * (childCunt - 2);
                        break;
                    }
                    case 2: {
                        childX = parentX + size * (childCunt - 2);
                        break;
                    }
                    default: {
                        childX = parentX + size * childCunt;
                        break;
                    }
                }
                break;
            }
            default: {
                switch (childIndex) {
                    case 0: {
                        childX = parentX - size * childCunt;
                        break;
                    }
                    case 1: {
                        childX = parentX - size * (childCunt - 2);
                        break;
                    }
                    case 2: {
                        childX = parentX;
                        break;
                    }
                    case 3: {
                        childX = parentX + size * (childCunt - 2);
                        break;
                    }
                    default: {
                        childX = parentX + size * (childCunt + increaser * 2);
                        increaser++;
                        break;
                    }
                }
                break;
            }
        }
        return childX;
    }

    public void graphNodeAdder(MouseEvent mouse) {
        temperoryName = graphNodeName.getText().trim();
        root.setCursor(cursor);
        inHand = true;
    }

    public void graphEdgeAdder() {
        String sourceName = graphSourse.getSelectionModel().getSelectedItem();
        String targetName = graphTarget.getSelectionModel().getSelectedItem();
        double cost = Double.parseDouble(graphEdgeCost.getText().trim());
        GraphNode source = null;
        GraphNode target = null;
        for(GraphNode element : graphNodes)
            if(element.getValue().equals(sourceName))
                source = element;
        for(GraphNode element : graphNodes)
            if(element.getValue().equals(targetName))
                target = element;
        Edge edge = new Edge(target, cost);
        source.addEdge(edge);
        target.addInEdge(edge);
        Line line = new Line(source.getX(), source.getY(), target.getX(), target.getY());
        line.setStrokeWidth(size/10);
        line.setStroke(edgeColor);
        edge.setLine(line);
        graphLines.add(line);
        repaint();
    }

    public void printGraphNode(MouseEvent mouse) {
        if(inHand) {
            GraphNode node = new GraphNode(temperoryName);
            Circle circle = new Circle(size/2);
            Text text = new Text(temperoryName);
            double mouseX = mouse.getSceneX();
            double mouseY = mouse.getSceneY();
            circle.setCenterX(mouseX);
            circle.setCenterY(mouseY);
            circle.setFill(nodeColor);
            node.setX(mouseX);
            node.setY(mouseY);
            length = temperoryName.length();
            if (length == 1) {
                x = circle.getCenterX() - size / 6;
                y = circle.getCenterY() + size / 5;
                text.setFont(new Font(size/2+4));
            } else if (length <= 3) {
                x = circle.getCenterX() - size / 4;
                y = circle.getCenterY() + size / 6;
                text.setFont(new Font(size/3+4));
            } else if (length <= 5) {
                x = circle.getCenterX() - size / 3;
                y = circle.getCenterY() + size / 9;
                text.setFont(new Font(size/5+4));
            } else {
                x = circle.getCenterX() - size / 2 + size/12;;
                y = circle.getCenterY() + size / 9;
                text.setFont(new Font(size/7+4));
                if (length > 6)
                    text.setText(text.getText().substring(0, 6)+"...");
            }
            text.setLayoutX(x);
            text.setLayoutY(y);
            text.setFill(textColor);
            circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    circle.setCenterX(me.getX());
                    circle.setCenterY(me.getY());
                    for(Edge edge : node.getEdges()) {
                        edge.getLine().setStartX(me.getX());
                        edge.getLine().setStartY(me.getY());
                    }
                    for(Edge edge : node.getInEdges()) {
                        edge.getLine().setEndX(me.getX());
                        edge.getLine().setEndY(me.getY());
                    }
                    length = text.getText().length();
                    if (length == 1) {
                        x = circle.getCenterX() - size / 6;
                        y = circle.getCenterY() + size / 5;
                        text.setFont(new Font(size/2+4));
                    } else if (length <= 3) {
                        x = circle.getCenterX() - size / 4;
                        y = circle.getCenterY() + size / 6;
                        text.setFont(new Font(size/3+4));
                    } else if (length <= 5) {
                        x = circle.getCenterX() - size / 3;
                        y = circle.getCenterY() + size / 9;
                        text.setFont(new Font(size/5+4));
                    } else {
                        x = circle.getCenterX() - size / 2 + size/12;;
                        y = circle.getCenterY() + size / 9;
                        text.setFont(new Font(size/7+4));
                        if (length > 6)
                            text.setText(text.getText().substring(0, 6)+"...");
                    }
                    text.setLayoutX(x);
                    text.setLayoutY(y);
                }});
            root.getChildren().addAll(circle, text);
            graphNodes.add(node);
            graphCircles.add(circle);
            graphTexts.add(text);
            graphSourse.getItems().add(text.getText());
            graphTarget.getItems().add(text.getText());
            root.setCursor(Cursor.DEFAULT);
            inHand = false;
        }
    }

    public void newTree() {
        type = 1;
        addNode.setDisable(false);
        removeNode.setDisable(false);
        saveMenu.setDisable(false);
        saveAsMenu.setDisable(false);
        imageMenu.setDisable(false);
        bfs.setDisable(false);
        dfs.setDisable(false);
        uniform.setDisable(true);
        duplicate.setDisable(true);
        slider.setDisable(false);
        scrollDesk.setVisible(false);
        scroll.setVisible(true);
        clearGraphShapes();
        nodeAdder();
    }

    public void newGraph() {
        type = 2;
        addNode.setDisable(false);
        removeNode.setDisable(false);
        saveMenu.setDisable(false);
        saveAsMenu.setDisable(false);
        imageMenu.setDisable(false);
        bfs.setDisable(true);
        dfs.setDisable(true);
        uniform.setDisable(false);
        duplicate.setDisable(false);
        slider.setDisable(false);
        scrollDesk.setVisible(true);
        scroll.setVisible(false);
        Canvas canvas = new Canvas(32, 32);
        GraphicsContext pencel = canvas.getGraphicsContext2D();
        pencel.setFill(nodeColor);
        pencel.fillOval(0, 0, 32, 32);
        pencel.setFill(textColor);
        pencel.setFont(new Font(11));
        pencel.fillText("نام", 10, 20);
        SnapshotParameters parametrs = new SnapshotParameters();
        parametrs.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(parametrs, null);
        cursor = new ImageCursor(image);
        nodeAdder();
    }

    public void treeDialogCloser() {
        treeDialog.setVisible(false);
    }

    public void graphDialogCloser() {
        graphDialog.setVisible(false);
    }

    public void removeDialogCloser() {
        removeDialog.setVisible(false);
    }

    public void removeDialogShower() {
        removeDialog.setVisible(true);
    }

    public void nodeAdder() {
        switch (type) {
            case 1: {
                treeDialog.setVisible(true);
                break;
            }
            case 2: {
                graphDialog.setVisible(true);
                graphTitled.setExpanded(true);
                break;
            }
        }
    }

    private void clearGraphShapes() {
        for(Circle element : graphCircles)
            root.getChildren().remove(element);
        for(Text element : graphTexts)
            root.getChildren().remove(element);
        for(Line element : graphLines)
            root.getChildren().remove(element);
    }

    public void BFS() {
        println("پیمایش اول سطح:");
        tree.breadthFirstSearch();
        println("");
    }

    public void DFS() {
        println("پیمایش اول عمق:");
        tree.depthFirstSearch();
        println("");
    }

    public void uniformSearch() {
        println("پیمایش هزینه یکنواخت:");
        graph.uniformCostSearch(null, null);
        println("");
    }

    public void setColors() {
        nodeColor = nodeColorPicker.getValue();
        edgeColor = edgeColorPicker.getValue();
        textColor = textColorPicker.getValue();
        colorDialog.setVisible(false);
    }

    public void closeColorDialog() {
        colorDialog.setVisible(false);
    }

    public void opencolorDialog() {
        colorDialog.setVisible(true);
    }

    public void setMain(Main m) {
        main = m;
        main.getStage().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode key = event.getCode();
                if (key.equals(KeyCode.LEFT)) {
                    main.getStage().setX(main.getStage().getX() - 10);
                } else if (key.equals(KeyCode.RIGHT)) {
                    main.getStage().setX(main.getStage().getX() + 10);
                } else if (key.equals(KeyCode.UP)) {
                    main.getStage().setY(main.getStage().getY() - 10);
                } else if (key.equals(KeyCode.DOWN)) {
                    main.getStage().setY(main.getStage().getY() + 10);
                }
            }
        });
    }

    public void exit() {
        main.saveSetting();
        System.exit(0);
    }

    public void minimize() {
        main.getStage().setIconified(true);
    }

    public void changeSize() {
        size = slider.getValue();
        repaint();
    }

    public void darker(MouseEvent me) {
        Node source = ((Node) me.getSource());
        if (source.equals(lightClose)) {
            lightClose.setVisible(false);
            close.setVisible(true);
        } else if (source.equals(lightMinimize)) {
            lightMinimize.setVisible(false);
            minimize.setVisible(true);
        }
    }

    public void lighter(MouseEvent me) {
        Node source = ((Node) me.getSource());
        if (source.equals(close)) {
            lightClose.setVisible(true);
            close.setVisible(false);
        } else if (source.equals(minimize)) {
            lightMinimize.setVisible(true);
            minimize.setVisible(false);
        }
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(String nc) {
        nodeColor = Color.valueOf(nc);
        nodeColorPicker.setValue(nodeColor);
    }

    public Color getEdgeColor() {
        return edgeColor;
    }

    public void setEdgeColor(String ec) {
        edgeColor = Color.valueOf(ec);
        edgeColorPicker.setValue(edgeColor);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(String tc) {
        textColor = Color.valueOf(tc);
        textColorPicker.setValue(textColor);
    }

    public void print(String s) {
        consol.appendText(s);
    }

    public void println(String s) {
        consol.appendText(s + "\r\n");
    }

    public void aboutApp() {
        println("درباره برنامه:");
        println("Artificail Intelligence");
        println("Version 1.0");
        println("Created on 19-nov-2017");
        println("Author: seyed abbas aghaei");
        flash(consol);
    }

    public void aboutUs() {
        println("درباره ما:");
        println("Contact: @abba5aghaei");
        println("Email: abba5aghaei@yahoo.com");
        flash(consol);
    }

    public void flash(Node node) {
        FadeTransition flasher = new FadeTransition();
        flasher.setNode(node);
        flasher.setFromValue(0);
        flasher.setToValue(100);
        flasher.setCycleCount(5);
        flasher.play();
    }

    public void open() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(main.getStage());
        if (file != null) {

        }
    }

    public void save() {
        saveAs();
    }

    public void saveAs() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(main.getStage());
        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                switch(type) {
                    case 1: {

                        break;
                    }
                    case 2: {

                        break;
                    }
                }
            }
        }
    }

    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(main.getStage());
        if (file != null) {
            if (!file.getPath().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
            }
            WritableImage image = paper.snapshot(null, null);
            BufferedImage buffer = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(buffer, "png", file);
            } catch (IOException e) {
                println("خطایی در ذخیره سازی تصویر رخ داد، لطفا دوباره سعی کنید");
            }
        } else
            println("ذخیره سازی لغو شد");
    }

    public void increseWidth() {
        paper.setWidth(paper.getWidth() + 10);
    }

    public void increseHeight() {
        paper.setHeight(paper.getHeight() + 10);
    }

    public void decreseWidth() {
        paper.setWidth(paper.getWidth() - 10);
    }

    public void decreseHeight() {
        paper.setHeight(paper.getHeight() - 10);
    }

    public void increse() {
        paper.setWidth(paper.getWidth() + 10);
        paper.setHeight(paper.getHeight() + 10);
    }

    public void decrese() {
        paper.setWidth(paper.getWidth() - 10);
        paper.setHeight(paper.getHeight() - 10);
    }

    public void move(MouseEvent me) {
        Node node = (Node) me.getSource();
        node.setLayoutX(me.getSceneX() - 100);
        node.setLayoutY(me.getSceneY());
    }
}
