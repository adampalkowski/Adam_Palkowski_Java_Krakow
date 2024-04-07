package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {
    private final String configFilePath = "C:\\Users\\adamp\\IdeaProjects\\ocadoTech\\src\\test\\resources\\config.json";

    @Test
    public void testSplit() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = new ArrayList<>();
            items.add("Cocoa Butter");
            items.add("Tart - Raisin And Pecan");
            items.add("Table Cloth 54x72 White");
            items.add("Flower - Daisies");
            items.add("Fond - Chocolate");
            items.add("Cookies - Englishbay Wht");


            Map<String, List<String>> result = splitter.split(items);
            System.out.println(result.toString());
            assertEquals(2, result.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testConfigInitialization() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);

            // Check if the config map is initialized
            Map<String, List<String>> actualConfig = splitter.getConfig();
            assertNotNull(actualConfig);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during config initialization");
        }
    }
}
