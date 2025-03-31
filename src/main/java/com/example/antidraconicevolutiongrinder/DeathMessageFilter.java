package com.example.antidraconicevolutiongrinder;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class DeathMessageFilter {

    @SubscribeEvent
    public void onConnection(FMLNetworkEvent.ServerConnectionFromClientEvent event) {
        try {
            event.manager.channel().pipeline().addBefore("packet_handler", "death_msg_filter", new ChannelDuplexHandler() {

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    try {
                        if (!filterChat(msg)) {
                            super.channelRead(ctx, msg);
                        }
                    } catch (Throwable t) {
                        super.channelRead(ctx, msg); // fail open
                    }
                }

                @Override
                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                    try {
                        if (!filterChat(msg)) {
                            super.write(ctx, msg, promise);
                        }
                    } catch (Throwable t) {
                        super.write(ctx, msg, promise); // fail open
                    }
                }

                private boolean filterChat(Object msg) {
                    if (msg instanceof S02PacketChat) {
                        S02PacketChat packet = (S02PacketChat) msg;
                        IChatComponent chat = null;

                        try {
                            for (java.lang.reflect.Field field : S02PacketChat.class.getDeclaredFields()) {
                                field.setAccessible(true);
                                if (IChatComponent.class.isAssignableFrom(field.getType())) {
                                    chat = (IChatComponent) field.get(packet);
                                    break;
                                }
                            }
                        } catch (Exception ignored) {}

                        if (chat != null) {
                            String text = chat.getUnformattedText();
                            if (text.contains("was slain by [Draconic-Evolution]")) {
                                System.out.println("[AntiGrinder] Blocked: " + text);
                                return true;
                            }
                        }
                    }
                    return false;
                }

            });
        } catch (Throwable ignored) {}
    }
}
