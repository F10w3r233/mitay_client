package com.flower.mitayclient.GUI.Button.MenuButton;

import com.flower.mitayclient.GUI.Button.MenuButton.MenuPressableWidget;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class MenuButton extends MenuPressableWidget
{
    protected final MenuButton.PressAction onPress;
    public static MenuButton.Builder builder(Text message, PressAction onPress) {
        return new MenuButton.Builder(message, onPress);
    }

    protected MenuButton(int x, int y, int width, int height, Text message, MenuButton.PressAction onPress)
    {
        super(x, y, width, height, message);
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
        private final MenuButton.PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, MenuButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public MenuButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public MenuButton.Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public MenuButton.Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public MenuButton.Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public MenuButton build() {
            MenuButton buttonWidget = new MenuButton(this.x, this.y, this.width, this.height, this.message, this.onPress);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(MenuButton button);
    }
}
