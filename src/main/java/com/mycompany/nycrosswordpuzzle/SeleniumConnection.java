/*
 * The MIT License
 *
 * Copyright 2019 kaan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.nycrosswordpuzzle;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author kaan
 */
public class SeleniumConnection {

    private WebDriver webDriver;

    public SeleniumConnection() {
        webDriver = new FirefoxDriver();

        //Open puzzle page
        webDriver.get( "https://www.nytimes.com/crosswords/game/mini" );

        webDriver.findElements( By.className( "buttons-modalButton--1REsR" ) ).get( 0 ).click();
        List<WebElement> li = webDriver.findElements( By.cssSelector( "li.Tool-button--39W4J:nth-child(2) > button:nth-child(1)" ) );
        li.get( 0 ).click();

        webDriver.findElement( By.xpath( "//*[@id=\"root\"]/div/div/div[4]/div/main/div[2]/div/div/ul/div[2]/li[2]/ul/li[3]/a" ) ).click();
        webDriver.findElements( By.xpath( "//*[@id=\"root\"]/div/div[2]/div[2]/article/div[2]/button[2]" ) ).get( 0 ).click();
        webDriver.findElement( By.xpath( "//*[@id=\"root\"]/div/div[2]/div[2]/span" ) ).click();

    }

    public String pageSource() {
        return webDriver.getPageSource();
    }

    public void closeCon() {
        webDriver.close();
//        webDriver.quit();
    }

}
