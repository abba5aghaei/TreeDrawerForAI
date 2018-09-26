/**INOG
 * @author abba5aghaei
 */

package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import ai.view.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private Stage stage;
	private Controller controller;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/Graphic.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			stage = primaryStage;
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.setTitle("هوش مصنوعی");
			stage.getIcons().addAll(new Image(getClass().getResourceAsStream("view/icon.png")));
			controller = loader.getController();
			controller.setMain(this);
			stage.show();
			loadSetting();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Controller getController() {
		return controller;
	}

	public Stage getStage() {
		return stage;
	}
	
	public void saveSetting() {
		try {
			File file = new File("config.abs");
			PrintWriter writer = new PrintWriter(file);
			writer.println(controller.getNodeColor().toString());
			writer.println(controller.getEdgeColor().toString());
			writer.println(controller.getTextColor().toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSetting() {
		controller.println("در حال بارگذاری تنظیمات...");
		Thread loader = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					File file = new File("config.abs");
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String nc = reader.readLine();
					String ec = reader.readLine();
					String tc = reader.readLine();
					reader.close();
					Platform.runLater(new Runnable() {
					@Override
					public void run() {
					controller.setNodeColor(nc);
					controller.setTextColor(ec);
					controller.setTextColor(tc);
					controller.println("انجام شد!");
					controller.println("رنگ گره ها با مقدار("+nc+") تنظیم شد");
					controller.println("رنگ یالها با مقدار("+ec+") تنظیم شد");
					controller.println("رنگ متن با مقدار("+tc+") تنظیم شد");
					controller.println(LocalDate.now().toString()+"\t"+LocalTime.now());
					}});
				} catch (Exception e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							controller.println("خطا در بارگذاری!");
							controller.println("در حال اعمال تنظیمات پیش فرض...");
							controller.println("انجام شد!");
						}});
				}
			}
		});
		loader.start();
	}
}
