package nn;


import java.io.*;
import java.util.*;

public class NN {
    private double speed = 0.4f;
    private double moment = 0.05f;
    private int inputs = 3;
    private int layersAmount;
    private int outIdeal;
    private String fileName = "C:\\Users\\aalbutov\\IdeaProjects\\RestClient\\TrainedData";
    List<int[]> trainingSet = new ArrayList<int[]>();

    public List<NNLayer> getLayers() {
        return layers;
    }

    List<NNLayer> layers = new ArrayList<NNLayer>();


    public NN(int inputs, int layersAmount) {
        this.inputs = inputs;
        this.layersAmount = layersAmount;
    }

    public void initTrainingSet(String file){
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

    public void initLayers() {
        String tempName;
        int neuralAmount;

        tempName = "Layer#"+1;
        layers.add(new NNLayer(this, tempName,inputs,false));

        for (int i=1; i<(layersAmount-1);i++){
            tempName = "Layer#"+(i+1);
            neuralAmount = inputs*3/(layersAmount-1);
//            neuralAmount = 3;
            layers.add(new NNLayer(this, tempName,neuralAmount,false));
        }

        tempName = "Layer#"+(layersAmount);
        layers.add(new NNLayer(this, tempName,1,true));

        for (int i=0; i<layersAmount;i++){
            layers.get(i).initSynapses();
        }
    }

    public void learn(int iters){
        for (int i = 0; i<iters; i++){
            for (int k = 0;k<trainingSet.size(); k++){
                double result;
                this.setOutIdeal(trainingSet.get(k)[3]);
                layers.get(0).doFirstWork(trainingSet.get(k));
                for (int j = 1;j<layers.size()-1;j++){
                    layers.get(j).doWork();
                }
                result = layers.get(layers.size()-1).doLastWork();
                System.out.println("result: "+result);
                for (int j = layers.size()-1;j>-1;j--){
                    layers.get(j).updateWeights();
                }
                for (NNLayer layer: layers){
                    String out ="";
                    for (Neural neural:layer.getNeurals()){
                        for (double[] d:neural.getSynapses().values()){
                            out = out.concat("   "+Double.toString(d[0]));
                        }

//                        System.out.println("weight: "+ (out==""?" end":out));
                        out = "";
                    }
                }
        }}
    }

    public double getSpeed() {
        return speed;
    }

    public double getMoment() {
        return moment;
    }

    public void setOutIdeal(int outIdeal) {
        this.outIdeal = outIdeal;
    }

    public int getOutIdeal() {
        return outIdeal;
    }
}
