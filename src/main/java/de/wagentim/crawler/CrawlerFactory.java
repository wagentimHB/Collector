package de.wagentim.collector.crawler;

import de.wagentim.collector.constants.ICrawlerConstants;
import de.wagentim.collector.controller.MainController;
import de.wagentim.collector.entity.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Create Crawler controller from the given parameters.
 * It is not thread safe
 */
public class CrawlerFactory {

    private static final CrawlerFactory crawlerFactory = new CrawlerFactory();
    private final Map<Integer, ICrawler> crawlerCache;
    private CrawlerFactory() {
        crawlerCache = new HashMap<>();
    }

    public static CrawlerFactory INSTANCE() {
        return crawlerFactory;
    }

    public ICrawler getCrawler(Task task){
        ICrawler crawler = crawlerCache.get(task.getCrawler());
        if(crawler == null) {
            crawler = createCrawler(task);
        }

        return crawler;
    }

    private ICrawler createCrawler(Task task) {
        int crawlerType = task.getCrawler();

        ICrawler crawler = crawlerCache.get(crawlerType);

        switch(crawlerType) {
            case ICrawlerConstants.CRAWLER_TYPE_FIREFOX:
                System.setProperty(MainController.INSTANCE().getSettingController().getFirefoxDriver(),
                        MainController.INSTANCE().getSettingController().getFirefoxDriverLocation());
                if(crawler == null) {
                    crawler = new FirefoxCrawler(task);
                    crawlerCache.put(ICrawlerConstants.CRAWLER_TYPE_FIREFOX, crawler);
                }
                break;
        }

        return crawler;
    }
}
