package main;

import engine.SimulationManager;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
    public static void main() {
        int foxNumber = 8;
        int hareNumber = 20;

        for (int i = 0; i < foxNumber; i++) {
            createAnimal("Fox_" + i, "main.Fox");
        }
        for (int i = 0; i < hareNumber; i++) {
            createAnimal("Hare_" + i, "main.Hare");
        }
    }

    public static void createAnimal(String name, String className) {
        try {
            ContainerController container = SimulationManager.animalContainer;
            AgentController ac = container.createNewAgent(name, className, null);
            ac.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
