package sesac.mockInvestment.test;

public class CrawlingTest {

    // 드라이버 설치 경로
    /*public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:\\Users\\sHu\\Desktop\\chromedriver.exe";
    private static WebDriver driver;
    private static String url;

    static {
        // WebDriver 경로 설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        // WebDriver 세팅
        driver = new ChromeDriver(options);

        // URL 세팅
        url = "https://kimpga.com/";
    }

    public static void main(String[] args) throws InterruptedException {
        driver.get(url);

        List<WebElement> elements = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        List<CoinDto> coinList = new ArrayList<>();

        for (WebElement elem : elements) {
            CoinDto coin = new CoinDto();

            String imgPath = elem.findElement(By.tagName("img")).getAttribute("src");
            String name = elem.findElement(By.tagName("span")).getText();
            String price = elem.findElement(By.cssSelector("td:nth-of-type(2)")).getText();

            System.out.println(imgPath + ", " + name + ", " + price + ", ");
        }
    }*/

    /*@GetMapping("/coins")
    @ResponseBody
//    public List<CoinDto> getCoinList() throws IOException {
    public String getCoinList() throws IOException {
        // WebDriver 경로 설정
        System.setProperty(crawlingDriverId, crawlingDriverPath);

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        // WebDriver 세팅
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://kimpga.com/");

        List<WebElement> elements = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        List<CoinDto> coinList = new ArrayList<>();

        for (WebElement elem : elements) {
            CoinDto coin = new CoinDto();

            String imgPath = elem.findElement(By.tagName("img")).getAttribute("src");
            String name = elem.findElement(By.tagName("span")).getText();
            String price = elem.findElement(By.cssSelector("td:nth-of-type(2)")).getText();

            System.out.println(imgPath + ", " + name + ", " + price + ", ");

            return imgPath;
        }
        return null;
    }*/
}
