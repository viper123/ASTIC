package ro.info.astic;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ro.info.asticlib.db.Dao;
import ro.info.asticlib.tests.DaoTests;


public class Main {
	
	public static final String APP_ICON_PATH = "res/img/appIcon.png";
	

	public static void main(String[] args) {
		
		//new TestWordNet().testNoun();
		//new TagsReaderTests().testGetTag();
		//new WordComparatorTests().testMatch();
		//new BWTests().testBWAdd();
		//new BWTests().testBWGet();
		//new TagsReaderTests().testGetTag();
		//new LanguageTests().testIsSeparator();
		//new ParserPerformanceTest().testWordListener();
		//new OracleTest().testConnection();
		//new BaseDaoTests().testTableExists();
		//new DaoTests().testGetAllDocuments();
		//new DaoTests().testGetFileContaining();
		//new DaoTests().testUpdateTFIDF();
		//new ClutersTest().testGetLastId();
		//new ClutersTest().testAllClusters();
		//new ClutersTest().selectAllFromClusters();
		//new ClutersTest().insertDummyData();
		//new Server().start();
		//new TestWordNet().testExpandWord();
		//new TagsReaderTests().testGetTagPDFFile();
		//new TagsReaderTests().testGetTag();
		//
		//new BackgroundService(Conf.TEST_FILE).start();
		//FileSystemWatcher watcher= FileSystemWatcher.getInstance();
		//watcher.setClusteringService(BaseClusteringService.class);
///		new InitialClusteringDaemon(Conf.ROOT,watcher) {
//			public void onFinish(Exception e) {
//				FileSystemWatcher.getInstance().start();
//				new Server().start();
//			};
//		}.start();
		//new QueryTest().queryTest();
		//new WordProcessorTest().test();
		new Dao().dropTables();
		GUIConsole.main(args);
		//new DaoTests().testSelect();
		//setSystemTray();
		//new TestInvertedClusterIndex().test();
		
	}
	
	private static void setSystemTray(){
		final PopupMenu popup = new PopupMenu();
        TrayIcon trayIcon = null;
		try {
			trayIcon = new TrayIcon(ImageIO.read(new File(APP_ICON_PATH)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        final SystemTray tray = SystemTray.getSystemTray();
       
        // Create a pop-up menu components
        MenuItem displayConsole = new MenuItem("Consola");
        MenuItem exitItem = new MenuItem("Iesire");
        displayConsole.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIConsole.main(null);
			}
		});
        //Add components to pop-up menu
        popup.add(displayConsole);
        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}

}
