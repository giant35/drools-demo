package demo;

import org.kie.api.KieServices;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ApplicationRunner r() {
        return args -> {
            droolsDemo();
        };
    }

    public void droolsDemo() {
        final var c = KieServices.Factory.get().getKieClasspathContainer();
        var resutl = c.verify();
        System.out.println("result:" + resutl);
        final var session = c.newKieSession("rule1");
        final Bill bill = bill();
        System.out.println("bill:" + bill);
        session.insert(bill);
        int count = session.fireAllRules();
        session.dispose();
        System.out.println("after fire rules count:" + count + " bill:" + bill);
    }

    private Bill bill() {
        final Item item1 = new Item(1, BigDecimal.valueOf(10), null);
        final Item item2 = new Item(2, BigDecimal.valueOf(15), null);

        Bill ret = new Bill();
        ret.items = List.of(item1, item2);
        return ret;
    }
}
