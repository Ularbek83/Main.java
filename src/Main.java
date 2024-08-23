import java.util.Random;

public class Main {

    public static int bossHelth = 700;
    public static int bossDamage = 75;
    public static String bossDefence;

    public static String[] heroes = {"Hero", "Hero", "Hero", "Medik", "Gelem", "Lucky", "Witcher", "Thor"};
    public static int[] heroesHealth = {350, 270, 250, 400, 300, 290, 320, 310};

    public static int[] heroesDamage = {20, 15, 10, 0, 5, 10};
    public static String[] heroesAttackType = {"Phisical", "Magical", "Kinetic","Phisical", "Magical", "Kinetic"};
    public static boolean isLuckySkipAttack = false;
    public static boolean isToSkipBossAttack = false;
    public static boolean isThorSentPower = false;
    public static boolean isWitcherDied = false;
    public static int medicHeroTreat = -1;
    public static int roundNumber = 0;

    public static void main(String[] args) {
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        System.out.println("ROUND NUMBER: " + roundNumber);
        chooseBossDefence();
        System.out.println("Boss Defence: " + bossDefence);
        bossAttacks();
        System.out.println("Heroes started attack");
        heroesAttacks();
        treatHero();
        System.out.println("========================================");
    }

    private static void treatHero() {

        medicHeroTreat = -1;
        if (heroesHealth[3] <= 0) {
            return;
        }

        for (int i = 0; i < heroesHealth.length; i++) {
            if(i == 3){
                continue;
            }

            if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                heroesHealth[i] += 25;
                medicHeroTreat = i;
                System.out.println("Medik treat hero: " + heroes[i]);
                return;
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHelth <= 0) {
            System.out.println("Heroes won !!! ");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won !!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttacks() {

        if (isToSkipBossAttack) {
            isToSkipBossAttack = false;
            return;
        }

        int index = -1;
        for (String hero : heroes) {
            index++;
            System.out.print("Boss attack: " + hero + " at health: " + heroesHealth[index]);
            switch (hero) {
                case "Hero":
                    minusHealth(index, bossDamage);
                    break;
                case "Medik":
                    minusHealth(index, bossDamage);
                    break;
                case "Gelem":
                    minusHealth(index, bossDamage / 5);
                    break;
                case "Lucky":
                    if (isLuckySkipAttack) {
                        minusHealth(index, bossDamage);
                    }
                    isLuckySkipAttack = true;
                    break;
                case "Witcher":
                    minusHealth(index, bossDamage);
                    break;
                case "Thor":
                    minusHealth(index, bossDamage);
                    break;
            }
            System.out.println(" "+hero + " has health: " + heroesHealth[index]);
        }
    }

    public static void minusHealth(int i, int bossDamage) {
        if (heroesHealth[i] > 0) {
            if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0;
                System.out.println(" DIED "  + heroes[i]);
                if(!isWitcherDied){
                    isWitcherDied =true;
                    heroesHealth[i] = heroesHealth[7];
                    heroesHealth[7] = 0;
                    System.out.println(" Witcher gave life to " + heroes[i]);
                }
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesAttacks() {

        int index = -1;
        for (String hero : heroes) {
            index++;
            switch (hero) {
                case "Hero":
                    System.out.print(hero +" attacked boss " + " at health " + bossHelth + "  ");
                    minusBossHealth(index, heroesDamage[index]);
                    System.out.println(" Boss health after attack: " + bossHelth);
                    break;
                case "Golem":
                    System.out.print(hero +" attacked boss " + " at health " + bossHelth + "  ");
                    minusBossHealth(index, heroesDamage[index]);
                    System.out.println(" Boss health after attack: " + bossHelth);
                    break;
                case "Lucky":
                    System.out.print(hero +" attacked boss " + " at health " + bossHelth + "  ");
                    minusBossHealth(index, heroesDamage[index]);
                    System.out.println(" Boss health after attack: " + bossHelth);
                    break;
                case "Thor":
                    System.out.print(hero +" attacked boss " + " at health " + bossHelth + "  ");
                    if (!isToSkipBossAttack && !isThorSentPower) {
                        isToSkipBossAttack = true;
                        isThorSentPower = true;
                        System.out.println("Boss stopped attacking for 1 round");
                    }
                    System.out.println(" Boss health after attack: " + bossHelth);
                    break;
            }

        }


    }

    private static void minusBossHealth(int i, int heroDamage) {
        if (heroesHealth[i] > 0 && bossHelth > 0) {
            int damage = heroDamage;
            if (heroesAttackType[i] == bossDefence) {
                Random random = new Random();
                int coefficient = random.nextInt(10) + 2;
                damage = heroesDamage[i] * coefficient;
                System.out.println("Critical damage: " + damage);
            }
            if (bossHelth - damage < 0) {
                bossHelth = 0;
            } else {
                bossHelth = bossHelth - damage;
            }

        }
    }

    public static void printStatistics() {
       /*
         Это классический вариант: Здесь много кодов
         здесь можно использовать ТЕРНАРНЫЙ МЕТОД
        String defence;
        if(bossDefence == null){
            defence = "No defence";
        }else {
            defence = bossDefence;
        } */

        System.out.println("ROUND  " + roundNumber + "  -------------");
        System.out.println("BOSS health: " + bossHelth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        // (bossDefence == null ? "No defence": bossDefence ) - Это называется
        // ТЕРНАРНЫЙ условия

        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println( " health: " + heroesHealth[i]);

        }

//        if(medicHeroTreat > -1){
//            System.out.println(" Hero number " + medicHeroTreat + "  got medic treat");
//        }
//
//        if(medicHealth <= 0){
//            System.out.println(" Medik died and can not treat any hero");
//
//        }

    }
}