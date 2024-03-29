package io.ylab.intensive.lesson04.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static final int BATCH_SIZE = 5_000;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (FileInputStream is = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(is, "Windows-1252");
                BufferedReader br = new BufferedReader(isr);) {

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

    private boolean saveMovies(List<Movie> movies) {
        boolean isSuccess = false;
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("INSERT INTO movie VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");) {

            long id = 1L;
            for (int i = 0; i < movies.size(); i++) {
                Movie m = movies.get(i);
                setStatementValues(ps, id++, m);
                ps.addBatch();
                if ((i + 1) % BATCH_SIZE == 0 || (i + 1) == movies.size()) {
                    ps.executeBatch();
                }
            }
            isSuccess = true;

        } catch (SQLException ex) {
            Logger.getLogger(MovieLoaderImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            return isSuccess;
        }
    }

    private void setStatementValues(PreparedStatement ps, long id, Movie m) throws SQLException {
        ps.setLong(1, id);
        setInteger(ps, 2, m.getYear());
        setInteger(ps, 3, m.getLength());
        setString(ps, 4, m.getTitle());
        setString(ps, 5, m.getSubject());
        setString(ps, 6, m.getActors());
        setString(ps, 7, m.getActress());
        setString(ps, 8, m.getDirector());
        setInteger(ps, 9, m.getPopularity());
        ps.setBoolean(10, m.getAwards());
    }

    private void setInteger(PreparedStatement ps, int column, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(column, value);
        } else {
            ps.setNull(column, Types.INTEGER);
        }
    }

    private void setString(PreparedStatement ps, int column, String value) throws SQLException {
        if (value != null) {
            ps.setString(column, value);
        } else {
            ps.setNull(column, Types.VARCHAR);
        }
    }

}
