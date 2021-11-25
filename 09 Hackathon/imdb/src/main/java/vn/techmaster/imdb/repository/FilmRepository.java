package vn.techmaster.imdb.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import vn.techmaster.imdb.model.Film;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class FilmRepository implements IFilmRepo{
  private List<Film> films;

  public FilmRepository(@Value("${datafile}") String datafile) {
    try {
      File file = ResourceUtils.getFile("classpath:static/" + datafile);
      ObjectMapper mapper = new ObjectMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
      films = Arrays.asList(mapper.readValue(file, Film[].class));
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<Film> getAll() {
    return films;
  }

  @Override
  public Map<String, List<Film>> getFilmByCountry(List<Film> films) {
    return films.stream().collect(Collectors.groupingBy(Film::getCountry));
  }

  @Override
  public Entry<String, Long> getcountryMakeMostFilms(List<Film> films) {
    // TODO Auto-generated method stub
    Map.Entry<String, Long> stringLongEntry = films.stream()
            .collect(Collectors.groupingBy(Film::getCountry, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue()).orElse(null);
    assert stringLongEntry != null;

    return stringLongEntry;
  }

  @Override
  public Entry<Integer, Integer> yearMakeMostFilms(List<Film> films) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getAllGeneres(List<Film> films) {
    // TODO Auto-generated method stub
    return films.stream().flatMap(p -> p.getGeneres().stream()).distinct().toList();
  }

  @Override
  public List<Film> getFilmsMadeByCountryFromYearToYear(String country, int fromYear, int toYear, List<Film> films) {
    // TODO Auto-generated method stub
    return films.stream().filter(p -> (p.getCountry().equals(country)) && (p.getYear() > fromYear) && (p.getYear() < toYear)).toList();
  }

  @Override
  public Map<String, List<Film>> categorizeFilmByGenere(List<Film> films) {
    // TODO Auto-generated method stub
//    var result = films.stream().collect(
//            Collectors.flatMapping(p -> p.getGeneres().stream(), Collectors.toList())
//    );
    return null;
  }

  @Override
  public List<Film> top5HighMarginFilms(List<Film> films) {
    // TODO Auto-generated method stub
    films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
    return films.stream()
            .sorted(Comparator.comparing(Film::getMargin).reversed())
            .limit(5)
            .toList();
  }

  @Override
  public List<Film> top5HighMarginFilmsIn1990to2000(List<Film> films) {
    // TODO Auto-generated method stub
    films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
    return films.stream()
            .filter(p -> (p.getYear() >= 1990) && (p.getYear() <= 2000))
            .sorted(Comparator.comparing(Film::getMargin).reversed())
            .limit(5)
            .toList();
  }

  @Override
  public double ratioBetweenGenere(String genreX, String genreY, List<Film> films) {
    // TODO Auto-generated method stub
    var result = films.stream()
            .flatMap(p -> p.getGeneres().stream())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return result.get(genreX)/result.get(genreY);
  }

  @Override
  public List<Film> top5FilmsHighRatingButLowMargin(List<Film> films) {
    // TODO Auto-generated method stub
    films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
    return films.stream()
            .sorted(Comparator.comparing(Film::getRating)
                    .thenComparing(Comparator.comparing(Film::getMargin).reversed())).limit(5).toList();
  }

}
