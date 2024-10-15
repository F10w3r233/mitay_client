package com.flower.mitayclient.GUI.Button.WallpaperButton;

import com.flower.mitayclient.GUI.Button.TeleportPlayer.TeleportPlayerPressable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class WallpaperButton extends WallpaperPressable
{
    protected final WallpaperButton.PressAction onPress;
    public static String thisIconName;
    public static Identifier thisWallpaper;
    public static WallpaperButton.Builder builder(Text message, WallpaperButton.PressAction onPress) {
        return new WallpaperButton.Builder(message, onPress);
    }

    public WallpaperButton(int x, int y, int width, int height, Text message, WallpaperButton.PressAction onPress, Identifier wallpaper)
    {
        super(x, y, width, height, message, wallpaper);
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Text message;
        private final WallpaperButton.PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, WallpaperButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public WallpaperButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public WallpaperButton.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public WallpaperButton.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public WallpaperButton.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public WallpaperButton.Builder wallpaper(Identifier wallpaper)
        {
            thisWallpaper = wallpaper;
            return this;
        }

        public WallpaperButton build() {
            WallpaperButton buttonWidget = new WallpaperButton(this.x, this.y, this.width, this.height, this.message, this.onPress, thisWallpaper);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(WallpaperButton button);
    }
}

