package com.github.badaccuracyid.cuddlyoctogarbanzo.menus;

import com.github.badaccuracyid.cuddlyoctogarbanzo.Main;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Menu {

    private static final List<Menu> menus = new ArrayList<>();
    protected static final Main main = Main.getInstance();
    protected static Menu lastMenu = null;

    protected Menu() {
        menus.add(this);
    }

    protected abstract void showMenu();

    public void open(Class<? extends Menu> clazz) {
        lastMenu = this;

        menus.stream()
                .filter(menu -> menu.getClass().equals(clazz))
                .findFirst()
                .ifPresent(Menu::showMenu);
    }

    public static void openMenu(Class<? extends Menu> clazz, Class<? extends Menu> laztMenu) {
        // yes i'm bad at variable naming
        Menu lazztMenu = menus.stream()
                .filter(menu1 -> menu1.getClass().equals(laztMenu))
                .findFirst()
                .orElse(null);

        assert lazztMenu != null;
        lastMenu = lazztMenu;

        menus.stream()
                .filter(menu -> menu.getClass().equals(clazz))
                .findFirst()
                .ifPresent(Menu::showMenu);
    }

    protected void enterOrGoBack() {
        System.out.println("Press enter to continue...");
        main.getScanner().nextLine();

        if (lastMenu != null) {
            lastMenu.showMenu();
        } else {
            Utils.clearScreen();
            System.out.println("No last menu!");
        }
    }

    protected int determineIntChoice(Runnable prompt, List<IntRequirement> intRequirements, boolean clearScreen) {
        int choice = -1;
        do {
            if (clearScreen) {
                Utils.clearScreen();
            }

            prompt.run();
            try {
                choice = Integer.parseInt(main.getScanner().nextLine());
            } catch (NumberFormatException ignored) {
            }

            for (IntRequirement intRequirement : intRequirements) {
                if (!intRequirement.getPredicate().test(choice)) {
                    System.out.println(intRequirement.getErrorMessage());
                    choice = -1;
                    break;
                }
            }
        } while (choice == -1);

        return choice;
    }

    protected String determineStrChoice(Runnable prompt, List<StrRequirement> strRequirements, boolean clearScreen) {
        String value;
        do {
            if (clearScreen) {
                Utils.clearScreen();
            }

            prompt.run();
            value = main.getScanner().nextLine();

            for (StrRequirement strRequirement : strRequirements) {
                if (!strRequirement.getPredicate().test(value)) {

                    System.out.println(strRequirement.getErrorMessage());
                    value = null;
                    break;
                }
            }
        } while (value == null);

        return value;
    }

    protected static class IntRequirement {

        private final Predicate<Integer> predicate;
        private final String errorMessage;

        public IntRequirement(Predicate<Integer> predicate, String errorMessage) {
            this.predicate = predicate;
            this.errorMessage = errorMessage;
        }

        public Predicate<Integer> getPredicate() {
            return predicate;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }

    protected static class StrRequirement {

        private final Predicate<String> predicate;
        private final String errorMessage;

        public StrRequirement(Predicate<String> predicate, String errorMessage) {
            this.predicate = predicate;
            this.errorMessage = errorMessage;
        }

        public Predicate<String> getPredicate() {
            return predicate;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }

}
