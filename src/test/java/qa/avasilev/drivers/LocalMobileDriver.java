package qa.avasilev.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import qa.avasilev.config.Project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class LocalMobileDriver implements WebDriverProvider {

    public static URL getAppiumServerUrl() {
        try {
            return new URL("http://localhost:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        File app = getApp();
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName("Android");
        options.setDeviceName(Project.config.device());
        options.setPlatformVersion(Project.config.osVersion());
        options.setApp(app.getAbsolutePath());
        options.setAppPackage("com.jetbrains.youtrack.mobile.app");
        options.setAppActivity("com.jetbrains.youtrack.mobile.app.MainActivity");

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    private File getApp() {
        /*String appUrl = "https://github.com/wikimedia/apps-android-wikipedia/" +
                "releases/download/latest/app-alpha-universal-release.apk";*/
        String appPath = "src/test/resources/app/YouTrack_v2022.3.1.apk";

        File app = new File(appPath);
       /* if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download application", e);
            }
        }*/
        return app;
    }

}