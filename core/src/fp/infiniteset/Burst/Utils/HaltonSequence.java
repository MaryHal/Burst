package fp.infiniteset.Burst.Utils;

import java.util.Random;
import java.util.TreeSet;

public class HaltonSequence
{
    private static final int[] PRIMES = new int[] 
    {
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173
    };

    int[] baseVector;

    // public HaltonSequence(int base)
    // {
    //     if(base < 2)
    //         throw new RuntimeException("Cannot create Halton sequence with base less than two.");

    //     this.baseVector = new int[] { base };
    // }

    public HaltonSequence(int dimension)
    {
        Random rng = new Random();
        TreeSet<Integer> bases = new TreeSet<Integer>();

        while (bases.size() < dimension)
        {
            for (int i = 0; i < dimension; i++)
            {
                bases.add(PRIMES[rng.nextInt(PRIMES.length)]);
            }
        }

        // Let's not work with Integers
        Integer[] uniqueBases = new Integer[dimension];
        uniqueBases = bases.toArray(uniqueBases);
        baseVector = new int[dimension];
        for (int i = 0; i < uniqueBases.length; i++)
        {
            baseVector[i] = uniqueBases[i];
        }
    }

    public HaltonSequence(int[] baseVector)
    {
        for(int base : baseVector)
        {
            if(base < 2)
                throw new RuntimeException("Cannot create Halton sequence with base less than two.");
        }

        this.baseVector = baseVector;
    }

    public double[] getHaltonNumber(int index)
    {
        double[] x = new double[baseVector.length];
        for (int i = 0; i < baseVector.length; i++) 
        {
            x[i] = getHaltonNumber(index, baseVector[i]);
        }
        return x;
    }

    static double getHaltonNumber(int index, int base)
    {
        // Check base
        if(base < 2) throw new RuntimeException("Cannot create Halton number with base less than two.");
        if(index < 0) throw new RuntimeException("Cannot create Halton number with index less than zero.");

        // Index shift: counting of the function start at 0, algorithm below start at 1.
        index++;

        // Calculate Halton number x
        double x = 0;
        double factor = 1.0/base;
        while(index > 0)
        {
            x += (index % base) * factor;
            factor /= base;
            index /= base;
        }
        return x;
    }
}

