/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Janelas extends Application {
    
    private static Stage stage;
    private static Scene mainScene;
    private static Scene Video;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        stage = primaryStage;
        
        Parent FxmlMain = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        mainScene = new Scene(FxmlMain);
        
        Parent Fxmlopcao1 = FXMLLoader.load(getClass().getResource("Video.fxml"));
        Video = new Scene(Fxmlopcao1);

        primaryStage.setScene(mainScene);
        primaryStage.show();
        
        
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
    }

   public static void changeScreen(int i){
       switch(i){
           case 0:
               stage.setScene(mainScene);
               stage.setFullScreen(true);
               stage.setResizable(false);
               break;
           case 1:
               stage.setScene(Video);
               stage.setFullScreen(true);
               stage.setResizable(false);
               Video.setOnKeyPressed((evt) -> {
               System.out.println("tecla apertada");
        });
               break;
       }
   }
   
   public static boolean isFullScreen()
   {
        return stage.isFullScreen();
   }
   
   public static void setFullScreen(){
       stage.setFullScreen(true);
   }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
