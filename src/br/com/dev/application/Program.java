package br.com.dev.application;

import br.com.dev.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        System.out.println("Entre o caminho do arquivo:");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Sale> sales = new ArrayList<>();
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                sales.add(new Sale(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), fields[2], Integer.valueOf(fields[3]), Double.valueOf(fields[4])));
                line = br.readLine();
            }

            System.out.println("\nTotal de vendas por vendedor:");
            Set<String> sellers = sales.stream()
                    .map(s -> s.getSeller())
                    .collect(Collectors.toSet());

            for (String name : sellers){

                double total = sales.stream()
                        .filter(s -> s.getSeller().equals(name))
                        .map( s -> s.getTotal())
                        .reduce(0.0, (x,y) -> x+y);

                System.out.println(name + " - R$ " + String.format("%.2f", total));
            }

        }
        catch (IOException e) {
            System.err.println("ERROR: " + path + " (O sistema não pode encontrar o arquivo especificado)");
        }
        sc.close();
    }
}