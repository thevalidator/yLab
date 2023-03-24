package io.ylab.intensive.lesson04.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {

    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            List<Movie> movies = new ArrayList<>();
            String line;
            br.readLine();  //skip 1st line
            br.readLine();  //skip 2nd line

            while ((line = br.readLine()) != null && !line.isBlank()) {
                Movie m = parseMovie(line);
                movies.add(m);
            }

            if (!movies.isEmpty()) {
                saveMovies(movies);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MovieLoaderImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(MovieLoaderImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    private Movie parseMovie(String line) {
        String[] data = line.split(";");
        //Year;Length;Title;Subject;Actor;Actress;Director;Popularity;Awards;*Image
        //INT;INT;STRING;CAT;CAT;CAT;CAT;INT;BOOL;STRING
        Integer year = data[0].isBlank() ? null : Integer.valueOf(data[0]);
        Integer length = data[1].isBlank() ? null : Integer.valueOf(data[1]);
        String title = data[2].isBlank() ? null : data[2];
        String subject = data[3].isBlank() ? null : data[3];
        String actor = data[4].isBlank() ? null : data[4];
        String actress = data[5].isBlank() ? null : data[5];
        String director = data[6].isBlank() ? null : data[6];
        Integer popularity = data[7].isBlank() ? null : Integer.valueOf(data[7]);
        Boolean awards = data[8].equals("Yes");

        Movie movie = new Movie();
        movie.setActors(actor);
        movie.setActress(actress);
        movie.setAwards(awards);
        movie.setDirector(director);
        movie.setSubject(subject);
        movie.setTitle(title);
        movie.setYear(year);
        movie.setLength(length);
        movie.setPopularity(popularity);

        return movie;
    }

    private int[] saveMovies(List<Movie> movies) {
        int[] idList = null;
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("INSERT INTO movie VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");) {

            long id = 1L;
            for (Movie m: movies) {
                setStatementValues(ps, id, m);
                ps.addBatch();
                ps.clearParameters();
                id++;
            }
            idList = ps.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(MovieLoaderImpl.class.getName()).log(Level.SEVERE,
                    ex.getMessage());
        } finally {
            return idList;
        }
    }

    private void setStatementValues(PreparedStatement ps, long id, Movie m) throws SQLException {
        ps.setLong(1, id);
        if (m.getYear() != null) {
            ps.setInt(2, m.getYear());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        if (m.getLength() != null) {
            ps.setInt(3, m.getLength());
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        if (m.getTitle() != null) {
            ps.setString(4, m.getTitle());
        } else {
            ps.setNull(4, Types.VARCHAR);
        }
        if (m.getSubject() != null) {
            ps.setString(5, m.getSubject());
        } else {
            ps.setNull(5, Types.VARCHAR);
        }
        if (m.getActors() != null) {
            ps.setString(6, m.getActors());
        } else {
            ps.setNull(6, Types.VARCHAR);
        }
        if (m.getActress() != null) {
            ps.setString(7, m.getActress());
        } else {
            ps.setNull(7, Types.VARCHAR);
        }
        if (m.getDirector() != null) {
            ps.setString(8, m.getDirector());
        } else {
            ps.setNull(8, Types.VARCHAR);
        }
        if (m.getPopularity() != null) {
            ps.setInt(9, m.getPopularity());
        } else {
            ps.setNull(9, Types.INTEGER);
        }
        ps.setBoolean(10, m.getAwards());
    }

}
