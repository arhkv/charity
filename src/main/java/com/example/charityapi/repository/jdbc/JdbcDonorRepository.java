package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Donor;
import com.example.charityapi.exception.DbException;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.DonorRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDonorRepository implements DonorRepository {

    private final JdbcTemplate jdbc;

    public JdbcDonorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Donor save(Donor donor) {
        try {
            String sql = "INSERT INTO donors(name, email) VALUES (?, ?)";

            KeyHolder kh = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, donor.getName());
                ps.setString(2, donor.getEmail());
                return ps;
            }, kh);

            Long id = kh.getKey().longValue();
            donor.setId(id);
            return donor;
        } catch (Exception e) {
            throw new DbException("DB error while saving donor", e);
        }
    }

    @Override
    public Optional<Donor> findById(Long id) {
        try {
            String sql = "SELECT id, name, email FROM donors WHERE id=?";
            List<Donor> list = jdbc.query(sql, (rs, rn) ->
                    new Donor(rs.getLong("id"), rs.getString("name"), rs.getString("email")), id);
            return list.stream().findFirst();
        } catch (Exception e) {
            throw new DbException("DB error while reading donor", e);
        }
    }

    @Override
    public List<Donor> findAll() {
        try {
            String sql = "SELECT id, name, email FROM donors ORDER BY id";
            return jdbc.query(sql, (rs, rn) ->
                    new Donor(rs.getLong("id"), rs.getString("name"), rs.getString("email")));
        } catch (Exception e) {
            throw new DbException("DB error while reading donors", e);
        }
    }

    @Override
    public Donor update(Long id, Donor donor) {
        try {
            String sql = "UPDATE donors SET name=?, email=? WHERE id=?";
            int updated = jdbc.update(sql, donor.getName(), donor.getEmail(), id);
            if (updated == 0) throw new NotFoundException("Donor not found: id=" + id);

            donor.setId(id);
            return donor;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DbException("DB error while updating donor", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM donors WHERE id=?";
            jdbc.update(sql, id);
        } catch (Exception e) {
            throw new DbException("DB error while deleting donor", e);
        }
    }
}
