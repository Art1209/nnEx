package nn;


public final class MathTools {
    public static double normalize(double x){
        return  sigmoid(x);
    }
    public static double normalizeDiff(double input){
        return  sigmoidDiff(input);
    }
    public static double sigmoid(double d){
        return (1/(1+Math.pow(Math.E, -1*d)));
    }
    public static double sigmoidDiff(double out){
        return ((1-out)*out);
    }
}
