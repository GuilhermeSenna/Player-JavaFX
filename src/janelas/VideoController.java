package janelas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class VideoController implements Initializable {

    //String path = new File("C:\\Users\\guilh\\OneDrive\\Documentos\\NetBeansProjects\\Janelas\\src\\vids\\falhar.mp4").getAbsolutePath();
      
    //DoubleProperty width = mv.fitWidthProperty();
    //DoubleProperty height = mv.fitHeightProperty();
    //width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
    //height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
    
    private MediaPlayer mp;
    private Media me;    
    private static int aba = 1;
    private static int tk = 5; //time skip
    private static double W;
    private static double Vol;
    private Status status;
    private static Duration duration;
    private boolean atEndOfMedia = false;
    
    @FXML
    private MediaView mv;
    @FXML
    private Button playpause;
    @FXML
    private Button reload;
    @FXML
    private Button btnRatio;
    @FXML
    private Button backButton;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnNext;
    @FXML
    private ChoiceBox<String> cb;
    @FXML
    private ChoiceBox<String> cb2;
    @FXML
    private Slider audioSlider;
    @FXML
    private Slider videoSlider;
    @FXML
    private ScrollPane sp;   
    @FXML
    private Label lblTexto;
    @FXML
    private StackPane stp;
    @FXML
    private Text volText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Por padrão não faz nada ao acabar o video
        cb.setValue("Nothing");
        cb.getItems().add("Nothing");
        cb.getItems().add("Looping");
        cb.getItems().add("Next");
        cb.getItems().add("Back");
        cb2.setValue("5");
        cb2.getItems().add("1");
        cb2.getItems().add("2");
        cb2.getItems().add("5");
        cb2.getItems().add("10");
        
        Vol = 100;
        volText.setText(Integer.toString((int)Vol));
        audioSlider.setValue(Vol);
        
        // Inicializa sem poder reproduzir nada, pois não tem vídeo nenhum selecionado
        playbuttons(true);
        
        audioSlider.valueProperty().addListener((Observable observable) -> {
           Vol = audioSlider.getValue();
           if(!playpause.isDisabled())
               mp.setVolume(Vol/100);
           volText.setText(Integer.toString((int)Vol));
        });
        
        cb2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tk = Integer.parseInt(cb2.getItems().get((Integer)newValue));
            }
        });
        
        stp.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                Maximizar();
            }
        }
    }
    });
        
        // imagem para os botões
        playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\play.png"));
        reload.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\restart.png"));
        btnRatio.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\ratio.png"));
        backButton.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\left.png"));
        btnNext.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\next.png"));
        btnBack.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\back.png"));
    }    
    
    // Ativar ou desativar os botões do player
    void playbuttons(boolean x){
        btnNext.setDisable(x);
        btnBack.setDisable(x);
        playpause.setDisable(x);
        reload.setDisable(x);
        btnRatio.setDisable(x);
    }
    
    @FXML
    void voltarMenu(ActionEvent event) {
        if(!playpause.isDisable()){
            status = mp.getStatus();
        }
        if(status == Status.PLAYING || status == Status.PAUSED){
            mp.stop(); 
        }
        playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\play.png"));
        Janelas.changeScreen(0);
    }
    
    // Ao clicar numa opção ele ativa os botões de controle de video (play, pause, etc...)
    // E também passa o caminho que está o vídeo.
    void video(String pt){
        
        // Segundo uso, após clicar em um vídeo e imediatamente outro.
        
        if(!playpause.isDisable()){
            status = mp.getStatus();
            if (status == Status.PLAYING)
                mp.stop();
       }     
        
        // Ativa os botões após algum video ser selecionado
        playpause.setGraphic(image("C:\\Users\\\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\play.png"));
        playbuttons(false);
        
       // O Vídeo é carregado
       String path = new File(pt).getAbsolutePath();
       me = new Media(new File(path).toURI().toString());
       mp = new MediaPlayer(me);
       mv.setMediaPlayer(mp);
       
       mp.setVolume(Vol/100);
 
       mp.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
           showTime();
           videoSlider.setValue(newValue.toSeconds());
        });
      
       videoSlider.setOnMousePressed((MouseEvent event) -> {
           mp.pause();
        });
       
       videoSlider.setOnMouseReleased((MouseEvent event) -> {
           mp.seek(Duration.seconds(videoSlider.getValue()));
           mp.play();
        });
       
       // Quando o video acabar
       mp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                switch (cb.getValue()) {
                    case "Nothing":
                        break;
                    case "Looping":
                        playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\pause.png"));
                        mp.seek(mp.getStartTime());
                        break;
                    case "Next":
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    ImageView image(String pt){
        String path = new File(pt).getAbsolutePath();
        Image image = new Image(new File(path).toURI().toString());
        return new ImageView(image);
    }
    
    @FXML
    void trocarVideo1(ActionEvent event) {
        video("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\vids\\falhar.mp4");
    }
    
    @FXML
    void trocarVideo2(ActionEvent event) {
        video("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\vids\\pixar.mp4");
    }
    
    @FXML
    void trocarVideo3(ActionEvent event) {
        //video("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\vids\\Mundo 2.mp4");
        video("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\vids\\pixar.mp4");
    }
    
    @FXML
    void trocarVideo4(ActionEvent event) {
      video("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\vids\\sogeking.mp4");
    }
    
    public void showTime(){
        duration = mp.getMedia().getDuration();
        Duration currentTime = mp.getCurrentTime();
        lblTexto.setText(formatTime(currentTime, duration));
        videoSlider.setDisable(duration.isUnknown());
        if (!videoSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !videoSlider.isValueChanging())
            videoSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
    }
    
    public void rightTimeVideoShow(){
        videoSlider.maxProperty().bind(Bindings.createDoubleBinding(
        () -> mp.getTotalDuration().toSeconds(),
        mp.totalDurationProperty()));
    }
    
    public void PlayPause(ActionEvent event){
        status = mp.getStatus();
        if(status == Status.PAUSED || status == Status.READY || status == Status.STOPPED){
            // O Status ready é obtido logo após carregar um vídeo e iniciar ele pela primeira vez.
            if(status == Status.READY){
               showTime();
               rightTimeVideoShow();
            }
            mp.play();
            playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\pause.png"));
        }
        else if(status == Status.PLAYING){
            mp.pause();
            playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\play.png"));
        }
    }
    
    public void Reload(ActionEvent event){
        mp.seek(mp.getStartTime());
        mp.play();
        playpause.setGraphic(image("C:\\Users\\guilh\\Documents\\NetBeansProjects\\Janelas\\src\\icons\\pause.png"));
    }
    
    void Maximizar(){
       if(!Janelas.isFullScreen()){
           Janelas.setFullScreen();
       }
    }
    
    public void ajustarTela(ActionEvent event){
        if (mv.isPreserveRatio()){
           mv.setPreserveRatio(false);
           mv.setFitWidth(mv.getFitWidth() + 40);
           mv.setFitHeight(mv.getFitHeight() + 25);
       }
       else{
           mv.setPreserveRatio(true);
           mv.setFitWidth(mv.getFitWidth() - 40);
           mv.setFitHeight(mv.getFitHeight() - 25);
       }
    }
    
    public void mostrarAba(MouseEvent event){
        if(aba == 0){
            sp.setMaxWidth(W);
            sp.setOpacity(1);
            aba = 1;
            mv.setFitWidth(mv.getFitWidth()-100);
        }
        else{
            
            W = sp.getWidth();
        }
    }
     
    public void fecharAba(MouseEvent event){
        if (aba == 1){
            sp.setMaxWidth(5);
            sp.setOpacity(0);
            sp.setStyle("-fx-background-color:black");
            aba = 0;
            mv.setFitWidth(mv.getFitWidth()+100);
        }
    }
    
    // Método pego do MediaControl do media player feito pela Oracle.
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
    
    @FXML
    void Next(ActionEvent event) {
        double d = mp.getCurrentTime().toSeconds();
        d += tk;
        mp.seek(new Duration(d*1000));
    }
    
    @FXML
    void Back(ActionEvent event) {
        double d = mp.getCurrentTime().toSeconds();
        d -= tk;
        mp.seek(new Duration(d*1000));
    }

}