package com.flower.mitayclient.GUI.Button.TeleportPlayer;


import com.flower.mitayclient.GUI.Button.MyPressableWidget;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;







@Environment(EnvType.CLIENT)
public class TeleportPlayerButton extends TeleportPlayerPressable
{
    protected final TeleportPlayerButton.PressAction onPress;
    public static String thisIconName;
    public static PlayerListEntry thisPlayerListEntry;
    public static TeleportPlayerButton.Builder builder(Text message, TeleportPlayerButton.PressAction onPress) {
        return new TeleportPlayerButton.Builder(message, onPress);
    }

    public TeleportPlayerButton(int x, int y, int width, int height, Text message, TeleportPlayerButton.PressAction onPress, PlayerListEntry player)
    {
        super(x, y, width, height, message, player);
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
        private final PressAction onPress;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(Text message, PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder width(int width)
        {
            this.width = width;
            return this;
        }

        public Builder size(int width, int height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder dimensions(int x, int y, int width, int height)
        {
            return this.position(x, y).size(width, height);
        }

        public Builder player(PlayerListEntry player)
        {
            thisPlayerListEntry = player;
            return this;
        }

        public TeleportPlayerButton build() {
            TeleportPlayerButton buttonWidget = new TeleportPlayerButton(this.x, this.y, this.width, this.height, this.message, this.onPress, thisPlayerListEntry);
            return buttonWidget;
        }

    }


    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(TeleportPlayerButton button);
    }
}
