package nn;

/**
 * Created by aalbutov on 28.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        NN net = new NN(3,3);
        net.initTrainingSet(null);
        net.initLayers();
        net.learn(100);
    }
}
