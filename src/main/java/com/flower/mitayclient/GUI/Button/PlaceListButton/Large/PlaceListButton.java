package com.flower.mitayclient.GUI.Button.PlaceListButton.Large;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class PlaceListButton extends PlaceListPressable
{
    protected final PlaceListButton.PressAction onPress;
    public static String thisIconName;
    public static PlaceListButton.Builder builder(Text message, PlaceListButton.PressAction onPress) {
        return new PlaceListButton.Builder(message, onPress);
    }

    protected PlaceListButton(int x, int y, int width, int height, Text message, PlaceListButton.PressAction onPress, String iconName)
    {
        super(x, y, width, height, message, iconName);
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
        private final PlaceListButton.PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, PlaceListButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public PlaceListButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public PlaceListButton.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public PlaceListButton.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public PlaceListButton.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public PlaceListButton.Builder icon(String iconName)
        {
            thisIconName = iconName;
            return this;
        }

        public PlaceListButton build() {
            PlaceListButton buttonWidget = new PlaceListButton(this.x, this.y, this.width, this.height, this.message, this.onPress, thisIconName);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(PlaceListButton button);
    }
}
