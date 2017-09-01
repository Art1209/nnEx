//package nn;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NNLayer {
//    private Neural.Type type;
//    private List<Neural> neurals = new ArrayList<Neural>();
//    private NN net;
//    private String name;
//    private int neuralAmount;
//
//
//    private void initNeurals() {
//        for (int i = 0; i<neuralAmount; i++){
//            neurals.add(new Neural());
//        }
//    }
//
//    public void initSynapses(){
//        for (int i = 0; i<neuralAmount; i++){
//            Neural neural = neurals.get(i).setLayer(this).setNet(this.net);
//            int index = net.getLayers().indexOf(this);
//            if (index< (net.getLayers().size()-1)){
//                List<Neural> synapses = net.getLayers().get(index+1).getNeurals();
//                neural.setSynapses(synapses);
//            }
//
//        }
//    }
//
//    public void doWork(){
//        for (Neural neural: neurals){
//            neural.doWork();
//        }
//    }
//
//    public void updateWeights(){
//        String log ="";
//        for (Neural neural: neurals){
//            neural.updateDelta();
//            if (type== Neural.Type.Output){
//                continue;
//            }
//            neural.updateCurrentWeights();
//            log = log.concat(neural.getId()+" " + neural.getCurrentDelta()+ "  "+ neural.getLastInput());
////            System.out.println(log);
//            log = "";
//        }
//    }
//
//    public void setType(Neural.Type type) {
//        this.type = type;
//    }
//
//    public Neural.Type getType() {
//        return type;
//    }
//
//    @Override
//    public String toString() {
//        return "Layer: "+name;
//    }
//
//    public NNLayer(NN net, String name, int neuralAmount, Neural.Type type) {
//        this.type = type;
//        this.net = net;
//        this.name = name;
//        this.neuralAmount = neuralAmount;
//        initNeurals();
//    }
//
//    public List<Neural> getNeurals() {
//        return neurals;
//    }
//}
