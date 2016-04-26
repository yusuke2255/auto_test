package jp.kawata.test;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yusukekawata on 2016/04/26.
 */
public class MyTest extends TestCase {


    public interface WebDriverFactory {

        public WebDriver create();

    }

    private static Iterable<WebDriverFactory> getDriverFactories() {
        List<WebDriverFactory> factories = new ArrayList<>();
        factories.add(new WebDriverFactory() {
            @Override
            public WebDriver create() {
                System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_mac");
                return new ChromeDriver();
            }
        });
        return factories;
    }

    // おためし：コードに直接書くべきではない・・・
    private static final String url = "https://52.192.81.175/";
//    private static final String id = "hogehoge";
//    private static final String password = "fugafuga";

    @Test
    public void testXXX() throws Exception {

        for (WebDriverFactory factory : getDriverFactories()) {
            WebDriver driver = factory.create();
            try {
                driver.get(url);
                Wait<WebDriver> wait = new WebDriverWait(driver, 2);

                List<WebElement> links = driver.findElements(By.tagName("a"));
                links.stream().forEach(l->System.out.println(l.getAttribute("href")));

                List<WebElement> inputs = driver.findElements(By.tagName("input"));

                inputs.stream().filter(e->"email".equals(e.getAttribute("type"))).findFirst().ifPresent(e->{
                    e.sendKeys("user2@test.jp");
                });

                inputs.stream().filter(e->"password".equals(e.getAttribute("type"))).findFirst().ifPresent(e->{
                    e.sendKeys("password");
                });

                List<WebElement> buttons = driver.findElements(By.tagName("button"));

                buttons.stream().filter(e->"submit".equals(e.getAttribute("type"))).findFirst().ifPresent(e->{
                    e.click();
                });
                Wait<WebDriver> wait2 = new WebDriverWait(driver, 30000);

//                // Login page
//                WebElement button = wait.until(ExpectedConditions
//                        .elementToBeClickable(By.className("btn-primary")));
//                assertTrue(driver.getTitle().contains("YYY Dashboard"));
//                assertTrue(driver.getTitle().contains("Login")
//                        || driver.getTitle().contains("ログイン"));
//                driver.findElement(By.id("id_username")).sendKeys(id);
//                driver.findElement(By.id("id_password")).sendKeys(password);
//                button.submit();
//
//                // Top page
//                WebElement linkElement = wait
//                        .until(ExpectedConditions.elementToBeClickable(By
//                                .xpath("//a[@href='/path/to/xxx']")));
//                linkElement.sendKeys(Keys.ENTER);
//
//                // XXX page
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By
//                        .id("xxx")));
//                assertTrue(driver.getTitle().contains("YYY Dashboard"));
//                assertTrue(driver.getTitle().contains("XXX"));
//
//                FileUtils.copyFile(((TakesScreenshot) driver)
//                        .getScreenshotAs(OutputType.FILE), new File(driver
//                        .getClass().getName() + "-xxx.png"));
            } finally {
                driver.quit();
            }
        }
    }

}
