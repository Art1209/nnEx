package nn;


import java.util.ArrayList;
import java.util.List;

public class NNLayer {
    private boolean outNeural;
    private List<Neural> neurals = new ArrayList<Neural>();
    private NN net;
    private String name;
    private int neuralAmount;


    private void initNeurals() {
        for (int i = 0; i<neuralAmount; i++){
            neurals.add(new Neural());
        }
    }

    public void initSynapses(){
        for (int i = 0; i<neuralAmount; i++){
            Neural neural = neurals.get(i).setLayer(this).setNet(this.net);
            int index = net.getLayers().indexOf(this);
            if (index< (net.getLayers().size()-1)){
                List<Neural> synapses = net.getLayers().get(index+1).getNeurals();
                neural.setSynapses(synapses);
            }

        }
    }

    public void doWork(){
        for (Neural neural: neurals){
            neural.doWork();
        }
    }

    public void doFirstWork(int[] args){
        for (Neural neural: neurals){
            neural.doFirstWork(args);
        }
    }

    public double doLastWork(){
        return neurals.get(0).doLastWork();

    }

    public void updateWeights(){
        String log ="";
        for (Neural neural: neurals){
            neural.updateDelta();
            if (outNeural){
                continue;
            }
            neural.updateCurrentWeights();
            log = log.concat(neural.getId()+" " + neural.getCurrentDelta()+ "  "+ neural.getLastInput());
//            System.out.println(log);
            log = "";
        }
    }

    public void setOutNeural(boolean outNeural) {
        this.outNeural = outNeural;
    }

    public boolean isOutNeural() {
        return outNeural;
    }

    @Override
    public String toString() {
        return "Layer: "+name;
    }

    public NNLayer(NN net, String name, int neuralAmount, boolean outNeural) {
        this.outNeural = outNeural;
        this.net = net;
        this.name = name;
        this.neuralAmount = neuralAmount;
        initNeurals();
    }

    public List<Neural> getNeurals() {
        return neurals;
    }
}
