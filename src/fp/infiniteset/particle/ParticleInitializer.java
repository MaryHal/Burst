package fp.infiniteset.particle;

public interface ParticleInitializer
{
    // Set a single point in PointCloud. This gets called
    // multiple times from Emitter
    public void setParticle(PointCloud p);
}

