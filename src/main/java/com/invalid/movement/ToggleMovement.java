package com.invalid.movement;

import ibxm.Player;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ToggleMovement extends LabyModAddon {

    private static boolean sneaking = false;
    private static boolean sprinting = false;
    private static KeyBinding sprintKey;
    private static KeyBinding sneakKey;
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onEnable() {
        System.out.println("ToggleMovement addon enabled!");
        sprintKey = new KeyBinding("Toggle Sprint", Keyboard.KEY_I, "Toggle Movement");
        sneakKey = new KeyBinding("Toggle Sneak", Keyboard.KEY_O, "Toggle Movement");
        ClientRegistry.registerKeyBinding(sprintKey);
        ClientRegistry.registerKeyBinding(sneakKey);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {

        //Toggle sprint
        int key_sprint = mc.gameSettings.keyBindSprint.getKeyCode();
        if (sprintKey.isPressed()) {
            sprinting = !sprinting;
            if (!sprinting) {
                if (key_sprint > 0) {
                    sprintKey.setKeyBindState(key_sprint, Keyboard.isKeyDown(key_sprint));
                }
            }
        }
        if (sprinting) {
            sprintKey.setKeyBindState(key_sprint, true);
        }

        //Toggle sneak
        int key_sneak = mc.gameSettings.keyBindSneak.getKeyCode();
        if (sneakKey.isPressed()) {
            sneaking = !sneaking;
            if (!sneaking) {
                if (key_sneak > 0) {
                    sneakKey.setKeyBindState(key_sneak, Keyboard.isKeyDown(key_sneak));
                }
            }
        }
        if (sneaking) {
            sneakKey.setKeyBindState(key_sneak, true);
        }

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (!sprinting && !sneaking)return;
        if (sprinting) {
            this.api.getDrawUtils().drawString("[Toggled Sprint]", 1, 1, 1);
        }
        if (sneaking) {
            this.api.getDrawUtils().drawString("[Toggled Sneak]", 1, 10, 1);
        }

    }


    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }
}


