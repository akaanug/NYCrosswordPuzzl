/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
