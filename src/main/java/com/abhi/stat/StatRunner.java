package com.abhi.stat;

import cern.colt.list.tdouble.DoubleArrayList;
import cern.colt.matrix.tdouble.algo.DoubleStatistic;
import cern.jet.stat.tdouble.DoubleDescriptive;
import org.uncommons.maths.random.DevRandomSeedGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SeedException;

/**
 * Created with IntelliJ IDEA.
 * User: abhiaiyer
 * Date: 10/16/13
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatRunner {

    public static void main(String[] args) throws SeedException {
        DoubleArrayList array = new DoubleArrayList();
        DevRandomSeedGenerator seedGenerator = new DevRandomSeedGenerator();
        MersenneTwisterRNG rng = new MersenneTwisterRNG(seedGenerator);

        for (int i = 0 ; i < 1000000; i++) {
            array.add(rng.nextDouble());
        }

        double mean = DoubleDescriptive.mean(array);
        double firstMoment = DoubleDescriptive.moment(array,1,0);
        double stdDev = DoubleDescriptive.moment(array,2,mean);

        System.out.println(String.format("%s %s %s", mean, firstMoment, stdDev));


    }
}
