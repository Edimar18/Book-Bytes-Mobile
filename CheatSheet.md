# Library Management System - OOP Concepts Cheat Sheet

## Classes Overview
| Class Name | Purpose | File Location |
|------------|---------|---------------|
| `App` | Main application class with UI components | `src/App.java` |
| `Book` | Book entity with properties and methods | `src/Book.java` |
| `User` | User entity for authentication | `src/User.java` |

## OOP Concepts Implementation

### 1. Encapsulation
- **Location**: All entity classes (`Book.java`, `User.java`)
- **Examples**:
  - Private fields with public getters/setters
  - `Book` class: Lines 2-6 (private fields)
  - `User` class: Lines 2-3 (private fields)

### 2. Inheritance
- **Location**: `App.java`
- **Examples**:
  - Anonymous inner classes extending JPanel (Lines 180-190)
  - Custom panel implementations with gradient backgrounds

### 3. Polymorphism
- **Method Overriding**:
  - `paintComponent()` method in custom panels (Lines 180-190)
  - Multiple implementations of MouseAdapter (Lines 450-460)
- **Method Overloading**:
  - Different versions of `createStyledButton()` methods

### 4. Abstraction
- Interface implementations (ActionListener, MouseAdapter)
- Event handling abstractions
- CardLayout for screen management

## Key Features & Implementations

### 1. File Handling
- **Location**: `App.java` (Lines 100-120)
- **Purpose**: Logo image loading
- **Implementation**: Using `ImageIO` and `File` classes

### 2. Collections
- **ArrayList Usage**: 
  - `users`: Stores User objects
  - `books`: Stores Book objects
- **Location**: `App.java` (Lines 20-21)

### 3. Event Handling
- Mouse Events: Lines 450-470
- Button Actions: Lines 500-520
- Focus Listeners: Lines 800-820

### 4. GUI Components
| Component | Purpose | Location |
|-----------|---------|----------|
| CardLayout | Screen navigation | Lines 55-60 |
| JPanel | Container components | Throughout |
| JButton | User interactions | Lines 700-750 |
| JTextField | Input fields | Lines 780-800 |

### 5. Design Patterns
- **Singleton Pattern**: Main application window
- **Observer Pattern**: Event listeners implementation
- **Factory Pattern**: Button creation methods

## Important Methods

### Navigation System
```java
private static void createAndShowGUI()  // Main UI setup
private static JPanel createNavigationBar()  // Bottom navigation
private static JButton createNavButton()  // Navigation buttons
```

### State Management
```java
private static void updateDashboardStats()  // Updates book counts
private static void forceRefreshDashboard()  // Refreshes UI
```

## Additional OOP Features

1. **Static Members**
   - Constants (Colors)
   - Utility methods
   - Shared resources

2. **Inner Classes**
   - Anonymous classes for custom components
   - Event listeners

3. **Interface Implementation**
   - ActionListener
   - MouseAdapter
   - FocusListener

## Testing & Debugging Tips
1. Check borrowed books count: `books.stream().filter(Book::isBorrowed).count()`
2. Verify user authentication: `users.stream().anyMatch()`
3. UI refresh: `mainPanel.revalidate()` and `repaint()`

## Common Issues & Solutions
1. **UI Not Updating**: Call `forceRefreshDashboard()`
2. **Navigation Issues**: Check CardLayout names
3. **State Management**: Verify book.isBorrowed() status

## Code Organization
- **Constants**: Top of App.java
- **UI Components**: Grouped by screen
- **Utility Methods**: Bottom of file
- **Event Handlers**: With respective components 