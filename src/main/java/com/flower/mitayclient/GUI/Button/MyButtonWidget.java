package com.flower.mitayclient.GUI.Button;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class MyButtonWidget extends MyPressableWidget
{
    protected final MyButtonWidget.PressAction onPress;
    public static String thisIconName;
    public static MyButtonWidget.Builder builder(Text message, MyButtonWidget.PressAction onPress) {
        return new MyButtonWidget.Builder(message, onPress);
    }

    protected MyButtonWidget(int x, int y, int width, int height, Text message, MyButtonWidget.PressAction onPress, String iconName)
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
        private final MyButtonWidget.PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, MyButtonWidget.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public MyButtonWidget.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public MyButtonWidget.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public MyButtonWidget.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public MyButtonWidget.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public MyButtonWidget.Builder icon(String iconName)
        {
            thisIconName = iconName;
            return this;
        }

        public MyButtonWidget build() {
            MyButtonWidget buttonWidget = new MyButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, thisIconName);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(MyButtonWidget button);
    }
}
