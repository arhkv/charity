package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Charity;
import com.example.charityapi.repository.CharityRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcCharityRepository implements CharityRepository {

    private final DataSource dataSource;

    public JdbcCharityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Charity save(Charity charity) {
        String sql = "INSERT INTO charities(name, description) VALUES (?, ?) RETURNING id, created_at";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, charity.getName());
            ps.setString(2, charity.getDescription());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                charity.setId(rs.getLong("id"));
                charity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return charity;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while saving charity: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Charity> findById(Long id) {
        String sql = "SELECT id, name, description, created_at FROM charities WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while finding charity: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Charity> findAll() {
        String sql = "SELECT id, name, description, created_at FROM charities ORDER BY id";
        List<Charity> list = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error while reading charities: " + e.getMessage(), e);
        }
    }

    @Override
    public Charity update(Long id, Charity charity) {
        String sql = "UPDATE charities SET name = ?, description = ? WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, charity.getName());
            ps.setString(2, charity.getDescription());
            ps.setLong(3, id);

            ps.executeUpdate();
            return findById(id).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while updating charity: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM charities WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while deleting charity: " + e.getMessage(), e);
        }
    }

    private Charity map(ResultSet rs) throws SQLException {
        return new Charity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
