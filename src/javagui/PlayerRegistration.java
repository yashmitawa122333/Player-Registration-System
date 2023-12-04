package javagui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class PlayerRegistration extends JFrame {
    Connection connection;
    final String jdbcUrl = ""; //Enter Your Creds
    final String username = ""; 
    final String password = ""; 


    // Create all TextField
    JTextField firstName_textField = new JTextField();
    JTextField lastName_textField = new JTextField();
    JTextField address_textField = new JTextField();
    JTextField province_textField = new JTextField();
    JTextField postalCode_textField = new JTextField();
    JTextField phoneNumber_textField = new JTextField();
    JTextField updatePlayerById_textField = new JTextField();
    JTextField gameTitle_textField = new JTextField();
    JTextField gameScore_textField = new JTextField();
    JTextField datePlayed_textField = new JTextField();

    public PlayerRegistration() {
        setTitle("Player Registration");
        setSize(850, 350); // Set size (width, height)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLocationRelativeTo(null); // Center the frame on the screen

        initilizeUi();

        // Create All the table in the database if not exists
        try {
            new Operations(jdbcUrl, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void initilizeUi() {
        // Create layout used in GUI
        JPanel gridLayout_personalInfo = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel gridLayout_gameInfo = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel gridLayout_updatePlayer = new JPanel(new GridLayout(0, 3, 10, 10));

        // Create Arranging layouts
        JPanel verticalLayout_presonalInfo = new JPanel();
        verticalLayout_presonalInfo.setLayout(new BoxLayout(verticalLayout_presonalInfo, BoxLayout.Y_AXIS));
        JPanel verticalLayout_gameInfo = new JPanel();
        verticalLayout_gameInfo.setLayout(new BoxLayout(verticalLayout_gameInfo, BoxLayout.Y_AXIS));
        JPanel wrapUpdatePlayerGameInfo = new JPanel();
        wrapUpdatePlayerGameInfo.setLayout(new BoxLayout(wrapUpdatePlayerGameInfo, BoxLayout.Y_AXIS));
        JPanel wrapPlayerInfoGameInfo = new JPanel();
        wrapPlayerInfoGameInfo.setLayout(new BoxLayout(wrapPlayerInfoGameInfo, BoxLayout.X_AXIS));
        JPanel wrapButtons = new JPanel();
        wrapButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JPanel wrapInfoButtons = new JPanel();
        wrapInfoButtons.setLayout(new BoxLayout(wrapInfoButtons, BoxLayout.Y_AXIS));

        // Create all TextFields JLabel's
        JLabel playerInformation_label = new JLabel("Player Information:");
        JLabel firstName_label = new JLabel("First Name:");
        JLabel lastName_label = new JLabel("Last Name");
        JLabel address_label = new JLabel("Address:");
        JLabel province_label = new JLabel("Province:");
        JLabel postalCode_label = new JLabel("Postal Code:");
        JLabel phoneNumber_label = new JLabel("Phone Number:");
        JLabel updatePlayerById_label = new JLabel("Update Player by ID:");
        JLabel gameInformation_label = new JLabel("Game Information:");
        JLabel gameTitle_label = new JLabel("Game Title:");
        JLabel gameScore_label = new JLabel("Game Score:");
        JLabel datePlayed_label = new JLabel("Date Played:");

        // create all button used
        JButton update_button = new JButton("Update");
        update_button.setMaximumSize(new Dimension(150, 50));
        JButton createPlayer_button = new JButton("Create Player");
        createPlayer_button.setMaximumSize(new Dimension(150, 50));
        JButton displayAllPlayer_button = new JButton("Display All Player");
        displayAllPlayer_button.setMaximumSize(new Dimension(150, 50));

        // Adding personal info to the grid layout
        gridLayout_personalInfo.add(firstName_label);
        gridLayout_personalInfo.add(firstName_textField);
        gridLayout_personalInfo.add(lastName_label);
        gridLayout_personalInfo.add(lastName_textField);
        gridLayout_personalInfo.add(address_label);
        gridLayout_personalInfo.add(address_textField);
        gridLayout_personalInfo.add(province_label);
        gridLayout_personalInfo.add(province_textField);
        gridLayout_personalInfo.add(postalCode_label);
        gridLayout_personalInfo.add(postalCode_textField);
        gridLayout_personalInfo.add(phoneNumber_label);
        gridLayout_personalInfo.add(phoneNumber_textField);

        // adding game info to the grid layout
        gridLayout_gameInfo.add(gameTitle_label);
        gridLayout_gameInfo.add(gameTitle_textField);
        gridLayout_gameInfo.add(gameScore_label);
        gridLayout_gameInfo.add(gameScore_textField);
        gridLayout_gameInfo.add(datePlayed_label);
        gridLayout_gameInfo.add(datePlayed_textField);

        // wrap up update player fields
        gridLayout_updatePlayer.add(updatePlayerById_label);
        gridLayout_updatePlayer.add(updatePlayerById_textField);
        gridLayout_updatePlayer.add(update_button);

        // wrap up personal info
        verticalLayout_presonalInfo.add(playerInformation_label);
        verticalLayout_presonalInfo.add(gridLayout_personalInfo);

        // wrap up game info
        verticalLayout_gameInfo.add(gameInformation_label);
        verticalLayout_gameInfo.add(gridLayout_gameInfo);

        // wrap up update player field with the game info fields
        wrapUpdatePlayerGameInfo.add(gridLayout_updatePlayer);
        wrapUpdatePlayerGameInfo.add(Box.createVerticalStrut(100));
        wrapUpdatePlayerGameInfo.add(verticalLayout_gameInfo);

        // wrap up player info and game info and update fields
        wrapPlayerInfoGameInfo.add(verticalLayout_presonalInfo);
        wrapPlayerInfoGameInfo.add(Box.createHorizontalStrut(10));
        wrapPlayerInfoGameInfo.add(Box.createHorizontalGlue());
        wrapPlayerInfoGameInfo.add(wrapUpdatePlayerGameInfo);

        // wrap create player button and display all player
        wrapButtons.add(createPlayer_button);
        wrapButtons.add(displayAllPlayer_button);

        // wrap info fields and button
        wrapInfoButtons.add(wrapPlayerInfoGameInfo);
        wrapInfoButtons.add(Box.createHorizontalStrut(100));
        wrapInfoButtons.add(wrapButtons);

        // Adding Action Listener
        createPlayer_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
        update_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        displayAllPlayer_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DisplayAllPlayer(jdbcUrl, username, password);
            }
        });

        // Adding Border to wrapInfoButtons
        JPanel borderPanel = new JPanel(new BorderLayout());
        int topBorder = 10;
        int leftBorder = 20;
        int bottomBorder = 10;
        int rightBorder = 20;
        borderPanel.setBorder(BorderFactory.createEmptyBorder(topBorder, leftBorder, bottomBorder, rightBorder));
        borderPanel.add(wrapInfoButtons);

        add(borderPanel);
    }

    public void addPlayer() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String firstName = firstName_textField.getText();
            String lastName = lastName_textField.getText();
            String address = address_textField.getText();
            String province = province_textField.getText();
            String postalCode = postalCode_textField.getText();
            String phoneNumber = phoneNumber_textField.getText();
            String gameTitle = gameTitle_textField.getText();
            String gameScore = gameScore_textField.getText(); // Fetching game score
            int gameId = 0; // Initializing gameId variable

            // Check if first name and last name fields are empty
            if (firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the first name and last name.");
                return;
            }

            // Inserting Game Title and fetching the incremented Game ID
            if (!gameTitle.isEmpty()) {
                String gameInsertQuery = "INSERT INTO Game (game_title) VALUES (?)";
                preparedStatement = connection.prepareStatement(gameInsertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, gameTitle);
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    gameId = generatedKeys.getInt(1); // Fetching the generated Game ID
                }
            }

            String sql = "INSERT INTO Player (first_name, last_name, address, province, postal_code, phone_number) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, province);
            preparedStatement.setString(5, postalCode);
            preparedStatement.setString(6, phoneNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Player added successfully.");

                // Add the player and game information to PlayerAndGame table
                if (gameId > 0 && !gameScore.isEmpty()) {
                    String playerAndGameQuery = "INSERT INTO PlayerAndGame (game_id, player_id, playing_date, score) " +
                                                "VALUES (?, LAST_INSERT_ID(), CURDATE(), ?)";
                    preparedStatement = connection.prepareStatement(playerAndGameQuery);
                    preparedStatement.setInt(1, gameId);
                    preparedStatement.setInt(2, Integer.parseInt(gameScore)); // Setting the game score

                    preparedStatement.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add player.");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String playerId = updatePlayerById_textField.getText();
            if (playerId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid player ID for updating.");
                return;
            }

            String gameTitle = gameTitle_textField.getText();
            if (gameTitle.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the game title.");
                return;
            }

            String gameScore = gameScore_textField.getText();
            if (gameScore.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in the game score.");
                return;
            }

            // Get today's date
            LocalDate today = LocalDate.now();
            String datePlayed = today.toString(); // Format today's date as a string

            String query = "UPDATE PlayerAndGame SET score = ?, playing_date = ? WHERE player_id = ? AND game_id IN " +
                            "(SELECT game_id FROM Game WHERE game_title = ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, Integer.parseInt(gameScore));
            preparedStatement.setString(2, datePlayed);
            preparedStatement.setInt(3, Integer.parseInt(playerId));
            preparedStatement.setString(4, gameTitle);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Player score and playing date updated successfully for the specified game title.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update player score and playing date. Game title may not match.");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PlayerRegistration().setVisible(true);
            }
        });

    }
}
