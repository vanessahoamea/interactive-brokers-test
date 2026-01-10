package ie.interactivebrokers.dashboard;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.dashboard.NewsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.MINOR;

public class NewsTest extends BaseTest {
    @Test
    @Severity(MINOR)
    @Epic("Dashboard Page")
    @Story("Saving news articles to Read Later")
    public void testNewsBookmarks() {
        DashboardPage dashboardPage = validLogin();
        NewsPage newsPage = dashboardPage.goToNewsPage();
        newsPage.clickPortfolioNewsButton();
        newsPage.openRandomNewsArticle();
        String expectedArticleTitle = newsPage.clickBookmarkButton();
        newsPage.clickReturnToNewsFeedButton();
        newsPage.clickReadLaterButton();
        String actualArticleTitle = newsPage.getLatestBookmarkedArticleTitle();
        Assert.assertEquals(
                actualArticleTitle,
                expectedArticleTitle,
                "The actual and expected bookmarked article titles do not match"
        );
    }
}