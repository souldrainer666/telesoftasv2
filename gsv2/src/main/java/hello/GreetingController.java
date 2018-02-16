package hello;

import gildedrose.GildedRose;
import gildedrose.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GildedRose app;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/list")
    public List<Item> list() {
        return Arrays.asList(app.getItems());
    }

    @RequestMapping("/details")
    public List<Item> details(@RequestParam(value = "name", defaultValue = "-") String name) {
        List<Item> items = Arrays.asList(app.getItems());
        if (!name.equals("-")) {
            List<Item> result = new ArrayList<>();
            for (Item item : items) {
                if (item.getName().equals(name)) {
                    result.add(item);
                }
            }
            return result;
        }
        return list();
    }
}
