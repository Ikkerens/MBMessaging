package com.ikkerens.messaging;

import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.Manifest;

@Manifest( name = "MBMessaging" )
public class Plugin extends MBServerPlugin {
    @Override
    public void onEnable() {
        this.getPluginManager().registerCommand( "message", new String[] { "pm", "msg" }, new MessageCommand( this.getServer() ) );
    }
}
