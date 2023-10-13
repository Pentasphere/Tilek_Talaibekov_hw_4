import java.util.Random;

public class Main {
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 280, 200, 200, 600, 400};
    public static int[] heroesDamage = {10, 15, 20, 0, 20, 20, 20, 5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky", "Thor", "Witcher", "Golem"};
    public static int roundNumber = 0;


    public static void main(String[] args) {
        
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        medicHealth();
        uklonLucky();
        oglohThor();
        treatWitcher();
        hitGolem();
    }

    public static void hitGolem(){
        int atac = bossDamage/5;
        int liveGolem = 0;
        for (int i = 0; i < heroesHealth.length; i++){
            if (i==7){
                continue;
            } else if (heroesHealth[i] > 0){
                liveGolem++;
                heroesHealth[i] += atac;
            }
            heroesHealth[7] -= atac * liveGolem;
            System.out.println("Голем получил урон: " + atac * liveGolem);
            break;
        }
    }

    private static void treatWitcher() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Witcher")) {
                continue;
            } else if (heroesHealth[i] == 0 && heroesHealth[6] > 0) {
                heroesHealth[i] = heroesHealth[i] + heroesHealth[6];
                heroesHealth[6] = 0;
                System.out.println("Witcher отдал жизнь " + heroesAttackType[i]);
                break;
            }
        }
    }
    /*private static void hitBerserk(){
        if (heroesHealth[6]>0){
            heroesHealth[6] +=10;
            heroesDamage[6] +=10;
        }
        System.out.println("режим берсерк");
    }*/

    private static void oglohThor(){
        if (bossDamage >= 0){
            bossDamage = 50;
        }
        Random random = new Random();
        boolean indexThor = random.nextBoolean();
        if (indexThor && heroesHealth[5] > 0){
            bossDamage = 0;
            System.out.println("тор оглушил");
        }
    }

    private static void uklonLucky(){
        Random random = new Random();
        boolean indexLucky = random.nextBoolean();
        if (indexLucky && heroesHealth[4] > 0){
            heroesHealth[4] += bossDamage;
            System.out.println("уклонился");
        }
    }

    private static void medicHealth(){
        for (int i = 0; i < heroesHealth.length; i++){
            if(heroesAttackType[i].equals("Medic")){
                continue;
            } else if (heroesHealth[i]>0 && heroesHealth[i]<100 && heroesHealth[3]>0) {
                heroesHealth[i] += 15;
                System.out.println("Medic heal " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2; // 2,3,4,5,6,7,8,9
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage; // heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: "
                + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
