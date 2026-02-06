package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Donation;
import com.example.charityapi.repository.DonationRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDonationRepository implements DonationRepository {

    private final DataSource dataSource;

    public JdbcDonationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Donation save(Donation donation) {
        String sql = """
            INSERT INTO donations(donor_id, charity_id, amount, donated_at, comment)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
            """;
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, donation.getDonorId());
            ps.setLong(2, donation.getCharityId());
            ps.setLong(3, donation.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(donation.getDonatedAt()));
            ps.setString(5, donation.getComment());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                donation.setId(rs.getLong("id"));
                return donation;
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while saving donation: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Donation> findById(Long id) {
        String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations WHERE id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while finding donation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Donation> findAll() {
        String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations ORDER BY id";
        List<Donation> list = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error while reading donations: " + e.getMessage(), e);
        }
    }

    @Override
    public Donation update(Long id, Donation d) {
        String sql = """
            UPDATE donations
            SET donor_id=?, charity_id=?, amount=?, donated_at=?, comment=?
            WHERE id=?
            """;
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, d.getDonorId());
            ps.setLong(2, d.getCharityId());
            ps.setLong(3, d.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(d.getDonatedAt()));
            ps.setString(5, d.getComment());
            ps.setLong(6, id);

            ps.executeUpdate();
            return findById(id).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while updating donation: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM donations WHERE id=?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while deleting donation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Donation> findByDonorId(Long donorId) {
        return queryList("SELECT * FROM donations WHERE donor_id=? ORDER BY donated_at DESC", donorId);
    }

    @Override
    public List<Donation> findByCharityId(Long charityId) {
        return queryList("SELECT * FROM donations WHERE charity_id=? ORDER BY donated_at DESC", charityId);
    }

    @Override
    public List<Donation> findByAmountGreaterThan(long minAmount) {
        return queryList("SELECT * FROM donations WHERE amount>? ORDER BY amount DESC", minAmount);
    }

    private List<Donation> queryList(String sql, long param) {
        List<Donation> list = new ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error while querying donations: " + e.getMessage(), e);
        }
    }

    private Donation map(ResultSet rs) throws SQLException {
        return new Donation(
                rs.getLong("id"),
                rs.getLong("donor_id"),
                rs.getLong("charity_id"),
                rs.getLong("amount"),
                rs.getTimestamp("donated_at").toLocalDateTime(),
                rs.getString("comment")
        );
    }
}
