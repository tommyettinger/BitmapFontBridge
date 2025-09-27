package com.github.tommyettinger.bridge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;

public class TestLoadingBitmapFont extends ApplicationAdapter {
    public void create() {
        BitmapFont json = BitmapFontSupport.loadStructuredJson(Gdx.files.internal("Grenze-standard.json"), "Grenze-standard.png");
        BitmapFont dat = BitmapFontSupport.loadStructuredJson(Gdx.files.internal("Grenze-standard.dat"), "Grenze-standard.png");
        BitmapFont ubj = BitmapFontSupport.loadStructuredJson(Gdx.files.internal("Grenze-standard.ubj"), "Grenze-standard.png");
        BitmapFont jsonLzma = BitmapFontSupport.loadStructuredJson(Gdx.files.internal("Grenze-standard.json.lzma"), "Grenze-standard.png");
        BitmapFont ubjLzma = BitmapFontSupport.loadStructuredJson(Gdx.files.internal("Grenze-standard.ubj.lzma"), "Grenze-standard.png");


        if(!MathUtils.isEqual(json.getLineHeight(), jsonLzma.getLineHeight(), 0.0001f)) System.out.println("Not equal!");
        if(!MathUtils.isEqual(ubj.getLineHeight(), ubjLzma.getLineHeight(), 0.0001f)) System.out.println("Not equal!");
        if(!MathUtils.isEqual(json.getLineHeight(), ubj.getLineHeight(), 0.0001f)) System.out.println("Not equal!");
        if(!MathUtils.isEqual(dat.getLineHeight(), ubj.getLineHeight(), 0.0001f)) System.out.println("Not equal!");

        System.out.println("Everything loaded successfully!");

        Gdx.app.exit();
    }

    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new TestLoadingBitmapFont(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("TestBitmapFont");
        configuration.disableAudio(true);
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(640, 480);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        return configuration;
    }

}
