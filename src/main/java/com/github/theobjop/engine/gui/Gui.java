package com.github.theobjop.engine.gui;

import com.github.theobjop.engine.Util;
import com.github.theobjop.engine.render.Transformation;
import com.github.theobjop.game.Main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Brandon on 2/24/2017.
 */
public class Gui {

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String FONT_NAME = "NORMAL";

    private List<IGui> guis;

    private NVGColor colour;
    private long vg;

    private ByteBuffer fontBuffer;

    private DoubleBuffer posx;
    private DoubleBuffer posy;
    private int counter;

    public Gui() {
        this.vg = Main.getWindow().getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new RuntimeException("Could not init nanovg");
        }

        fontBuffer = Util.ioResourceToByteBuffer("/fonts/Finale.ttf", 150 * 1024);
        int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new RuntimeException("Could not add font");
        }
        colour = NVGColor.create();

        posx = MemoryUtil.memAllocDouble(1);
        posy = MemoryUtil.memAllocDouble(1);

        counter = 0;

        guis = new ArrayList<>();
    }

    public void update() { }

    public void render(Transformation transformation) {
        nvgBeginFrame(vg, Main.getWindow().getWidth(), Main.getWindow().getHeight(), 1);

        // Upper ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, Main.getWindow().getHeight() - 100, Main.getWindow().getWidth(), 50);
        nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 200, colour));
        nvgFill(vg);

        // Lower ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, Main.getWindow().getHeight() - 50, Main.getWindow().getWidth(), 10);
        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
        nvgFill(vg);

        GLFW.glfwGetCursorPos(Main.getWindow().getWindowHandle(), posx, posy);
        int xcenter = 50;
        int ycenter = Main.getWindow().getHeight() - 75;
        int radius = 20;
        int x = (int) posx.get(0);
        int y = (int) posy.get(0);
        boolean hover = Math.pow(x - xcenter, 2) + Math.pow(y - ycenter, 2) < Math.pow(radius, 2);

        // Circle
        nvgBeginPath(vg);
        nvgCircle(vg, xcenter, ycenter, radius);
        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
        nvgFill(vg);

        // Clicks Text
        nvgFontSize(vg, 25.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_CENTER | NVG_ALIGN_TOP);
        if (hover) {
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, colour));
        } else {
            nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 255, colour));

        }
        NanoVG.nvgText(vg, 50, Main.getWindow().getHeight() - 87, String.format("%02d", counter));

        // Render hour text
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
        NanoVG.nvgText(vg, Main.getWindow().getWidth() - 150, Main.getWindow().getHeight() - 95, dateFormat.format(new Date()));

        nvgEndFrame(vg);

        // Restore state
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

    public void addIGui(IGui iGui) {
        guis.add(iGui);
    }
}
