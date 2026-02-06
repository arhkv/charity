package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Donor;
import com.example.charityapi.repository.DonorRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDonorRepository implements DonorRepository {

    private final DataSource dataSource;

    public JdbcDonorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Donor save(Donor donor) {
        String sql = "INSERT INTO donors(name, email) VALUES (?, ?) RETURNING id, created_at";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, donor.getName());
            ps.setString(2, donor.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                donor.setId(rs.getLong("id"));
                donor.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return donor;
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while saving donor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Donor> findById(Long id) {
        String sql = "SELECT id, name, email, created_at FROM donors WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while finding donor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Donor> findByEmail(String email) {
        String sql = "SELECT id, name, email, created_at FROM donors WHERE email = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while finding donor by email: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Donor> findAll() {
        String sql = "SELECT id, name, email, created_at FROM donors ORDER BY id";
        List<Donor> donors = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) donors.add(map(rs));
            return donors;

        } catch (SQLException e) {
            throw new RuntimeException("DB error while reading donors: " + e.getMessage(), e);
        }
    }

    @Override
    public Donor update(Long id, Donor donor) {
        String sql = "UPDATE donors SET name = ?, email = ? WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, donor.getName());
            ps.setString(2, donor.getEmail());
            ps.setLong(3, id);

            int updated = ps.executeUpdate();
            if (updated == 0) throw new RuntimeException("Donor not updated (id=" + id + ")");
            return findById(id).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while updating donor: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM donors WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while deleting donor: " + e.getMessage(), e);
        }
    }

    private Donor map(ResultSet rs) throws SQLException {
        return new Donor(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
