package nn;

/**
 * Created by aalbutov on 28.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        NNBuilder builder = NN.getBuilder();
        NN net = builder.build();
        net.learn(10);
        System.out.println("end");
    }
}
