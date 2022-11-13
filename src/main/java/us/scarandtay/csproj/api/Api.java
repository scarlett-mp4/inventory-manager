package us.scarandtay.csproj.api;

import us.scarandtay.csproj.utils.Category;

import java.io.File;
import java.time.LocalDate;

public class Api {

    private static void create(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
    }


    public static QueueAction<Runnable> createItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        return new QueueAction<>() {
            @Override
            public void queue() {

            }

            @Override
            public void complete() {
            }
        };
    }

}
