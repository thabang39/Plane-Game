package com.warship;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    ImageView c1 = new ImageView(new Image("v.png"));
    ImageView image = new ImageView(new Image("p.png"));
    ImageView c2 = new ImageView(new Image("v.png"));
    ImageView c3 = new ImageView(new Image("v.png"));

    //coins
    ImageView coin1 = new ImageView(new Image("coin.png"));
    ImageView coin2 = new ImageView(new Image("coin.png"));

    //buttons
    Button btn = new Button("Play");
    Button btn2 = new Button("Pause");
    Button btn1 = new Button("Exit");

    int x = 0;

    Label text = new Label(" ");
    Label score = new Label("Score : ");
    Label scoreNo = new Label(" "+x);


    Pane root = new Pane();

    //cloud translation
    TranslateTransition t1 = new TranslateTransition(Duration.millis(6000), c1);
    TranslateTransition t2 = new TranslateTransition(Duration.millis(5000), c2);
    TranslateTransition t3 = new TranslateTransition(Duration.millis(7000), c3);

    //coin translation
    TranslateTransition tc1 = new TranslateTransition(Duration.millis(7000), coin1);
    TranslateTransition tc2 = new TranslateTransition(Duration.millis(6000), coin2);
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(root, 800, 400);
        scene.getStylesheets().add("style.css");

        btn1.setTranslateX(110);
        btn2.setTranslateX(50);

        text.setTranslateX(300);
        text.setTranslateY(150);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));

        score.setTranslateX(700);
        scoreNo.setTranslateX(750);


        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                t1.play();
                t2.play();
                t3.play();
                tc1.play();
                tc2.play();
                text.setText(" ");
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                t1.stop();
                t2.stop();
                t3.stop();
                tc1.stop();
                tc2.stop();
                text.setText(" ");
            }
        });

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        ImageView c1 = Cloud1(scene);
        ImageView c2 = Cloud2(scene);
        ImageView c3 = Cloud3(scene);
        ImageView ship = createShip(scene);

        ImageView coin1 = Coin1(scene);
        ImageView coin2 = Coin2(scene);

        root.getChildren().addAll(ship, c1, c2, c3,coin1,coin2,btn, btn1,btn2,text,score,scoreNo);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            double y = ship.getY();
            double xx = ship.getX();

            switch (event.getCode()){
                case UP -> ship.setY(y - 10);
                case DOWN -> ship.setY(y + 10);
                case RIGHT -> ship.setX(xx + 10);
                case LEFT -> ship.setX(xx - 10);
            }
        });
        collisionTimer.start();

        stage.setTitle("Air Game");
        stage.setScene(scene);
        stage.show();
    }

    private ImageView Cloud1(Scene scene) {

        c1.setFitWidth(80);
        c1.setFitHeight(80);
        c1.setY(300);
        c1.setX(-130);


        t1.setFromX(scene.getWidth() + 300);
        t1.setCycleCount(Integer.MAX_VALUE);
        t1.setToX(100);
        t1.play();
        return c1;
    }

    private ImageView Cloud2(Scene scene) {
        c2.setFitWidth(80);
        c2.setFitHeight(80);
        c2.setX(-300);


        t2.setFromX(scene.getWidth() + 250);
        t2.setCycleCount(Integer.MAX_VALUE);
        t2.setToX(100);
        t2.play();
        return c2;
    }

    private ImageView Cloud3(Scene scene) {
        c3.setFitWidth(80);
        c3.setFitHeight(80);
        c3.setY(120);
        c3.setX(-120);


        t3.setFromX(scene.getWidth() + 450);
        t3.setCycleCount(Integer.MAX_VALUE);
        t3.setToX(5);
        t3.play();
        return c3;
    }

    private ImageView Coin1(Scene scene) {

        coin1.setFitWidth(40);
        coin1.setFitHeight(40);
        coin1.setY(300);
        coin1.setX(-130);


        tc1.setFromX(scene.getWidth() + 300);
        tc1.setCycleCount(Integer.MAX_VALUE);
        tc1.setToX(100);
        tc1.play();
        return coin1;
    }
    private ImageView Coin2(Scene scene) {
        coin2.setFitWidth(40);
        coin2.setFitHeight(40);
        coin2.setX(-300);


        tc2.setFromX(scene.getWidth() + 250);
        tc2.setCycleCount(Integer.MAX_VALUE);
        tc2.setToX(100);
        tc2.play();
        return coin2;
    }


    private ImageView createShip(Scene scene) {
        image.setFitWidth(90);
        image.setFitHeight(90);
        //image.setRotate(-90);
        image.setY(scene.getHeight() - image.getFitHeight());
        return image;
    }

    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            CheckCollision(image,c1,c2,c3,coin1);
        }
    };

    public void CheckCollision(ImageView image, ImageView c1,ImageView c2,ImageView c3,ImageView coin1 ){
        if(image.getBoundsInParent().intersects(c1.getBoundsInParent())){
            t1.stop();
            t2.stop();
            t3.stop();
            tc1.stop();
            tc2.stop();
            text.setText("Game Over!!");


        }
        else if (image.getBoundsInParent().intersects(c2.getBoundsInParent())){
            t2.stop();
            t1.stop();
            t3.stop();
            tc1.stop();
            tc2.stop();
            text.setText("Game Over!!");

        }
        else if (image.getBoundsInParent().intersects(c3.getBoundsInParent())){
            t3.stop();
            t1.stop();
            t2.stop();
            tc1.stop();
            tc2.stop();
            text.setText("Game Over!!");

        }

        if(image.getBoundsInParent().intersects(coin1.getBoundsInParent())){
            //System.out.println(x);
            scoreNo.setText(""+x);
            x=x+1;
            coin1.setVisible(false);


        }
        else if( image.getBoundsInParent().intersects(coin2.getBoundsInParent())){
            //System.out.println(x);
            scoreNo.setText(""+x);
            x=x+1;
            coin2.setVisible(false);
        }

    }
//MediaView mediaView=new MediaView();


    public static void main(String[] args) {
        launch();
    }
}