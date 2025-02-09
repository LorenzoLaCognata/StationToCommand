package stationtocommand.model.personStructure;

import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.utilsStructure.Utils;

import java.util.Arrays;
import java.util.List;

public abstract class Person {

    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private Location location;

    public Person(String firstName, String lastName, Gender gender, Location location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.location = location;
    }

    public Person(Location location) {
        Gender gender = generateGender();
        String firstName = generateFirstName(gender);
        String lastName = generateLastName();
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String generateFirstName(Gender gender) {
        List<String> firstNames;
        if (gender.equals(Gender.MALE)) {
            firstNames = Arrays.asList("Gary", "Rodolfo", "Bill", "Russ", "Francisco", "Dennis", "Darrell", "George", "Tyrone", "Isaac", "Eddie", "Erick", "Karl", "Andy", "Ruben", "Julius", "Jared", "Luis", "Ernesto", "Hubert", "Austin", "Mario", "Terrence", "Cecil", "Adam", "Kelvin", "Sidney", "Howard", "Benny", "Alvin", "Douglas", "Lyle", "Raymond", "Johnnie", "Gustavo", "Justin", "Virgil", "Mitchell", "Phillip", "Jen", "Carl", "Josh", "Mitch", "Charles", "Ronald", "Ignacio", "Garrett", "Troy", "Terrance", "Mark", "Wendell", "Damon", "Saul", "Reginald", "Evan", "Casey", "Owen", "Ernest", "Julian", "Jeffrey", "Arturo", "Ronnie", "Patrick", "Robert", "Jan", "Luke", "Bud", "Guillermo", "Lawrence", "Clifford", "Andrew", "Rodney", "Mohammad", "Warren", "Mohammed", "Brent", "Christian", "Ali", "Jordan", "Clint", "Maurice", "Emmanuel", "Marty", "Marco", "Byron", "Roland", "Malcolm", "Gregg", "Drew", "Colin", "Jeff", "Leo", "Rick", "Rob", "Allen", "Claude", "Franklin", "Bobby", "Jaime", "Neal");
        }
        else {
            firstNames = Arrays.asList("Misty", "Tracy", "April", "Susana", "Kristy", "Krystal", "Elaine", "Cassandra", "Andrea", "Lorna", "Renee", "Annette", "Ethel", "Meredith", "Ana", "Sandra", "Rebekah", "Hope", "Joanne", "Marie", "Vickie", "Rosie", "Therese", "Jocelyn", "Della", "Sophia", "Charlotte", "Lindsey", "Loretta", "Nikki", "Evelyn", "Kristina", "Ginger", "Michele", "Thelma", "Lora", "Johanna", "Sonya", "Bonita", "Marianne", "Robin", "Katy", "Vivian", "Marisa", "Gladys", "Jean", "Mindy", "Anna", "Corinne", "Jayne");
        }
        return firstNames.get(Utils.randomGenerator.nextInt(firstNames.size()));
    }

    @SuppressWarnings("SpellCheckingInspection")
    public String generateLastName() {
        List<String> lastNames = Arrays.asList("Hayden","Fleming","Dennis","Lawson","Castaneda","Dwyer","Ibarra","Cox","Humphrey","Golden","Bass","Sandoval","Pittman","Soto","Garcia","Mathews","Roth","Bryant","Hanson","Larsen","Perry","Walsh","Baker","Orozco","Russell","Romero","Jordan","Leach","Wallace","Roberson","Wyatt","Gentry","Hughes","Peck","Whitehead","Meadows","Bond","Weeks","Jamison","Collins","O'Connor","Weber","Britton","Holder","Stout","Gutierrez","Booth","House","Fuller","Walls","Sampson","McGrath","Mack","Rogers","Hull","Albright","Brennan","Church","Byrd","Brady","Gregory","McKee","Willis","Graham","Hunter","Robbins","Kent","Douglas","Madden","Clay","McIntyre","Barber","Morris","McGuire","Fischer","Decker","Powers","Muller","Gilmore","Snyder","Noble","Perkins","Hayes","Thomson","Cobb","Kelly","Page","Cameron","Patton","Compton","Downey","Carpenter","Cline","Foley","Harper","Sexton","Gordon","Bradshaw","Gaines","Nixon");
        return lastNames.get(Utils.randomGenerator.nextInt(lastNames.size()));
    }

    public Gender generateGender() {
        if (Utils.randomGenerator.nextFloat() < 0.8) {
            return Gender.MALE;
        }
        else {
            return Gender.FEMALE;
        }
    }

}