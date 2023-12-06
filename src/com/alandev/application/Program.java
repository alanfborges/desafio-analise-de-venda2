package com.alandev.application;

import com.alandev.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Sale> list = new ArrayList<>();

        System.out.print("Entre com o caminho do arquivo: ");
        String path = sc.nextLine();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                Sale sale = new Sale(month, year, seller, items, total);
                list.add(sale);
                line = br.readLine();
            }

            System.out.println("Total vendas por vendedor: ");
            Map<String, Double> salesSumValues = list.stream()
                    .collect(Collectors.groupingBy(
                            Sale::getSeller,
                            Collectors.summingDouble(Sale::getTotal)));

            for (String key : salesSumValues.keySet()) {
                System.out.println(key + " - R$ " + String.format("%.2f", salesSumValues.get(key)));
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
