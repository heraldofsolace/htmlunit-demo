package com.mycompany.app;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = client.getPage("https://scrapingbee.com");

        DomNodeList<DomNode> features = page.querySelectorAll("section:nth-child(3) h3.text-20.leading-a26.text-black-100.mb-15");

        for (DomNode domNode : features) {
            HtmlHeading3 heading = (HtmlHeading3) domNode;

            System.out.println(heading.getTextContent());
            System.out.println();
        }

        DomNode h4WithText = page.getFirstByXPath("//*[contains(text(),'Learning Web Scraping')]");
        DomNode siblingUl = h4WithText.getNextElementSibling();
        DomNodeList<DomNode> lis = siblingUl.getChildNodes();
        System.out.println(lis.size());
        for (DomNode domNode : lis) {
            if(domNode.getNodeType() == DomNode.ELEMENT_NODE) {
                HtmlAnchor link = (HtmlAnchor) domNode.getFirstChild();

                System.out.println(link.getTextContent());
                System.out.println(link.getBaseURI() + link.getHrefAttribute());
                System.out.println();
            }

        }


        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);


        HtmlPage teamsPage = client.getPage("https://www.scrapethissite.com/pages/forms");
        HtmlForm form = teamsPage.getForms().get(0);

        System.out.println(form.getLocalName());
        HtmlSubmitInput button = form.getInputByValue("Search");
        HtmlTextInput textField = form.getInputByName("q");

        textField.type("Toronto");
        HtmlPage resultsPage = button.click();

        DomNodeList<DomNode> teams = resultsPage.querySelectorAll("tr.team");
        for(DomNode team: teams) {
            String year = team.querySelector(".year").getTextContent().strip();
            String wins = team.querySelector(".wins").getTextContent().strip();

            System.out.println("Year: " + year + ", Wins: " + wins);
        }
    }
}
