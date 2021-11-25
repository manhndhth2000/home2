package vn.techmaster.imdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.techmaster.imdb.model.Film;
import vn.techmaster.imdb.repository.FilmRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmRepoTest {
	@Autowired private FilmRepository filmRepo;

	@Test
	public void getAll() {
		List<Film> filmList = filmRepo.getAll();
		System.out.println(filmList);
	}

	@Test
	public void getFilmByCountry(){
		List<Film> films = new ArrayList<>();

		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "abc", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "aaaa", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);

		var response = filmRepo.getFilmByCountry(films);

		assertThat(response.get("abc").size()).isEqualTo(1L);
	}

	@Test
	public void getcountryMakeMostFilms(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);

		var stringLongEntry = filmRepo.getcountryMakeMostFilms(films);

		assertThat(stringLongEntry.getValue()).isEqualTo(2L);
	}

	@Test
	public void getAllGeneres(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);
		var response = filmRepo.getAllGeneres(films);

		assertThat(response.size()).isEqualTo(2L);
	}

	@Test
	public void getFilmsMadeByCountryFromYearToYear(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);

		var country = "123";
		var fromYear = 2000;
		var toYear = 2021;

		var response = filmRepo.getFilmsMadeByCountryFromYearToYear(country, fromYear, toYear, films);
		assertThat(response.size()).isEqualTo(0L);
	}

	@Test
	public void top5HighMarginFilms(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);

		films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
		var response = filmRepo.top5HighMarginFilms(films);

		assertThat(response.size()).isEqualTo(2L);
	}

	@Test
	public void top5HighMarginFilmsIn1990To2000(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);

		films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
		var response = filmRepo.top5HighMarginFilmsIn1990to2000(films);

		assertThat(response.size()).isEqualTo(2L);
	}

	@Test
	public void categorizeFilmByGenere(){
		List<Film> films = new ArrayList<>();
		System.out.println(films);
	}

	@Test
	public void ratioBetweenGenere(){
		List<Film> films = new ArrayList<>();

		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		List<String> genre2 = new ArrayList<>();
		genre2.add("horror");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film3 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre2), 2000, 3000, 0);
		films.add(film1);
		films.add(film2);
		films.add(film3);

		var genreX = "love";
		var genreY = "horror";

		var result = filmRepo.ratioBetweenGenere(genreX, genreY, films);

		assertThat(result).isEqualTo(1L);
	}

	@Test
	public void top5FilmsHighRatingButLowMargin(){
		List<Film> films = new ArrayList<>();
		List<String> genre = new ArrayList<>();
		genre.add("love");
		genre.add("comedy");

		var film1 = new Film(123, "123", 2000, "123", 7.8, new ArrayList<>(genre), 2000, 3000, 0);
		var film2 = new Film(124, "123", 2000, "123", 8.8, new ArrayList<>(genre), 2500, 3000, 0);
		var film3 = new Film(125, "123", 2000, "123", 9.8, new ArrayList<>(genre), 2600, 3000, 0);
		var film4 = new Film(126, "123", 2000, "123", 10.8, new ArrayList<>(genre), 2700, 3000, 0);
		var film5 = new Film(127, "123", 2000, "123", 11.8, new ArrayList<>(genre), 2800, 3000, 0);
		films.add(film1);
		films.add(film2);
		films.add(film3);
		films.add(film4);
		films.add(film5);

		films.forEach(p -> p.setMargin(p.getRevenue() - p.getCost()));
		var response = filmRepo.top5FilmsHighRatingButLowMargin(films);

		assertThat(response).isSortedAccordingTo(Comparator.comparing(Film::getRating).thenComparing(Film::getMargin));
		assertThat(response.size()).isEqualTo(5L);
	}


}
