import java.util.HashMap;
import java.util.Map;

public class RentalInfoCorregido {
  
  private Map<String, Movie> movies;

  public RentalInfoCorregido() {
    movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", "regular"));
    movies.put("F002", new Movie("Matrix", "regular"));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));
  }

  public String statement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

    for (MovieRental rental : customer.getRentals()) {
      Movie movie = movies.get(rental.getMovieId());
      double thisAmount = calculateAmount(rental, movie);

      // add frequent bonus points
      frequentEnterPoints++;
      if (movie.getCode().equals("new") && rental.getDays() > 2) {
        frequentEnterPoints++;
      }

      // print figures for this rental
      result.append("\t").append(movie.getTitle()).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }

    // add footer lines
    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

    return result.toString();
  }

  private double calculateAmount(MovieRental rental, Movie movie) {
    double thisAmount = 0;

    if (movie.getCode().equals("regular")) {
      thisAmount = 2;
      if (rental.getDays() > 2) {
        thisAmount += (rental.getDays() - 2) * 1.5;
      }
    } else if (movie.getCode().equals("new")) {
      thisAmount = rental.getDays() * 3;
    } else if (movie.getCode().equals("childrens")) {
      thisAmount = 1.5;
      if (rental.getDays() > 3) {
        thisAmount += (rental.getDays() - 3) * 1.5;
      }
    }

    return thisAmount;
  }
}