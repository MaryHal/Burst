package fp.infiniteset.Burst.Utils;

// import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.utils.Array;

public class FreeTypeFontLoader extends AsynchronousAssetLoader<BitmapFont, FreeTypeFontLoader.FreeTypeParameter>
{
    static public class FreeTypeParameter extends AssetLoaderParameters<BitmapFont>
    {
        public FreeTypeFontGenerator.FreeTypeFontParameter parameters;

        public FreeTypeParameter()
        {
            parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        }
    }

    private BitmapFont font;

    public FreeTypeFontLoader(FileHandleResolver resolver)
    {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file,
            FreeTypeParameter parameter)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        font = generator.generateFont(parameter.parameters);
        generator.dispose();
    }

    @Override
    public BitmapFont loadSync(AssetManager manager, String fileName, FileHandle file,
            FreeTypeParameter parameter)
    {
        return font;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
            FreeTypeParameter parameter)

    {
        return null;
    }
}
