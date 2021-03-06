package net.md_5.bungee;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeCordLauncher
{

	private static boolean updatefromlink(String link) {
		try {
			FileUtils.copyURLToFile(new URL(link), new File("UpdatedBungeeCord.jar"), 30000, 30000);
			try {
				new File(BetterBungee.class.getProtectionDomain().getCodeSource().getLocation().toURI()).delete();
				new File("UpdatedBungeeCord.jar").renameTo(new File(BetterBungee.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
				return true;
			} catch (URISyntaxException e) {
			}
			System.out.println("Hotfixed BetterBungee");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean crashed = true;
	
	public static int port = 0;

	public static BungeeCord bungeecord;
	
	static String betterbungee = "http://betterbungeeapi.skydb.de";
	
    public static void main(String[] args) throws Exception
    {
    	if (args.length > 0) {
    		try {
    			port = Integer.valueOf(args[0]);
    		} catch (Throwable th) {}
    	}
    	
    	new Thread(() -> {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
			}
    		if (crashed) {
    			try {
        			updatefromlink(betterbungee + "/downloadupdate");
        			File file = new File("betterbungeeconfig.yml");
					Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
					String snapshotupdater = "serversettings.snapshotupdater";
					config.set(snapshotupdater, "false");
					ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
					System.exit(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}

    	}).start();
    	
        Security.setProperty( "networkaddress.cache.ttl", "30" );
        Security.setProperty( "networkaddress.cache.negative.ttl", "10" );

        OptionParser parser = new OptionParser();
        
        parser.allowsUnrecognizedOptions();
        parser.acceptsAll( Arrays.asList( "help" ), "Show the help" );
        parser.acceptsAll( Arrays.asList( "v", "version" ), "Print version and exit" );
        parser.acceptsAll( Arrays.asList( "noconsole" ), "Disable console input" );

        OptionSet options = parser.parse( args );

        if ( options.has( "help" ) )
        {
            parser.printHelpOn( System.out );
            return;
        }
        
        if ( options.has( "version" ) )
        {
            System.out.println( BungeeCord.class.getPackage().getImplementationVersion() );
            return;
        }

        if ( BungeeCord.class.getPackage().getSpecificationVersion() != null && System.getProperty( "IReallyKnowWhatIAmDoingISwear" ) == null )
        {
            Date buildDate = new SimpleDateFormat( "yyyyMMdd" ).parse( BungeeCord.class.getPackage().getSpecificationVersion() );

            Calendar deadline = Calendar.getInstance();
            deadline.add( Calendar.WEEK_OF_YEAR, -8 );
            if ( buildDate.before( deadline.getTime() ) )
            {
                System.err.println( "*** Warning, this build is outdated ***" );
                System.err.println( "*** Please download a new build from http://ci.md-5.net/job/BungeeCord ***" );
                System.err.println( "*** You will get NO support regarding this build ***" );
                System.err.println( "*** Server will start in 10 seconds ***" );
                Thread.sleep( TimeUnit.SECONDS.toMillis( 10 ) );
            }
        }

        BungeeCord bungee = new BungeeCord();
        
        bungeecord = bungee;
        
        ProxyServer.setInstance( bungee );
        
        bungee.getLogger().info( "Enabled BungeeCord version " + bungee.getVersion() );
        
        bungee.start();

        if ( !options.has( "noconsole" ) )
        {
            String line;
            while ( bungee.isRunning && ( line = bungee.getConsoleReader().readLine( ">" ) ) != null )
            {
                if ( !bungee.getPluginManager().dispatchCommand( ConsoleCommandSender.getInstance(), line ) )
                {
                    bungee.getConsole().sendMessage( new ComponentBuilder( "Command not found" ).color( ChatColor.RED ).create() );
                }
            }
        }
    }
}
