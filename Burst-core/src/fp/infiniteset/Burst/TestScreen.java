package fp.infiniteset.Burst;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class TestScreen implements Screen
{
    private BurstGame game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ParticleEffect prototype;
    private ParticleEffectPool pool;
    private Array<PooledEffect> effects;

    // constructor to keep a reference to the main Game class
    public TestScreen(BurstGame game)
    {
        this.game = game;
    }

    @Override
    public void render(float delta)
    {
        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(PooledEffect effect : effects) {
            effect.draw(batch, delta);
            if(effect.isComplete()) {
                effects.removeValue(effect, true);
                effect.free();
            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void show()
    {
        // Called when this screen is set as the screen with game.setScreen();
        System.out.println("Opengl ES 2.0: " + (Gdx.graphics.isGL20Available() ? "Supported!" : "Unsupported."));
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        batch = new SpriteBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);

        prototype = new ParticleEffect();
        prototype.load(Gdx.files.internal("effects/test.p"), Gdx.files.internal("effects"));
        prototype.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        prototype.start();

        pool = new ParticleEffectPool(prototype, 0, 70);
        effects = new Array<PooledEffect>();

        InputAdapter adapter = new InputAdapter()
        {
            public boolean keyUp(int keycode)
            {
                if (keycode == Keys.A)
                {
                    PooledEffect effect = pool.obtain();
                    effect.setPosition(240, 150);
                    effects.add(effect);

                    return true;
                }
                return false;
            }
            public boolean touchUp(int x, int y, int pointer, int button)
            {
                PooledEffect effect = pool.obtain();
                effect.setPosition(x, y);
                effects.add(effect);

                return true;
            }
        };
        Gdx.input.setInputProcessor(adapter);

        System.out.println("All Set!");
    }

    @Override
    public void hide()
    {
        // called when current screen changes from this to a different screen
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        // never called automatically
        batch.dispose();
        prototype.dispose();
    }
}
