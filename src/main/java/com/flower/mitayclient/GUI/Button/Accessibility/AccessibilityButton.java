package com.flower.mitayclient.GUI.Button.Accessibility;

import com.flower.mitayclient.GUI.Button.MenuButton.MenuPressableWidget;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class AccessibilityButton extends AccessibilityPressableWidget
{
    String type;
    protected final AccessibilityButton.PressAction onPress;
    public static AccessibilityButton.Builder builder(Text message, PressAction onPress) {
        return new AccessibilityButton.Builder(message, onPress);
    }

    protected AccessibilityButton(int x, int y, int width, int height, Text message, AccessibilityButton.PressAction onPress, String type)
    {
        super(x, y, width, height, message, type);
        this.onPress = onPress;
        this.type = type;
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
        String type;
        private final Text message;
        private final AccessibilityButton.PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, AccessibilityButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public AccessibilityButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public AccessibilityButton.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public AccessibilityButton.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public AccessibilityButton.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public AccessibilityButton.Builder type(String type)
        {
            switch (type)
            {
                case "language" -> this.type = "language";
                case "accessibility" -> this.type = "accessibility";
                case "wallpaper" -> this.type = "wallpaper";
                case "about" -> this.type = "about";
            }
            return this;
        }

        public AccessibilityButton build() {
            AccessibilityButton buttonWidget = new AccessibilityButton(this.x, this.y, this.width, this.height, this.message, this.onPress, this.type);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(AccessibilityButton button);
    }
}
