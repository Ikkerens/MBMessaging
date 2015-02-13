package com.ikkerens.messaging;

import com.mbserver.api.CommandExecutor;
import com.mbserver.api.CommandSender;
import com.mbserver.api.Server;
import com.mbserver.api.dynamic.ChatColor;

public class MessageCommand implements CommandExecutor {
    private static final String MSG_FORMAT = "%s%s " + ChatColor.RESET + "-> %s%s" + ChatColor.RESET + ":%s";

    private final Server        server;

    public MessageCommand( final Server server ) {
        this.server = server;
    }

    @Override
    public void execute( final String command, final CommandSender sender, final String[] args, final String label ) {
        if ( !sender.hasPermission( "ikkerens.messaging.send" ) ) {
            sender.sendMessage( "You don't have the required permission for /%s", label );
            return;
        }

        if ( args.length < 2 ) {
            sender.sendMessage( "Usage: /pm <Target> <Message...>" );
            return;
        }

        CommandSender target = null;
        if ( args[ 0 ].equalsIgnoreCase( "console" ) || args[ 0 ].equalsIgnoreCase( "server" ) )
            target = this.server.getConsoleCommandSender();
        else
            target = this.server.getPlayer( args[ 0 ] );

        if ( target == null ) {
            sender.sendMessage( "Player '%s' is not online!", args[ 0 ] );
            return;
        }

        final StringBuilder sb = new StringBuilder();
        for ( int i = 1; i < args.length; i++ ) {
            sb.append( ' ' );
            sb.append( args[ i ] );
        }

        final String message = sb.toString();

        final ChatColor senderColor, targetColor;
        senderColor = server.getPermissionsHandler().getColor( sender );
        targetColor = server.getPermissionsHandler().getColor( target );

        target.sendMessage( MSG_FORMAT, senderColor, sender.getName(), targetColor, "Me", message );
        sender.sendMessage( MSG_FORMAT, senderColor, "Me", targetColor, target.getName(), message );
    }
}
