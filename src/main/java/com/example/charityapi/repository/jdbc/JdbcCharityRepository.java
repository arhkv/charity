package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Charity;
import com.example.charityapi.exception.DbException;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.CharityRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCharityRepository implements CharityRepository {

    private final JdbcTemplate jdbc;

    public JdbcCharityRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Charity save(Charity charity) {
        try {
            String sql = "INSERT INTO charities(name, description) VALUES (?, ?)";

            KeyHolder kh = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, charity.getName());
                ps.setString(2, charity.getDescription());
                return ps;
            }, kh);

            Long id = kh.getKey().longValue();
            charity.setId(id);
            return charity;
        } catch (Exception e) {
            throw new DbException("DB error while saving charity", e);
        }
    }

    @Override
    public Optional<Charity> findById(Long id) {
        try {
            String sql = "SELECT id, name, description FROM charities WHERE id=?";
            List<Charity> list = jdbc.query(sql, (rs, rn) ->
                    new Charity(rs.getLong("id"), rs.getString("name"), rs.getString("description")), id);
            return list.stream().findFirst();
        } catch (Exception e) {
            throw new DbException("DB error while reading charity", e);
        }
    }

    @Override
    public List<Charity> findAll() {
        try {
            String sql = "SELECT id, name, description FROM charities ORDER BY id";
            return jdbc.query(sql, (rs, rn) ->
                    new Charity(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            throw new DbException("DB error while reading charities", e);
        }
    }

    @Override
    public Charity update(Long id, Charity charity) {
        try {
            String sql = "UPDATE charities SET name=?, description=? WHERE id=?";
            int updated = jdbc.update(sql, charity.getName(), charity.getDescription(), id);
            if (updated == 0) throw new NotFoundException("Charity not found: id=" + id);

            charity.setId(id);
            return charity;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DbException("DB error while updating charity", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM charities WHERE id=?";
            jdbc.update(sql, id);
        } catch (Exception e) {
            throw new DbException("DB error while deleting charity", e);
        }
    }
}
