/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import janelas.Janelas;
import java.io.File;
import javafx.scene.media.Media;

public class FXMLDocumentController implements Initializable {
    @FXML
    private BorderPane bpBorder;
    
    @FXML
    private ImageView ivView;
    
    private static int status = 0; 
    
    private static String decodedPath = null;
    
    void image(String pt){
        String path = new File(pt).getAbsolutePath();
        Image image = new Image(new File(path).toURI().toString());
        ivView.setImage(image);
        ivView.setPreserveRatio(false);
        ivView.setSmooth(true);
        ivView.setCache(true);
    }
    
    
    @FXML
    void mostrarImagem1(ActionEvent event) {
        image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\imgs\\angry.jpg");
        status = 1;
    }

    @FXML
    void mostrarImagem2(ActionEvent event) {
        image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\imgs\\twd.png");
        status = 2;
    }

    @FXML
    void mostrarImagem3(ActionEvent event) {
        image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\imgs\\juiz.png");
        status = 3;
    }
    
    @FXML
    void mostrarValor(ActionEvent event) {
        switch(status){
            case 1:
                Janelas.changeScreen(1);
                break;
            case 2:
                System.out.println("O valor escolhido foi 2");
                break;
            case 3:
                System.out.println("O valor escolhido foi 3");
                break;
            default:
                System.out.println("Nenhuma acao foi escolhida ");
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
