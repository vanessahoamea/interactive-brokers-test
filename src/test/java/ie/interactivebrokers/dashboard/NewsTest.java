package ie.interactivebrokers.dashboard;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.dashboard.NewsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewsTest extends BaseTest {
    @Test
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