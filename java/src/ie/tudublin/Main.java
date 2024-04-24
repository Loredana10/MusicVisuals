package ie.tudublin;

// import example.CubeVisual;
// import example.CubeVisual1;
import example.MyVisual;
// import example.RotatingAudioBands;
// import example.WaveForm; 

public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new MyVisual());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}