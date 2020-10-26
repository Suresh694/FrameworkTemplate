package com.project.qa.pages;
import java.io.FileNotFoundException;
import org.openqa.selenium.support.PageFactory;
import com.project.qa.Base.TestBase;

public class HomePage extends TestBase{
	HomePage home;
	
	/**Write Down all the Page Elements of individual Pages separately
	 * In Different Class Files under this com.project.qa.pages package
	 * Example:-
	 * @FindBy(xpath="//input[conatins(@id,'email')]")
	 *	WebElement mailID;
	 */	

	public HomePage() throws FileNotFoundException {
		super();
		PageFactory.initElements(driver, this);
	}

}
