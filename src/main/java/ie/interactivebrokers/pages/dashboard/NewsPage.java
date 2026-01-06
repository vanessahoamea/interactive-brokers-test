package ie.interactivebrokers.pages.dashboard;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.WaitUtils.*;

public class NewsPage extends BasePage {
    private final By portfolioNewsButton = By.xpath("//button //p[text()='Portfolio News']");
    private final By readLaterButton = By.xpath("//button //p[text()='Read Later']");
    private final By loadingSkeleton = By.cssSelector(".news3-skeleton.news3-article-list");
    private final By newsArticles = By.cssSelector("button:has(.news3-magazine-list__headline)");
    private final By newsArticleTitle = By.cssSelector(".article-headline p.semibold");
    private final By bookmarkButton = By.cssSelector("button[aria-label='bookmark']");
    private final By bookmarkToast = By.cssSelector("._sbar[key='bookmark']");
    private final By returnToNewsFeedButton = By.cssSelector(".news3-content__header > button");
    private final By bookmarkedArticleTitle = By.cssSelector(".news3-magazine-list:has(> button) h5.news3-magazine-list__headline");

    public void clickPortfolioNewsButton() {
        waitUntilClickable(portfolioNewsButton, 15);
        click(portfolioNewsButton);
    }

    public void openRandomNewsArticle() {
        waitUntilInvisible(loadingSkeleton, 15);
        delay(5000); // ensures the news articles load properly before trying to access them
        List<WebElement> articles = findAll(newsArticles);
        int articleCount = articles.size();
        int randomIndex = ThreadLocalRandom.current().nextInt(articleCount) % articleCount;
        WebElement selectedArticle = articles.get(randomIndex);
        selectedArticle.click();
    }

    public String clickBookmarkButton() {
        waitUntilVisible(newsArticleTitle, 5);
        waitUntilClickable(bookmarkButton, 5);
        click(bookmarkButton);
        waitUntilVisible(bookmarkToast, 5);
        return getText(newsArticleTitle);
    }

    public void clickReturnToNewsFeedButton() {
        waitUntilClickable(returnToNewsFeedButton, 5);
        click(returnToNewsFeedButton);
    }

    public void clickReadLaterButton() {
        waitUntilClickable(readLaterButton, 5);
        click(readLaterButton);
    }

    public String getLatestBookmarkedArticleTitle() {
        waitUntilInvisible(loadingSkeleton, 15);
        delay(5000); // ensures the list of bookmarked articles loads properly
        return getText(bookmarkedArticleTitle);
    }
}
