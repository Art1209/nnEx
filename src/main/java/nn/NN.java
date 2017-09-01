package nn;


import java.io.*;
import java.util.*;

public class NN {
    private double speed = 0.5f;
    private double moment = 0.3f;
    private int inputs = 3;
    private int hiddenLayersAmount =3;
    private int neuronPerHiddenLayerAmount=20;
    private String fileName = "C:\\Users\\aalbutov\\IdeaProjects\\FannEx\\TrainedData";

    private int rightAnswer;

    List<int[]> trainingSet = new ArrayList<int[]>();
    List<List<Neural>> layers = new ArrayList<List<Neural>>();


    NN() {
    }

    public static NNBuilder getBuilder(){
        return new NNBuilder();
    }

    void initTrainingSet(String file){
        if (file!=null)fileName = file;
        try{
            BufferedReader buff= new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String line;
            while ((line = buff.readLine())!=null){
                String[] tempStringArray = line.split(" ");
                int[] tempIntArray = new int[tempStringArray.length];
                for (int j =0; j<tempStringArray.length;j++){
                    tempIntArray[j] = Integer.parseInt(tempStringArray[j]);
                }
                trainingSet.add(tempIntArray);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();}
    }

    void initLayers() {

        // build output layer with 1 output neuron
        NeuronBuilder builder = Neural.getBuilder(1);
        layers.add(builder.setNet(this).setType(Neural.Type.Output).build());

        // build hidden layers with set number of neurons
        for (int i=0; i<hiddenLayersAmount;i++){
            builder = Neural.getBuilder(neuronPerHiddenLayerAmount);
            layers.add(builder.setNet(this).setType(Neural.Type.Hidden).setSynapses(layers.get(layers.size()-1)).build());
        }

        builder = Neural.getBuilder(inputs);
        layers.add(builder.setNet(this).setType(Neural.Type.Input).setSynapses(layers.get(layers.size()-1)).build());
    }

    public void iterate(int [] input, int output){
        setRightAnswer(output);
        for (int i = 0; i<input.length;i++){
            layers.get(layers.size()-1).get(i).addInput(input[i]); // set input values to input layer
        }
        for (int i = layers.size()-1; i>=0; i--){
            for (Neural neural: layers.get(i)){
                neural.doWork();
            }
        }
        for (int i = 0; i<layers.size(); i++){
            for (Neural neural: layers.get(i)){
                for (Synapse synapse:neural.getSynapses()){
                    synapse.updateCurrentWeights();
                }
                neural.updateDelta();
            }
        }
        System.out.println(Arrays.toString(input)+" ->  "+
                layers.get(0).get(0).getOutput()+ " ideal = " + output+ " , delta ="+layers.get(0).get(0).getCurrentDelta());

    }

    public void learn(int iters){
        for (int i = 0; i<iters; i++){
            for (int k = 0;k<trainingSet.size(); k++){
                double result;
                int [] trainArray = trainingSet.get(k);
                int[] inputArray = Arrays.copyOfRange(trainArray, 0, trainArray.length-1);
                int answer = trainArray[trainArray.length-1];
                iterate(inputArray, answer);
            }
        }
    }


    public List<List<Neural>> getLayers() {
        return layers;
    }

    public double getSpeed() {
        return speed;
    }

    public double getMoment() {
        return moment;
    }


    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setMoment(double moment) {
        this.moment = moment;
    }

    public void setInputs(int inputs) {
        this.inputs = inputs;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setHiddenLayersAmount(int hiddenLayersAmount) {
        this.hiddenLayersAmount = hiddenLayersAmount;
    }

    public int getNeuronPerHiddenLayerAmount() {
        return neuronPerHiddenLayerAmount;
    }

    public void setNeuronPerHiddenLayerAmount(int neuronPerHiddenLayerAmount) {
        this.neuronPerHiddenLayerAmount = neuronPerHiddenLayerAmount;
    }
}

class NNBuilder{
    NN net;
    NNBuilder(){
        net = new NN();
    }
    public NNBuilder setSpeed(double speed){
        net.setSpeed(speed);
        return this;
    }
    public NNBuilder setMoment(double moment){
        net.setMoment(moment);
        return this;
    }
    public NNBuilder setInputs(int inputs){
        net.setInputs(inputs);
        return this;
    }
    public NNBuilder setHiddenLayersAmount(int layerAmount){
        net.setHiddenLayersAmount(layerAmount);
        return this;
    }
    public NNBuilder setNeuronPerHiddenLayerAmount(int neuronPerHiddenLayerAmount){
        net.setNeuronPerHiddenLayerAmount(neuronPerHiddenLayerAmount);
        return this;
    }
    public NNBuilder setTrainedDataFile(String path){
        net.initTrainingSet(path);
        return this;
    }
    public NN build(){
        net.initTrainingSet(null);
        net.initLayers();
        return net;
    }
}
