package com.example.charityapi.repository.jdbc;

import com.example.charityapi.entity.Donation;
import com.example.charityapi.exception.DbException;
import com.example.charityapi.exception.NotFoundException;
import com.example.charityapi.repository.DonationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDonationRepository implements DonationRepository {

    private final JdbcTemplate jdbc;

    public JdbcDonationRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Donation save(Donation donation) {
        try {
            String sql = "INSERT INTO donations(donor_id, charity_id, amount, donated_at, comment) VALUES (?, ?, ?, ?, ?)";

            KeyHolder kh = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, donation.getDonorId());
                ps.setLong(2, donation.getCharityId());
                ps.setLong(3, donation.getAmount());
                ps.setTimestamp(4, Timestamp.valueOf(donation.getDonatedAt()));
                ps.setString(5, donation.getComment());
                return ps;
            }, kh);

            Long id = kh.getKey().longValue();
            donation.setId(id);
            return donation;
        } catch (Exception e) {
            throw new DbException("DB error while saving donation", e);
        }
    }

    @Override
    public Optional<Donation> findById(Long id) {
        try {
            String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations WHERE id=?";
            List<Donation> list = jdbc.query(sql, (rs, rn) -> new Donation(
                    rs.getLong("id"),
                    rs.getLong("donor_id"),
                    rs.getLong("charity_id"),
                    rs.getLong("amount"),
                    rs.getTimestamp("donated_at").toLocalDateTime(),
                    rs.getString("comment")
            ), id);
            return list.stream().findFirst();
        } catch (Exception e) {
            throw new DbException("DB error while reading donation", e);
        }
    }

    @Override
    public List<Donation> findAll() {
        try {
            String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations ORDER BY id DESC";
            return jdbc.query(sql, (rs, rn) -> new Donation(
                    rs.getLong("id"),
                    rs.getLong("donor_id"),
                    rs.getLong("charity_id"),
                    rs.getLong("amount"),
                    rs.getTimestamp("donated_at").toLocalDateTime(),
                    rs.getString("comment")
            ));
        } catch (Exception e) {
            throw new DbException("DB error while reading donations", e);
        }
    }

    @Override
    public List<Donation> findByDonorId(Long donorId) {
        try {
            String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations WHERE donor_id=? ORDER BY id DESC";
            return jdbc.query(sql, (rs, rn) -> new Donation(
                    rs.getLong("id"),
                    rs.getLong("donor_id"),
                    rs.getLong("charity_id"),
                    rs.getLong("amount"),
                    rs.getTimestamp("donated_at").toLocalDateTime(),
                    rs.getString("comment")
            ), donorId);
        } catch (Exception e) {
            throw new DbException("DB error while reading donations by donor", e);
        }
    }

    @Override
    public List<Donation> findByCharityId(Long charityId) {
        try {
            String sql = "SELECT id, donor_id, charity_id, amount, donated_at, comment FROM donations WHERE charity_id=? ORDER BY id DESC";
            return jdbc.query(sql, (rs, rn) -> new Donation(
                    rs.getLong("id"),
                    rs.getLong("donor_id"),
                    rs.getLong("charity_id"),
                    rs.getLong("amount"),
                    rs.getTimestamp("donated_at").toLocalDateTime(),
                    rs.getString("comment")
            ), charityId);
        } catch (Exception e) {
            throw new DbException("DB error while reading donations by charity", e);
        }
    }

    @Override
    public Donation update(Long id, Donation d) {
        try {
            String sql = "UPDATE donations SET donor_id=?, charity_id=?, amount=?, donated_at=?, comment=? WHERE id=?";
            int updated = jdbc.update(sql,
                    d.getDonorId(),
                    d.getCharityId(),
                    d.getAmount(),
                    Timestamp.valueOf(d.getDonatedAt()),
                    d.getComment(),
                    id
            );
            if (updated == 0) throw new NotFoundException("Donation not found: id=" + id);

            d.setId(id);
            return d;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DbException("DB error while updating donation", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM donations WHERE id=?";
            jdbc.update(sql, id);
        } catch (Exception e) {
            throw new DbException("DB error while deleting donation", e);
        }
    }
}
