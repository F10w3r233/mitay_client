package com.flower.mitayclient.util;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.List;

public class FontDrawer
{
    public static TextRenderer getTr() throws IOException
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        List<Font> list = Lists.newArrayList();
        TrueTypeFontLoader loader = new TrueTypeFontLoader(
                new Identifier("mitayclient:raleway.ttf"),
                16,
                10,
                TrueTypeFontLoader.Shift.NONE,
                ""
        );
        FontLoader.Loadable loadable = loader.build().orThrow();
        Font font = loadable.load(mc.getResourceManager());
        list.add(font);
        FontStorage storage = new FontStorage(mc.getTextureManager(), new Identifier("mitayclient:tr"));
        storage.setFonts(list);
        return new TextRenderer(id -> storage, true);
    }
}
