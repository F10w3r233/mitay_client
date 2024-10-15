package com.flower.mitayclient.GUI.Button.PlaceListButton.Small;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class SmallButtonWidget extends SmallPressableWidget
{
    protected final SmallButtonWidget.PressAction onPress;
    public static String thisIconName;
    public static SmallButtonWidget.Builder builder(Text message, SmallButtonWidget.PressAction onPress) {
        return new SmallButtonWidget.Builder(message, onPress);
    }

    protected SmallButtonWidget(int x, int y, int width, int height, Text message, SmallButtonWidget.PressAction onPress, String iconName)
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
        private final SmallButtonWidget.PressAction onPress;
        private int x;
        private int y;
        private int width = 100;
        private int height = 22;

        public Builder(Text message, SmallButtonWidget.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public SmallButtonWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public SmallButtonWidget.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public SmallButtonWidget.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public SmallButtonWidget.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public SmallButtonWidget.Builder icon(String iconName)
        {
            thisIconName = iconName;
            return this;
        }

        public SmallButtonWidget build() {
            SmallButtonWidget buttonWidget = new SmallButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, thisIconName);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(SmallButtonWidget button);
    }
}
