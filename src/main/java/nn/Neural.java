package nn;

import java.util.*;
import static nn.MathTools.normalizeDiff;

public class Neural {
    private static int counter;
    private int id;

    private NN net;
    private Type type;
    private List<Synapse> synapses = new ArrayList<Synapse>(); //Исходящие синапсы

    private double output;
    private double input;
    private double lastInput;
    private double currentDelta;

    public static NeuronBuilder getBuilder(int amount){
        return new NeuronBuilder(amount);
    }

    Neural() {
        counter++;
        this.id = counter;
    }


    public void addInput(double partialIn){
        input+=partialIn;
    }

    public void doWork() {
        getType().work(this);
    }
    public void updateDelta() {
        getType().updateDelta(this);
    }


    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Neural)obj).getId()==id;
    }



    public enum Type {
        Input {},
        Output {
            @Override
            public void work(Neural neuron) {
                neuron.output = MathTools.normalize(neuron.input-5);
                neuron.lastInput = neuron.input;
                neuron.input = 0;
            }
            @Override
            public void updateDelta(Neural neuron) {
                double tempSum = 0;
                tempSum = (neuron.net.getRightAnswer() - neuron.output);
                System.out.println("delta=" + "(ideal=" + neuron.net.getRightAnswer() + ") - (output=" + neuron.output + ") *(normdiiff(output)=" + normalizeDiff(neuron.output) + ") =" + tempSum * normalizeDiff(neuron.output));
                neuron.currentDelta = tempSum * normalizeDiff(neuron.output);
            }
        },
        Hidden {};

        public void work(Neural neuron) {
            neuron.output = MathTools.normalize(neuron.input);
            for (Synapse synapse : neuron.getSynapses()) {
                synapse.getConsumerNeural().addInput(neuron.output * synapse.getWeight());
            }
            neuron.lastInput = neuron.input;
            neuron.input = 0;
        }

        public void updateDelta(Neural neuron){
            double tempSum = 0;
            for (Synapse synapse:neuron.getSynapses()) {
                tempSum += (synapse.getWeight() * synapse.getConsumerNeural().getCurrentDelta());
            }
            neuron.currentDelta = tempSum * normalizeDiff(neuron.output);
        }
    }


    public void setType(Type type) {
        this.type = type;
    }

    public void setNet(NN net) {
        this.net = net;
    }

    public void setSynapses(List<Synapse> synapses) {
        this.synapses = synapses;
    }

    public List<Synapse> getSynapses() {
        return synapses;
    }

    public double getLastInput() {
        return lastInput;
    }

    public double getCurrentDelta() {
        return currentDelta;
    }

    public int getId() {
        return id;
    }

    public NN getNet() {
        return net;
    }

    public Type getType() {
        return type;
    }

    public double getOutput() {
        return output;
    }

    public double getInput() {
        return input;
    }

//    public NNLayer getLayer() {
//        return layer;
//    }

//    public void setLayer(NNLayer layer) {
//        this.layer = layer;
//    }

}
class NeuronBuilder {
    private int amount;
    private List<Neural> neurons = new ArrayList<Neural>();

    NeuronBuilder(int amount){
        this.amount = amount;
        for (int i = 0; i<amount; i++){
            neurons.add(new Neural());
        }
    }

    public NeuronBuilder setNet(NN net) {
        for (int i = 0; i<amount; i++){
            neurons.get(i).setNet(net);
        }
        return this;
    }


    public NeuronBuilder setType(Neural.Type type) {
        for (int i = 0; i<amount; i++){
            neurons.get(i).setType(type);
        }
        return this;
    }

    public NeuronBuilder setSynapses(List<Neural> NextLayerNeuronsList) {
        for (int i = 0; i<amount; i++){
            List<Synapse> synapsesList = new ArrayList<Synapse>();
            for (Neural neuron: NextLayerNeuronsList){
                synapsesList.add(new Synapse(neurons.get(i),neuron));
            }
            neurons.get(i).setSynapses(synapsesList);
        }
        return this;
    }

    public List<Neural> build(){
        for (int i = 0; i<amount; i++) {
            if (neurons.get(i).getType()==null){
                if (neurons.get(i).getSynapses().size()==0) {
                    neurons.get(i).setType(Neural.Type.Output);
                } else neurons.get(i).setType(Neural.Type.Hidden);
            }
            if (neurons.get(i).getNet()==null) System.out.println("missing NN bind declare");
        }
        return neurons;
    }

//        public NeuronBuilder setLayer(NNLayer layer) {
//            for (int i = 0; i<amount; i++){
//                neurons.get(i).setLayer(layer);
//            }
//            return this;
//        }
}
