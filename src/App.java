import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;

public class App {
    // Enhanced color scheme
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);  // Indigo
    private static final Color ACCENT_COLOR = new Color(255, 64, 129);  // Pink
    private static final Color BACKGROUND_COLOR = new Color(237, 241, 247);  // Light blue-gray
    private static final Color TEXT_COLOR = new Color(33, 33, 33);  // Almost black
    private static final Color SECONDARY_TEXT_COLOR = new Color(117, 117, 117);  // Dark gray
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);  // Green
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);  // White
    private static final Color HOVER_COLOR = new Color(83, 109, 254);  // Lighter Indigo
    
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Book> books = new ArrayList<>();
    private static User currentUser = null;
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public static void main(String[] args) {
        // Add some sample data
        initializeSampleData();
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show GUI
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void initializeSampleData() {
        // Add sample users
        users.add(new User("edimar", "edimar"));
        
        // Add more sample books with varied titles and authors
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "1951", "Classic novel about teenage alienation"));
        books.add(new Book("1984", "George Orwell", "1949", "Dystopian social science fiction"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "1960", "Novel about justice and innocence"));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "1813", "Classic romance novel"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Story of the American Dream"));
        books.add(new Book("Lord of the Rings", "J.R.R. Tolkien", "1954", "Epic fantasy adventure"));
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "1997", "Magical coming of age story"));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "1937", "Fantasy adventure novel"));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", "1953", "Dystopian novel about censorship"));
        books.add(new Book("The Alchemist", "Paulo Coelho", "1988", "Philosophical adventure tale"));
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Library Explorer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 700);  // Phone-like dimensions
        mainFrame.setResizable(false); // Fixed size like a phone
        
        // Center the frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 400) / 2;
        int y = (screenSize.height - 700) / 2;
        mainFrame.setLocation(x, y);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Create screens
        JPanel launchScreen = createLaunchScreen();
        JPanel loginScreen = createLoginScreen();
        JPanel dashboardScreen = createDashboardScreen();
        JPanel bookListScreen = createBookListScreen();
        JPanel borrowingScreen = createBorrowingScreen();

        // Add screens to main panel
        mainPanel.add(launchScreen, "launch");
        mainPanel.add(loginScreen, "login");
        mainPanel.add(dashboardScreen, "dashboard");
        mainPanel.add(bookListScreen, "booklist");
        mainPanel.add(borrowingScreen, "borrowing");

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        
        cardLayout.show(mainPanel, "launch");
    }

    private static JPanel createLaunchScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Logo
        try {
            // Create image directory if it doesn't exist
            File imageDir = new File("image");
            if (!imageDir.exists()) {
                imageDir.mkdir();
            }
            
            // Check if logo exists, if not create a gradient logo
            File logoFile = new File("./src/image/logo.png");
            System.out.println(logoFile);
            if (logoFile.exists()) {
                Image logoImage = ImageIO.read(logoFile);
                System.out.println("sucess");
                JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                panel.add(logoLabel, gbc);
            } else {
                JPanel logo = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), getHeight(), ACCENT_COLOR);
                        g2d.setPaint(gradient);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    }
                };
                logo.setPreferredSize(new Dimension(120, 120));
                panel.add(logo, gbc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Library Explorer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        panel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Your Gateway to Knowledge");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(SECONDARY_TEXT_COLOR);
        panel.add(subtitleLabel, gbc);

        JButton getStartedButton = createStyledButton("Get Started");
        getStartedButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        panel.add(getStartedButton, gbc);

        return panel;
    }

    private static JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.weightx = 1.0;
        
        // Container panel for content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        JLabel titleLabel = new JLabel("Access Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        contentPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Access your library account", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(SECONDARY_TEXT_COLOR);
        contentPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(15, 30, 8, 30);
        JTextField emailField = createStyledTextField("Email");
        contentPanel.add(emailField, gbc);

        gbc.insets = new Insets(8, 30, 15, 30);
        JPasswordField passwordField = createStyledPasswordField("Password");
        contentPanel.add(passwordField, gbc);

        JButton loginButton = createStyledButton("Log In");
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            boolean authenticated = users.stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
            
            if (authenticated) {
                currentUser = users.stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .get();
                cardLayout.show(mainPanel, "dashboard");
            } else {
                showErrorDialog("Invalid credentials!");
            }
        });
        contentPanel.add(loginButton, gbc);

        // Add content panel to main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.NONE;
        mainGbc.anchor = GridBagConstraints.CENTER;
        panel.add(contentPanel, mainGbc);

        return panel;
    }

    private static JPanel createDashboardScreen() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setName("dashboard");
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header with gradient
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, ACCENT_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBackground(CARD_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel dashboardLabel = new JLabel("Dashboard");
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        dashboardLabel.setForeground(Color.WHITE);
        headerPanel.add(dashboardLabel, BorderLayout.WEST);
        
        JTextField searchField = createStyledTextField("Search books...");
        headerPanel.add(searchField, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Calculate current borrowed books count
        int borrowedCount = (int) books.stream().filter(Book::isBorrowed).count();
        
        JPanel borrowedPanel = createStatsPanel("Borrowed Books", String.valueOf(borrowedCount));
        borrowedPanel.setName("borrowed");
        JPanel favoritesPanel = createStatsPanel("Favorites", "0");
        favoritesPanel.setName("favorites");
        
        contentPanel.add(borrowedPanel);
        contentPanel.add(favoritesPanel);
        
        // Wrap content in a panel with padding
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(BACKGROUND_COLOR);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        contentWrapper.add(contentPanel, BorderLayout.NORTH);
        
        panel.add(contentWrapper, BorderLayout.CENTER);
        panel.add(createNavigationBar(), BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createBookListScreen() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header with gradient
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, ACCENT_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JButton backButton = createIconButton("←");
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        headerPanel.add(backButton, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("Available Books");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);

        // Book list
        JPanel bookListPanel = new JPanel();
        bookListPanel.setLayout(new BoxLayout(bookListPanel, BoxLayout.Y_AXIS));
        bookListPanel.setBackground(Color.WHITE);
        bookListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Show only available books
        boolean hasAvailableBooks = false;
        for (Book book : books) {
            if (!book.isBorrowed()) {
                hasAvailableBooks = true;
                bookListPanel.add(createBookListItem(book));
                bookListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        if (!hasAvailableBooks) {
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(Color.WHITE);
            
            JLabel emptyLabel = new JLabel("No available books at the moment");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            emptyLabel.setForeground(TEXT_COLOR);
            
            emptyPanel.add(emptyLabel);
            bookListPanel.add(emptyPanel);
        }

        JScrollPane scrollPane = new JScrollPane(bookListPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(createNavigationBar(), BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createBorrowingScreen() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setName("borrowing");
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, ACCENT_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JButton backButton = createIconButton("←");
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        headerPanel.add(backButton, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("My Borrowed Books");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);

        // Borrowed books list
        JPanel borrowedListPanel = new JPanel();
        borrowedListPanel.setLayout(new BoxLayout(borrowedListPanel, BoxLayout.Y_AXIS));
        borrowedListPanel.setBackground(Color.WHITE);
        borrowedListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add borrowed books
        updateBorrowedBooksList(borrowedListPanel);

        JScrollPane scrollPane = new JScrollPane(borrowedListPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(createNavigationBar(), BorderLayout.SOUTH);

        return panel;
    }

    private static void updateBorrowedBooksList(JPanel borrowedListPanel) {
        borrowedListPanel.removeAll();
        boolean hasBorrowedBooks = false;

        for (Book book : books) {
            if (book.isBorrowed()) {
                hasBorrowedBooks = true;
                borrowedListPanel.add(createBorrowedBookItem(book));
                borrowedListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        if (!hasBorrowedBooks) {
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(Color.WHITE);
            
            JLabel emptyLabel = new JLabel("No borrowed books yet");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            emptyLabel.setForeground(SECONDARY_TEXT_COLOR);
            
            emptyPanel.add(emptyLabel);
            borrowedListPanel.add(emptyPanel);
        }

        borrowedListPanel.revalidate();
        borrowedListPanel.repaint();
    }

    private static JPanel createBorrowedBookItem(Book book) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 247, 250));
            }
            public void mouseExited(MouseEvent e) {
                panel.setBackground(CARD_BACKGROUND);
            }
        });
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(panel.getBackground());
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        
        JLabel authorLabel = new JLabel(book.getAuthor());
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        authorLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        JLabel yearLabel = new JLabel(book.getPublicationYear());
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        yearLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(authorLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        textPanel.add(yearLabel);
        
        panel.add(textPanel, BorderLayout.CENTER);
        
        JButton returnButton = createStyledButton("Return");
        returnButton.setBackground(SUCCESS_COLOR);
        returnButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                returnButton.setBackground(SUCCESS_COLOR.brighter());
            }
            public void mouseExited(MouseEvent e) {
                returnButton.setBackground(SUCCESS_COLOR);
            }
        });
        returnButton.addActionListener(e -> {
            book.setBorrowed(false);
            updateBorrowedBooksList((JPanel)panel.getParent());
            updateDashboardStats();
        });
        panel.add(returnButton, BorderLayout.EAST);
        
        return panel;
    }

    private static JPanel createStatsPanel(String title, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(170, 100)); // Fixed size for mobile view
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 247, 250));
            }
            public void mouseExited(MouseEvent e) {
                panel.setBackground(CARD_BACKGROUND);
            }
        });
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(SECONDARY_TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Slightly smaller for mobile
        valueLabel.setForeground(TEXT_COLOR);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(valueLabel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    private static JPanel createNavigationBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 4, 5, 0));
        navBar.setBackground(Color.WHITE);
        navBar.setPreferredSize(new Dimension(400, 60)); // Fixed height for mobile-style navbar
        navBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0, 0, 0, 20)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        navBar.add(createNavButton("Home", true));
        navBar.add(createNavButton("Books", false));
        navBar.add(createNavButton("Borrowing", false));
        navBar.add(createNavButton("Notifications", false));
        
        return navBar;
    }

    private static JPanel createBookListItem(Book book) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setName("booklist");
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 247, 250));
            }
            public void mouseExited(MouseEvent e) {
                panel.setBackground(CARD_BACKGROUND);
            }
        });
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(panel.getBackground());
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        
        JLabel authorLabel = new JLabel(book.getAuthor());
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        authorLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        JLabel yearLabel = new JLabel(book.getPublicationYear());
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        yearLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(authorLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        textPanel.add(yearLabel);
        
        panel.add(textPanel, BorderLayout.CENTER);
        
        JButton borrowButton = createStyledButton(book.isBorrowed() ? "Return" : "Borrow");
        borrowButton.setBackground(book.isBorrowed() ? SUCCESS_COLOR : PRIMARY_COLOR);
        borrowButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                borrowButton.setBackground(book.isBorrowed() ? 
                    SUCCESS_COLOR.brighter() : HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                borrowButton.setBackground(book.isBorrowed() ? 
                    SUCCESS_COLOR : PRIMARY_COLOR);
            }
        });
        borrowButton.addActionListener(e -> {
            book.setBorrowed(!book.isBorrowed());
            borrowButton.setText(book.isBorrowed() ? "Return" : "Borrow");
            borrowButton.setBackground(book.isBorrowed() ? SUCCESS_COLOR : PRIMARY_COLOR);
            forceRefreshDashboard();
        });
        panel.add(borrowButton, BorderLayout.EAST);
        
        return panel;
    }

    // Utility methods for creating styled components
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16)); // Smaller padding for mobile
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(HOVER_COLOR);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(PRIMARY_COLOR);
                }
            }
        });
        
        return button;
    }

    private static JButton createIconButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static JButton createNavButton(String text, boolean isHome) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(0, 3));
        
        // Create icon label (you can replace this with actual icons)
        JLabel iconLabel = new JLabel(getIconForNav(text), SwingConstants.CENTER);
        
        // Create text label
        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textLabel.setForeground(isHome ? PRIMARY_COLOR : SECONDARY_TEXT_COLOR);
        
        button.add(iconLabel, BorderLayout.CENTER);
        button.add(textLabel, BorderLayout.SOUTH);
        
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                textLabel.setForeground(PRIMARY_COLOR);
                iconLabel.setForeground(PRIMARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                if (!isHome) {
                    textLabel.setForeground(SECONDARY_TEXT_COLOR);
                    iconLabel.setForeground(SECONDARY_TEXT_COLOR);
                }
            }
        });
        
        // Add action listeners
        if (text.equals("Home")) {
            button.addActionListener(e -> {
                forceRefreshDashboard();
                cardLayout.show(mainPanel, "dashboard");
            });
        } else if (text.equals("Books")) {
            button.addActionListener(e -> cardLayout.show(mainPanel, "booklist"));
        } else if (text.equals("Borrowing")) {
            button.addActionListener(e -> {
                Component[] components = mainPanel.getComponents();
                for (Component comp : components) {
                    if (comp instanceof JPanel && "borrowing".equals(comp.getName())) {
                        mainPanel.remove(comp);
                    }
                }
                mainPanel.add(createBorrowingScreen(), "borrowing");
                mainPanel.revalidate();
                mainPanel.repaint();
                cardLayout.show(mainPanel, "borrowing");
            });
        }
        
        return button;
    }

    private static String getIconForNav(String text) {
        switch (text) {
            case "Home": return "🏠";
            case "Books": return "📚";
            case "Borrowing": return "📖";
            case "Notifications": return "🔔";
            default: return "•";
        }
    }

    private static JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(TEXT_COLOR);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Add placeholder
        textField.setText(placeholder);
        textField.setForeground(SECONDARY_TEXT_COLOR);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(SECONDARY_TEXT_COLOR);
                }
            }
        });
        
        return textField;
    }

    private static JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return passwordField;
    }

    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
            mainFrame,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private static void updateDashboardStats() {
        int borrowedCount = (int) books.stream().filter(Book::isBorrowed).count();
        
        // Find and update the dashboard panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                updatePanelStats((JPanel) comp, borrowedCount);
            }
        }
    }

    private static void updatePanelStats(JPanel panel, int borrowedCount) {
        // Recursively search through all components
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel innerPanel = (JPanel) comp;
                if ("borrowed".equals(innerPanel.getName())) {
                    // Found the borrowed books stats panel, update its value
                    for (Component innerComp : innerPanel.getComponents()) {
                        if (innerComp instanceof JLabel) {
                            JLabel label = (JLabel) innerComp;
                            if (label.getFont().getSize() == 32) {
                                label.setText(String.valueOf(borrowedCount));
                            }
                        }
                    }
                } else {
                    // Continue searching in nested panels
                    updatePanelStats(innerPanel, borrowedCount);
                }
            }
        }
    }

    private static void forceRefreshDashboard() {
        EventQueue.invokeLater(() -> {
                    // Remove old dashboard
            for (Component comp : mainPanel.getComponents()) {
                        if (comp instanceof JPanel && "dashboard".equals(comp.getName())) {
                            mainPanel.remove(comp);
            }
        }
        
            // Create and add new dashboard
            JPanel newDashboard = createDashboardScreen();
            mainPanel.add(newDashboard, "dashboard");
                mainPanel.revalidate();
                mainPanel.repaint();
        });
    }
}
