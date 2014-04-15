package fp.infiniteset.Burst.Utils;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class HaltonSequence
{
    private static final int[] PRIMES = new int[]
    {
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173
    };

    private static final int[] WEIGHTS = new int[]
    {
        1, 2, 3, 3, 8, 11, 12, 14, 7, 18, 12, 13, 17, 18, 29, 14, 18, 43, 41,
            44, 40, 30, 47, 65, 71, 28, 40, 60, 79, 89, 56, 50, 52, 61, 108, 56,
            66, 63, 60, 66
    };

    private int dimension;
    private int[] base;
    private int[] weight;
    private int index;

    public HaltonSequence(int dimension)
    {
        this(dimension, PRIMES, WEIGHTS);
    }

    public HaltonSequence(int dimension, int[] bases, int[] weights)
    {
        this.dimension = dimension;
        this.base = bases.clone();
        this.weight = weights == null ? null : weights.clone();

        Random rng = new Random();
        index = rng.nextInt(30) + 20;
    }

    public Vector2 nextFloat2d()
    {
        final double[] v = nextVector();
        return new Vector2((float)v[0], (float)v[1]);
    }

    public double[] nextVector()
    {
        final double[] v = new double[dimension];
        for (int i = 0; i < dimension; i++)
        {
            int count = index;
            double f = 1.0 / base[i];

            int j = 0;
            while (count > 0)
            {
                final int digit = scramble(i, j, base[i], count % base[i]);
                v[i] += f * digit;
                count /= base[i]; // floor( index / base )
                f /= base[i];
            }
        }
        index++;
        return v;
    }

    protected int scramble(final int i, final int j, final int b, final int digit)
    {
        return weight != null ? (weight[i] * digit) % b : digit;
    }

    public double[] skipTo(final int index)
    {
        this.index = index;
        return nextVector();
    }

    public int getNextIndex()
    {
        return index;
    }
}

