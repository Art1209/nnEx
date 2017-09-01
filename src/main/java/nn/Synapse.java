package nn;


import java.util.Random;

public class Synapse {
    private static Random rand = new Random();
    private Neural consumerNeural;
    private Neural producerNeural;
    private double weight;
    private double weightCorrection;

    public void updateCurrentWeights(){
        weightCorrection = (producerNeural.getNet().getSpeed()*consumerNeural.getCurrentDelta()*consumerNeural.getOutput())+
                (producerNeural.getNet().getMoment()*weightCorrection);
        weight += weightCorrection;
    }

    public Neural getConsumerNeural() {
        return consumerNeural;
    }

    public Synapse(Neural producerNeural, Neural consumerNeural ) {
        this.weight = rand.nextDouble();
        this.consumerNeural = consumerNeural;
        this.producerNeural = producerNeural;
    }

    public Neural getProducerNeural() {
        return producerNeural;
    }

    public double getWeight() {
        return weight;
    }

    public double getWeightCorrection() {
        return weightCorrection;
    }
}
