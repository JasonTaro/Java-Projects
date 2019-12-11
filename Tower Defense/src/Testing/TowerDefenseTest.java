package Testing;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import static java.lang.Math.random;

public class TowerDefenseTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        Circle c = new Circle();
		c.setFill(Color.WHITE);
		c.setRadius(10);
		c.setCenterX(50);
		c.setCenterY(50);
		
		Path p = new Path();
//		
//		System.out.println("move to ROW : " + message.getTower().getTowerROW()*50);
//		System.out.println("move to COL : " + message.getTower().getTowerCOL()*50);
//		System.out.println("line to ROW : " + message.getEnemy().getX());
//		System.out.println("line to COL : " + message.getEnemy().getY());
		p.getElements().add(new MoveTo(50, 50));
		p.getElements().add(new LineTo(100, 100));

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(1000));
		pt.setNode(c);
		pt.setPath(p);
		pt.play();

        primaryStage.show();
    }
}