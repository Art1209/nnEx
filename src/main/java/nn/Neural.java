package nn;


import java.util.*;

public class Neural {
    public static Random rand = new Random();
    private static int counter;
    private int id;

    private double output;
    private double input;
    private double lastInput;
    private double currentDelta;

    private NN net;
    private NNLayer layer;

    public Map<Neural, double[]> getSynapses() {
        return synapses;
    }

    private Map<Neural,double[]> synapses = new HashMap<Neural, double[]>(); //Исходящие синапсы, в double[] хранятся пары вес, предыдущее изменение веса

    public void addInput(double partialIn){
        input+=partialIn;
    }

    public void doWork(){
        output = normalize(input);
        for (Neural neural:synapses.keySet()){
            neural.addInput(output*synapses.get(neural)[0]);
        }
//        System.out.println(input+" --> "+ output);
        lastInput=input;
        this.input = 0;
    }
    public void doFirstWork(int[] args){
        output = normalize(args[id]);
        for (Neural neural:synapses.keySet()){
            neural.addInput(output*synapses.get(neural)[0]);
        }
//        System.out.println(args[id]+" --> "+ output);
        lastInput=args[id];
        input = 0;
    }
    public double doLastWork(){
        System.out.println("input: "+input);
        output = normalize(input);
        lastInput=input;
//        System.out.println(input+" --> "+ output);
        input = 0;
        return output;
    }
    public double normalize(double x){
        return  sigmoid(x);
    }
    public double normalizeDiff(double input){
        return  sigmoidDiff(input);
    }
    public void updateDelta(){
        double tempSum=0;
        if (this.layer.isOutNeural()){
            tempSum =  (net.getOutIdeal() - output);
            System.out.println("delta="+"(ideal="+net.getOutIdeal()+") - (output="+output+") *(normdiiff(output = "+output+")="+normalizeDiff(output)+") ="+tempSum*normalizeDiff(output));
        } else {
            for (Neural neural: synapses.keySet()){
                tempSum+=(synapses.get(neural)[0]*neural.getCurrentDelta());
            }
        }
        currentDelta = tempSum*normalizeDiff(output);
    }
    public void updateCurrentWeights(){
        for (Neural neural:synapses.keySet()){

            double tempLastWeight = synapses.get(neural)[0];
            double tempLastWeightDiff = synapses.get(neural)[1];

            double tempCurrentWeightDiff = (net.getSpeed()*neural.getCurrentDelta()*output)+(net.getMoment()*tempLastWeightDiff);
            double tempCurrentWeight = tempLastWeight+tempCurrentWeightDiff;

            synapses.get(neural)[0] = tempCurrentWeight;
            synapses.get(neural)[1] = tempCurrentWeightDiff;
        }
    }
    private double sigmoid(double d){
        return (1/(1+Math.pow(Math.E, -d)));
    }
    private double sigmoidDiff(double out){
        return ((1-out)*out);
    }

    public double getLastInput() {
        return lastInput;
    }

    public double getCurrentDelta() {
        return currentDelta;
    }

    public Neural setNet(NN net) {
        this.net = net;
        return this;
    }

    public Neural setLayer(NNLayer layer) {
        this.layer = layer;
        return this;
    }

    public Neural setSynapses(List<Neural> synapsesList) {
        for (Neural neural: synapsesList){
            double weightToStart = rand.nextDouble();
            synapses.put(neural, new double[]{weightToStart,0});
        }
        return this;
    }

    public Neural() {
        counter++;
        this.id = counter;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Neural)obj).getId()==id;
    }

    public int getId() {
        return id;
    }
}
