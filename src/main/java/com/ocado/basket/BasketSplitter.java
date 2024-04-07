package com.ocado.basket;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BasketSplitter {
    private final Map<String, List<String>> config; // Map to store configuration data

    // Constructor to initialize BasketSplitter with configuration data from a JSON file
    public BasketSplitter(String absolutePathToConfigFile) throws IOException {
        if (absolutePathToConfigFile == null) {
            throw new IllegalArgumentException("Config file path cannot be null");
        }
        Gson gson = new Gson();
        FileReader reader = new FileReader(absolutePathToConfigFile);
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        this.config = gson.fromJson(reader, type);
        reader.close();
    }
    public Map<String, List<String>> getConfig(){
        return  this.config;
    }
    // Method to split items into delivery groups based on configuration

    public Map<String, List<String>> split(List<String> items) {
        // Check if items list is null
        if (items == null) {
            // Handle the case when items list is null, possibly by returning an empty map
            return Collections.emptyMap();
        }
        // Check if config map is null
        if (config == null) {
            // Handle the case when config is null, possibly by returning an empty map
            return Collections.emptyMap();
        }

        // Map to store items grouped by delivery method
        Map<String, List<String>> deliveryMap = new HashMap<>();

        // Group items according to the configuration
        for (String item : items) {
            List<String> deliveryMethods = config.get(item);
            if (deliveryMethods != null) {
                for (String deliveryMethod : deliveryMethods) {
                    // Add item to the corresponding delivery method
                    deliveryMap.computeIfAbsent(deliveryMethod, k -> new ArrayList<>()).add(item);
                }
            }
        }

        // Sort deliveryMap by the size of lists (items) in each delivery method
        Map<String, List<String>> sortedDeliveryMap = deliveryMap.entrySet().stream()
                .sorted(Map.Entry.<String, List<String>>comparingByValue(Comparator.comparingInt(List::size)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Iterate through sortedDeliveryMap to remove items that belong to multiple delivery methods
        Iterator<Map.Entry<String, List<String>>> iterator = sortedDeliveryMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String deliveryMethod = entry.getKey();
            List<String> itemsForDelivery = entry.getValue();

            // Create an iterator for the list to safely remove elements
            Iterator<String> listIterator = itemsForDelivery.iterator();
            while (listIterator.hasNext()) {
                String item = listIterator.next();
                for (Map.Entry<String, List<String>> otherEntry : sortedDeliveryMap.entrySet()) {
                    if (otherEntry.getKey().equals(deliveryMethod)) {
                        continue;
                    }

                    if (otherEntry.getValue().contains(item)) {
                        // Remove the item from the current delivery group
                        listIterator.remove();
                        // If the list becomes empty, remove the key as well
                        if (itemsForDelivery.isEmpty()) {
                            iterator.remove(); // Remove the entry from the map
                        }
                        break;
                    }
                }
            }
        }

        return sortedDeliveryMap;// Return the sorted and cleaned delivery map
    }

}
