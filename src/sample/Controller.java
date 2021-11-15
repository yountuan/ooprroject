package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    private final Double snakeSize = 50.;
    private final Rectangle snakeHead = new Rectangle(250,250,snakeSize,snakeSize);
    Rectangle snakeTail_1 = new Rectangle(snakeHead.getX() - snakeSize,snakeHead.getY(),snakeSize,snakeSize);


    double xPos = snakeHead.getLayoutX();
    double yPos = snakeHead.getLayoutY();

    private Direction direction = Direction.RIGHT;

    private final List<Position> positions = new ArrayList<>();

    private final ArrayList<Rectangle> snakeBody = new ArrayList<>();

    private int gameTicks = 0;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3),e ->{
        positions.add(new Position(snakeHead.getX() +xPos, snakeHead.getY() + yPos));
        moveSnakeHead(snakeHead);
        for (int i = 1; i < snakeBody.size(); i++) {
            moveSnakeTail(snakeBody.get(i),i);
        }
        gameTicks++;
    }));



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        snakeBody.add(snakeHead);
        snakeHead.setFill(Color.RED);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        snakeBody.add(snakeTail_1);

        anchorPane.getChildren().addAll(snakeHead,snakeTail_1);
    }


    @FXML
    void start(MouseEvent event) {
    }

    @FXML
    void moveSquareKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.W) && direction != Direction.DOWN){
            direction = Direction.UP;
        } else if(event.getCode().equals(KeyCode.S) && direction != Direction.UP){
            direction = Direction.DOWN;
        }else if(event.getCode().equals(KeyCode.A) && direction != Direction.RIGHT){
            direction = Direction.LEFT;
        }else if(event.getCode().equals(KeyCode.D) && direction != Direction.LEFT){
            direction = Direction.RIGHT;
        }
    }


    @FXML
    void addBodyPart(ActionEvent event) {
        addSnakeTail();
    }

    private void moveSnakeHead(Rectangle snakeHead){
        if(direction.equals(Direction.RIGHT)){
            xPos = xPos + snakeSize;
            snakeHead.setTranslateX(xPos);
        } else if(direction.equals(Direction.LEFT)) {
            xPos = xPos - snakeSize;
            snakeHead.setTranslateX(xPos);
        }else if(direction.equals(Direction.UP)) {
            yPos = yPos - snakeSize;
            snakeHead.setTranslateY(yPos);
        }else if(direction.equals(Direction.DOWN)) {
            yPos = yPos + snakeSize;
            snakeHead.setTranslateY(yPos);
        }
    }

    private void moveSnakeTail(Rectangle snakeTail, int tailNumber){
        double yPos = positions.get(gameTicks - tailNumber + 1).getYPos() - snakeTail.getY();
        double xPos = positions.get(gameTicks - tailNumber + 1).getXPos() - snakeTail.getX();
        snakeTail.setTranslateX(xPos);
        snakeTail.setTranslateY(yPos);
    }


    private void addSnakeTail(){
        Rectangle rectangle = snakeBody.get(snakeBody.size() - 1);
        Rectangle snakeTail = new Rectangle(
                snakeBody.get(1).getX() + xPos + snakeSize,
                snakeBody.get(1).getY() + yPos,
                snakeSize,snakeSize);
        snakeBody.add(snakeTail);
        anchorPane.getChildren().add(snakeTail);
    }
}
