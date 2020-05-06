package com.gil.gyrotouch;

public class CosineSimilarity {
    public static double cosinsimility(float[] baltest1, float[] mea1){

        float head = 0;
        float mnohat = 0;
        float bnohat = 0;
        double body = 0;
        double cosmlity = 0;

        for(int i = 0; i<6; i++){
            head += baltest1[i] * mea1[i];
        }

        for(int i = 0; i<6; i++){
            mnohat +=  mea1[i] * mea1[i];
            bnohat += baltest1[i] * baltest1[i];
            body = Math.sqrt(mnohat) * Math.sqrt(bnohat);
        }
        cosmlity = head / body;
        return cosmlity;
    }
}
