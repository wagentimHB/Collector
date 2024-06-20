package de.wagentim.collector.sites.musics;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.wagentim.collector.persistance.objectdb.ObjectDBMusicHandler;
import de.wagentim.collector.entity.KeyValuePair;
import de.wagentim.collector.sites.main.AbstractSite;
import de.wagentim.collector.utils.FileDownloadUtil;
import de.wagentim.collector.utils.IConstants;
import de.wagentim.collector.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.NgWebDriver;

import de.wagentim.collector.entity.Music;
import de.wagentim.collector.sites.MusicSiteHandler;

import static de.wagentim.collector.sites.MusicSiteHandler.MUSIC_SOURCE_DIR;

public class Flitting extends AbstractSite
{

	private static final String SELECT_NET_MUSIC_RANKING_SONG_LIST = "#wlsong li";
	private static final String SELECT_MUSIC_NAME = "a.gname";
	private ObjectDBMusicHandler dbHandler;
	private String uuid = IConstants.TXT_EMPTY_STRING;
	private static final String SELECT_MUSIC_LINK = "div#musickrc audio#audio";
	private static int MAX_MUSICS = 15;
	
	private Logger logger = LoggerFactory.getLogger(Flitting.class);
	private FileDownloadUtil downloader;
	
	public Flitting()
	{
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		dbHandler = new ObjectDBMusicHandler("D:\\test\\flitting.odb");
		downloader = new FileDownloadUtil();
	}

	@Override
	public void execute()
	{
		uuid = dbHandler.createEntityManager();
		dbHandler.getAllMusicFromDB(uuid);
		
		// step 1: open first page
		openPage(getStartLink(), false, false);

		// step 2:
		List<WebElement> list = webDriver.findElements(By.cssSelector(SELECT_NET_MUSIC_RANKING_SONG_LIST));
		
		if(list == null || list.isEmpty())
		{
			logger.error("Cannot find Net Music Ranking Access Element");
			return;
		}

		Iterator<WebElement> it = list.iterator();

		int index = 0;

		List<Music> musics = new ArrayList<Music>();
		while(it.hasNext() && (index < MAX_MUSICS))
		{
			WebElement we = it.next();
			we = we.findElement(By.cssSelector(SELECT_MUSIC_NAME));
			String name = we.getText();
			KeyValuePair kvp = StringUtils.parserKeyValue(name, "-");
			Music m = new Music();
			m.setSinger(kvp.getKey());
			m.setSongName(kvp.getValue());

			if(!dbHandler.containMusic(m))
			{
				dbHandler.addMusic(m);
				String link = we.getAttribute("href");
				openTab(we, link);
				WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SELECT_MUSIC_LINK)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SELECT_MUSIC_LINK)));
				String musicLink = webDriver.findElement(By.cssSelector(SELECT_MUSIC_LINK)).getAttribute("src");
				webDriver.close();
				ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
				webDriver.switchTo().window(tabs.get(0));
				if(musicLink == null)
				{
					continue;
				}
				try {
					System.out.println(index);
					downloader.simpleDownloadFile(musicLink, MUSIC_SOURCE_DIR + m.getMusicNameMP3());
				} catch (IOException e) {
					e.printStackTrace();
				}
				index++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		//printMusics(musics);
		webDriver.quit();
		dbHandler.saveBackToDB(uuid);
		dbHandler.exit();
	}

	@Override
	protected String getStartLink()
	{
		return "http://flitting.cn/?l=1";
	}

	@Override
	protected String getSiteName()
	{
		return "Flitting";
	}
}
