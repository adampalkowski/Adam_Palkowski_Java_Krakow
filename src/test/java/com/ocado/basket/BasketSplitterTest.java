package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {
    //Change file paths accordingly!
    //BasketSplitter asks for absolute file path, that is why we hardcode it here
    private final String configFilePath = "C:\\Users\\adam\\IdeaProjects\\ocadoTech\\src\\test\\resources\\config.json";
    private final String emptyConfigFilePath = "C:\\Users\\adam\\IdeaProjects\\ocadoTech\\src\\test\\resources\\empty-config.json";
    private final String basket1FilePath = "C:\\Users\\adam\\IdeaProjects\\ocadoTech\\src\\test\\resources\\basket-1.json";
    private final String basket2FilePath = "C:\\Users\\adam\\IdeaProjects\\ocadoTech\\src\\test\\resources\\basket-2.json";

    // Sample test case to ensure correct splitting of items,( from the pdf file)
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
    //Test case for reapeated items
    @Test
    public void testSplitReapeated() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = new ArrayList<>();
            items.add("Cocoa Butter");
            items.add("Tart - Raisin And Pecan");
            items.add("Table Cloth 54x72 White");
            items.add("Flower - Daisies");
            items.add("Fond - Chocolate");
            items.add("Cookies - Englishbay Wht");
            items.add("Cocoa Butter");
            items.add("Tart - Raisin And Pecan");
            items.add("Table Cloth 54x72 White");
            items.add("Flower - Daisies");
            items.add("Fond - Chocolate");
            items.add("Cookies - Englishbay Wht");
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

    // Test case to split items from basket-1.json
    @Test
    public void testBasket1Split() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = new ArrayList<>();

            // Load items from basket-1.json file
            File basketFile = new File(basket1FilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            items = objectMapper.readValue(basketFile, new TypeReference<List<String>>() {});

            Map<String, List<String>> result = splitter.split(items);
            System.out.println(result.toString());

            assertEquals(2, result.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test case to split items from basket-2.json
    @Test
    public void testBasket2Split() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = new ArrayList<>();

            // Load items from basket-2.json file
            File basketFile = new File(basket2FilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            items = objectMapper.readValue(basketFile, new TypeReference<List<String>>() {});

            // Split the items
            Map<String, List<String>> result = splitter.split(items);
            System.out.println(result.toString());

            assertEquals(3, result.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test case to ensure proper initialization of configuration
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


    // Test what happens when the input list of items is empty
    @Test
    public void testEmptyItemList() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = Collections.emptyList();
            Map<String, List<String>> result = splitter.split(items);
            assertNotNull(result);
            assertEquals(0, result.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    //Test for All Products in Basket
    @Test
    public void testAllProductsInBasket() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            Map<String, List<String>> config = splitter.getConfig();
            List<String> items = new ArrayList<>(config.keySet()); // Add all products from configuration to the basket
            Map<String, List<String>> result = splitter.split(items);
            System.out.println(result);
            // Assert the result to ensure each product is assigned to the correct delivery method
            assertEquals(7, result.size()); // Ensure all delivery methods are present in the result
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    // Test what happens when the input list of items is null
    @Test
    public void testNullItemList() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = null;
            Map<String, List<String>> result = splitter.split(items);
            assertNotNull(result);
            assertEquals(0, result.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test what happens when the configuration map is empty
    @Test
    public void testEmptyConfig() {
        try {
            BasketSplitter splitter = new BasketSplitter(emptyConfigFilePath); // Provide path to empty config
            List<String> items = Collections.singletonList("SomeItem");
            Map<String, List<String>> result = splitter.split(items);
            assertNotNull(result);
            assertEquals(0, result.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test what happens when the configuration map is null
    @Test
    public void testNullConfig() {
        // Use assertThrows to verify that BasketSplitter constructor throws IllegalArgumentException
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            BasketSplitter splitter = new BasketSplitter(null);
        });

        // Verify that the exception message is correct
        assertEquals("Config file path cannot be null", exception.getMessage());
    }

    // Test what happens when some items in the input list are not present in the configuration map
    @Test
    public void testItemsNotInConfig() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = Collections.singletonList("NonExistingItem");
            Map<String, List<String>> result = splitter.split(items);
            assertNotNull(result);
            assertEquals(0, result.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test what happens when there are duplicate items in the input list
    @Test
    public void testDuplicateItems() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = List.of("Item1", "Item2", "Item1"); // Duplicate "Item1"
            Map<String, List<String>> result = splitter.split(items);
            // Assert the expected behavior, e.g., duplicates are handled properly
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test what happens when the items have different casings compared to the keys in the configuration map
    @Test
    public void testDifferentCasing() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            List<String> items = List.of("COCOA butter", "TABLE Cloth 54x72 WHITE"); // Different casings
            Map<String, List<String>> result = splitter.split(items);
            // Assert the expected behavior regarding casing sensitivity
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test the performance and behavior of the splitter when given a large number of items
    @Test
    public void testLargeNumberOfItems() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            // Generate a large number of items and test
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test what happens when there are delivery methods with unusual formats or special characters
    @Test
    public void testSpecialDeliveryMethods() {
        try {
            BasketSplitter splitter = new BasketSplitter(configFilePath);
            // Test with delivery methods having special characters or unusual formats
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

}
