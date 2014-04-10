package fp.infiniteset.Burst.Utils;

public class HaltonSequence
{
    int[] baseVector;

    public HaltonSequence(int[] baseVector)
    {
        for(int base : baseVector)
        {
            if(base < 2)
                throw new RuntimeException("Cannot create Halton sequence with base less than two.");
        }

        this.baseVector = baseVector;
    }

    public HaltonSequence(int base)
    {
        if(base < 2)
            throw new RuntimeException("Cannot create Halton sequence with base less than two.");

        this.baseVector = new int[] { base };
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

