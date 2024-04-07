package com.ocado.basket;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BasketSplitter {
    /* ... */
    private final Map<String, List<String>> config;

    public BasketSplitter(String absolutePathToConfigFile) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(absolutePathToConfigFile);
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        this.config = gson.fromJson(reader, type);
        reader.close();
    }
    public Map<String, List<String>> getConfig(){
        return  this.config;
    }
    public Map<String, List<String>> split(List<String> items) {
        /* ... */
        //Stwórz mape deliveryMap dostaw i pustych list do przechowania produktow
        Map<String, List<String>> deliveryMap = new HashMap<>();
        // Dla każdego produktu z koszyka dodaj go do `deliveryMap`
        for (String item : items) {
            List<String> deliveryMethods = config.get(item);
            if (deliveryMethods != null) {
                for (String deliveryMethod : deliveryMethods) {
                        if (deliveryMap.containsKey(deliveryMethod)){
                            List<String> arr = new ArrayList<>();
                            arr = deliveryMap.get(deliveryMethod);
                            arr.add(item);
                            deliveryMap.put(deliveryMethod,arr);
                        }else{
                            List<String> arr = new ArrayList<>();
                            arr.add(item);
                            deliveryMap.put(deliveryMethod,arr);

                        }
                }
            }
        }
        System.out.println(deliveryMap.toString());

        //posortuj mape od po wielkosci value
        // Posortuj mapę `deliveryMap` od największej do najmniejszej liczby produktów
        Map<String, List<String>> sortedDeliveryMap = deliveryMap.entrySet().stream()
                .sorted(Map.Entry.<String, List<String>>comparingByValue(Comparator.comparingInt(List::size)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(sortedDeliveryMap.toString());

        //idz od najmniejszej wielkosc value
        //jesli element z value znajduje sie w dalej w mapie usun go z z obecnego elementu
        //idz w gore mapy elementow
        //nie musimy spwadzac lub usuwac z ostaniej wartosci mapy
        // Iteruj po posortowanej mapie
// Iteruj po posortowanej mapie
        // Iterate through the sorted map
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
        System.out.println(sortedDeliveryMap);

        return sortedDeliveryMap;
    }
    /* ... */
    public static void main(String[] args) {
        try {
            BasketSplitter splitter = new BasketSplitter("config.json");
            List<String> items = new ArrayList<>();
            items.add("Steak (300g)");
            items.add("Carrots (1kg)");
            items.add("Soda (24x330ml)");
            items.add("AA Battery (4 Pcs.)");
            items.add("Espresso Machine");
            items.add("Garden Chair");

            Map<String, List<String>> result = splitter.split(items);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
