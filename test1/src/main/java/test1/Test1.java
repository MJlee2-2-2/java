package test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class Test1 {
    private String studentNumber;
    private String password;

    public Test1(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public void loginAndAccessClassroom() {
        // 크롬 드라이버 위치 설정
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");

        // 웹 드라이버 정의
        WebDriver driver = new ChromeDriver();

        // 웹사이트 접속
        driver.get("https://door.deu.ac.kr/sso/login.aspx");

        // 대기 시간 1초
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 학번 입력 필드를 찾아 학번 입력
        WebElement studentNumberElement = driver.findElement(By.name("userid"));
        studentNumberElement.clear();
        studentNumberElement.sendKeys(studentNumber);

        // 비밀번호 입력 필드를 찾아 비밀번호 입력
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);

        // 로그인 버튼을 찾아 클릭
        WebElement loginButton = driver.findElement(By.xpath("//a[contains(text(), '로그인')]"));
	    loginButton.click();

        // 로그인 후 리다이렉트되는 페이지의 URL 확인
        String expectedUrl = "http://door.deu.ac.kr/Home/Index";
        String actualUrl = driver.getCurrentUrl();

        if (expectedUrl.equals(actualUrl)) {
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }

        // 대기 시간 1초
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 개인 강의실 접근
        WebElement link = driver.findElement(By.cssSelector("a[href='/MyPage']"));
        link.click();

        // 강의실 접근 후 리다이렉트되는 페이지의 URL 확인
        expectedUrl = "http://door.deu.ac.kr/MyPage";
        actualUrl = driver.getCurrentUrl();

        if (expectedUrl.equals(actualUrl)) {
            System.out.println("강의실 접근 성공");
        } else {
            System.out.println("강의실 접근 실패");
        }

        // 모든 강의의 입장 클릭
        List<WebElement> enterButtons = driver.findElements(By.xpath("//img[@alt='입장']"));
        for (WebElement enterButton : enterButtons) {
            enterButton.click();
            // 다음 버튼 클릭을 기다리고 상태를 반영
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 과제란 클릭
            WebElement homeworkLink = driver.findElement(By.xpath("//a[contains(text(), '과제')]"));
            homeworkLink.click();

            /* 실제 필요한 정보들 크롤링 코드 */
            
            // 페이지를 되돌아가는 동작 수행
            driver.navigate().back();
            // 다음 버튼 클릭을 기다리고 상태를 반영
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 웹 드라이버를 종료합니다.
        driver.quit();
    }

    public static void main(String[] args) {
        String studentNumber = "20212857"; // 학번 입력
        String password = "dkqkdkqk55!!"; // 비밀번호 입력

        Test1 tester = new Test1(studentNumber, password);
        tester.loginAndAccessClassroom();
    }
}
