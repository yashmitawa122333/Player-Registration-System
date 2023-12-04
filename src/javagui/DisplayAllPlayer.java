package javagui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DisplayAllPlayer extends JFrame {
    private JTable table;

    public DisplayAllPlayer(String jdbcUrl,String username,String password) {
        setTitle("Display All Players");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Add columns to the table
        model.addColumn("Player ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Address");
        model.addColumn("Province");
        model.addColumn("Postal Code");
        model.addColumn("Phone Number");
        model.addColumn("Game Title");
        model.addColumn("Score");
        model.addColumn("Date Played");

        // Fetch data from the database and populate the table
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT Player.player_id, Player.first_name, Player.last_name, Player.address, " +
                    "Player.province, Player.postal_code, Player.phone_number, " +
                    "PlayerAndGame.game_id, Game.game_title, PlayerAndGame.score, PlayerAndGame.playing_date " +
                    "FROM Player " +
                    "LEFT JOIN PlayerAndGame ON Player.player_id = PlayerAndGame.player_id " +
                    "LEFT JOIN Game ON PlayerAndGame.game_id = Game.game_id";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("player_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("province"),
                        resultSet.getString("postal_code"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("game_title"),
                        resultSet.getInt("score"),
                        resultSet.getDate("playing_date")
                });
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

        // Add the table to a scroll pane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
