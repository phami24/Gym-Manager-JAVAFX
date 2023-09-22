package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Members;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembersRepository {
    private final JDBCConnect jdbcConnect;

    public MembersRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    public void addMember(Members member) {
        String query = "INSERT INTO members (first_name, last_name, dob, gender, email, phone_number, address, join_date, end_date, membership_status_id, membership_type_id, instructor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getFirst_name());
            statement.setString(2, member.getLast_name());
            statement.setDate(3, Date.valueOf(member.getDob()));
            statement.setString(4, member.getGender());
            statement.setString(5, member.getEmail());
            statement.setString(6, member.getPhone_number());
            statement.setString(7, member.getAddress());
            statement.setDate(8, Date.valueOf(member.getJoin_date()));
            statement.setDate(9, Date.valueOf(member.getEnd_date()));
            statement.setInt(10, member.getMembership_status_id());
            statement.setInt(11, member.getMembership_type_id());
            statement.setInt(12, member.getInstructorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(Members member) {
        String query = "UPDATE members " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, join_date = ?, end_date = ?, " +
                "membership_status_id = ?, membership_type_id = ?, instructor_id = ? " +
                "WHERE member_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getFirst_name());
            statement.setString(2, member.getLast_name());
            statement.setDate(3, Date.valueOf(member.getDob()));
            statement.setString(4, member.getGender());
            statement.setString(5, member.getEmail());
            statement.setString(6, member.getPhone_number());
            statement.setString(7, member.getAddress());
            statement.setDate(8, Date.valueOf(member.getJoin_date()));
            statement.setDate(9, Date.valueOf(member.getEnd_date()));
            statement.setInt(10, member.getMembership_status_id());
            statement.setInt(11, member.getMembership_type_id());
            statement.setInt(12, member.getInstructorId());
            statement.setInt(13, member.getMember_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(int memberId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM members WHERE member_id = ?")) {
            statement.setInt(1, memberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Members getMemberById(int memberId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE member_id = ?")) {
            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Members> getAllMembers() {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE membership_status_id != 4");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                membersList.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByNames(String firstName, String lastName) {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE first_name = ? AND last_name = ?")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    membersList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByInstructorId(int instructorId) {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE instructor_id = ?")) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    membersList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByYearAndMonth(int year, int month) {
        List<Members> membersList = new ArrayList<>();
        String query = "SELECT * FROM members WHERE YEAR(join_date) = ? AND MONTH(join_date) = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, year);
            statement.setInt(2, month);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Members member = fromResultSet(resultSet);
                    membersList.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;

    }

    public void deleteMemberByStatus(int memberId, int newStatusId) {
        String query = "UPDATE members SET membership_status_id = ? WHERE member_id = ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newStatusId);
            statement.setInt(2, memberId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalMembers() {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(member_id) as count FROM members");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Members fromResultSet(ResultSet resultSet) throws SQLException {
        Members member = new Members();
        member.setMember_id(resultSet.getInt("member_id"));
        member.setJoin_date(resultSet.getString("join_date"));
        member.setEnd_date(resultSet.getString("end_date"));
        member.setMembership_status_id(resultSet.getInt("membership_status_id"));
        member.setMembership_type_id(resultSet.getInt("membership_type_id"));
        member.setInstructorId(resultSet.getInt("instructor_id"));
        member.setFirst_name(resultSet.getString("first_name"));
        member.setLast_name(resultSet.getString("last_name"));
        member.setDob(resultSet.getString("dob"));
        member.setGender(resultSet.getString("gender"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone_number(resultSet.getString("phone_number"));
        member.setAddress(resultSet.getString("address"));
        return member;
    }

//    public void executeMemberQuery(String query, Members member) {
//        try (Connection connection = jdbcConnect.getJDBCConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, member.getFirst_name());
//            statement.setString(2, member.getLast_name());
//            statement.setDate(3, Date.valueOf(member.getDob()));
//            statement.setString(4, member.getGender());
//            statement.setString(5, member.getEmail());
//            statement.setString(6, member.getPhone_number());
//            statement.setString(7, member.getAddress());
//            statement.setDate(8, Date.valueOf(member.getJoin_date()));
//            statement.setDate(9, Date.valueOf(member.getEnd_date()));
//            statement.setInt(10, member.getMembership_status_id());
//            statement.setInt(11, member.getMembership_type_id());
//            statement.setInt(12, member.getInstructorId());
//            statement.setInt(13, member.getMember_id());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void updateFirstName(int memberId, String newFirstName) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setFirst_name(newFirstName);
            updateMember(member);
        }
    }

    public void updateLastName(int memberId, String newLastName) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setLast_name(newLastName);
            updateMember(member);
        }
    }

    public void updateDob(int memberId, String newDob) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setDob(newDob);
            updateMember(member);
        }
    }

    public void updateGender(int memberId, String newGender) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setGender(newGender);
            updateMember(member);
        }
    }

    public void updateEmail(int memberId, String newEmail) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setEmail(newEmail);
            updateMember(member);
        }
    }

    public void updatePhoneNumber(int memberId, String newPhoneNumber) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setPhone_number(newPhoneNumber);
            updateMember(member);
        }
    }

    public void updateJoinDate(int memberId, String newJoinDate) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setJoin_date(newJoinDate);
            updateMember(member);
        }
    }

    public void updateEndDate(int memberId, String newEndDate) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setEnd_date(newEndDate);
            updateMember(member);
        }
    }

    public void updateMembershipStatus(int memberId, int selectedStatusId) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setMembership_status_id(selectedStatusId);
            updateMember(member);
        }
    }

    public void updateMembershipType(int memberId, int selectedTypeId) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setMembership_type_id(selectedTypeId);
            updateMember(member);
        }
    }

    public void updateInstructor(int memberId, int selectedInstructorId) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setInstructorId(selectedInstructorId);
            updateMember(member);
        }
    }

    public void updateAddress(int memberId, String newAddress) {
        Members member = getMemberById(memberId);
        if (member != null) {
            member.setAddress(newAddress);
            updateMember(member);
        }
    }

    public List<Members> searchMembersByName(String name) {
        List<Members> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnect.getJDBCConnection();
            statement = connection.prepareStatement("SELECT * FROM members WHERE first_name LIKE ? OR last_name LIKE ?");

            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Members member = new Members();
                member.setMember_id(resultSet.getInt("member_id"));
                member.setJoin_date(resultSet.getString("join_date"));
                member.setEnd_date(resultSet.getString("end_date"));
                member.setMembership_status_id(Integer.parseInt(resultSet.getString("membership_status_id")));
                member.setMembership_type_id(resultSet.getInt("membership_type_id"));
                member.setFirst_name(resultSet.getString("first_name"));
                member.setLast_name(resultSet.getString("last_name"));
                member.setDob(resultSet.getDate("dob").toString());
                member.setGender(resultSet.getString("gender"));
                member.setEmail(resultSet.getString("email"));
                member.setPhone_number(resultSet.getString("phone_number"));
                member.setAddress(resultSet.getString("address"));

                results.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    public List<Members> getMembersByPage(int pageNumber, int pageSize) {
        List<Members> membersList = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        String query = "SELECT * FROM members WHERE membership_status_id != 4 LIMIT ? OFFSET ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    membersList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public String getMemberNameById(Long memberId) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT CONCAT(first_name, ' ', last_name) AS member_name FROM members WHERE member_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("member_name");
                }
                return null; // Return null if member not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Ném lại ngoại lệ để lớp gọi viên có thể xử lý ngoại lệ này
        }
    }
    public void updateMembershipStatusBasedOnEndDate() {
        String query = "UPDATE members SET membership_status_id = ? WHERE end_date < ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 2); // Thay thế EXPIRED_STATUS_ID bằng ID của trạng thái 'Expired'
            statement.setDate(2, Date.valueOf(LocalDate.now())); // Lấy ngày hiện tại
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Members> getMembersWithBirthday() {
        List<Members> membersWithBirthday = new ArrayList<>();

        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        String query = "SELECT * FROM members WHERE MONTH(dob) = ? AND DAY(dob) = ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            int currentMonth = currentDate.getMonthValue();
            int currentDay = currentDate.getDayOfMonth();

            statement.setInt(1, currentMonth);
            statement.setInt(2, currentDay);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Members member = fromResultSet(resultSet);
                    membersWithBirthday.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membersWithBirthday;
    }

}
